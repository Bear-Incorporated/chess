package dataaccess;

import com.google.gson.Gson;
import io.javalin.http.Context;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Map;

public class GameDAO
{
    private ArrayList<GameData> Game_List = new ArrayList<>();

    public void Game_add(GameData added) {
        Game_List.add(added);
    }

    public ArrayList<GameData> Game_list() {
        return Game_List;
    }

    public void Game_delete(GameData removed) {
        Game_List.remove(removed);
    }
}
