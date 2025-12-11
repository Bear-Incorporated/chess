package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.ResponseException;
import jakarta.websocket.*;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.net.URI;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    jakarta.websocket.Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    // System.out.print("The message is " + message + "\n");
                    // System.out.print("The message is " + message + "\n");

                    ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, "error : bad notification");

                    try
                    {
//
                        JsonObject tempJson = JsonParser.parseString(message).getAsJsonObject();

                        // System.out.print("Part 1" + tempJson + "\n");
                        notification = new Gson().fromJson(tempJson, ServerMessage.class);
                        // System.out.print("Part 2 " + notification + "\n");
                        // notification = new Gson().fromJson(message, ServerMessage.class);

                    }
                    catch (Exception ex)
                    {
                        // System.out.print("Error: " + ex.getMessage() + "\n");
                    }


                    notificationHandler.notify(notification);
                }
            });
        } catch (Exception ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }




    public void connect(String authToken, Integer gameID) throws ResponseException {
        try {
            var action = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    public void makeMove(String authToken, Integer gameID, ChessMove move) throws ResponseException {
        try {

            var action = new UserGameCommand(UserGameCommand.CommandType.MAKE_MOVE, authToken, gameID, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }


    public void leaveGame(String authToken, Integer gameID) throws ResponseException {
        try {
            var action = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    public void resign(String authToken, Integer gameID) throws ResponseException {
        try {
            var action = new UserGameCommand(UserGameCommand.CommandType.RESIGN, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }



}

