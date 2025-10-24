package dataaccess;

import com.google.gson.Gson;
import io.javalin.http.Context;
import model.*;

import java.util.ArrayList;
import java.util.Map;


public class UserDAO
{

    private ArrayList<UserData> User_List;




    public UserDAO() {
        User_List = new ArrayList<>();

    }



    public ArrayList<UserData> User_list() {
        return User_List;
    }



    public void User_delete(UserData removed) {
        User_List.remove(removed);
    }

    public void User_delete_all() {
        User_List.clear();

    }



    public void User_add(UserData added) {
        User_List.add(added);
    }

    public boolean User_found_via_username(String username){

        for (int i = 0; i < User_List.size(); i++)
        {
            if (User_List.get(i).username().equals(username))
            {
                return true;
            }
        }
        return false;
    }

    public boolean User_login_credentials(UserData logging_in) {

        for (int i = 0; i < User_List.size(); i++)
        {
            System.out.println("Name Check " + i);
            System.out.println("Name Looking " + logging_in.username());
            System.out.println("Name Find " + User_List.get(i).username());
            if (User_List.get(i).username().equals(logging_in.username()))
            {
                System.out.println("Name Found!");
                if (User_List.get(i).password().equals(logging_in.password()))
                {
                    System.out.println("Password Correct!");
                    return true;
                }
            }
        }
        return false;
    }





}
