package dataaccess;

/**
 * Indicates there was an error connecting to the database
 */

public class DataAccessException extends Exception{
    public static String ERROR_403 = "Error: already taken";
    public static String ERROR_400 = "Error: bad request";
    public static String ERROR_401 = "Error: unauthorized";

    public DataAccessException(String message) {
        super(message);
    }
    public DataAccessException(String message, Throwable ex) {
        super(message, ex);
    }
}
