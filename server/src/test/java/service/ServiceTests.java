package service;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import passoff.model.*;
import passoff.server.TestServerFacade;
import server.Server;

import java.net.HttpURLConnection;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTests
{

    private static TestUser existingUser;
    private static TestUser newUser;
    private static TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private static Server server;
    private String existingAuth;

    // ### TESTING SETUP/CLEANUP ###

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        serverFacade = new TestServerFacade("localhost", Integer.toString(port));
        existingUser = new TestUser("ExistingUser", "existingUserPassword", "eu@mail.com");
        newUser = new TestUser("NewUser", "newUserPassword", "nu@mail.com");
        createRequest = new TestCreateRequest("testGame");
    }

    @BeforeEach
    public void setup() {
        serverFacade.clear();

        //one user already logged in
        TestAuthResult regResult = serverFacade.register(existingUser);
        existingAuth = regResult.getAuthToken();
    }

    // ### SERVER-LEVEL API TESTS ###



    // ### HELPER ASSERTIONS ###

    private void assertHttpOk(TestResult result) {
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, serverFacade.getStatusCode(),
                "Server response code was not 200 OK (message: %s)".formatted(result.getMessage()));
        Assertions.assertFalse(result.getMessage() != null &&
                        result.getMessage().toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");
    }

    private void assertHttpBadRequest(TestResult result) {
        assertHttpError(result, HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
    }

    private void assertHttpUnauthorized(TestResult result) {
        assertHttpError(result, HttpURLConnection.HTTP_UNAUTHORIZED, "Unauthorized");
    }

    private void assertHttpForbidden(TestResult result) {
        assertHttpError(result, HttpURLConnection.HTTP_FORBIDDEN, "Forbidden");
    }

    private void assertHttpError(TestResult result, int statusCode, String message) {
        Assertions.assertEquals(statusCode, serverFacade.getStatusCode(),
                "Server response code was not %d %s (message: %s)".formatted(statusCode, message, result.getMessage()));
        Assertions.assertNotNull(result.getMessage(), "Invalid Request didn't return an error message");
        Assertions.assertTrue(result.getMessage().toLowerCase(Locale.ROOT).contains("error"),
                "Error message didn't contain the word \"Error\"");
    }

    private void assertAuthFieldsMissing(TestAuthResult result) {
        Assertions.assertNull(result.getUsername(), "Response incorrectly returned username");
        Assertions.assertNull(result.getAuthToken(), "Response incorrectly return authentication String");
    }

}

