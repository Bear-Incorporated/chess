package dataaccess;

import chess.ChessPiece;
import com.google.gson.Gson;
import io.javalin.http.Context;
import model.AuthData;
import model.GameData;
import model.UserData;
import model.User_Request_Logout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class AuthDAO
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
            createTable_AuthSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }

    }






    static public void createTable_AuthSQL() throws DataAccessException {
        System.out.println("createTable_AuthSQL");
        var statement = "CREATE TABLE IF NOT EXISTS AuthSQL (authToken VARCHAR(255) DEFAULT NULL, username VARCHAR(255) DEFAULT NULL);";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("failed to create table", ex);
        }
    }







    public AuthDAO() {

    }






    public void Auth_delete_all() throws DataAccessException {
        System.out.println("In Auth_delete_all");

        var statement = "DROP TABLE IF EXISTS AuthSQL;";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("failed to delete table", ex);
        }

        try
        {
            createTable_AuthSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }
    }



    public String Auth_get_userName_via_authToken(String authToken) throws DataAccessException
    {
        System.out.println("In Auth_get_userName_via_authToken");

        var statement = "SELECT * FROM AuthSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var authToken_found = rs.getString("authToken");
                var username_found = rs.getString("username");
                System.out.printf("User Found! AuthToken: %s, username: %s%n", authToken_found, username_found);
                if (authToken_found != null)
                {
                    if (authToken_found.equals(authToken))
                    {
                        System.out.println("User Found!");
                        return username_found;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("failed to add user", ex);
        }


        return "";

    }



    public Boolean authorized_via_authToken(String data) throws DataAccessException
    {
        System.out.println("In authorized_via_authToken");

        var statement = "SELECT * FROM AuthSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var authToken_found = rs.getString("authToken");
                var username_found = rs.getString("username");
                System.out.printf("User Found! AuthToken: %s, username: %s%n", authToken_found, username_found);
                if (authToken_found != null)
                {
                    if (authToken_found.equals(data))
                    {
                        System.out.println("User Authorized!");
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("failed to find user", ex);
        }



        return false;
    }

    public Boolean authorized_via_username(String new_name) throws DataAccessException
    {
        System.out.println("In authorized_via_username");



        var statement = "SELECT * FROM AuthSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var authToken_found = rs.getString("authToken");
                var username_found = rs.getString("username");
                System.out.printf("User Found! AuthToken: %s, username: %s%n", authToken_found, username_found);
                if (username_found != null)
                {
                    if (username_found.equals(new_name))
                    {
                        System.out.println("User Authorized!");
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("failed to find user", ex);
        }



        return false;


    }



    public void Auth_add(AuthData added) throws DataAccessException
    {
        System.out.println("In Auth_add adding " + added.toString());


        var statement = "INSERT INTO AuthSQL (authToken, username) VALUES ( \"" + added.authToken() + "\" , \"" + added.username() + "\" );";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("failed to add user", ex);
        }
    }


    public void Auth_delete_via_authToken(String removed) throws DataAccessException
    {
        System.out.println("In Auth_delete_via_authToken");
        var statement = "DELETE FROM AuthSQL WHERE authToken=\"" + removed + "\";";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("failed to delete user", ex);
        }
    }

}
