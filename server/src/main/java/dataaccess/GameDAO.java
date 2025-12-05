package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.GameData_Short;
import model.Game_Response_List;

import java.sql.SQLException;
import java.util.ArrayList;

public class GameDAO
{



    /*
     * Start the database to begin with
     */
    static {
        try
        {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }
        try
        {
            createTable_GameSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }

    }






    static public void createTable_GameSQL() throws DataAccessException {
        System.out.println("createTable_GameSQL");


        var statement = "CREATE TABLE IF NOT EXISTS GameSQL (gameID INT NOT NULL AUTO_INCREMENT, whiteUsername VARCHAR(255) DEFAULT NULL, blackUsername VARCHAR(255) DEFAULT NULL, gameName VARCHAR(255) DEFAULT NULL, chessGame LONGTEXT DEFAULT NULL, PRIMARY KEY(gameID));";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }
    }


    public GameDAO() {

    }


    public int Game_add_gameName(String name_adding) throws DataAccessException
    {
        System.out.println("Game_add_gameName " + name_adding);



        var statement = "INSERT INTO GameSQL (gameName) VALUES ( \"" + name_adding + "\" );";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }

        return Game_get_gameID_via_gameName(name_adding);



    }

    public int Game_get_gameID_via_gameName(String gameName) throws DataAccessException
    {
        System.out.println("In Game_get_gameID_via_gameName");

        var statement = "SELECT * FROM GameSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var gameName_found = rs.getString("gameName");
                var gameID_found = rs.getInt("gameID");
                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameName_found, gameID_found);
                if (gameName_found != null)
                {
                    if (gameName_found.equals(gameName))
                    {
                        System.out.println("Game Found!");
                        return gameID_found;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }


        return 0;
    }


    public Game_Response_List Game_list() throws DataAccessException
    {
        System.out.println("In Game_list");

        ArrayList<GameData_Short> Game_List_output = new ArrayList<>();

        var statement = "SELECT * FROM GameSQL;";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            System.out.printf(rs + "In Game_list");
            while (rs.next()) {
                System.out.printf("In rs.next of Game_list");
                var gameName_found = rs.getString("gameName");
                var gameID_found = rs.getInt("gameID");
                var whiteUsername_found = rs.getString("whiteUsername");
                var blackUsername_found = rs.getString("blackUsername");

                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameName_found, gameID_found);
                Game_List_output.add(new GameData_Short(gameID_found, whiteUsername_found, blackUsername_found, gameName_found));
            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }


        return new Game_Response_List(Game_List_output);
    }



    public boolean Game_found_via_gameName(String finding) throws DataAccessException
    {
        System.out.println("In Game_found_via_gameName");

        var statement = "SELECT * FROM GameSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var gameName_found = rs.getString("gameName");
                var gameID_found = rs.getInt("gameID");
                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameName_found, gameID_found);
                if (gameName_found != null)
                {
                    if (gameName_found.equals(finding))
                    {
                        System.out.println("Game Found!");
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }


        return false;


    }


    public boolean Game_found_via_gameID(int finding) throws DataAccessException
    {
        System.out.println("In Game_found_via_gameName");

        var statement = "SELECT * FROM GameSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var gameName_found = rs.getString("gameName");
                var gameID_found = rs.getInt("gameID");
                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameName_found, gameID_found);

                if (gameID_found == finding)
                {
                    System.out.println("Game Found!");
                    return true;
                }

            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }


        return false;

    }

    public GameData getGameDataViaGameID(int finding) throws DataAccessException
    {
        System.out.println("In Game_get_via_gameID");

        var statement = "SELECT * FROM GameSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var gameName_found = rs.getString("gameName");
                var gameID_found = rs.getInt("gameID");
                var whiteUsername_found = rs.getString("whiteUsername");
                var blackUsername_found = rs.getString("blackUsername");

                // Read and deserialize the chessGame JSON.
                var json = rs.getString("chessGame");
                ChessGame chessGame_found = new Gson().fromJson(json, ChessGame.class);

                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameName_found, gameID_found);

                if (gameID_found == finding)
                {
                    System.out.println("Game Found!");
                    return new GameData(gameID_found, whiteUsername_found, blackUsername_found, gameName_found, chessGame_found);
                }

            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }

        return null;
    }








    public void Game_delete_via_gameID(int finding) throws DataAccessException
    {
        System.out.println("In Game_delete_via_gameID");
        var statement = "DELETE FROM GameSQL WHERE gameID=\"" + finding + "\";";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }
    }


    public void Game_delete_all() throws DataAccessException
    {
        System.out.println("Game_delete_all");

        var statement = "DROP TABLE IF EXISTS GameSQL;";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection())
        {
            try (var preparedStatement = conn.prepareStatement(statement))
            {
                preparedStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }

        try
        {
            createTable_GameSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }
    }




    public void Game_add_keep_gameID(GameData added) throws DataAccessException
    {
        System.out.println("Game_add_keep_gameID " + added.gameName());


        // Because this will only run when you add a player to it, I am giving the chessGame in the SQL a value at this point.
        // Serialize and store the friend JSON.
        var json = new Gson().toJson(new ChessGame());

        var statement = ("INSERT INTO GameSQL (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES ( \"" + added.gameID() + "\" , \"" + added.whiteUsername() + "\" , \"" + added.blackUsername() + "\" , \"" + added.gameName() + "\" , \"" + json + "\" );");

        // var statement = ("INSERT INTO GameSQL (gameID, whiteUsername, blackUsername, gameName) VALUES ( \"" + added.gameID() + "\" , \"" + added.whiteUsername() + "\" , \"" + added.blackUsername() + "\" , \"" + added.gameName() + "\" );");



        if (added.whiteUsername() == null)
        {
            // if white Username is null, need to keep null
            statement = ("INSERT INTO GameSQL (gameID, whiteUsername, blackUsername, gameName) VALUES ( \"" + added.gameID() + "\" , null , \"" + added.blackUsername() + "\" , \"" + added.gameName() + "\" );");
        } else if (added.blackUsername() == null)
        {
            // if black Username is null, need to keep null
            statement = ("INSERT INTO GameSQL (gameID, whiteUsername, blackUsername, gameName) VALUES ( \"" + added.gameID() + "\" , \"" + added.whiteUsername() + "\" , null , \"" + added.gameName() + "\" );");
        }




        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500", ex);
        }
    }



}
