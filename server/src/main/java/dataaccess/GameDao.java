package dataaccess;

import com.google.gson.Gson;
import io.javalin.http.Context;
import model.*;

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
        Game_List.add(new GameData(gameID_current, added.whiteUsername(), added.blackUsername(), added.gameName(), added.chessGame()));
        gameID_current ++;

    }

    public void Game_add_keep_gameID(GameData added) {
        Game_List.add(added);
        gameID_current ++;

    }

    public int Game_add_gameName(String name_adding) {
        GameData game_adding = new GameData(gameID_current, null, null, name_adding, null);
        Game_add(game_adding);
        return game_adding.gameID();
    }

    public Game_Response_List Game_list() {
        ArrayList<GameData_Short> Game_List_output = new ArrayList<>();

        for (int i = 0; i < Game_List.size(); i++)
        {
            Game_List_output.add(new GameData_Short(Game_List.get(i).gameID(), Game_List.get(i).whiteUsername(), Game_List.get(i).blackUsername(), Game_List.get(i).gameName()));

        }

        return new Game_Response_List(Game_List_output);
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
                return new GameData(Game_List.get(i).gameID(), Game_List.get(i).whiteUsername(), Game_List.get(i).blackUsername(), Game_List.get(i).gameName(), Game_List.get(i).chessGame());
            }
        }
        return null;
    }

    public void Game_delete_via_gameID(int finding) {
        for (int i = 0; i < Game_List.size(); i++)
        {
            if (Game_List.get(i).gameID() == finding)
            {
                Game_List.remove(Game_List.get(i));
            }
        }
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
