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
    public gameResponseCreate create(gameRequestCreate data) throws DataAccessException
    {

        // Bad Input
        if (data == null)
        {
            // Return error
            return new gameResponseCreate(-1);
        }
        if (data.gameName() == null)
        {
            // Return error
            return new gameResponseCreate(-1);
        }

        // Check to see if name already used
        if (dataList.gameFoundViaGameName(data.gameName()))
        {
            // Return error
            return new gameResponseCreate(-2);
        }

        int game_id = -2;
        try {
            game_id = dataList.gameAddGameName(data.gameName());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }


        gameResponseCreate output = new gameResponseCreate(game_id);

        return output;

    }

    /**
     * Join a game
     *
     * @param
     * @return
     */
    public gameResponseJoin join(gameRequestJoin data) throws Exception
    {
        String join_userName = data.username();
        int join_gameID = data.gameID();
        String join_color = data.playerColor();

        System.out.println("I am in GameService.java join!!");

        // If the game doesn't exist, give error
        if (!dataList.gameFoundViaGameID(join_gameID))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData join_game = dataList.getGameDataViaGameID(join_gameID);
        System.out.println("Game does exist still");
        System.out.println(join_game);


        String join_whiteUsername = join_game.whiteUsername();
        String join_blackUsername = join_game.blackUsername();
        String join_gameName = join_game.gameName();
        ChessGame join_chessgame = join_game.chessGame();

        // if

        if (join_color.equals("BLACK"))
        {
            if (join_game.blackUsername() == null)
            {
                dataList.gameDeleteViaGameID(join_gameID);
                dataList.gameAddKeepGameID(new GameData(join_gameID, join_whiteUsername, join_userName, join_gameName, join_chessgame));
            }
            else
            {
                throw new DataAccessException("403");
            }
        } else if (join_color.equals("WHITE"))
        {
            if (join_game.whiteUsername() == null)
            {
                dataList.gameDeleteViaGameID(join_gameID);
                dataList.gameAddKeepGameID(new GameData(join_gameID, join_userName, join_blackUsername, join_gameName, join_chessgame));
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
        return new gameResponseJoin();
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
        dataList.gameAddKeepGameID(new GameData(unjoinGameID, unjoinWhiteUsername, unjoinUserName, unjoinGameName, unjoinChessgame));



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
    public gameDataShort getGameDataShort(int gameID) throws Exception
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
        GameData join_game = dataList.getGameDataViaGameID(gameID);
        System.out.println("Game does exist still");
        System.out.println(join_game);


        String whiteUsername = join_game.whiteUsername();
        String blackUsername = join_game.blackUsername();
        String gameName = join_game.gameName();


        return(new gameDataShort(gameID, whiteUsername, blackUsername, gameName));

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
        GameData view_game = dataList.getGameDataViaGameID(gameIDViewing);
        System.out.println("Game does exist still");
        System.out.println(view_game);


        return view_game.chessGame();
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
        System.out.println("Moved Game re-adding: " + gameIDMoving + gameMovingData.whiteUsername() + gameMovingData.blackUsername() + gameMovingData.gameName() + gameMoving);
        dataList.gameAddKeepGameID(new GameData(gameIDMoving, gameMovingData.whiteUsername(), gameMovingData.blackUsername(), gameMovingData.gameName(), gameMoving));


        return gameMoving;
    }


    /**
     * List Games
     *
     * @param
     * @return
     */
    public gameResponseList list(gameRequestList data) throws DataAccessException
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
