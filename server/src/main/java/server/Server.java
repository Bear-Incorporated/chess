package server;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccessException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import model.*;
import servermodel.GameRequestView;
import service.ChessService;
import server.websocket.WebSocketHandler;

import java.util.ArrayList;
import java.util.Map;


public class Server {

    private final Javalin javalin;
    private ArrayList<String> names = new ArrayList<>();


    private ChessService service = new ChessService();



    private final WebSocketHandler webSocketHandler;


    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
    public Server() {

        webSocketHandler = new WebSocketHandler();

        javalin = Javalin.create(config -> config.staticFiles.add("web"))

        // Register your endpoints and exception handlers here.


        .before(context -> {
            boolean authenticated;

            // Check if authenticated
            authenticated = true;
            // Need to program this for real





            if (!authenticated) {
                throw new UnauthorizedResponse();


            }


        })

        .get("/hell",
            ctx -> ctx.result("bad word"))
        .get("/hello",
            ctx -> ctx.result("hi"))
        .get("/hel",
            (ctx) -> {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", "Hello, JSON!");

                ctx.result(jsonObject.toString());

            })
        .post("/name", this::addName)
        .get("/name", this::listNames)
        .delete("/name", this::deleteName)

        .post("/user", (ctx) -> {
            JsonObject output = new JsonObject();
            try
            {
                parsePost(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in .post /user");
                System.out.println(e.getMessage());
                System.out.println(e.toString());
                JsonObject outputError = new JsonObject();
                if (e.getMessage().equals("400"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                } else if (e.getMessage().equals("403"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_403);
                    ctx.status(403);
                } else if (e.getMessage().equals("500"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }
                ctx.result(outputError.toString());
            }


        })
        .post("/session", (ctx) -> {
            JsonObject output = new JsonObject();
            try
            {
                parsePost(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in post /session");
                System.out.println(e.toString());
                JsonObject outputError = new JsonObject();
                if (e.getMessage().equals("400"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                } else if (e.getMessage().equals("401"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }  else if (e.getMessage().equals("500"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }
                ctx.result(outputError.toString());
            }

        })
        .delete("/session", (ctx) -> {
            //JsonObject output = new JsonObject();
            try
            {
                parseDelete(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in .delete/session");
                System.out.println(e.toString());
                JsonObject outputError = new JsonObject();

                if (e.getMessage().equals("401"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }  else if (e.getMessage().equals("500"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }




                ctx.result(outputError.toString());
            }
            //output.addProperty("not done", "Not implemented");
            //output.addProperty("message2", "Hello, JSON!");

            //ctx.result(output.toString());
        })
        .get("/game", (ctx) -> {

            try
            {
                parseGet(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in .get/game");
                System.out.println(e.toString());
                JsonObject outputError = new JsonObject();





                if (e.getMessage().equals("401"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }  else if (e.getMessage().equals("500"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }

                ctx.result(outputError.toString());
            }

        })
        .post("/game", (ctx) -> {

            try
            {
                parsePost(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in ,post/game");
                System.out.println(e.toString());
                JsonObject outputError = new JsonObject();
                if (e.getMessage().equals("400"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                } else if (e.getMessage().equals("401"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }  else if (e.getMessage().equals("500"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }
                ctx.result(outputError.toString());
            }

        })
        .put("/game", (ctx) -> {
            try
            {
                parsePut(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in .put/game");
                System.out.println(e.toString());
                JsonObject outputError = new JsonObject();
                if (e.getMessage().equals("400"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                } else if (e.getMessage().equals("401"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                } else if (e.getMessage().equals("403"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_403);
                    ctx.status(403);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }
                ctx.result(outputError.toString());
            }
        })
        .delete("/db", (ctx) -> {
            //JsonObject output = new JsonObject();


            try {
                parseDelete(ctx);
            } catch (DataAccessException e) {
                JsonObject outputError = new JsonObject();
                outputError.addProperty("message", DataAccessException.ERROR_500);
                ctx.status(500);
                ctx.result(outputError.toString());
            }

            //output.addProperty("not done", "Not implemented");
            //output.addProperty("message2", "Hello, JSON!");

            //ctx.result(output.toString());
        })
        .post("/chess", (ctx) -> {

            try
            {
                parsePost(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in .get/chess");
                System.out.println(e.toString());
                JsonObject outputError = new JsonObject();



                if (e.getMessage().equals("401"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }
                else if (e.getMessage().equals("400"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                }
                else if (e.getMessage().equals("500"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    outputError.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }

                ctx.result(outputError.toString());
            }

        })
        .ws("/ws", ws -> {
            ws.onConnect(webSocketHandler);
            ws.onMessage(webSocketHandler);
            ws.onClose(webSocketHandler);
        });



    }

    private void parsePost(Context context) throws Exception
    {


        System.out.println("post");
        if (context.path().equals("/user"))
        {
            // registers the new User
            System.out.println("user");
            var serializer = new Gson();

            var input = new UserRequestRegister("", "", "");

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), UserRequestRegister.class);

            // Check inputs for errors
            if (input.username() == null)
            {
                throw new DataAccessException("400");
            }
            if (input.password() == null)
            {
                throw new DataAccessException("400");
            }
            if (input.email() == null)
            {
                throw new DataAccessException("400");
            }

            try
            {
                // Run Function
                UserResponseRegister output = service.userRegister(input);

                if (output.authToken().equals("404"))
                {
                    throw new DataAccessException("403");
                }

                // serialize to JSON
                var json = serializer.toJson(output);
                System.out.println("auth java: " + output);

                // Update output
                context.result(json);
            } catch (DataAccessException e) {
                throw new DataAccessException(e.getMessage());
            }



        } else if (context.path().equals("/session"))
        {
            System.out.println("session");

            var serializer = new Gson();

            var input = new UserRequestLogin("","");

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), UserRequestLogin.class);

            System.out.println("post session json converted");

            if (input.username() == null)
            {
                System.out.println("No Username!!!");

                throw new DataAccessException("400");
                //return;
            }
            if (input.password() == null)
            {
                System.out.println("No Password!!!");

                throw new DataAccessException("400");
                //return;
            }


            try
            {
                System.out.println("Inputing " + input);

                input = new UserRequestLogin(input.username(), input.password());
                System.out.println("Inputing " + input);

                // Run Function
                UserResponseLogin output = service.userLogin(input);

                System.out.println(context.headerMap());


                // serialize to JSON
                var json = serializer.toJson(output);
                System.out.println("auth java: " + output);

                // Update output
                context.result(json);

            } catch (DataAccessException e) {
                throw new DataAccessException(e.getMessage());
            }



        } else if (context.path().equals("/game"))
        {
            // Check to see if they are authorized
            String authInput = context.headerMap().get("Authorization");
            if (!service.userAuthorized(authInput))
            {
                throw new DataAccessException("401");
            }

            System.out.println("game");

            var serializer = new Gson();

            var input = new GameRequestCreate("");

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), GameRequestCreate.class);

            if (input.gameName() == null)
            {
                throw new DataAccessException("400");
            }


            // Run Function
            GameResponseCreate output = service.gameCreate(input);

            // serialize to JSON
            var json = serializer.toJson(output);

            // Update output
            context.result(json);

            // Errors

            if (output.gameID() == -1)
            {
                JsonObject outputError = new JsonObject();
                outputError.addProperty("error", "Bad Input");

                context.result(outputError.toString());
                context.status(400);
            }

            if (output.gameID() == -2)
            {
                JsonObject outputError = new JsonObject();
                outputError.addProperty("error", "duplicate game name");

                context.result(outputError.toString());
                context.status(400);
            }


        }
        else if (context.path().equals("/chess"))
        {
            System.out.println("chess");

            var serializer = new Gson();

            var input = new GameRequestView(0);

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), GameRequestView.class);


            try {
                // Run Function
                ChessGame output = service.gameView(input);

                // serialize to JSON
                var json = serializer.toJson(output);

                // Update output
                context.result(json);

            }
            catch (DataAccessException e)
            {
                throw new DataAccessException(e.getMessage());
            }

        }


    }

    private void parseGet(Context context) throws Exception
    {
        // Check to see if they are authorized
        String authInput = context.headerMap().get("Authorization");
        if (!service.userAuthorized(authInput))
        {
            System.out.println("Unauthorized!!!!");
            throw new DataAccessException("401");
        }




        System.out.println("get");

        if (context.path().equals("/game"))
        {
            System.out.println("game");

            var serializer = new Gson();

            var input = new GameRequestList();

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), GameRequestList.class);

            // Run Function
            GameResponseList output = service.gameList(input);

            // serialize to JSON
            var json = serializer.toJson(output);

            // Update output
            context.result(json);


        }

    }


    private void parseDelete(Context context) throws Exception
    {
        System.out.println("delete");
        if (context.path().equals("/session"))
        {

            // Check to see if they are authorized
            String authInput = context.headerMap().get("Authorization");
            if (!service.userAuthorized(authInput))
            {
                throw new DataAccessException("401");
            }


            System.out.println("session");

            var serializer = new Gson();

            var input = new UserRequestLogout(context.headerMap().get("Authorization"));


            // Run Function
            service.userLogout(input);


        } else if (context.path().equals("/db"))
        {
            System.out.println("db");

            var serializer = new Gson();




            try {
                service.Clear();
            } catch (DataAccessException ex) {
                throw new DataAccessException("500");
            }


        }
    }

    private void parsePut(Context context) throws Exception
    {
        // Check to see if they are authorized
        String authInput = context.headerMap().get("Authorization");
        if (!service.userAuthorized(authInput))
        {
            throw new DataAccessException("401");
        }

        System.out.println("put");
        if (context.path().equals("/game"))
        {
            System.out.println("game");

            var serializer = new Gson();

            var input = new GameRequestJoin("test user", "",1);

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), GameRequestJoin.class);

            if (input.playerColor() == null)
            {
                throw new DataAccessException("400");
            }

            // Add the AuthToken to the input
            input = new GameRequestJoin(context.headerMap().get("Authorization"), input.playerColor(), input.gameID());

            try
            {
                // Run Function
                GameResponseJoin output = service.gameJoin(input);

                // serialize to JSON
                var json = serializer.toJson(output);

                // Update output
                context.result(json);
            } catch (DataAccessException e)
            {
                throw new DataAccessException(e.getMessage());
            }
        }
    }

    private void parsedOld(Context context)
    {
        System.out.println("post");
        if (context.path().equals("/user"))
        {
            System.out.println("user");
            System.out.println(context);

            System.out.println("gameName = " + context.path());
            System.out.println("body = " + context.body());

            System.out.println("Authorization = " + context.headerMap().get("Authorization"));
            context.headerMap().get("Authorization");

            var serializer = new Gson();

            var game = new UserRequestRegister("test user", "psw", "test email");
            System.out.println(game);

            // serialize to JSON
            var json = serializer.toJson(game);
            System.out.println(json);


            // deserialize back to ChessGame
            game = serializer.fromJson(context.body(), UserRequestRegister.class);
            System.out.println(game);

        }
    }


    private void addGame(Context context) {
        System.out.println("Opened program");
        System.out.println("gameName = " + context.path());
        System.out.println("body = " + context.body());
        System.out.println("Authorization = " + context.headerMap().get("Authorization"));
        context.headerMap().get("Authorization");

    }

    private void addName(Context context) {
        names.add(context.pathParam("name"));
        listNames(context);
    }

    private void listNames(Context context) {
        String jsonNames = new Gson().toJson(Map.of("name", names));
        // System.out.println("Test");
        // System.out.println("Auth = " + context.pathParam("Authorization"));
        context.json(jsonNames);
    }

    private void deleteName(Context context) {

        names.remove(context.pathParam("name"));
        listNames(context);
    }




}
