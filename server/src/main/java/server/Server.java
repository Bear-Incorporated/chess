package server;

import com.google.gson.Gson;
import io.javalin.*;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;


import com.google.gson.JsonObject;
import kotlin.ContextFunctionTypeParams;
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
                output.addProperty("not done", "Not implemented");
                output.addProperty("message2", "Hello, JSON!");

                ctx.result(output.toString());
            })
            .post("/session", (ctx) -> {
                JsonObject output = new JsonObject();
                ctx.body()
                parse_post(ctx);
                output.addProperty("not done", "Not implemented");
                output.addProperty("message2", "Hello, JSON!");

                ctx.result(output.toString());
            })
            .delete("/session", (ctx) -> {
                JsonObject output = new JsonObject();
                parse_delete(ctx);
                output.addProperty("not done", "Not implemented");
                output.addProperty("message2", "Hello, JSON!");

                ctx.result(output.toString());
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
                JsonObject output = new JsonObject();
                parse_delete(ctx);
                output.addProperty("not done", "Not implemented");
                output.addProperty("message2", "Hello, JSON!");

                ctx.result(output.toString());
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
        } else if (context.path().equals("/session"))
        {
            System.out.println("session");
        } else if (context.path().equals("/game"))
        {
            System.out.println("game");
        }


    }

    private void parse_get(Context context)
    {
        System.out.println("get");
        if (context.path().equals("/game"))
        {
            System.out.println("game");
        }
    }

    private void parse_delete(Context context)
    {
        System.out.println("delete");
        if (context.path().equals("/session"))
        {
            System.out.println("session");
        } else if (context.path().equals("/db"))
        {
            System.out.println("db");
        }
    }

    private void parse_put(Context context)
    {
        System.out.println("put");
        if (context.path().equals("/game"))
        {
            System.out.println("game");
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




}
