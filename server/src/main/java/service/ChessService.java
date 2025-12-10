package service;


import chess.ChessGame;
import dataaccess.DataAccessException;
import model.*;

public class ChessService
{

    private final GameService serviceGame = new GameService();
    private final UserService serviceUser = new UserService();


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
            serviceGame.clear();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }


        try {
            serviceUser.clear();
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
    public GameResponseCreate gameCreate(GameRequestCreate data) throws DataAccessException
    {
        System.out.println("game_create");


        try {
            return serviceGame.create(data);
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
    public GameResponseJoin gameJoin(GameRequestJoin data) throws Exception
    {
        System.out.println("game_join");

        // Swaps the AuthToken with correct userName
        String auth_token = data.username(); // actually AuthToken at this point
        System.out.println("auth token: " + auth_token);
        String username = serviceUser.getUserNameViaAuthToken(auth_token);
        GameRequestJoin data_with_name = new GameRequestJoin(username, data.playerColor(), data.gameID());
        System.out.println("username: " + username);

        try {
            System.out.println("trying game_join");
            System.out.println(data_with_name);
            GameResponseJoin gameResponseJoinTemp = serviceGame.join(data_with_name);
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
            return serviceGame.view(gameIDViewing.gameID());
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
    public GameResponseList gameList(GameRequestList data) throws DataAccessException
    {
        System.out.println("game_list");

        try {
            return serviceGame.list(data);
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
    public UserResponseRegister userRegister(UserRequestRegister data) throws DataAccessException
    {
        System.out.println("user_register");

        try {
            return serviceUser.register(data);
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
    public UserResponseLogin userLogin(UserRequestLogin data) throws DataAccessException
    {
        System.out.println("user_login");


        try {
            return serviceUser.login(data);
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
    public void userLogout(UserRequestLogout data) throws DataAccessException
    {
        System.out.println("user_logout");


        try {
            serviceUser.logout(data);
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
    public boolean userAuthorized(String data) throws DataAccessException {
        System.out.println("user_authorize");


        try {
            return serviceUser.authorized(data);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }



}
