package server.websocket;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import io.javalin.websocket.*;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;


import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();

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
            AuthDAO authTemp = new AuthDAO();
            String username;
            username = authTemp.authGetUserNameViaAuthToken(command.getAuthToken());
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

        connections.add(ctx);
        var message = String.format("Starting you game %s.", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, message);
        // connections.broadcast(null, notification);
        connections.narrowcast(ctx, notification, true);
    }

    private void makeMove(WsMessageContext ctx, String username, UserGameCommand command) throws IOException {
        System.out.print("Moving user " + username + "\n");


        var message = String.format("%s is in the shop", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(null, notification);
    }

    private void leaveGame(WsMessageContext ctx, String username, UserGameCommand command) throws IOException {
        System.out.print("Leaving user " + username + "\n");

        var message = String.format("%s left the shop", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(null, notification);
        connections.remove(ctx);
    }

    private void resign(WsMessageContext ctx, String username, UserGameCommand command) throws IOException {
        System.out.print("Resigning user " + username + "\n");


        var message = String.format("%s is in the shop", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(null, notification);
    }
    private void saveSession(int gameId, WsMessageContext ctx)
    {
        connections.add(ctx);

    }


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