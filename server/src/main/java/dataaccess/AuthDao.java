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
    private ArrayList<AuthData> Auth_List;

    public AuthDAO() {
        Auth_List = new ArrayList<>();
    }


    public ArrayList<AuthData> Auth_list() {
        return Auth_List;
    }

    public void Auth_delete(AuthData removed) {
        Auth_List.remove(removed);
    }

    public void Auth_delete_all() {
        Auth_List.clear();
    }



    public String Auth_get_userName_via_authToken(String authToken)
    {
        for (int i = 0; i < Auth_List.size(); i++)
        {
            if (Auth_List.get(i).authToken().equals(authToken))
            {
                return Auth_List.get(i).username();
            }
        }
        return "";
    }

    public void Auth_delete_via_authToken(String removed)
    {
        for (int i = 0; i < Auth_List.size(); i++)
        {
            if (Auth_List.get(i).authToken().equals(removed))
            {
                Auth_List.remove(Auth_List.get(i));
            }
        }
    }


    public Boolean authorized(String data) {
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
        Auth_List.add(added);



    }
}
