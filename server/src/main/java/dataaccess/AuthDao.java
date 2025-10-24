package dataaccess;

import chess.ChessPiece;
import com.google.gson.Gson;
import io.javalin.http.Context;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Map;

public class AuthDAO
{
    private ArrayList<AuthData> Auth_List;

    public AuthDAO() {
        Auth_List = new ArrayList<>();
    }
    public void Auth_add(AuthData added) {
        Auth_List.add(added);
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
        return "Not created yet";
    }
}
