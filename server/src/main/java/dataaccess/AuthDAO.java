package dataaccess;

import model.AuthData;

import java.sql.SQLException;

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
            createTableAuthSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }

    }






    static public void createTableAuthSQL() throws DataAccessException {
        System.out.println("createTableAuthSQL");
        var statement = "CREATE TABLE IF NOT EXISTS AuthSQL (authToken VARCHAR(255) DEFAULT NULL, username VARCHAR(255) DEFAULT NULL);";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }
    }







    public AuthDAO() {

    }






    public void authDeleteAll() throws DataAccessException {
        System.out.println("In authDeleteAll");

        var statement = "DROP TABLE IF EXISTS AuthSQL;";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }

        try
        {
            createTableAuthSQL();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }
    }



    public String authGetUserNameViaAuthToken(String authToken) throws DataAccessException
    {
        System.out.println("In authGetUserNameViaAuthToken");

        var statement = "SELECT * FROM AuthSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var authTokenFound = rs.getString("authToken");
                var usernameFound = rs.getString("username");
                System.out.printf("User Found! AuthToken: %s, username: %s%n", authTokenFound, usernameFound);
                if (authTokenFound != null)
                {
                    if (authTokenFound.equals(authToken))
                    {
                        System.out.println("User Found!");
                        return usernameFound;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }


        return "";

    }




    /**
     * Determines if the user is Authorized given an AuthToken
     *
     * @param data   AuthToken
     *
     */

    public Boolean authorizedViaAuthToken(String data) throws DataAccessException
    {
        System.out.println("In authorizedViaAuthToken");

        var statement = "SELECT * FROM AuthSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var authTokenFound = rs.getString("authToken");
                var usernameFound = rs.getString("username");
                System.out.printf("User Found! AuthToken: %s, username: %s%n", authTokenFound, usernameFound);
                if (authTokenFound != null)
                {
                    if (authTokenFound.equals(data))
                    {
                        System.out.println("User Authorized!");
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }



        return false;
    }

    public Boolean authorizedViaUsername(String newName) throws DataAccessException
    {
        System.out.println("In authorizedViaUsername");



        var statement = "SELECT * FROM AuthSQL;";
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var authTokenFound = rs.getString("authToken");
                var usernameFound = rs.getString("username");
                System.out.printf("User Found! AuthToken: %s, username: %s%n", authTokenFound, usernameFound);
                if (usernameFound != null)
                {
                    if (usernameFound.equals(newName))
                    {
                        System.out.println("User Authorized!");
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }



        return false;


    }



    public void authAdd(AuthData added) throws DataAccessException
    {
        System.out.println("In authAdd adding " + added.toString());


        var statement = "INSERT INTO AuthSQL (authToken, username) VALUES ( \"" + added.authToken() + "\" , \"" + added.username() + "\" );";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }
    }


    public void authDeleteViaAuthToken(String removed) throws DataAccessException
    {
        System.out.println("In authDeleteViaAuthToken");
        var statement = "DELETE FROM AuthSQL WHERE authToken=\"" + removed + "\";";
        System.out.println(statement);
        try (var conn = DatabaseManager.getConnection();
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("500");
        }
    }

}
