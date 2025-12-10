package ui;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.ResponseException;
import model.GameDataShort;
import websocket.HttpTalker;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;
import websocket.messages.ServerMessage;

import java.util.*;

import static ui.EscapeSequences.*;


public class Repl implements NotificationHandler  {
    private String visitorName = null;
    private final WebSocketFacade ws;
    private State state = State.SIGNEDOUT;
    private HttpTalker client = new HttpTalker();

    private String authToken;
    private String usernameLoggedInAs;
    private ChessBoard currentChessBoard;
    private ChessGame.TeamColor currentPlayerColor = ChessGame.TeamColor.NONE;
    private String currentGameID;
    private GameDataShort[] currentGameListArray = new GameDataShort[100];

    private boolean playingNotObserving = false;

    private boolean resigning = false;


    private final String SERVER_HOST = "localhost";
    private final int SERVER_PORT = 8080;

    // private Chess_Service service = new Chess_Service();


    public Repl() throws ResponseException
    {
        // server = new ServerFacade(serverUrl);
        ws = new WebSocketFacade("http://" + SERVER_HOST + ":" + SERVER_PORT, this);
        client = new HttpTalker();
        authToken = "";
        usernameLoggedInAs = "";
    }

//    public Repl(String serverUrl) {
//        client = new HttpTalker();
//        ws = new WebSocketFacade(serverUrl, this);
//    }

    public void run() {
        System.out.println(LOGO + " Welcome to chess! Sign in to start.");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }


//    public void notify(Notification notification) {
//        System.out.println(SET_TEXT_COLOR_RED + notification.message());
//        printPrompt();
//    }

    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + SET_TEXT_COLOR_GREEN);
    }


    public String eval(String input) throws Exception
    {

        String[] tokens = input.toLowerCase().split(" ");
        String cmd = (tokens.length > 0) ? tokens[0] : "help";
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        String input1 = (tokens.length > 1) ? tokens[1] : "";
//        System.out.print(input1 + "\n");
        String input2 = (tokens.length > 2) ? tokens[2] : "";
        // System.out.print(input2 + "\n");
        String input3 = (tokens.length > 3) ? tokens[3] : "";
        // System.out.print(input3 + "\n");


        return switch (cmd) {
            // case "signin" -> signIn(params);
            // case "rescue" -> rescuePet(params);
            case "list" -> listGames();
            case "l" -> listGames();
            case "login" -> login(input1, input2);
            case "register" -> register(input1, input2, input3);
            case "reg" -> register(input1, input2, input3);
            case "logout" -> logout();
            case "lo" -> logout();
            case "out" -> logout();
            case "create" -> newGame(input1);
            case "c" -> newGame(input1);
            case "new" -> newGame(input1);
            case "n" -> newGame(input1);
            case "join" -> joinGame(input1, input2);
            case "j" -> joinGame(input1, input2);
            case "observe" -> observeGame(input1);
            case "o" -> observeGame(input1);
            case "view" -> viewGame(input1);
            case "v" -> viewGame(input1);
            case "play" -> joinGame(input1, input2);
            case "p" -> joinGame(input1, input2);
            case "redraw" -> redrawBoard();
            case "reload" -> redrawBoard();
            case "r" -> redrawBoard();
            case "leave" -> leave();
            case "resign" -> resign();
            case "legal" -> getLegalMoves(input1);
            case "valid" -> getLegalMoves(input1);
            case "move" -> movePiece(input1, input2, input3);
            case "m" -> movePiece(input1, input2, input3);
            // case "adopt" -> adoptPet(params);
            // case "adoptall" -> adoptAllPets();
            case "quit" -> "quit";
            case "q" -> "quit";
            case "help" -> help();
            case "h" -> help();
            default -> help();
        };


    }


//
//    """
//    Options:
//
//    else if (state == State.INGAME) {
//        return """
//
//                "move <LOCAL_START> <LOCAL_END>" or "m <LOCAL_START> <LOCAL_END>" - Moves a piece.
//
//
//    """;




//    public String signIn(String... params) {
//        if (params.length >= 1) {
//            state = State.SIGNEDIN;
//            visitorName = String.join("-", params);
//            ws.enterPetShop(visitorName);
//            return String.format("You signed in as %s.", visitorName);
//        }
//    }


    private String playGame(String gameID)
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if in game
        if (isInGame()) { return "You are already in a game!"; }

        // Checks to see if game exists
        if (gameID.isBlank()) { return gameID + " is not a valid Game ID"; }

        try
        {
            Integer.parseInt(gameID);
        }
        catch (Exception ex)
        {
            return gameID + " is not a number";
        }


//        if (currentGameListArray[Integer.parseInt(gameID)].whiteUsername().isBlank())
//        {
//            return gameID + " doesn't have enough players yet.";
//        }
//        if (currentGameListArray[Integer.parseInt(gameID)].blackUsername().isBlank())
//        {
//            return gameID + " doesn't have enough players yet.";
//        }

        // System.out.print("White Player is " + currentGameListArray[Integer.parseInt(gameID)].whiteUsername() + "\n");
        // System.out.print("Black Player is " + currentGameListArray[Integer.parseInt(gameID)].blackUsername() + "\n");

        if (currentGameListArray[Integer.parseInt(gameID)].whiteUsername().equals(usernameLoggedInAs))
        {
            currentPlayerColor = ChessGame.TeamColor.WHITE;
        } else if (currentGameListArray[Integer.parseInt(gameID)].blackUsername().equals(usernameLoggedInAs))
        {
            currentPlayerColor = ChessGame.TeamColor.BLACK;
        } else {
            return "You are not in that game.";
        }

        state = State.INGAME;
        try {
            currentGameID = gameID;
            playingNotObserving = true;
            ws.connect(authToken, Integer.parseInt(gameID));
            return String.format("You signed in as %s for game #%s.\n", usernameLoggedInAs, gameID);

        }
        catch (Exception e)
        {
            state = State.SIGNEDIN;
            return "ERROR: Cannot play that game " + e;
        }

    }

    private String observeGame(String gameID)
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if in game
        if (isInGame()) { return "You are already in a game!"; }

        // Checks to see if game exists
        if (gameID.isBlank()) { return gameID + " is not a valid Game ID"; }

        try
        {
            Integer.parseInt(gameID);
        }
        catch (Exception ex)
        {
            return gameID + " is not a number";
        }

        if (Integer.parseInt(gameID) < 1 || Integer.parseInt(gameID) > 90)
        {
            return gameID + " is not a valid game.";
        }

        if (currentGameListArray[Integer.parseInt(gameID)] == null)
        {
            return gameID + " is not a valid game.";
        }


//        if (currentGameListArray[Integer.parseInt(gameID)].whiteUsername().isBlank())
//        {
//            return gameID + " doesn't have enough players yet.";
//        }
//        if (currentGameListArray[Integer.parseInt(gameID)].blackUsername().isBlank())
//        {
//            return gameID + " doesn't have enough players yet.";
//        }

        // System.out.print("White Player is " + currentGameListArray[Integer.parseInt(gameID)].whiteUsername() + "\n");
        // System.out.print("Black Player is " + currentGameListArray[Integer.parseInt(gameID)].blackUsername() + "\n");

        currentPlayerColor = ChessGame.TeamColor.WHITE;
        state = State.INGAME;

        try {
            currentGameID = gameID;
            playingNotObserving = false;
            ws.connect(authToken, Integer.parseInt(gameID));
            return String.format("You signed in as %s for game #%s.\n", usernameLoggedInAs, gameID);

        }
        catch (Exception e)
        {
            state = State.SIGNEDIN;
            return "ERROR: Cannot play that game " + e;
        }

    }

    private String redrawBoard()
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if not in game
        if (!isInGame()) { return "You need to be in a game to do that!"; }

        return printBoard(currentChessBoard, currentPlayerColor);
    }


    private String leave()
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if not in game
        if (!isInGame()) { return "You need to be in a game to do that!"; }

        try {
            ws.leaveGame(authToken, Integer.parseInt(currentGameID));
            currentGameID = "";
            state = State.SIGNEDIN;
            return String.format("You signed out as %s for game #%s.\n", usernameLoggedInAs, currentGameID);

        }
        catch (Exception e)
        {
            state = State.SIGNEDIN;
            return "ERROR: Cannot leave that game " + e;
        }
    }

    private String resign()
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if not in game
        if (!isInGame()) { return "You need to be in a game to do that!"; }

        // Returns an error if they are only observing
        if (!playingNotObserving) { return "You are only watching the game";}

        // Double checks if they've typed resign twice
        if (resigning) {

            try {
                ws.resign(authToken, Integer.parseInt(currentGameID));
                resigning = false;
                return String.format("Okay, you have resigned! %s for game #%s.\n", usernameLoggedInAs, currentGameID);
            }
            catch (Exception e)
            {
                state = State.SIGNEDIN;
                return "ERROR: Cannot resign that game " + e;
            }
        }

        resigning = true;
        return "Type \"resign\" again to resign";


    }


    private String movePiece(String localStart, String localEnd, String localPromotion)
    {

        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if not in game
        if (!isInGame()) { return "You need to be in a game to do that!"; }

        // Returns an error if they are only observing
        if (!playingNotObserving) { return "You are only watching the game";}

        ChessPosition positionStart;
        ChessPosition positionEnd;
        try
        {
            positionStart = validatePosition(localStart);
        }
        catch (Exception ex)
        {
            return localStart + "is not a valid position.";
        }

        try
        {
            positionEnd = validatePosition(localEnd);
        }
        catch (Exception ex)
        {
            return localEnd + "is not a valid position.";
        }

        if (currentChessBoard.getPiece(positionStart) == null)
        {
            return "There is no piece at " + localStart;
        }

        ChessPiece.PieceType promotion = null;

        // Check if pawn is being promoted
        if (currentChessBoard.getPiece(positionStart).getPieceType() == ChessPiece.PieceType.PAWN)
        {
            if (positionEnd.getRow() == 1 || positionEnd.getRow() == 8)
            {
                if (localPromotion.isBlank())
                {
                    return "You need to add pawn promotion if you move to " + localEnd;
                } else if (localPromotion.equals("queen"))
                {
                    promotion = ChessPiece.PieceType.QUEEN;
                } else if (localPromotion.equals("rook"))
                {
                    promotion = ChessPiece.PieceType.ROOK;
                } else if (localPromotion.equals("bishop"))
                {
                    promotion = ChessPiece.PieceType.BISHOP;
                } else if (localPromotion.equals("knight"))
                {
                    promotion = ChessPiece.PieceType.KNIGHT;
                } else {
                    return localPromotion + " is not a piece type.";
                }
            }
        }



        try
        {
            ws.makeMove(authToken, Integer.parseInt(currentGameID), new ChessMove(positionStart, positionEnd, promotion));
        }
        catch (Exception ex)
        {
            return "Can't do that move";
        }

        return String.format("Tried to move the %s from %s to %s.", currentChessBoard.getPiece(positionStart).getPieceType(), localStart, localEnd);


    }

    private ChessPosition validatePosition(String location) throws Exception
    {
        if (location.length() > 2)
        {
            throw new Exception("Invalid Position");
        }

        int row;
        int col;


        if (location.endsWith("1") || location.endsWith("2") || location.endsWith("3") || location.endsWith("4") || location.endsWith("5") || location.endsWith("6") || location.endsWith("7") || location.endsWith("8"))
        {
            row = Integer.parseInt(location.substring(1,2));
            // System.out.print("Row is " + row + "\n");
        } else
        {
            throw new Exception("Invalid Position");
        }

        if (location.startsWith("a"))
        {
            col = 1;
        }
        else if (location.startsWith("b"))
        {
            col = 2;
        }
        else if (location.startsWith("c"))
        {
            col = 3;
        }
        else if (location.startsWith("d"))
        {
            col = 4;
        }
        else if (location.startsWith("e"))
        {
            col = 5;
        }
        else if (location.startsWith("f"))
        {
            col = 6;
        }
        else if (location.startsWith("g"))
        {
            col = 7;
        }
        else if (location.startsWith("h"))
        {
            col = 8;
        } else
        {
            throw new Exception("Invalid Position");
        }

        return new ChessPosition(row, col);
    }


    private String getLegalMoves(String location)
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if not in game
        if (!isInGame()) { return "You need to be in a game to do that!"; }

        ChessPosition squareFindingLegal;

        try
        {
            squareFindingLegal = validatePosition(location);
        }
        catch (Exception ex)
        {
            return location + " is not a valid position.";
        }


        ChessGame chessGameTemp = new ChessGame();
        chessGameTemp.setBoard(currentChessBoard);
        if (currentChessBoard.getPiece(squareFindingLegal) == null)
        {
            return "There is no piece at " + location;
        }
        chessGameTemp.setTeamTurn(currentChessBoard.getPiece(squareFindingLegal).getTeamColor());

        Collection<ChessMove> validMoves = new HashSet<ChessMove>();
        validMoves = chessGameTemp.validMoves(squareFindingLegal);
        // ("Here are the valid moves " + validMoves + "\n");



        return printBoardValid(currentChessBoard, currentPlayerColor, validMoves);
    }


    private String printBoardValid(ChessBoard chessBoard, ChessGame.TeamColor activePlayer, Collection<ChessMove> validMoves)
    {

        try {
            String printBoardOutput = "\n";

            Boolean squareColorWhite = true;

            // System.out.print("Printing Board Now for the " + activePlayer + " player.\n");

            if (activePlayer.equals(ChessGame.TeamColor.BLACK))
            {
                printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003\u2003\u2003h\u2003 g\u2003 f\u2003 e\u2003 d\u2003 c\u2003 b\u2003 a\u2003\u2003\u2003" + RESET + "\n");
                for (int row = 1; row <= 8; row++ )
                {
                    // Move to the next row
                    printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + " " + (row) + "\u2003");
                    for (int col = 8; col >= 1; col-- )
                    {
                        // System.out.print("Current Board: row = " + row + ", col = " + col + ", piece = " + chessBoard.getPiece(row, col) + "\n");

                        // Move to the next col



                        // Set Background Color
                        if (squareColorWhite)
                        {
                            // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_WHITE);
                            printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_PEACH);
                        }
                        else
                        {
                            // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_GREEN);
                            printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_BROWN);
                        }

                        for (ChessMove move : validMoves)
                        {
                            // System.out.print("Checking " + move + "\n");
                            if (move.getEndPosition().getColumn() == col && move.getEndPosition().getRow() == row)
                            {
                                // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_YELLOW);
                                printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_GREEN);
                            }
                        }


                        printBoardOutput = printBoardOutput.concat(printPiece(chessBoard.getPiece(row, col)));




                        squareColorWhite = !squareColorWhite;
                    }
                    squareColorWhite = !squareColorWhite;
                    printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003" + (row) + " " + RESET + "\n");
                }
                printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003\u2003\u2003h\u2003 g\u2003 f\u2003 e\u2003 d\u2003 c\u2003 b\u2003 a\u2003\u2003\u2003" + RESET + "\n");
            }
            else if (activePlayer.equals(ChessGame.TeamColor.WHITE))
            {
                printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003\u2003\u2003a\u2003 b\u2003 c\u2003 d\u2003 e\u2003 f\u2003 g\u2003 h\u2003\u2003\u2003" + RESET + "\n");
                for (int row = 8; row >= 1; row-- )
                {
                    // Move to the next row
                    printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + " " + (row) + "\u2003");
                    for (int col = 1; col <= 8; col++ )
                    {
                        // System.out.print("Current Board: row = " + row + ", col = " + col + ", piece = " + chessBoard.getPiece(row, col) + "\n");

                        // Move to the next col

                        // Set Backgroud Color
                        if (squareColorWhite)
                        {
                            // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_WHITE);
                            printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_PEACH);
                        }
                        else
                        {
                            // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_GREEN);
                            printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_BROWN);
                        }

                        for (ChessMove move : validMoves)
                        {
                            // System.out.print("Checking " + move + "\n");
                            if (move.getEndPosition().getColumn() == col && move.getEndPosition().getRow() == row)
                            {
                                // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_YELLOW);
                                printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_GREEN);
                            }
                        }

                        printBoardOutput = printBoardOutput.concat(printPiece(chessBoard.getPiece(row, col)));


                        squareColorWhite = !squareColorWhite;
                    }
                    squareColorWhite = !squareColorWhite;
                    printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003" + (row) + " " + RESET + "\n");
                }
                printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003\u2003\u2003a\u2003 b\u2003 c\u2003 d\u2003 e\u2003 f\u2003 g\u2003 h\u2003\u2003\u2003" + RESET + "\n");
            }


            // return gameList;
            return printBoardOutput;

        } catch (Exception e) {
            // System.out.print("\n" + e + "\n");
            return "Error " + e;
        }




    }

    public String listGames()
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if in game
        if (isInGame()) { return "You are in a game!"; }

        try
        {
            String gameList = client.get("/game", authToken);
            // System.out.print(client.toString() + "\n");
            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());

            String[] gameListSplit = gameList.split("\"");
            for (int i = 0; i < gameListSplit.length; i++ )
            {
                // System.out.print("number " + i + " is " + gameListSplit[i] + ", ");
            }
            // System.out.print("\n");

            String gameListOutput = "\nGame List\n---------\n";

            currentGameListArray = new GameDataShort[100];

            for (int i = 0; i < gameListSplit.length; i++ )
            {
                if (gameListSplit[i].equals("gameID"))
                {
                    gameListOutput = gameListOutput.concat("Game #" + gameListSplit[i+1].split(":")[1] + " is ");

                    String gameName = "";
                    String whiteUsername = "";
                    String blackUsername = "";


                    for (int j = i + 1; j < gameListSplit.length; j++ )
                    {
                        // System.out.print(" i=" + i + " j=" + j + ",");

                        if (gameListSplit[j].equals("gameID"))
                        {
                            break;
                        } else if (gameListSplit[j].equals("}]}"))
                        {
                            break;
                        } else if (gameListSplit[j].equals("gameName"))
                        {
                            gameName = gameListSplit[j + 2];
                            // System.out.print(" gameName=" + gameName + ",");
                        }
                        else if (gameListSplit[j].equals("blackUsername"))
                        {
                            blackUsername = gameListSplit[j + 2];
                            // System.out.print(" blackUsername=" + blackUsername + ",");
                        }
                        else if (gameListSplit[j].equals("whiteUsername"))
                        {
                            whiteUsername = gameListSplit[j + 2];
                            // System.out.print(" whiteUsername=" + whiteUsername + ",");
                        }


                    }

                    // System.out.print("\nAppending to gameListArray= \n" + gameListSplit[i+1].split(":")[1].split(",")[0] + "\n");
                    currentGameListArray[Integer.parseInt(gameListSplit[i+1].split(":")[1].split(",")[0])] = new GameDataShort(Integer.parseInt(gameListSplit[i+1].split(":")[1].split(",")[0]), whiteUsername, blackUsername, gameName);

                    // Only print the ones found.
                    gameListOutput = gameListOutput.concat(gameName);
                    if (!whiteUsername.isEmpty())
                    {
                        gameListOutput = gameListOutput.concat("     WhitePlayerName: " + whiteUsername);
                    }
                    if (!blackUsername.isEmpty())
                    {
                        gameListOutput = gameListOutput.concat("     BlackPlayerName: " + blackUsername);
                    }
                    gameListOutput = gameListOutput.concat("\n");

                    for (int k=0; k < currentGameListArray.length; k++)
                    {
                        // System.out.print(" Game Data Short #" + i + " = " + currentGameListArray[i] + "\n");
                    }

                }

            }

            // return gameList;
            return gameListOutput;

        } catch (Exception e) {

            return "There was an error listing games";
        }
    }

    public String newGame(String gameName)
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if in game
        if (isInGame()) { return "You are already in a game!"; }

        String body = String.format(
                """
                  {
                  "gameName": "%s"
                  }
                  """, gameName);
        // System.out.print(body + "\n");
        try
        {
            client.post("/game", authToken, body);
            // System.out.print(client.toString() + "\n");
            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());

            // return output.toString();
            listGames();
            return gameName + " created";

        } catch (Exception e) {
            if (e.getMessage().equals("400"))
            {
                return "Pick a new Name.  That one already exists.";
            }
            return "Error";
        }
    }

    public String login(String username, String password)
    {

        // assertSignedIn();
        // System.out.print("username = " + username + "\n");
        // System.out.print("password = " + password + "\n");

        if (username.isBlank())
        {
            return "You need a name";
        }
        if (password.isBlank())
        {
            return "You need a password";
        }

        String body = String.format(
                """
                  {
                  "username": "%s",
                  "password": "%s"
                  }
                  """, username, password);
        // System.out.print("body = " + body + "\n");
        try
        {
            String authTokenTemp = client.post("/session", authToken, body);

            //System.out.print(client.toString() + "\n");


            String[] authTokenTempSplit = authTokenTemp.split("\"");
            for (int i = 0; i < authTokenTempSplit.length; i++ )
            {
                // System.out.print("number " + i + " is " + authTokenTempSplit[i] + ", ");
            }
            authToken = authTokenTempSplit[7];
            usernameLoggedInAs = username;
            state = State.SIGNEDIN;
            // System.out.print("\nLogged in " + authToken);


            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());
            listGames();

            // return output.toString();
            return "You are now logged in " + username;



        } catch (Exception e) {

            return "There was an error logging you in";
        }
    }

    public String register(String username, String password, String email)
    {
        // System.out.print("username = " + username + "\n");
        // System.out.print("password = " + password + "\n");

        if (username.isBlank())
        {
            return "You need a name";
        }
        if (password.isBlank())
        {
            return "You need a password";
        }
        if (email.isBlank())
        {
            return "You need an email address.";
        }
        if (!email.contains("@"))
        {
            return email + " is not an email address.";
        }
        if (!email.contains("."))
        {
            return " is not an email address.";
        }

        String body = String.format(
                """
                  {
                  "username": "%s",
                  "password": "%s",
                  "email": "%s"
                  }
                  """, username, password, email);
        // System.out.print(body + "\n");
        try
        {
            String results = client.post("/user", authToken, body);
            // System.out.print(results + "\n");


            login(username, password);

            return "You are now registered and logged in";


        } catch (Exception e) {
            if (e.getMessage().equals("403"))
            {
                return "That User Already Exists!";
            }
            return "Error";
        }
    }

    public String joinGame(String gameID, String playerColor)
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if in game
        if (isInGame()) { return "You are already in a game!"; }

        listGames();

        try {
            Integer.parseInt(gameID);
        }
        catch (Exception ex)
        {
            return gameID + " is not a number.";
        }

        if (Integer.parseInt(gameID) < 1 || Integer.parseInt(gameID) > 90)
        {
            return gameID + " is not a valid game.";
        }

        if (currentGameListArray[Integer.parseInt(gameID)] == null)
        {
            return gameID + " is not a valid game.";
        }



        try {
                if (currentGameListArray[Integer.parseInt(gameID)].whiteUsername().equals(usernameLoggedInAs))
                {
                    if (playerColor.equals("black") || playerColor.equals("b"))
                    {
                        playerColor = "BLACK";
                    }
                    if (playerColor.equals("BLACK"))
                    {
                        return "You are already white in that game";
                    }

                    return playGame(gameID);
                }
                if (currentGameListArray[Integer.parseInt(gameID)].blackUsername().equals(usernameLoggedInAs))
                {
                    if (playerColor.equals("white") || playerColor.equals("w"))
                    {
                        playerColor = "WHITE";
                    }
                    if (playerColor.equals("WHITE"))
                    {
                        return "You are already black in that game";
                    }

                    // return "You are already in that game";
                    return playGame(gameID);
                }
        }
        catch (Exception ex)
        {
            return gameID + " is not a valid game.";
        }

        if (playerColor.equals("white") || playerColor.equals("w"))
        {
            playerColor = "WHITE";
        }
        else if (playerColor.equals("black") || playerColor.equals("b"))
        {
            playerColor = "BLACK";
        } else
        {
            return playerColor + " is not a valid color.";
        }



        try {
            String body = String.format(
                    """
                      {
                      "playerColor": "%s",
                      "gameID": "%s"
                      }
                      """, playerColor, gameID);
            // System.out.print(body + "\n");
            client.put("/game", authToken, body);
            // System.out.print(client.toString() + "\n");
            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());

            listGames();

            // return output.toString();
            return playGame(gameID);

        } catch (Exception e) {
            return "Error: Cannot join that game";
        }
    }



    public String logout() throws Exception
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        // Returns an error if in game
        if (isInGame()) { return "You are in a game!"; }

        client.delete("/session", authToken);

        state = State.SIGNEDOUT;

        return "You are now logged out.";

        // Run Function
        //Game_Response_List output = service.Game_List(new Game_Request_List());

        // return output.toString();

    }


    private String viewGame(String gameID)
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        if (gameID.isBlank()) { return gameID + " is not a valid Game ID"; }

        // System.out.print("gameID = " + gameID + "\n");
        String body = String.format(
                """
                  {
                  "gameID": "%s"
                  }
                  """, gameID);
        // System.out.print(body + "\n");
        try {
            String chessList = client.post("/chess", authToken, body);

            // System.out.print(chessList + "\n");

            if (chessList == null){
                return "That game hasn't started yet.  Add players.";
            }


            // System.out.print(client.toString() + "\n");
            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());

            String[] gameListSplit = chessList.split("\"");
            for (int i = 0; i < gameListSplit.length; i++ )
            {
                // System.out.print("number " + i + " is " + gameListSplit[i] + ", ");
            }
            // System.out.print("\n");


            String viewGameOutput = "\nGame #" + gameID + "\n---------\n";
            viewGameOutput = viewGameOutput.concat("Active Player is " + gameListSplit[3] + "\n");

            ChessBoard chessBoard = new ChessBoard();
            int r = 1;
            int c = 1;

            for (int i = 8; i < gameListSplit.length; i++ )
            {
                if (gameListSplit[i].equals("pieceType"))
                {

                    String pieceType = gameListSplit[i+2];
                    String pieceColor = gameListSplit[i+6];

                    ChessGame.TeamColor pieceColorNew = ChessGame.TeamColor.BLACK;
                    ChessPiece.PieceType pieceTypeNew = ChessPiece.PieceType.PAWN;

                    if (pieceColor.equals("WHITE"))
                    {
                        pieceColorNew = ChessGame.TeamColor.WHITE;
                    }

                    if (pieceType.equals("ROOK"))
                    {
                        pieceTypeNew = ChessPiece.PieceType.ROOK;
                    } else if (pieceType.equals("KNIGHT"))
                    {
                        pieceTypeNew = ChessPiece.PieceType.KNIGHT;
                    } else if (pieceType.equals("BISHOP"))
                    {
                        pieceTypeNew = ChessPiece.PieceType.BISHOP;
                    } else if (pieceType.equals("KING"))
                    {
                        pieceTypeNew = ChessPiece.PieceType.KING;
                    } else if (pieceType.equals("QUEEN"))
                    {
                        pieceTypeNew = ChessPiece.PieceType.QUEEN;
                    }


                    // chess_board[r][c] = pieceColor + " " + pieceType;
                    chessBoard.addPiece(r, c, new ChessPiece(pieceColorNew, pieceTypeNew));

                    // Move to the next row
                    c++;
                    if (c>8)
                    {
                        r++;
                        c=1;
                    }
                }
                if (gameListSplit[i].contains("null"))
                {
                    // System.out.print("\n null found! \n");
                    String[] gameListSplitNull = gameListSplit[i].split(",");
                    for (int j = 0; j < gameListSplitNull.length; j++ )
                    {
                        // System.out.print("number " + j + " is " + gameListSplitNull[j] + ", ");


                        if (gameListSplitNull[j].contains("null"))
                        {

                            // System.out.print(" Empty Place Found!");

                            // Move to the next row
                            c++;
                            if (c>8)
                            {
                                r++;
                                c=1;
                            }
                        }
                        // System.out.print(" good ");

                    }

                    // System.out.print(" last null ");

                }
                // System.out.print(" Did Number " + i);
            }

            // System.out.print(" out of for \n");

            // viewGameOutput = viewGameOutput.concat(printBoard(chess_board, gameListSplit[3]));
            viewGameOutput = viewGameOutput.concat(printBoard(chessBoard, ChessGame.TeamColor.WHITE));

            // return gameList;
            return viewGameOutput;

        } catch (Exception e) {
            // System.out.print("\n" + e + "\n");
            return "Error";
        }

    }


    private String printBoard(ChessBoard chessBoard, ChessGame.TeamColor activePlayer)
    {
        currentChessBoard = chessBoard;

        try {
            String printBoardOutput = "\n";

            Boolean squareColorWhite = true;

            // System.out.print("Printing Board Now for the " + activePlayer + " player.\n");
            if (activePlayer.equals(ChessGame.TeamColor.BLACK))
            {
                printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003\u2003\u2003h\u2003 g\u2003 f\u2003 e\u2003 d\u2003 c\u2003 b\u2003 a\u2003\u2003\u2003" + RESET + "\n");
                for (int row = 1; row <= 8; row++ )
                {
                    // Move to the next row
                    printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + " " + (row) + "\u2003");
                    for (int col = 8; col >= 1; col-- )
                    {
                        // System.out.print("Current Board: row = " + row + ", col = " + col + ", piece = " + chessBoard.getPiece(row, col) + "\n");

                        // Move to the next col

                        // Set Backgroud Color
                        if (squareColorWhite)
                        {
                            // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_WHITE);
                            printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_PEACH);
                        }
                        else
                        {
                            // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_GREEN);
                            printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_BROWN);
                        }


                        printBoardOutput = printBoardOutput.concat(printPiece(chessBoard.getPiece(row, col)));




                        squareColorWhite = !squareColorWhite;
                    }
                    squareColorWhite = !squareColorWhite;
                    printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003" + (row) + " " + RESET + "\n");
                }
                printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003\u2003\u2003h\u2003 g\u2003 f\u2003 e\u2003 d\u2003 c\u2003 b\u2003 a\u2003\u2003\u2003" + RESET + "\n");
            }
            else if (activePlayer.equals(ChessGame.TeamColor.WHITE))
            {
                printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003\u2003\u2003a\u2003 b\u2003 c\u2003 d\u2003 e\u2003 f\u2003 g\u2003 h\u2003\u2003\u2003" + RESET + "\n");
                for (int row = 8; row >= 1; row-- )
                {
                    // Move to the next row
                    printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + " " + (row) + "\u2003");
                    for (int col = 1; col <= 8; col++ )
                    {
                        // System.out.print("Current Board: row = " + row + ", col = " + col + ", piece = " + chessBoard.getPiece(row, col) + "\n");

                        // Move to the next col

                        // Set Backgroud Color
                        if (squareColorWhite)
                        {
                            // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_WHITE);
                            printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_PEACH);
                        }
                        else
                        {
                            // printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_GREEN);
                            printBoardOutput = printBoardOutput.concat(SET_BG_COLOR_BROWN);
                        }

                        printBoardOutput = printBoardOutput.concat(printPiece(chessBoard.getPiece(row, col)));


                        squareColorWhite = !squareColorWhite;
                    }
                    squareColorWhite = !squareColorWhite;
                    printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003" + (row) + " " + RESET + "\n");
                }
                printBoardOutput = printBoardOutput.concat(SET_TEXT_COLOR_BLACK + SET_BG_COLOR_LIGHT_GREY + "\u2003\u2003\u2003a\u2003 b\u2003 c\u2003 d\u2003 e\u2003 f\u2003 g\u2003 h\u2003\u2003\u2003" + RESET + "\n");
            }


            // return gameList;
            return printBoardOutput;

        } catch (Exception e) {
            // System.out.print("\n" + e + "\n");
            return "Error " + e;
        }




    }

    private String printPiece(ChessPiece piece)
    {
        String printPieceOutput = "";

        if (piece == null)
        {
            return EMPTY;
        }

        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE)
        {
            printPieceOutput = printPieceOutput.concat(SET_TEXT_COLOR_WHITE);
        } else if  (piece.getTeamColor() == ChessGame.TeamColor.BLACK)
        {
            // It is black
            printPieceOutput = printPieceOutput.concat(SET_TEXT_COLOR_BLACK);
        }

        if (piece.getPieceType() == ChessPiece.PieceType.ROOK)
        {
            printPieceOutput = printPieceOutput.concat(BLACK_ROOK);
        } else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT)
        {
            printPieceOutput = printPieceOutput.concat(BLACK_KNIGHT);
        } else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP)
        {
            printPieceOutput = printPieceOutput.concat(BLACK_BISHOP);
        } else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN)
        {
            printPieceOutput = printPieceOutput.concat(BLACK_QUEEN);
        } else if (piece.getPieceType() == ChessPiece.PieceType.KING)
        {
            printPieceOutput = printPieceOutput.concat(BLACK_KING);
        } else if (piece.getPieceType() == ChessPiece.PieceType.PAWN)
        {
            printPieceOutput = printPieceOutput.concat(BLACK_PAWN);
        }

        return printPieceOutput;
    }



//    public String adoptPet(String... params) {
//        assertSignedIn();
//        if (params.length == 1) {
//            try {
//                int id = Integer.parseInt(params[0]);
//                Pet pet = getPet(id);
//                if (pet != null) {
//                    server.deletePet(id);
//                    return String.format("%s says %s", pet.name(), pet.sound());
//                }
//            } catch (NumberFormatException ignored) {
//            }
//        }
//
//    }

//    public String adoptAllPets() {
//        assertSignedIn();
//        var buffer = new StringBuilder();
//        for (Pet pet : server.listPets()) {
//            buffer.append(String.format("%s says %s%n", pet.name(), pet.sound()));
//        }
//
//        server.deleteAllPets();
//        return buffer.toString();
//    }

//    public String signOut() {
//        assertSignedIn();
//        ws.leavePetShop(visitorName);
//        state = State.SIGNEDOUT;
//        return String.format("%s left the shop", visitorName);
//    }

//    private Pet getPet(int id) {
//        for (Pet pet : server.listPets()) {
//            if (pet.id() == id) {
//                return pet;
//            }
//        }
//        return null;
//    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    Options:
                    \"help\" or \"h\" - Displays text informing the user what actions you can take.
                    \"quit\" or \"q\" - Exits the program.
                    \"login <USERNAME> <PASSWORD>\" - Logs in.
                    \"register <USERNAME> <PASSWORD> <EMAIL>\" or \"reg <USERNAME> <PASSWORD> <EMAIL>\" - Registers a new user.
                    """;
        }
        else if (state == State.SIGNEDIN) {
            return """
                    Options:
                    \"help\" or \"h\" - Displays text informing the user what actions you can take.
                    \"logout\" or \"lo\" or \"out\" - Logs you out.
                    \"create <NAME>\" or \"c <NAME>\" or \"new <NAME>\" or \"n <NAME>\" - creates a new game.
                    \"list\" or \"l\" - Lists all the game.
                    \"join <ID> [WHITE|BLACK]\" or \"j <ID> [W|B]\" - Join specified game.
                    "play <ID> [WHITE|BLACK]" or "p <ID> [W|B]" - Play the game as your color (same as join)
                    \"view <ID>\" or \"v <ID>\" or \"observe <ID>\" or \"o <ID>\" - View specified game.
                    
                    """;
        }
        else if (state == State.INGAME) {
            return """
                    Options:
                    "help" or "h" - Displays text informing the user what actions you can take.
                    "move <LOCAL_START> <LOCAL_END>" or "m <LOCAL_START> <LOCAL_END>" - Moves a piece.
                    "move <LOCAL_START> <LOCAL_END> <QUEEN|BISHOP|KNIGHT|ROOK>" - Moves a pawn to promotion.
                    "redraw" or "reload" or "r" - Redraws the chess board
                    "leave" - Leave the chess game
                    "resign" - Resign the chess game
                    "legal <LOCAL_START>" or "valid <LOCAL_START>" - Shows legal moves
                    """;
        }

        return "";
    }

    private boolean isLoggedIn() {
        if (state == State.SIGNEDOUT) {
            // System.out.print("You need to be logged in to do that!");
            return false;
        } else
        {
            return true;
        }
    }

    private boolean isInGame() {
        if (state == State.INGAME) {
            // System.out.print("You need to be logged in to do that!");
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public void notify(ServerMessage notification)
    {
        // System.out.print("In Notify");

        switch (notification.getServerMessageType()) {
            case NOTIFICATION -> displayNotificiation(notification.getMessage());
            case ERROR -> displayError(notification.getErrorMessage());
            case LOAD_GAME -> System.out.print(printBoard(notification.getGame().getBoard(), currentPlayerColor));
            // case LOAD_GAME -> printBoard(notification.getGame());

        }

        printPrompt();




    }


    void displayNotificiation(String message)
    {
        System.out.print(SET_TEXT_COLOR_BLUE + message + "\n" + SET_TEXT_COLOR_GREEN);
    }
    void displayError(String message)
    {
        System.out.print(SET_TEXT_COLOR_MAGENTA + message + "\n" + SET_TEXT_COLOR_GREEN);
    }

}
