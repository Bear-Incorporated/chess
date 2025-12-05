package service;


import chess.ChessGame;
import dataaccess.DataAccessException;
import model.*;

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
    public void Clear() throws DataAccessException
    {
        System.out.println("clear");

        try {
            service_game.clear();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }


        try {
            service_user.clear();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }



    }

    /**
     * create a new Game
     *
     * @param
     * @return
     */
    public gameResponseCreate Game_Create(gameRequestCreate data) throws DataAccessException
    {
        System.out.println("game_create");


        try {
            return service_game.create(data);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Join a game
     *
     * @param
     * @return
     */
    public gameResponseJoin Game_Join(gameRequestJoin data) throws Exception
    {
        System.out.println("game_join");

        // Swaps the AuthToken with correct userName
        String auth_token = data.username(); // actually AuthToken at this point
        System.out.println("auth token: " + auth_token);
        String username = service_user.get_userName_via_authToken(auth_token);
        gameRequestJoin data_with_name = new gameRequestJoin(username, data.playerColor(), data.gameID());
        System.out.println("username: " + username);

        try {
            System.out.println("trying game_join");
            System.out.println(data_with_name);
            gameResponseJoin gameResponseJoinTemp = service_game.join(data_with_name);
            return gameResponseJoinTemp;
        } catch (DataAccessException e)
        {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * View a game in progress
     *
     * @param
     * @return
     */
    public ChessGame gameView(GameRequestView gameIDViewing) throws Exception
    {
        System.out.println("gameView");
        if (gameIDViewing == null)
        {
            throw new DataAccessException("400");
        }

        try {
            return service_game.view(gameIDViewing.gameID());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * List Games
     *
     * @param
     * @return
     */
    public gameResponseList Game_List(gameRequestList data) throws DataAccessException
    {
        System.out.println("game_list");

        try {
            return service_game.list(data);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public userResponseRegister User_Register(userRequestRegister data) throws DataAccessException
    {
        System.out.println("user_register");

        try {
            return service_user.register(data);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public userResponseLogin User_Login(userRequestLogin data) throws DataAccessException
    {
        System.out.println("user_login");


        try {
            return service_user.login(data);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }

    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public void User_Logout(userRequestLogout data) throws DataAccessException
    {
        System.out.println("user_logout");


        try {
            service_user.logout(data);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public boolean User_Authorized(String data) throws DataAccessException {
        System.out.println("user_authorize");


        try {
            return service_user.authorized(data);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }



}
