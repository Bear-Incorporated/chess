package service;

import dataaccess.AuthDAO;
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
        Game_Response_Create output = new Game_Response_Create();

        throw new RuntimeException("Not implemented");
    }

    /**
     * Join a game
     *
     * @param
     * @return
     */
    public Game_Response_Join join(Game_Request_Join data) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * List Games
     *
     * @param
     * @return
     */
    public Game_Response_List list(Game_Request_List data) {
        throw new RuntimeException("Not implemented");
    }


    public Clear_Response clear(Clear_Request data) {
        data_list.Game_delete_all();
        return new Clear_Response();
    }
}
