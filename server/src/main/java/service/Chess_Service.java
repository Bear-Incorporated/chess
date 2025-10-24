package service;


import dataaccess.DataAccessException;
import model.*;

import chess.ChessPiece;
import chess.ChessPosition;

public class Chess_Service
{

    private final GameService service_game = new GameService();
    private final UserService service_user = new UserService();


    /**
     * ClearResult
     *
     * @param
     * @return
     */
    public Clear_Response Clear(Clear_Request data) {
        System.out.println("clear");
        service_game.clear(data);
        service_user.clear(data);
        return new Clear_Response();

    }

    /**
     * create a new Game
     *
     * @param
     * @return
     */
    public Game_Response_Create Game_Create(Game_Request_Create data) {
        System.out.println("game_create");
        return service_game.create(data);
    }

    /**
     * Join a game
     *
     * @param
     * @return
     */
    public Game_Response_Join Game_Join(Game_Request_Join data) throws Exception
    {
        System.out.println("game_join");

        // Swaps the AuthToken with correct userName
        String auth_token = data.username(); // actually AuthToken at this point
        System.out.println("auth token: " + auth_token);
        String username = service_user.get_userName_via_authToken(auth_token);
        Game_Request_Join data_with_name = new Game_Request_Join(username, data.playerColor(), data.gameID());
        System.out.println("username: " + username);

        try {
            System.out.println("trying game_join");
            System.out.println(data_with_name);
            Game_Response_Join Game_Response_Join_temp = service_game.join(data_with_name);
            return Game_Response_Join_temp;
        } catch (DataAccessException e)
        {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * List Games
     *
     * @param
     * @return
     */
    public Game_Response_List Game_List(Game_Request_List data) {
        System.out.println("game_list");
        return service_game.list(data);
    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public User_Response_Register User_Register(User_Request_Register data) throws DataAccessException
    {
        System.out.println("user_register");
        return service_user.register(data);
    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public User_Response_Login User_Login(User_Request_Login data) throws DataAccessException
    {
        System.out.println("user_login");
        return service_user.login(data);

    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public User_Response_Logout User_Logout(User_Request_Logout data) {
        System.out.println("user_logout");
        return service_user.logout(data);
    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public boolean User_Authorized(String data) {
        System.out.println("user_authorize");
        return service_user.authorized(data);
    }



}
