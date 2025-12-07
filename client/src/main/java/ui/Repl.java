package ui;

import websocket.HttpTalker;

import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;


public class Repl {
    private String visitorName = null;
    // private final ServerFacade server;
    // private final WebSocketFacade ws;
    private State state = State.SIGNEDOUT;
    private HttpTalker client = new HttpTalker();

    private String authToken;

    private final String SERVER_HOST = "localhost";
    private final int SERVER_PORT = 8080;

    // private Chess_Service service = new Chess_Service();


    public Repl() {
        // server = new ServerFacade(serverUrl);
        // ws = new WebSocketFacade(serverUrl, this);
        client = new HttpTalker();
        authToken = "";
    }

    public Repl(String serverUrl) {
        // server = new ServerFacade(serverUrl);
        // ws = new WebSocketFacade(serverUrl, this);
    }

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
        System.out.print(input1 + "\n");
        String input2 = (tokens.length > 2) ? tokens[2] : "";
        System.out.print(input2 + "\n");
        String input3 = (tokens.length > 3) ? tokens[3] : "";
        System.out.print(input3 + "\n");


        return switch (cmd) {
            // case "signin" -> signIn(params);
            // case "rescue" -> rescuePet(params);
            case "list" -> listGames();
            case "l" -> listGames();
            case "login" -> login(input1, input2);
            case "register" -> register(input1, input2, input3);
            case "r" -> register(input1, input2, input3);
            case "logout" -> logout();
            case "o" -> logout();
            case "create" -> newGame(input1);
            case "c" -> newGame(input1);
            case "new" -> newGame(input1);
            case "n" -> newGame(input1);
            case "join" -> joinGame(input1, input2);
            case "j" -> joinGame(input1, input2);
            case "observe" -> viewGame(input1);
            case "view" -> viewGame(input1);
            case "v" -> viewGame(input1);
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
//
//    \"view <ID>\" or \"v <ID>\" - View specified game.
//    """;




//    public String signIn(String... params) {
//        if (params.length >= 1) {
//            state = State.SIGNEDIN;
//            visitorName = String.join("-", params);
//            ws.enterPetShop(visitorName);
//            return String.format("You signed in as %s.", visitorName);
//        }
//    }


    public String listGames()
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        try
        {
            String gameList = client.get("/game", authToken);
            System.out.print(client.toString() + "\n");
            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());

            String[] gameListSplit = gameList.split("\"");
            for (int i = 0; i < gameListSplit.length; i++ )
            {
                System.out.print("number " + i + " is " + gameListSplit[i] + ", ");
            }
            System.out.print("\n");

            String gameListOutput = "\nGame List\n---------\n";

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
                        System.out.print(" i=" + i + " j=" + j + ",");

                        if (gameListSplit[j].equals("gameID"))
                        {
                            break;
                        } else if (gameListSplit[j].equals("}]}"))
                        {
                            break;
                        } else if (gameListSplit[j].equals("gameName"))
                        {
                            gameName = gameListSplit[j + 2];
                            System.out.print(" gameName=" + gameName + ",");
                        }
                        else if (gameListSplit[j].equals("blackUsername"))
                        {
                            blackUsername = gameListSplit[j + 2];
                            System.out.print(" blackUsername=" + blackUsername + ",");
                        }
                        else if (gameListSplit[j].equals("whiteUsername"))
                        {
                            whiteUsername = gameListSplit[j + 2];
                            System.out.print(" whiteUsername=" + whiteUsername + ",");
                        }


                    }

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
                }

            }

            // return gameList;
            return gameListOutput;

        } catch (Exception e) {

            return "There was an error logging you in";
        }
    }

    public String newGame(String gameName)
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

        String body = String.format(
                """
                  {
                  "gameName": "%s"
                  }
                  """, gameName);
        System.out.print(body + "\n");
        try
        {
            client.post("/game", authToken, body);
            System.out.print(client.toString() + "\n");
            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());

            // return output.toString();
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
        System.out.print("username = " + username + "\n");
        System.out.print("password = " + password + "\n");
        String body = String.format(
                """
                  {
                  "username": "%s",
                  "password": "%s"
                  }
                  """, username, password);
        System.out.print("body = " + body + "\n");
        try
        {
            String authTokenTemp = client.post("/session", authToken, body);
            //System.out.print(client.toString() + "\n");

            String[] authTokenTempSplit = authTokenTemp.split("\"");
            for (int i = 0; i < authTokenTempSplit.length; i++ )
            {
                System.out.print("number " + i + " is " + authTokenTempSplit[i] + ", ");
            }
            authToken = authTokenTempSplit[7];
            state = State.SIGNEDIN;
            System.out.print("\nLogged in " + authToken);


            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());

            // return output.toString();
            return "You are now logged in";

        } catch (Exception e) {

            return "There was an error logging you in";
        }
    }

    public String register(String username, String password, String email)
    {
        System.out.print("username = " + username + "\n");
        System.out.print("password = " + password + "\n");
        String body = String.format(
                """
                  {
                  "username": "%s",
                  "password": "%s",
                  "email": "%s"
                  }
                  """, username, password, email);
        System.out.print(body + "\n");
        try
        {
            String results = client.post("/user", authToken, body);
            System.out.print(results + "\n");


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
            System.out.print(body + "\n");
            client.put("/game", authToken, body);
            System.out.print(client.toString() + "\n");
            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());

            // return output.toString();
            return "Done";

        } catch (Exception e) {
            return "Error";
        }
    }

    public String logout() throws Exception
    {
        // Returns an error if logged out
        if (!isLoggedIn()) { return "You need to be logged in to do that!"; }

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

        System.out.print("gameID = " + gameID + "\n");
        String body = String.format(
                """
                  {
                  "gameID": "%s"
                  }
                  """, gameID);
        System.out.print(body + "\n");
        try {
            String chessList = client.post("/chess", authToken, body);

            System.out.print(chessList + "\n");

            if (chessList == null){
                return "That game hasn't started yet.  Add players.";
            }


            System.out.print(client.toString() + "\n");
            // Run Function
            //Game_Response_List output = service.Game_List(new Game_Request_List());

            String[] gameListSplit = chessList.split("\"");
            for (int i = 0; i < gameListSplit.length; i++ )
            {
                System.out.print("number " + i + " is " + gameListSplit[i] + ", ");
            }
            System.out.print("\n");


            String viewGameOutput = "\nGame #" + gameID + "\n---------\n";
            viewGameOutput = viewGameOutput.concat("Active Player is " + gameListSplit[3] + "\n");

            String[][] chess_board = new String[8][8];
            int r = 0;
            int c = 0;

            for (int i = 8; i < gameListSplit.length; i++ )
            {
                if (gameListSplit[i].equals("pieceType"))
                {

                    String pieceType = gameListSplit[i+2];
                    String pieceColor = gameListSplit[i+6];

                    chess_board[r][c] = pieceColor + " " + pieceType;

                    // Move to the next row
                    c++;
                    if (c>7)
                    {
                        r++;
                        c=0;
                    }
                }
                if (gameListSplit[i].contains("null"))
                {
                    System.out.print("\n null found! \n");
                    String[] gameListSplitNull = gameListSplit[i].split(",");
                    for (int j = 0; j < gameListSplitNull.length; j++ )
                    {
                        System.out.print("number " + j + " is " + gameListSplitNull[j] + ", ");


                        if (gameListSplitNull[j].contains("null"))
                        {

                            chess_board[r][c] = "";
                            System.out.print(" Empty Place Found!");

                            // Move to the next row
                            c++;
                            if (c>7)
                            {
                                r++;
                                c=0;
                            }
                        }
                        System.out.print(" good ");

                    }

                    System.out.print(" last null ");

                }
                System.out.print(" Did Number " + i);
            }

            System.out.print(" out of for \n");

            // viewGameOutput = viewGameOutput.concat(printBoard(chess_board, gameListSplit[3]));
            viewGameOutput = viewGameOutput.concat(printBoard(chess_board, "BLACK"));

            // return gameList;
            return viewGameOutput;

        } catch (Exception e) {
            System.out.print("\n" + e + "\n");
            return "Error";
        }




    }


    private String printBoard(String[][] chess_board, String activePlayer)
    {

        try {



            String printBoardOutput = "";





            System.out.print("Printing Board Now \n");
            if (activePlayer.equals("BLACK"))
            {
                printBoardOutput = printBoardOutput.concat(SET_TEXT_BOLD + SET_BG_COLOR_LIGHT_GREY + EMPTY);
                for (int row = 0; row < 8; row++ )
                {
                    for (int col = 0; col < 8; col++ )
                    {
                        System.out.print("Current Board: row = " + row + ", col = " + col + ", piece = " + chess_board[row][col] + "\n");



                        // Move to the next row
                        printBoardOutput = printBoardOutput.concat("\n");
                    }
                }
            }


            // return gameList;
            return printBoardOutput;

        } catch (Exception e) {
            System.out.print("\n" + e + "\n");
            return "Error";
        }




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
                    \"help\" or \"h\" - Displays text informing the user what actions they can take.
                    \"quit\" or \"q\" - Exits the program.
                    \"login <USERNAME> <PASSWORD>\" - Logs in.
                    \"register <USERNAME> <PASSWORD> <EMAIL>\" or \"r <USERNAME> <PASSWORD> <EMAIL>\" - Registers a new user.
                    """;
        }
        else if (state == State.SIGNEDIN) {
            return """
                    Options:
                    \"help\" or \"h\" - Displays text informing the user what actions they can take.
                    \"logout\" or \"o\" - Logs you out.
                    \"create <NAME>\" or \"c <NAME>\" or \"new <NAME>\" or \"n <NAME>\" - creates a new game.
                    \"list\" or \"l\" - Lists all the game.
                    \"join <ID> [WHITE|BLACK]\" or \"j <ID> [WHITE|BLACK]\" - Join specified game.
                    \"view <ID>\" or \"v <ID>\" - View specified game.
                    """;
        }
        else if (state == State.INGAME) {
            return """
                    Options:
                    \"move <LOCAL_START> <LOCAL_END>\" or \"<LOCAL_START> <LOCAL_END>\" - Moves a piece.
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
}
