package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.*;

public class UserService
{

    private final UserDAO user_list = new UserDAO();

    /**
     * Register new User
     *
     * @param
     * @return
     */
    public User_Response_Register register(User_Request_Register data) {
        System.out.println("in the function");
        System.out.println("username: " + data.username());
        System.out.println("email: " + data.email());
        System.out.println("password: " + data.password());

        String output_auth = user_list.User_add(new UserData(data.username(), data.password(), data.email()));

        System.out.println("auth: " + output_auth);
        return new User_Response_Register(output_auth, data.username());
    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public User_Response_Login login(User_Request_Login data) {
        System.out.println("Checking user_list.User_find_name ");
        String output_auth = user_list.User_find_name(new UserData(data.username(), data.password(), null));

        System.out.println("auth: " + output_auth);
        if (output_auth == null)
        {
            return new User_Response_Login(null, null, false);
        }
        return new User_Response_Login(output_auth, data.username(), true);
    }

    /**
     * CreateResult
     *
     * @param
     * @return
     */
    public User_Response_Logout logout(User_Request_Logout data) {
        throw new RuntimeException("Not implemented");
    }


    public Clear_Response clear(Clear_Request data) {
        user_list.User_delete_all();
        return new Clear_Response();
    }

    public Boolean authorized(String data) {
        return user_list.authorized(data);
    }

}
