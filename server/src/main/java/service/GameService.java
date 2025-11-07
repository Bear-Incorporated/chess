package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.*;

import java.sql.DriverManager;
import java.sql.SQLException;

public class GameService
{

    private final GameDAO data_list = new GameDAO();

    /**
     * create a new Game
     *
     * @param
     * @return
     */
    public Game_Response_Create create(Game_Request_Create data) throws DataAccessException
    {

        // Bad Input
        if (data == null)
        {
            // Return error
            return new Game_Response_Create(-1);
        }
        if (data.gameName() == null)
        {
            // Return error
            return new Game_Response_Create(-1);
        }

        // Check to see if name already used
        if (data_list.Game_found_via_gameName(data.gameName()))
        {
            // Return error
            return new Game_Response_Create(-2);
        }

        int game_id = -2;
        try {
            game_id = data_list.Game_add_gameName(data.gameName());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }


        Game_Response_Create output = new Game_Response_Create(game_id);

        return output;

    }

    /**
     * Join a game
     *
     * @param
     * @return
     */
    public Game_Response_Join join(Game_Request_Join data) throws Exception
    {
        String join_userName = data.username();
        int join_gameID = data.gameID();
        String join_color = data.playerColor();

        System.out.println("I am in GameService.java join!!");

        // If the game doesn't exist, give error
        if (!data_list.Game_found_via_gameID(join_gameID))
        {
            System.out.println("Game doesn't exist");
            throw new DataAccessException("400");
        }
        System.out.println("Game does exist");

        // Find the game
        GameData join_game = data_list.Game_get_via_gameID(join_gameID);
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
                data_list.Game_delete_via_gameID(join_gameID);
                data_list.Game_add_keep_gameID(new GameData(join_gameID, join_whiteUsername, join_userName, join_gameName, join_chessgame));
            }
            else
            {
                throw new DataAccessException("403");
            }
        } else if (join_color.equals("WHITE"))
        {
            if (join_game.whiteUsername() == null)
            {
                data_list.Game_delete_via_gameID(join_gameID);
                data_list.Game_add_keep_gameID(new GameData(join_gameID, join_userName, join_blackUsername, join_gameName, join_chessgame));
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
        return new Game_Response_Join();
    }

    /**
     * List Games
     *
     * @param
     * @return
     */
    public Game_Response_List list(Game_Request_List data) throws DataAccessException
    {


        try {
            return data_list.Game_list();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    public void clear() throws DataAccessException
    {
        try {
            data_list.Game_delete_all();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }


        return;
    }
}
