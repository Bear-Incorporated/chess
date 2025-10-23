package server;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import io.javalin.*;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;


import com.google.gson.JsonObject;
import kotlin.ContextFunctionTypeParams;
import model.*;
import service.Chess_Service;
import service.ClearService;
import service.GameService;
import service.UserService;

import java.net.http.HttpHeaders;
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
            parse_post(ctx);

        })
        .post("/session", (ctx) -> {
            JsonObject output = new JsonObject();
            parse_post(ctx);


        })
        .delete("/session", (ctx) -> {
            //JsonObject output = new JsonObject();
            parse_delete(ctx);
            //output.addProperty("not done", "Not implemented");
            //output.addProperty("message2", "Hello, JSON!");

            //ctx.result(output.toString());
        })
        .get("/game", (ctx) -> {
            JsonObject output = new JsonObject();
            parse_get(ctx);
            output.addProperty("not done", "Not implemented");
            output.addProperty("message2", "Hello, JSON!");

            ctx.result(output.toString());
        })
        .post("/game", (ctx) -> {
            JsonObject output = new JsonObject();
            parse_post(ctx);
            output.addProperty("not done", "Not implemented");
            output.addProperty("message2", "Hello, JSON!");

            ctx.result(output.toString());
        })
        .put("/game", (ctx) -> {
            JsonObject output = new JsonObject();
            parse_put(ctx);
            output.addProperty("not done", "Not implemented");
            output.addProperty("message2", "Hello, JSON!");

            ctx.result(output.toString());
        })
        .delete("/db", (ctx) -> {
            //JsonObject output = new JsonObject();
            parse_delete(ctx);
            //output.addProperty("not done", "Not implemented");
            //output.addProperty("message2", "Hello, JSON!");

            //ctx.result(output.toString());
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

    private void parse_post(Context context)
    {


        System.out.println("post");
        if (context.path().equals("/user"))
        {
            System.out.println("user");
            var serializer = new Gson();

            var input = new User_Request_Register("", "", "");

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), User_Request_Register.class);

            // Run Function
            User_Response_Register output = service.User_Register(input);

            if (output.authToken().equals("404"))
            {
                context.status(400);
                return;
            }

            // serialize to JSON
            var json = serializer.toJson(output);
            System.out.println("auth java: " + output);

            // Update output
            context.result(json);

        } else if (context.path().equals("/session"))
        {
            System.out.println("session");

            var serializer = new Gson();

            var input = new User_Request_Login("","","");

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), User_Request_Login.class);

            System.out.println("Inputing " + input);

            input = new User_Request_Login(input.username(), input.password(), context.headerMap().get("Authorization"));
            System.out.println("Inputing " + input);

            // Run Function
            User_Response_Login output = service.User_Login(input);

            System.out.println(context.headerMap());

            if (output.success())
            {
                // serialize to JSON
                var json = serializer.toJson(output);
                System.out.println("auth java: " + output);

                // Update output
                context.result(json);
            } else {
                context.status(400);

            }



        } else if (context.path().equals("/game"))
        {
            // Check to see if they are authorized
            String auth_input = context.headerMap().get("Authorization");
            if (!service.User_Authorized(auth_input))
            {
                System.out.println("Unauthorized!!!!");
                context.status(400);
                return;
            }

            System.out.println("game");

            var serializer = new Gson();

            var input = new Game_Request_Create("");

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), Game_Request_Create.class);

            // Run Function
            Game_Response_Create output = service.Game_Create(input);

            // serialize to JSON
            var json = serializer.toJson(output);

            // Update output
            context.result(json);
        }


    }

    private void parse_get(Context context)
    {
        // Check to see if they are authorized
        String auth_input = context.headerMap().get("Authorization");
        if (!service.User_Authorized(auth_input))
        {
            System.out.println("Unauthorized!!!!");
            context.status(400);
            return;
        }




        System.out.println("get");

        if (context.path().equals("/game"))
        {
            System.out.println("game");

            var serializer = new Gson();

            var input = new Game_Request_List();

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), Game_Request_List.class);

            // Run Function
            Game_Response_List output = service.Game_List(input);

            // serialize to JSON
            var json = serializer.toJson(output);

            // Update output
            context.result(json);
        }
    }


    private void parse_delete(Context context)
    {
        System.out.println("delete");
        if (context.path().equals("/session"))
        {

            // Check to see if they are authorized
            String auth_input = context.headerMap().get("Authorization");
            if (!service.User_Authorized(auth_input))
            {
                System.out.println("Unauthorized!!!!");
                context.status(400);
                return;
            }


            System.out.println("session");

            var serializer = new Gson();

            var input = new User_Request_Logout(context.headerMap().get("Authorization"));


            // Run Function
            User_Response_Logout output = service.User_Logout(input);

            // serialize to JSON
            var json = serializer.toJson(output);

            // Update output
            context.result(json);
        } else if (context.path().equals("/db"))
        {
            System.out.println("db");

            var serializer = new Gson();

            var input = new Clear_Request();

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), Clear_Request.class);

            // Run Function
            Clear_Response output = service.Clear(input);

            // serialize to JSON
            var json = serializer.toJson(output);

            // Update output
            context.result(json);
        }
    }

    private void parse_put(Context context)
    {
        // Check to see if they are authorized
        String auth_input = context.headerMap().get("Authorization");
        if (!service.User_Authorized(auth_input))
        {
            System.out.println("Unauthorized!!!!");
            context.status(400);
            return;
        }

        System.out.println("put");
        if (context.path().equals("/game"))
        {
            System.out.println("game");

            var serializer = new Gson();

            var input = new Game_Request_Join("test user", 1);

            // deserialize back to ChessGame
            input = serializer.fromJson(context.body(), Game_Request_Join.class);

            // Run Function
            Game_Response_Join output = service.Game_Join(input);

            // serialize to JSON
            var json = serializer.toJson(output);

            // Update output
            context.result(json);
        }
    }

    private void parsed_old(Context context)
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

            var game = new User_Request_Register("test user", "psw", "test email");
            System.out.println(game);

            // serialize to JSON
            var json = serializer.toJson(game);
            System.out.println(json);


            // deserialize back to ChessGame
            game = serializer.fromJson(context.body(), User_Request_Register.class);
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
