package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.GameDataShort;
import model.GameResponseList;

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
            createTableGameSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }

    }






    static public void createTableGameSQL() throws DataAccessException {
        System.out.println("createTableGameSQL");


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


    public int gameAddGameName(String nameAdding) throws DataAccessException
    {
        System.out.println("gameAddGameName " + nameAdding);



        var statement = "INSERT INTO GameSQL (gameName) VALUES ( \"" + nameAdding + "\" );";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }

        return gameGetGameIDViaGameName(nameAdding);



    }

    public int gameGetGameIDViaGameName(String gameName) throws DataAccessException
    {
        System.out.println("In gameGetGameIDViaGameName");

        var statement = "SELECT * FROM GameSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var gameNameFound = rs.getString("gameName");
                var gameIDFound = rs.getInt("gameID");
                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameNameFound, gameIDFound);
                if (gameNameFound != null)
                {
                    if (gameNameFound.equals(gameName))
                    {
                        System.out.println("Game Found!");
                        return gameIDFound;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }


        return 0;
    }


    public GameResponseList gameList() throws DataAccessException
    {
        System.out.println("In gameList");

        ArrayList<GameDataShort> gameListOutput = new ArrayList<>();

        var statement = "SELECT * FROM GameSQL;";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            System.out.printf(rs + "In GameList");
            while (rs.next()) {
                System.out.printf("In rs.next of GameList");
                var gameNameFound = rs.getString("gameName");
                var gameIDFound = rs.getInt("gameID");
                var whiteUsernameFound = rs.getString("whiteUsername");
                var blackUsernameFound = rs.getString("blackUsername");

                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameNameFound, gameIDFound);
                gameListOutput.add(new GameDataShort(gameIDFound, whiteUsernameFound, blackUsernameFound, gameNameFound));
            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }


        return new GameResponseList(gameListOutput);
    }



    public boolean gameFoundViaGameName(String finding) throws DataAccessException
    {
        System.out.println("In gameFoundViaGameName");

        var statement = "SELECT * FROM GameSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var gameNameFound = rs.getString("gameName");
                var gameIDFound = rs.getInt("gameID");
                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameNameFound, gameIDFound);
                if (gameNameFound != null)
                {
                    if (gameNameFound.equals(finding))
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


    public boolean gameFoundViaGameID(int finding) throws DataAccessException
    {
        System.out.println("In gameFoundViaGameID");

        var statement = "SELECT * FROM GameSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var gameNameFound = rs.getString("gameName");
                var gameIDFound = rs.getInt("gameID");
                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameNameFound, gameIDFound);

                if (gameIDFound == finding)
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
        System.out.println("In getGameDataViaGameID");

        var statement = "SELECT * FROM GameSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var gameNameFound = rs.getString("gameName");
                var gameIDFound = rs.getInt("gameID");
                var whiteUsernameFound = rs.getString("whiteUsername");
                var blackUsernameFound = rs.getString("blackUsername");

                // Read and deserialize the chessGame JSON.
                var json = rs.getString("chessGame");

                ChessGame chessGameFound;



                if (json == null)
                {
                    chessGameFound = new ChessGame();
                    System.out.println("Game is Null");
                }
                else
                {
                    System.out.println("Game found!\n");
                    System.out.println("json = " + json + "\n");
                    String jsonWithParenthesis = json.replace('\'', '"');
                    System.out.println("fixed = " + jsonWithParenthesis + "\n");

                    chessGameFound = new Gson().fromJson(jsonWithParenthesis, ChessGame.class);
                    System.out.println("Game = " + chessGameFound + "\n");

                }


                System.out.printf("User Found! gameName: %s, gameID: %s%n", gameNameFound, gameIDFound);

                if (gameIDFound == finding)
                {
                    System.out.println("Game Found!");
                    return new GameData(gameIDFound, whiteUsernameFound, blackUsernameFound, gameNameFound, chessGameFound);
                }

            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }

        return null;
    }








    public void gameDeleteViaGameID(int finding) throws DataAccessException
    {
        System.out.println("In gameDeleteViaGameID");
        var statement = "DELETE FROM GameSQL WHERE gameID=\"" + finding + "\";";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }
    }


    public void gameDeleteAll() throws DataAccessException
    {
        System.out.println("gameDeleteAll");

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
            createTableGameSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }
    }




    public void gameAddKeepGameID(GameData added) throws DataAccessException
    {
        System.out.println("gameAddKeepGameID " + added.gameName() + "\n");


        // Because this will only run when you add a player to it, I am giving the chessGame in the SQL a value at this point.
        // Serialize and store the friend JSON.
        var json = new Gson().toJson(added.chessGame());
        String jsonWithoutParenthesis = json.toString().replace('"', '\'');

        System.out.println("json = " + json + "\n");
        System.out.println("jsonWithoutParenthesis = " + jsonWithoutParenthesis + "\n");

        // System.out.println(new ChessGame() + "\n");
        // System.out.println(new ChessGame() + "\n");
        // System.out.println(new ChessGame() + "\n");

//        System.out.println(json + "\n");
//        System.out.println(json + "\n");
//        System.out.println(json + "\n");
//        System.out.println(json + "\n");


        var statement = ("INSERT INTO GameSQL (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES ( \"" + added.gameID() + "\" , \"" + added.whiteUsername() + "\" , \"" + added.blackUsername() + "\" , \"" + added.gameName() + "\" , \"" + jsonWithoutParenthesis + "\" );");

        // var statement = ("INSERT INTO GameSQL (gameID, whiteUsername, blackUsername, gameName) VALUES ( \"" + added.gameID() + "\" , \"" + added.whiteUsername() + "\" , \"" + added.blackUsername() + "\" , \"" + added.gameName() + "\" );");


        if (added.chessGame() == null)
        {
            // Add game new game to it at this point
            json = new Gson().toJson(new ChessGame());
            jsonWithoutParenthesis = json.toString().replace('"', '\'');

        }

        if (added.whiteUsername() == null && added.blackUsername() == null)
        {
            // if white Username is null, need to keep null
            System.out.println("whiteUsername() == null");
            System.out.println("blackUsername() == null");

            statement = ("INSERT INTO GameSQL (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES ( \"" + added.gameID() + "\" , NULL , NULL , \"" + added.gameName() + "\" , \"" + jsonWithoutParenthesis + "\" );");

        } else if (added.whiteUsername() == null)
        {
            // if white Username is null, need to keep null
            System.out.println("whiteUsername() == null");

            statement = ("INSERT INTO GameSQL (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES ( \"" + added.gameID() + "\" , NULL , \"" + added.blackUsername() + "\" , \"" + added.gameName() + "\" , \"" + jsonWithoutParenthesis + "\" );");

        } else if (added.blackUsername() == null)
        {
            // if black Username is null, need to keep null
            System.out.println("blackUsername() == null");

            statement = ("INSERT INTO GameSQL (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES ( \"" + added.gameID() + "\" , \"" + added.whiteUsername() + "\" , NULL , \"" + added.gameName() + "\" , \"" + jsonWithoutParenthesis + "\" );");

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
