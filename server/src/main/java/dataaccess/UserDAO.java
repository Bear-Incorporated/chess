package dataaccess;

import model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;


public class UserDAO
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
            createTableUserSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }

    }






    static public void createTableUserSQL() throws DataAccessException {
        System.out.println("createTable_UserSQL");
        var statement = "CREATE TABLE IF NOT EXISTS UserSQL (username VARCHAR(255) DEFAULT NULL, password VARCHAR(255) " +
                "DEFAULT NULL, email VARCHAR(255) DEFAULT NULL);";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }
    }








    public UserDAO() {
        System.out.println("UserDAO");
    }




    public void userDeleteAll() throws DataAccessException {
        System.out.println("User_delete_all");

        var statement = "DROP TABLE IF EXISTS UserSQL;";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }

        try
        {
            createTableUserSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }
    }



    public void userAdd(UserData added) throws DataAccessException {
        System.out.println("User_add" + added.username().toString());

        String hashedPassword = BCrypt.hashpw(added.password(), BCrypt.gensalt());

        var statement = "INSERT INTO UserSQL (username, password, email) VALUES ( \"" + added.username() + "\" , \"" +
                hashedPassword + "\" , \"" + added.email() + "\" );";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }
    }

    public boolean userFoundViaUsername(String username) throws DataAccessException{
        String output;

        System.out.println("User_found_via_username");
        var statement = "SELECT * FROM UserSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var usernameFound = rs.getString("username");
                var passwordFound = rs.getString("password");
                var emailFound = rs.getString("email");
                System.out.printf("User Found! username: %s, password: %s, email: %s%n", usernameFound, passwordFound, emailFound);
                if (usernameFound != null)
                {
                    if (usernameFound.equals(username))
                    {
                        System.out.println("User Found!");
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Catching Error in User_found_via_username");
            throw new DataAccessException("500");
        }


        return false;
    }

    public boolean userLoginCredentials(UserData loggingIn) throws DataAccessException {

        System.out.println("User_login_credentials " + loggingIn.username());
        var statement = "SELECT * FROM UserSQL;";

        System.out.println("User_login_credentials 2 " + statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            System.out.println("User_login_credentials 3" + rs.toString());
            while (rs.next()) {
                System.out.println("User_login_credentials 4");
                var usernameFound = rs.getString("username");
                var hashedPassword = rs.getString("password");
                var emailFound = rs.getString("email");
                System.out.printf("User Found! username: %s, password: %s, email: %s%n", usernameFound, hashedPassword, emailFound);
                System.out.println("Password Have: " + loggingIn.password());
                if (hashedPassword != null)
                {
                    if (BCrypt.checkpw(loggingIn.password(), hashedPassword))
                    {
                        System.out.println("Password Correct!");
                        return true;
                    }
                }
            }
            System.out.println("User_login_credentials 5");
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }


        return false;


    }





}
