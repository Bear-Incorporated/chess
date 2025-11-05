package dataaccess;

import com.google.gson.Gson;
import io.javalin.http.Context;
import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;


import java.sql.*;


public class UserDAO
{

    private ArrayList<UserData> User_List;










    private static String databaseName;
    private static String dbUsername;
    private static String dbPassword;
    private static String connectionUrl;

    /*
     * Load the database information for the db.properties file.
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
            createTable_UserSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }

    }






    static public void createTable_UserSQL() throws DataAccessException {
        System.out.println("createTable_UserSQL");
        var statement = "CREATE TABLE IF NOT EXISTS UserSQL (username VARCHAR(255) DEFAULT NULL, password VARCHAR(255) DEFAULT NULL, email VARCHAR(255) DEFAULT NULL);";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("failed to create table", ex);
        }
    }








    public UserDAO() {
        User_List = new ArrayList<>();
        System.out.println("UserDAO");
    }




    public void User_delete_all() throws DataAccessException {
        System.out.println("User_delete_all");

        var statement = "DROP TABLE IF EXISTS UserSQL;";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("failed to delete all", ex);
        }

        try
        {
            createTable_UserSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }
    }



    public void User_add(UserData added) throws DataAccessException {
        System.out.println("User_add" + added.username().toString());

        var statement = "INSERT INTO UserSQL (username, password, email) VALUES ( \"" + added.username() + "\" , \"" + added.password() + "\" , \"" + added.email() + "\" );";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("failed to add user", ex);
        }
    }

    public boolean User_found_via_username(String username) throws DataAccessException{
        String output;

        System.out.println("User_found_via_username");
        var statement = "SELECT * FROM UserSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var username_found = rs.getString("username");
                var password_found = rs.getString("password");
                var email_found = rs.getString("email");
                System.out.printf("User Found! username: %s, password: %s, email: %s%n", username_found, password_found, email_found);
                if (username_found != null)
                {
                    if (username_found.equals(username))
                    {
                        System.out.println("User Found!");
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("failed to add user", ex);
        }


        return false;
    }

    public boolean User_login_credentials(UserData logging_in) throws DataAccessException {

        System.out.println("User_login_credentials " + logging_in.username());
        var statement = "SELECT * FROM UserSQL;";

        System.out.println("User_login_credentials 2 " + statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            System.out.println("User_login_credentials 3" + rs.toString());
            while (rs.next()) {
                System.out.println("User_login_credentials 4");
                var username_found = rs.getString("username");
                var password_found = rs.getString("password");
                var email_found = rs.getString("email");
                System.out.printf("User Found! username: %s, password: %s, email: %s%n", username_found, password_found, email_found);
                System.out.println("Password Have: " + logging_in.password());
                if (password_found != null)
                {
                    if (password_found.equals(logging_in.password()))
                    {
                        System.out.println("Password Correct!");
                        return true;
                    }
                }
            }
            System.out.println("User_login_credentials 5");
        } catch (SQLException ex) {
            throw new DataAccessException("failed to add user", ex);
        }


        return false;


    }





}
