package dataaccess;

import com.google.gson.Gson;
import io.javalin.http.Context;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Map;

public class UserDAO
{

    private ArrayList<UserData> User_List = new ArrayList<>();

    public void User_add(UserData added) {
        User_List.add(added);
    }

    public ArrayList<UserData> User_list() {
        return User_List;
    }

    public void User_delete(UserData removed) {
        User_List.remove(removed);
    }
}
