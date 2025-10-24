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

    public int Game_add_gameName(String added) {
        GameData game_adding = new GameData(gameID_current, null, null, added, null);
        Game_add(game_adding);
        return game_adding.gameID();
    }

    public ArrayList<GameData> Game_list() {
        return Game_List;
    }

    public boolean Game_found_via_gameName(String finding) {
        for (int i = 0; i < Game_List.size(); i++)
        {
            if (Game_List.get(i).gameName().equals(finding))
            {
                return true;
            }
        }

        return false;
    }

    public boolean Game_found_via_gameID(int finding) {
        for (int i = 0; i < Game_List.size(); i++)
        {
            if (Game_List.get(i).gameID() == finding)
            {
                return true;
            }
        }

        return false;
    }

    public GameData Game_get_via_gameID(int finding) {
        for (int i = 0; i < Game_List.size(); i++)
        {
            if (Game_List.get(i).gameID() == finding)
            {
                return new GameData(Game_List.get(i).gameID(), Game_List.get(i).whiteUsername(), Game_List.get(i).blackUsername(), Game_List.get(i).gameName(), Game_List.get(i).game());
            }
        }
        return null;
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
