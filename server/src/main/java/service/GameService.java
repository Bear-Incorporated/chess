package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.*;

public class GameService
{

    private final GameDAO data_list = new GameDAO();

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
        if (data_list.gameFoundViaGameName(data.gameName()))
        {
            // Return error
            return new gameResponseCreate(-2);
        }

        int game_id = -2;
        try {
            game_id = data_list.gameAddGameName(data.gameName());
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
        if (!data_list.gameFoundViaGameID(join_gameID))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData join_game = data_list.getGameDataViaGameID(join_gameID);
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
                data_list.gameDeleteViaGameID(join_gameID);
                data_list.gameAddKeepGameID(new GameData(join_gameID, join_whiteUsername, join_userName, join_gameName, join_chessgame));
            }
            else
            {
                throw new DataAccessException("403");
            }
        } else if (join_color.equals("WHITE"))
        {
            if (join_game.whiteUsername() == null)
            {
                data_list.gameDeleteViaGameID(join_gameID);
                data_list.gameAddKeepGameID(new GameData(join_gameID, join_userName, join_blackUsername, join_gameName, join_chessgame));
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
        if (!data_list.gameFoundViaGameID(gameIDViewing))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData view_game = data_list.getGameDataViaGameID(gameIDViewing);
        System.out.println("Game does exist still");
        System.out.println(view_game);


        return view_game.chessGame();
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
            return data_list.gameList();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    public void clear() throws DataAccessException
    {
        try {
            data_list.gameDeleteAll();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }


        return;
    }
}
