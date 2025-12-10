package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.*;

import java.util.Random;

public class UserService
{

    private UserDAO userList;
    private AuthDAO authList;

    Random rand;




    public UserService(){
        userList = new UserDAO();
        authList = new AuthDAO();
        rand = new Random();
    }
    /**
     * Register new User
     *
     * @param
     * @return
     */
    public UserResponseRegister register(UserRequestRegister added) throws DataAccessException
    {
        System.out.println("in the function");
        System.out.println("username: " + added.username());
        System.out.println("email: " + added.email());
        System.out.println("password: " + added.password());

        if (added.username() == null)
        {
            return new UserResponseRegister("404", "404");
        }
        if (added.password() == null)
        {
            return new UserResponseRegister("404", "404");
        }
        if (added.email() == null)
        {
            return new UserResponseRegister("404", "404");
        }
        // Check to see if name already used
        Boolean userFound = false;

        try {
            userFound = userList.userFoundViaUsername(added.username());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }

        if (userFound)
        {
            return new UserResponseRegister("404", "404");
        }

        // Add to user list
        userList.userAdd(new UserData(added.username(), added.password(), added.email()));

        UserResponseLogin outputLogin = login(new UserRequestLogin(added.username(), added.password()));

        return new UserResponseRegister(added.username(), outputLogin.authToken());


    }



    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public void logout(UserRequestLogout data) throws DataAccessException
    {
        try {
            authList.authDeleteViaAuthToken(data.authToken());
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
        return;
    }



    public String getUserNameViaAuthToken(String data) throws DataAccessException
    {
        return authList.authGetUserNameViaAuthToken(data);
    }

    public void clear() throws DataAccessException
    {

        try {
            userList.userDeleteAll();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
        try {
            authList.authDeleteAll();
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
        return;

    }

    public Boolean authorized(String data) throws DataAccessException {
        try {
            return authList.authorizedViaAuthToken(data);
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
    public UserResponseLogin login(UserRequestLogin data) throws DataAccessException
    {
        System.out.println("Checking user_list.User_find_name ");
        String outputAuth = userLogin(data);

        System.out.println("auth: " + outputAuth);
        if (outputAuth.equals(""))
        {
            throw new DataAccessException("401");
        }
        return new UserResponseLogin(data.username(), outputAuth);
    }

    public String userLogin(UserRequestLogin loginer) throws DataAccessException
    {
        System.out.println("Looking for Name!" + loginer.username());
        String name = loginer.username();

        System.out.println("Looking for " + loginer);

        // Check to see if using wrong username and password
        if (!userList.userLoginCredentials(new UserData(loginer.username(), loginer.password(), loginer.username())))
        {
            throw new DataAccessException("401");
        }



        // If they already are logged in, logout and log back in
        // Apparently don't do that, because we want to be easily accessible
        if (authList.authorizedViaUsername(loginer.username()))
        {
            // auth_list.Auth_delete_via_authToken(auth_list.Auth_get_authToken_via_username(loginer.username()));

        }



        // Login the new user

        String authToken =  loginer.username() + randInt();
        System.out.println("New Auth Token " + authToken + " for " + loginer.username());
        authList.authAdd(new AuthData(authToken, loginer.username()));

        return authToken;

    }

}
