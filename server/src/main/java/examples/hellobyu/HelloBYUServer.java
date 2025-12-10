package examples.hellobyu;

import io.javalin.Javalin;


public class HelloBYUServer {
    public static void main(
            String[] args) {
        int requestedPort =
                8080; // Integer.parseInt(args[0]);

        var server =
                new HelloBYUServer();
        int port =
                server.run(requestedPort);
        System.out.println(
                "Running on port " + port);
    }


    public int run(int requestedPort) {
        Javalin javalinServer =
                Javalin.create();
        createHandlers(javalinServer);

        javalinServer.start(
                requestedPort);
        return javalinServer.port();
    }

    private void createHandlers(
            Javalin javalinServer) {
        javalinServer.get("/hello",
                new HelloBYUHandler());
        // Other routes here
    }
}



