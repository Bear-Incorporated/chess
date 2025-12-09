package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.websocket.*;
import model.SessionInfo;
import model.gameDataShort;
import org.eclipse.jetty.websocket.api.Session;
import service.GameService;
import service.UserService;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;


import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();

    GameService serviceGame = new GameService();
    UserService serviceUser = new UserService();

    String usernameConnected = "";

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        int gameId = -1;
        Session session = ctx.session;

        try {
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            gameId = command.getGameID();

            String username;
            username = serviceUser.getUserNameViaAuthToken(command.getAuthToken());
            // saveSession(gameId, session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(ctx, username, command);
                case MAKE_MOVE -> makeMove(ctx, username, command);
                case LEAVE -> leaveGame(ctx, username, command);
                case RESIGN -> resign(ctx, username, command);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (DataAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    private void connect(WsMessageContext ctx, String username, UserGameCommand command) throws IOException {
        System.out.print("Connecting user " + username + "\n");
        System.out.print("Command " + command + "\n");

        usernameConnected = username;
        String message;
        ServerMessage notification;


        try
        {
//
//            I need to implement this client side if I want to ensure they are playing their game.
//            Maybe, have them store the list every time it comes up, so I can check it.
//
//
//            gameDataShort gameData = serviceGame.getGameDataShort(command.getGameID());
//            if (gameData.blackUsername().equals(username) || gameData.whiteUsername().equals(username))
//            {
//                System.out.print(String.format("%s is a player in the game.", username));
//            } else {
//                message = String.format("error : %s is not a part of that game.", username);
//                notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
//                connections.narrowcast(ctx, notification);
//            }

            if (!serviceUser.authorized(command.getAuthToken()))
            {
                message = String.format("error : %s is not valid. Login", command.getAuthToken());
                notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.narrowcast(ctx, notification);
                return;
            }



            ChessGame game;
            game = serviceGame.view(command.getGameID());
            System.out.print(String.format("Starting your game %s.", username));
            notification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, game);

            connections.add(ctx, new SessionInfo(username));

            connections.narrowcast(ctx, notification);

            message = String.format("%s has joined the game", username);
            notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(ctx, notification);


        }
        catch (Exception ex)
        {
            message = String.format("error : %s", ex);
            notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.narrowcast(ctx, notification);
        }


    }

    private void makeMove(WsMessageContext ctx, String username, UserGameCommand command) throws IOException {
        System.out.print("Moving user " + username + "\n");
        System.out.print("Move " + command.getMove() + "\n");

        String message;
        ServerMessage notification;


        try
        {

            if (!serviceUser.authorized(command.getAuthToken()))
            {
                message = String.format("error : %s is not valid. Login", command.getAuthToken());
                notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.narrowcast(ctx, notification);
                return;
            }

            System.out.print("Moving user " + username + "\n");
            System.out.print("Moving user 2 " + usernameConnected + "\n");
            System.out.print("Moving user 3 " + serviceUser.getUserNameViaAuthToken(command.getAuthToken()) + "\n");

            System.out.print("Moving user 4 " + connections.checkUserName(ctx, new SessionInfo(username)) + "\n");





            if (!connections.checkUserName(ctx, new SessionInfo(username)))
            {
                message = String.format("error : You are %s, not %s", usernameConnected, username);
                notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.narrowcast(ctx, notification);
                return;
            }



            gameDataShort gameData = serviceGame.getGameDataShort(command.getGameID());
            if (gameData.blackUsername().equals(username) || gameData.whiteUsername().equals(username))
            {
                System.out.print(String.format("%s is a player in the game.", username));
            } else {
                message = String.format("error : %s is not a part of that game.", username);
                notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.narrowcast(ctx, notification);
            }




            ChessGame game;
            game = serviceGame.move(command.getGameID(), command.getMove());


            System.out.print(String.format("Starting your move %s.", username));
            notification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, game);

            connections.broadcast(null, notification);

            message = String.format("%s has made a move", username);
            notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(ctx, notification);


        }
        catch (Exception ex)
        {
            message = String.format("error : %s", ex);
            notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.narrowcast(ctx, notification);
        }




        // var message = String.format("%s is in the shop", username);
        // var notification = ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, game);
        // connections.broadcast(null, notification);
    }

    private void leaveGame(WsMessageContext ctx, String username, UserGameCommand command) throws IOException {
        System.out.print("Leaving user " + username + "\n");

        usernameConnected = "";
        var message = String.format("%s left the game", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(null, notification);
        connections.remove(ctx);
    }

    private void resign(WsMessageContext ctx, String username, UserGameCommand command) throws IOException {
        System.out.print("Resigning user " + username + "\n");



        try
        {
            serviceGame.gameOver(command.getGameID());

        }
        catch (Exception ex)
        {
            System.out.print(String.format("error : %s", ex));
        }


        var message = String.format("%s is a quitter", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(null, notification);
    }
//    private void saveSession(int gameId, WsMessageContext ctx)
//    {
//        connections.add(ctx);
//
//    }


//    private void enter(String visitorName, Session session) throws IOException {
//        connections.add(session);
//        var message = String.format("%s is in the shop", visitorName);
//        var notification = new Notification(Notification.Type.ARRIVAL, message);
//        connections.broadcast(session, notification);
//    }
//
//    private void exit(String visitorName, Session session) throws IOException {
//        var message = String.format("%s left the shop", visitorName);
//        var notification = new Notification(Notification.Type.DEPARTURE, message);
//        connections.broadcast(session, notification);
//        connections.remove(session);
//    }

//    public void makeNoise(String petName, String sound) throws ResponseException {
//        try {
//            var message = String.format("%s says %s", petName, sound);
//            var notification = new ServerMessage(ServerMessage.Type.NOISE, message);
//            connections.broadcast(null, notification);
//        } catch (Exception ex) {
//            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
//        }
//    }
}