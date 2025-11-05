package dataaccess;

import chess.ChessPiece;
import com.google.gson.Gson;
import io.javalin.http.Context;
import model.AuthData;
import model.GameData;
import model.UserData;
import model.User_Request_Logout;

import java.util.ArrayList;
import java.util.Map;

public class AuthDAO
{
    private final ArrayList<AuthData> Auth_List;

    public AuthDAO() {
        Auth_List = new ArrayList<>();
    }




    public ArrayList<AuthData> Auth_list() {
        System.out.println("In Auth_list");
        return Auth_List;
    }

    public void Auth_delete(AuthData removed) {
        System.out.println("In AuthData");
        Auth_List.remove(removed);
    }

    public void Auth_delete_all() {
        System.out.println("In Auth_delete_all");
        Auth_List.clear();
    }



    public String Auth_get_userName_via_authToken(String authToken)
    {
        System.out.println("In Auth_get_userName_via_authToken");
        System.out.println("Auth_List has " + Auth_List.size() + " entries");
        System.out.println("Full Auth_List " + Auth_List);
        for (int i = 0; i < Auth_List.size(); i++)
        {
            System.out.println("Checking i = " + i);
            System.out.println("authToken in = " + authToken);
            System.out.println("authToken " + i + " = " + Auth_List.get(i).authToken());
            if (Auth_List.get(i).authToken().equals(authToken))
            {
                return Auth_List.get(i).username();
            }
        }
        return "";
    }

    public void Auth_delete_via_authToken(String removed)
    {
        System.out.println("In Auth_delete_via_authToken");
        for (int i = 0; i < Auth_List.size(); i++)
        {
            if (Auth_List.get(i).authToken().equals(removed))
            {
                Auth_List.remove(Auth_List.get(i));
                return;
            }
        }
    }


    public Boolean authorized(String data) {
        System.out.println("In authorized");
        for (int i = 0; i < Auth_List.size(); i++)
        {
            if (Auth_List.get(i).authToken().equals(data))
            {
                return true;
            }
        }
        return false;
    }

    public Boolean authorized_via_username(String new_name) {
        System.out.println("In authorized_via_username");
        System.out.println("List has " + Auth_List.size() + "entries");
        System.out.println("Full Auth_List " + Auth_List);
        for (int i = 0; i < Auth_List.size(); i++)
        {
            if (Auth_List.get(i).username().equals(new_name))
            {
                return true;
            }
        }
        return false;
    }

    public String Auth_get_authToken_via_username(String new_name) {
        System.out.println("In Auth_get_authToken_via_username");
        System.out.println("List has " + Auth_List.size() + "entries");
        System.out.println("Full Auth_List " + Auth_List);
        for (int i = 0; i < Auth_List.size(); i++)
        {
            if (Auth_List.get(i).username().equals(new_name))
            {
                return Auth_List.get(i).authToken();
            }
        }
        return "";
    }

    public void Auth_add(AuthData added) {
        System.out.println("In Auth_add adding " + added.toString());
        Auth_List.add(added);
        System.out.println("Auth_List " + Auth_List.toString());
        System.out.println("Auth_List is " + Auth_List.size() + " long");


    }
}
