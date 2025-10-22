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
    private ArrayList<GameData> Game_List;
    private int gameID_current;

    public GameDAO() {
        Game_List = new ArrayList<>();
        gameID_current = 1;
    }

    public void Game_add(GameData added) {
        Game_List.add(new GameData(gameID_current, added.whiteUsername(), added.blackUsername(), added.gameName(), added.game()));
        gameID_current ++;
    }

    public ArrayList<GameData> Game_list() {
        return Game_List;
    }

    public void Game_delete(GameData removed) {
        if (Game_List.contains(removed))
        {
            Game_List.remove(removed);
            gameID_current--;
        }
    }

    public void Game_delete_all() {
        Game_List.clear();
        gameID_current = 1;
    }


}
