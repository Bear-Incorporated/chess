package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.*;

import java.util.Random;

public class UserService
{

    private UserDAO user_list;
    private AuthDAO auth_list;

    Random rand;




    public UserService(){
        user_list = new UserDAO();
        auth_list = new AuthDAO();
        rand = new Random();
    }
    /**
     * Register new User
     *
     * @param
     * @return
     */
    public userResponseRegister register(userRequestRegister added) throws DataAccessException
    {
        System.out.println("in the function");
        System.out.println("username: " + added.username());
        System.out.println("email: " + added.email());
        System.out.println("password: " + added.password());

        if (added.username() == null)
        {
            return new userResponseRegister("404", "404");
        }
        if (added.password() == null)
        {
            return new userResponseRegister("404", "404");
        }
        if (added.email() == null)
        {
            return new userResponseRegister("404", "404");
        }
        // Check to see if name already used
        Boolean user_found = false;

        try {
            user_found = user_list.userFoundViaUsername(added.username());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }

        if (user_found)
        {
            return new userResponseRegister("404", "404");
        }

        // Add to user list
        user_list.userAdd(new UserData(added.username(), added.password(), added.email()));

        userResponseLogin output_login = login(new userRequestLogin(added.username(), added.password()));

        return new userResponseRegister(added.username(), output_login.authToken());


    }



    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public void logout(userRequestLogout data) throws DataAccessException
    {
        try {
            auth_list.authDeleteViaAuthToken(data.authToken());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
        return;
    }



    public String getUserNameViaAuthToken(String data) throws DataAccessException
    {
        return auth_list.authGetUserNameViaAuthToken(data);
    }

    public void clear() throws DataAccessException
    {

        try {
            user_list.userDeleteAll();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
        try {
            auth_list.authDeleteAll();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
        return;

    }

    public Boolean authorized(String data) throws DataAccessException {
        try {
            return auth_list.authorizedViaAuthToken(data);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }

    }


    public int randInt() {

        // create an object of Random class

        return rand.nextInt();
    }





    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public userResponseLogin login(userRequestLogin data) throws DataAccessException
    {
        System.out.println("Checking user_list.User_find_name ");
        String output_auth = userLogin(data);

        System.out.println("auth: " + output_auth);
        if (output_auth.equals(""))
        {
            throw new DataAccessException("401");
        }
        return new userResponseLogin(data.username(), output_auth);
    }

    public String userLogin(userRequestLogin loginer) throws DataAccessException
    {
        System.out.println("Looking for Name!" + loginer.username());
        String name = loginer.username();

        System.out.println("Looking for " + loginer);

        // Check to see if using wrong username and password
        if (!user_list.userLoginCredentials(new UserData(loginer.username(), loginer.password(), loginer.username())))
        {
            throw new DataAccessException("401");
        }



        // If they already are logged in, logout and log back in
        // Apparently don't do that, because we want to be easily accessible
        if (auth_list.authorizedViaUsername(loginer.username()))
        {
            // auth_list.Auth_delete_via_authToken(auth_list.Auth_get_authToken_via_username(loginer.username()));

        }



        // Login the new user

        String authToken =  loginer.username() + randInt();
        System.out.println("New Auth Token " + authToken + " for " + loginer.username());
        auth_list.authAdd(new AuthData(authToken, loginer.username()));

        return authToken;

    }

}
