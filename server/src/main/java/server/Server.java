package server;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccessException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import model.*;
import service.Chess_Service;

import java.util.ArrayList;
import java.util.Map;


public class Server {

    private final Javalin javalin;


    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
    public Server() {


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
                parse_post(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in .post /user");
                System.out.println(e.getMessage());
                System.out.println(e.toString());
                JsonObject output_error = new JsonObject();
                if (e.getMessage().equals("400"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                } else if (e.getMessage().equals("403"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_403);
                    ctx.status(403);
                } else if (e.getMessage().equals("500"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }
                ctx.result(output_error.toString());
            }


        })
        .post("/session", (ctx) -> {
            JsonObject output = new JsonObject();
            try
            {
                parse_post(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in post /session");
                System.out.println(e.toString());
                JsonObject output_error = new JsonObject();
                if (e.getMessage().equals("400"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                } else if (e.getMessage().equals("401"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }  else if (e.getMessage().equals("500"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }
                ctx.result(output_error.toString());
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
                JsonObject output_error = new JsonObject();

                if (e.getMessage().equals("401"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }  else if (e.getMessage().equals("500"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }




                ctx.result(output_error.toString());
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
                JsonObject output_error = new JsonObject();





                if (e.getMessage().equals("401"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }  else if (e.getMessage().equals("500"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }

                ctx.result(output_error.toString());
            }

        })
        .post("/game", (ctx) -> {

            try
            {
                parse_post(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in ,post/game");
                System.out.println(e.toString());
                JsonObject output_error = new JsonObject();
                if (e.getMessage().equals("400"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                } else if (e.getMessage().equals("401"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }  else if (e.getMessage().equals("500"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }
                ctx.result(output_error.toString());
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
                JsonObject output_error = new JsonObject();
                if (e.getMessage().equals("400"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                } else if (e.getMessage().equals("401"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                } else if (e.getMessage().equals("403"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_403);
                    ctx.status(403);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }
                ctx.result(output_error.toString());
            }
        })
        .delete("/db", (ctx) -> {
            //JsonObject output = new JsonObject();


            try {
                parseDelete(ctx);
            } catch (DataAccessException e) {
                JsonObject output_error = new JsonObject();
                output_error.addProperty("message", DataAccessException.ERROR_500);
                ctx.status(500);
                ctx.result(output_error.toString());
            }

            //output.addProperty("not done", "Not implemented");
            //output.addProperty("message2", "Hello, JSON!");

            //ctx.result(output.toString());
        })
        .post("/chess", (ctx) -> {

            try
            {
                parse_post(ctx);
            }
            catch (DataAccessException e)
            {
                System.out.println("I'm catching the issue in .get/chess");
                System.out.println(e.toString());
                JsonObject output_error = new JsonObject();



                if (e.getMessage().equals("401"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_401);
                    ctx.status(401);
                }
                else if (e.getMessage().equals("400"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_400);
                    ctx.status(400);
                }
                else if (e.getMessage().equals("500"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                } else if (e.getMessage().equals("Error: failed to get connection"))
                {
                    output_error.addProperty("message", DataAccessException.ERROR_500);
                    ctx.status(500);
                }

                ctx.result(output_error.toString());
            }

        })




//                .post("/user",
//                        (ctx) -> {
//                            JsonObject output = new JsonObject();
//                            output.addProperty("not done", "Not implemented");
//                            output.addProperty("message2", "Hello, JSON!");
//
//                            ctx.result(output.toString());
//
//                        })
//                .post("/session",
//                        (ctx) -> {
//                            JsonObject output = new JsonObject();
//                            output.addProperty("not done", "Not implemented");
//
//                            ctx.result(output.toString());
//
//                        })
//                .delete("/session",
//                        (ctx) -> {
//                            JsonObject output = new JsonObject();
//                            output.addProperty("not done", "Not implemented");
//
//                            ctx.result(output.toString());
//
//                        })
//                .get("/game", this::listNames)
//                .post("/game", this::addGame)
//                .put("/game",
//                        (ctx) -> {
//                            JsonObject output = new JsonObject();
//                            output.addProperty("not done", "Not implemented");
//
//                            ctx.result(output.toString());
//
//                        })
//                .delete("/db",
//                        (ctx) -> {
//                            JsonObject output = new JsonObject();
//                            output.addProperty("not done", "Not implemented");
//
//                            ctx.result(output.toString());
//
//                        })

        ;



    }

    private void parse_post(Context context) throws Exception
    {


        System.out.println("post");
        if (context.path().equals("/user"))
        {
            // registers the new User
            System.out.println("user");
            var serializer = new Gson();

            var input = new userRequestRegister("", "", "");

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), userRequestRegister.class);

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
                userResponseRegister output = service.User_Register(input);

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

            var input = new userRequestLogin("","");

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), userRequestLogin.class);

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

                input = new userRequestLogin(input.username(), input.password());
                System.out.println("Inputing " + input);

                // Run Function
                userResponseLogin output = service.User_Login(input);

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
            String auth_input = context.headerMap().get("Authorization");
            if (!service.User_Authorized(auth_input))
            {
                throw new DataAccessException("401");
            }

            System.out.println("game");

            var serializer = new Gson();

            var input = new gameRequestCreate("");

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), gameRequestCreate.class);

            if (input.gameName() == null)
            {
                throw new DataAccessException("400");
            }


            // Run Function
            gameResponseCreate output = service.Game_Create(input);

            // serialize to JSON
            var json = serializer.toJson(output);

            // Update output
            context.result(json);

            // Errors

            if (output.gameID() == -1)
            {
                JsonObject output_error = new JsonObject();
                output_error.addProperty("error", "Bad Input");

                context.result(output_error.toString());
                context.status(400);
            }

            if (output.gameID() == -2)
            {
                JsonObject output_error = new JsonObject();
                output_error.addProperty("error", "duplicate game name");

                context.result(output_error.toString());
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
        String auth_input = context.headerMap().get("Authorization");
        if (!service.User_Authorized(auth_input))
        {
            System.out.println("Unauthorized!!!!");
            throw new DataAccessException("401");
        }




        System.out.println("get");

        if (context.path().equals("/game"))
        {
            System.out.println("game");

            var serializer = new Gson();

            var input = new gameRequestList();

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), gameRequestList.class);

            // Run Function
            gameResponseList output = service.Game_List(input);

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
            String auth_input = context.headerMap().get("Authorization");
            if (!service.User_Authorized(auth_input))
            {
                throw new DataAccessException("401");
            }


            System.out.println("session");

            var serializer = new Gson();

            var input = new userRequestLogout(context.headerMap().get("Authorization"));


            // Run Function
            service.User_Logout(input);


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
        String auth_input = context.headerMap().get("Authorization");
        if (!service.User_Authorized(auth_input))
        {
            throw new DataAccessException("401");
        }

        System.out.println("put");
        if (context.path().equals("/game"))
        {
            System.out.println("game");

            var serializer = new Gson();

            var input = new gameRequestJoin("test user", "",1);

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), gameRequestJoin.class);

            if (input.playerColor() == null)
            {
                throw new DataAccessException("400");
            }

            // Add the AuthToken to the input
            input = new gameRequestJoin(context.headerMap().get("Authorization"), input.playerColor(), input.gameID());

            try
            {
                // Run Function
                gameResponseJoin output = service.Game_Join(input);

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

            var game = new userRequestRegister("test user", "psw", "test email");
            System.out.println(game);

            // serialize to JSON
            var json = serializer.toJson(game);
            System.out.println(json);


            // deserialize back to ChessGame
            game = serializer.fromJson(context.body(), userRequestRegister.class);
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

    private ArrayList<String> names = new ArrayList<>();


    private Chess_Service service = new Chess_Service();





}
