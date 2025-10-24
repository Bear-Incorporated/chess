package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
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
    public Game_Response_Create create(Game_Request_Create data) {

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

        int game_id = data_list.Game_add_gameName(data.gameName());

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
        int join_gameID = data.gameID();
        String join_color = data.playerColor();

        // If the game doesn't exist, give error
        if (!data_list.Game_found_via_gameID(join_gameID))
        {
            throw new DataAccessException("400");
        }

        GameData join_game = data_list.Game_get_via_gameID(join_gameID);





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
    public Game_Response_List list(Game_Request_List data) {
        return new Game_Response_List(data_list.Game_list());
    }


    public Clear_Response clear(Clear_Request data) {
        data_list.Game_delete_all();
        return new Clear_Response();
    }
}
