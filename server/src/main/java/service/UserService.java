package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.*;

import java.util.ArrayList;
import java.util.Random;

public class UserService
{

    private UserDAO user_list = new UserDAO();
    private AuthDAO auth_list = new AuthDAO();

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
    public User_Response_Register register(User_Request_Register added) throws DataAccessException
    {
        System.out.println("in the function");
        System.out.println("username: " + added.username());
        System.out.println("email: " + added.email());
        System.out.println("password: " + added.password());

        if (added.username() == null)
        {
            return new User_Response_Register("404", "404");
        }
        if (added.password() == null)
        {
            return new User_Response_Register("404", "404");
        }
        if (added.email() == null)
        {
            return new User_Response_Register("404", "404");
        }
        // Check to see if name already used
        if (user_list.User_found_via_username(added.username()))
        {
            return new User_Response_Register("404", "404");
        }

        // Add to user list
        user_list.User_add(new UserData(added.username(), added.password(), added.email()));

        User_Response_Login output_login = login(new User_Request_Login(added.username(), added.password()));

        return new User_Response_Register(added.username(), output_login.authToken());


    }



    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public User_Response_Logout logout(User_Request_Logout data) {
        auth_list.Auth_delete_via_authToken(data.authToken());
        return new User_Response_Logout();
    }



    public Clear_Response clear(Clear_Request data) {
        user_list.User_delete_all();
        return new Clear_Response();
    }

    public Boolean authorized(String data) {
        return auth_list.authorized(data);
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
    public User_Response_Login login(User_Request_Login data) throws DataAccessException
    {
        System.out.println("Checking user_list.User_find_name ");
        String output_auth = User_login(data);

        System.out.println("auth: " + output_auth);
        if (output_auth.equals(""))
        {
            throw new DataAccessException("401");
        }
        return new User_Response_Login(data.username(), output_auth);
    }

    public String User_login(User_Request_Login loginer) throws DataAccessException
    {
        System.out.println("Looking for Name!" + loginer.username());
        String name = loginer.username();

        System.out.println("Looking for " + loginer);

        // Check to see if using wrong username and password
        if (!user_list.User_login_credentials(new UserData(loginer.username(), loginer.password(), loginer.username())))
        {
            throw new DataAccessException("401");
        }



        // If they already are logged in, send them their current authtoken back
        if (auth_list.authorized_via_username(loginer.username()))
        {
            return auth_list.Auth_get_authToken_via_username(loginer.username());
        }



        // Login the new user
        String authToken =  loginer.username() + loginer.password() + randInt();
        auth_list.Auth_add(new AuthData(authToken, loginer.username()));

        return authToken;

    }

}
