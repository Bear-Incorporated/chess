package service;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.*;

public class GameService
{

    private final GameDAO dataList = new GameDAO();

    /**
     * create a new Game
     *
     * @param
     * @return
     */
    public GameResponseCreate create(GameRequestCreate data) throws DataAccessException
    {

        // Bad Input
        if (data == null)
        {
            // Return error
            return new GameResponseCreate(-1);
        }
        if (data.gameName() == null)
        {
            // Return error
            return new GameResponseCreate(-1);
        }

        // Check to see if name already used
        if (dataList.gameFoundViaGameName(data.gameName()))
        {
            // Return error
            return new GameResponseCreate(-2);
        }

        int gameId = -2;
        try {
            gameId = dataList.gameAddGameName(data.gameName());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }


        GameResponseCreate output = new GameResponseCreate(gameId);

        return output;

    }

    /**
     * Join a game
     *
     * @param
     * @return
     */
    public GameResponseJoin join(GameRequestJoin data) throws Exception
    {
        String joinUserName = data.username();
        int joinGameID = data.gameID();
        String joinColor = data.playerColor();

        System.out.println("I am in GameService.java join!!");

        // If the game doesn't exist, give error
        if (!dataList.gameFoundViaGameID(joinGameID))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData joinGame = dataList.getGameDataViaGameID(joinGameID);
        System.out.println("Game does exist still");
        System.out.println(joinGame);


        String joinWhiteUsername = joinGame.whiteUsername();
        String joinBlackUsername = joinGame.blackUsername();
        String joinGameName = joinGame.gameName();
        ChessGame joinChessgame = joinGame.chessGame();

        // if

        if (joinColor.equals("BLACK"))
        {
            System.out.println("joinGame.blackUsername() = " + joinGame.blackUsername());

            if (joinGame.blackUsername() == null)
            {
                dataList.gameDeleteViaGameID(joinGameID);
                dataList.gameAddKeepGameID(new GameData(joinGameID, joinWhiteUsername, joinUserName, joinGameName, joinChessgame));
            }
            else
            {
                throw new DataAccessException("403");
            }
        } else if (joinColor.equals("WHITE"))
        {
            System.out.println("joinGame.whiteUsername() = " + joinGame.whiteUsername());
            if (joinGame.whiteUsername() == null)
            {
                dataList.gameDeleteViaGameID(joinGameID);
                dataList.gameAddKeepGameID(new GameData(joinGameID, joinUserName, joinBlackUsername, joinGameName, joinChessgame));
            }
            else
            {
                throw new DataAccessException("403");
            }
        } else
        {
            //Trying to join non-black or white team
            throw new DataAccessException("400");
        }


        // throw new DataAccessException("400");
        // throw new DataAccessException("403");
        // throw new RuntimeException("Not implemented");
        return new GameResponseJoin();
    }


    public void unjoin(int unjoinGameID, String unjoinUserName) throws Exception
    {

        System.out.println("I am in GameService.java unjoin!!");

        // If the game doesn't exist, give error
        if (!dataList.gameFoundViaGameID(unjoinGameID))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData unjoinGame = dataList.getGameDataViaGameID(unjoinGameID);
        System.out.println("Game does exist still");
        System.out.println(unjoinGame);



        String unjoinWhiteUsername = unjoinGame.whiteUsername();
        String unjoinBlackUsername = unjoinGame.blackUsername();
        String unjoinGameName = unjoinGame.gameName();
        ChessGame unjoinChessgame = unjoinGame.chessGame();

        if (unjoinUserName.equals(unjoinWhiteUsername))
        {
            unjoinWhiteUsername = null;
        } else if (unjoinUserName.equals(unjoinBlackUsername))
        {
            unjoinBlackUsername = null;
        } else {
            throw new DataAccessException("400");
        }

        dataList.gameDeleteViaGameID(unjoinGameID);
        dataList.gameAddKeepGameID(new GameData(unjoinGameID, unjoinWhiteUsername, unjoinBlackUsername, unjoinGameName, unjoinChessgame));



        // throw new DataAccessException("400");
        // throw new DataAccessException("403");
        // throw new RuntimeException("Not implemented");
        return;
    }


    /**
     * Get the shortened GameData
     *
     * @param
     * @return
     */
    public GameDataShort getGameDataShort(int gameID) throws Exception
    {

        System.out.println("I am in GameService.java getPlayers!!");

        // If the game doesn't exist, give error
        if (!dataList.gameFoundViaGameID(gameID))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData joinGame = dataList.getGameDataViaGameID(gameID);
        System.out.println("Game does exist still");
        System.out.println(joinGame);


        String whiteUsername = joinGame.whiteUsername();
        String blackUsername = joinGame.blackUsername();
        String gameName = joinGame.gameName();


        return(new GameDataShort(gameID, whiteUsername, blackUsername, gameName));

    }

    public void gameOver(int gameOverID) throws Exception
    {
        System.out.println("I am in gameOver!!");

        // If the game doesn't exist, give error
        if (!dataList.gameFoundViaGameID(gameOverID))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData gameOverData = dataList.getGameDataViaGameID(gameOverID);
        System.out.println("Game does exist still");
        System.out.println(gameOverData);


        ChessGame chessGameOver = gameOverData.chessGame();
        chessGameOver.gameOver();


        dataList.gameDeleteViaGameID(gameOverID);
        dataList.gameAddKeepGameID(new GameData(gameOverID, gameOverData.whiteUsername(), gameOverData.blackUsername(), gameOverData.gameName(), chessGameOver));

        // throw new DataAccessException("400");
        // throw new DataAccessException("403");
        // throw new RuntimeException("Not implemented");
        return;
    }


    /**
     * View a game
     *
     * @param
     * @return
     */
    public ChessGame view(int gameIDViewing) throws Exception
    {

        System.out.println("I am in GameService.java view!!");

        // If the game doesn't exist, give error
        if (!dataList.gameFoundViaGameID(gameIDViewing))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData viewGame = dataList.getGameDataViaGameID(gameIDViewing);
        System.out.println("Game does exist still");
        System.out.println(viewGame);


        return viewGame.chessGame();
    }



    /**
     * Move a piece in a game
     *
     * @param
     * @return
     */
    public ChessGame move(int gameIDMoving, ChessMove chessMove) throws Exception
    {

        System.out.println("I am in GameService.java move!!");

        // If the game doesn't exist, give error
        if (!dataList.gameFoundViaGameID(gameIDMoving))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData gameMovingData = dataList.getGameDataViaGameID(gameIDMoving);
        ChessGame gameMoving = gameMovingData.chessGame();
        System.out.println("Game does exist still");
        System.out.println(gameMoving);

        try
        {
            gameMoving.makeMove(chessMove);
        }
        catch (InvalidMoveException ex)
        {
            throw new InvalidMoveException(ex.getMessage());
        }



        dataList.gameDeleteViaGameID(gameIDMoving);
        System.out.println("Moved Game re-adding: " + gameIDMoving + " " + gameMovingData.whiteUsername() + " " + gameMovingData.blackUsername() + " " + gameMovingData.gameName() + " " + gameMoving.toString());
        dataList.gameAddKeepGameID(new GameData(gameIDMoving, gameMovingData.whiteUsername(), gameMovingData.blackUsername(), gameMovingData.gameName(), gameMoving));


        return gameMoving;
    }


    /**
     * List Games
     *
     * @param
     * @return
     */
    public GameResponseList list(GameRequestList data) throws DataAccessException
    {


        try {
            return dataList.gameList();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    public void clear() throws DataAccessException
    {
        try {
            dataList.gameDeleteAll();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }


        return;
    }
}
