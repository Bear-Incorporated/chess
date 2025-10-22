package dataaccess;

import com.google.gson.Gson;
import io.javalin.http.Context;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class UserDAO
{

    private ArrayList<UserData> User_List;

    Random rand;

    private ArrayList<String> User_List_Authorized;

    public UserDAO() {
        User_List = new ArrayList<>();
        User_List_Authorized = new ArrayList<>();
        rand = new Random();
    }

    public String User_add(UserData added) {
        String authToken =  added.username() + added.password() + randInt();
        User_List_Authorized.add(authToken);
        System.out.println("Added " + authToken + " to List");
        System.out.println(User_List_Authorized.size() + "tokens in List");

        User_List.add(added);
        return authToken;
    }

    public String User_find_name(UserData finding) {
        System.out.println("Looking for Name!" + finding.username());
        String name = finding.username();
        System.out.println("List Size is " + User_List.size());

        for (int i = 0; i < User_List.size(); i++)
        {
            System.out.println("Name Check " + i);
            System.out.println("Name Looking " + name);
            System.out.println("Name Find " + User_List.get(i).username());
            if (User_List.get(i).username().equals(name))
            {
                System.out.println("Name Found!");
                if (User_List.get(i).password().equals(finding.password()))
                {
                    System.out.println("Password Correct!");
                    String authToken =  finding.username() + finding.password() + randInt();
                    User_List_Authorized.add(authToken);
                    return authToken;
                }
            }
        }
        return null;
    }

    public ArrayList<UserData> User_list() {
        return User_List;
    }

    public void User_delete(UserData removed) {
        User_List.remove(removed);
    }

    public void User_delete_all() {
        User_List.clear();
        User_List_Authorized.clear();
    }

    public int randInt() {

        // create an object of Random class

        return rand.nextInt();
    }

    public Boolean authorized(String data) {
        for (int i = 0; i < User_List_Authorized.size(); i++)
        {
            if (User_List_Authorized.get(i).equals(data))
            {
                return true;
            }
        }
        return false;
    }

}
