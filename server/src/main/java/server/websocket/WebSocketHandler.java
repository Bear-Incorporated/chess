package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.websocket.*;
import servermodel.SessionInfo;
import model.GameDataShort;
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

            connections.add(ctx, new SessionInfo(username, command.getGameID()));

            connections.narrowcast(ctx, notification);



            // Send different messages depending on color
            // message = String.format("%s has joined the game", username);

            GameDataShort gameData = serviceGame.getGameDataShort(command.getGameID());
            if (gameData.whiteUsername().equals(username))
            {
                message = String.format("%s has joined the game as White", username);
            }
            else if (gameData.blackUsername().equals(username))
            {
                message = String.format("%s has joined the game as Black", username);
            }
            else
            {
                message = String.format("%s is observing the game", username);
            }













            notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcastViaGameID(ctx, notification, command.getGameID());


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

            // System.out.print("Moving user 4 " + connections.checkUserName(ctx, new SessionInfo(username)) + "\n");





            if (!connections.checkUserName(ctx, new SessionInfo(username, command.getGameID())))
            {
                message = String.format("error : You are %s, not %s", usernameConnected, username);
                notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.narrowcast(ctx, notification);
                return;
            }





            GameDataShort gameData = serviceGame.getGameDataShort(command.getGameID());
            if (gameData.whiteUsername().equals(username))
            {
                System.out.print(String.format("%s is the white player in the game.", username));
                ChessGame gameTemp = serviceGame.view(command.getGameID());
                if (gameTemp.getTeamTurn() == ChessGame.TeamColor.BLACK)
                {
                    message = String.format("error : %s It is not your turn.", username);
                    notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                    connections.narrowcast(ctx, notification);
                    return;
                }
            }
            else if (gameData.blackUsername().equals(username))
            {
                System.out.print(String.format("%s is the black player in the game.", username));
                ChessGame gameTemp = serviceGame.view(command.getGameID());
                if (gameTemp.getTeamTurn() == ChessGame.TeamColor.WHITE)
                {
                    message = String.format("error : %s It is not your turn.", username);
                    notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                    connections.narrowcast(ctx, notification);
                    return;
                }
            }
            else
            {
                message = String.format("error : %s is not a part of that game.", username);
                notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.narrowcast(ctx, notification);
                return;
            }



            ChessGame game = null;
            try
            {

                game = serviceGame.move(command.getGameID(), command.getMove());
            }
            catch (Exception ex)
            {
                System.out.print(String.format("Error Starting your move %s.", username));
                message = String.format("error : %s", ex);
                notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.narrowcast(ctx, notification);
                return;
            }



            System.out.print(String.format("Starting your move %s.", username));
            notification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, game);
            connections.broadcastViaGameID(null, notification, command.getGameID());

            // Tell them what piece was moved
            message = String.format("%s has moved from %s to %s.", username, command.getMove().getStartPosition().toStringShort(),
                    command.getMove().getEndPosition().toStringShort());

            notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcastViaGameID(ctx, notification, command.getGameID());





            // Tell if in Checkmate.
            if (game.isInCheckmate(ChessGame.TeamColor.WHITE))
            {
                if (gameData.whiteUsername().equals(username))
                {
                    message = "You are in Checkmate.";
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.narrowcast(ctx, notification);

                    message = String.format("White player, %s, is in Checkmate.  Game over", gameData.whiteUsername());
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.broadcastViaGameID(ctx, notification, command.getGameID());
                } else
                {
                    message = String.format("White player, %s, is in Checkmate.  Game over", gameData.whiteUsername());
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.broadcastViaGameID(null, notification, command.getGameID());
                }
                serviceGame.gameOver(command.getGameID());
            }
            // Tell if in Checkmate.
            else if (game.isInCheckmate(ChessGame.TeamColor.BLACK))
            {
                if (gameData.blackUsername().equals(username))
                {
                    message = "You are in Checkmate.";
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.narrowcast(ctx, notification);

                    message = String.format("Black player, %s, is in Checkmate.  Game over", gameData.blackUsername());
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.broadcastViaGameID(ctx, notification, command.getGameID());
                } else
                {
                    message = String.format("Black player, %s, is in Checkmate.  Game over", gameData.blackUsername());
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.broadcastViaGameID(null, notification, command.getGameID());
                }
                serviceGame.gameOver(command.getGameID());
            }
            // Tell if in Check.
            else if (game.isInCheck(ChessGame.TeamColor.WHITE))
            {
                if (gameData.whiteUsername().equals(username))
                {
                    message = "You are in Check.";
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.narrowcast(ctx, notification);

                    message = String.format("White player, %s, is in Check.", gameData.whiteUsername());
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.broadcastViaGameID(ctx, notification, command.getGameID());
                } else
                {
                    message = String.format("White player, %s, is in Check.", gameData.whiteUsername());
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.broadcastViaGameID(null, notification, command.getGameID());
                }
            }
            // Tell if in Check.
            else if (game.isInCheck(ChessGame.TeamColor.BLACK))
            {
                if (gameData.blackUsername().equals(username))
                {
                    message = "You are in Check.";
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.narrowcast(ctx, notification);

                    message = String.format("Black player, %s, is in Check.", gameData.blackUsername());
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.broadcastViaGameID(ctx, notification, command.getGameID());
                } else
                {
                    message = String.format("Black player, %s, is in Check.", gameData.blackUsername());
                    notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                    connections.broadcastViaGameID(null, notification, command.getGameID());
                }
            }


        }
        catch (Exception ex)
        {
            message = String.format("error : %s", ex);
            notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.narrowcast(ctx, notification);
        }




    }

    private void leaveGame(WsMessageContext ctx, String username, UserGameCommand command) throws IOException {
        System.out.print("Leaving user " + username + "\n");

        usernameConnected = "";
        var message = String.format("%s left the game", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcastViaGameID(ctx, notification, command.getGameID());
        connections.remove(ctx);

        try
        {
            serviceGame.unjoin(command.getGameID(), username);

        }
        catch (Exception ex)
        {
            System.out.print(String.format("error : %s", ex));
        }
    }

    private void resign(WsMessageContext ctx, String username, UserGameCommand command) throws IOException {
        System.out.print("Resigning user " + username + "\n");

        try {

            // Check if they are a player
            GameDataShort gameData = serviceGame.getGameDataShort(command.getGameID());
            if (gameData.whiteUsername().equals(username))
            {
                System.out.print(String.format("%s is the white player in the game.", username));

            }
            else if (gameData.blackUsername().equals(username))
            {
                System.out.print(String.format("%s is the black player in the game.", username));
            }
            else
            {
                var message = String.format("error : %s is not a part of that game.", username);
                var notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.narrowcast(ctx, notification);
                return;
            }

            // Check if the game is already over.
            ChessGame gameTemp = serviceGame.view(command.getGameID());
            if (gameTemp.getTeamTurn() == ChessGame.TeamColor.NONE)
            {
                var message = String.format("error : Game is already over %s.  Go home.", username);
                var notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
                connections.narrowcast(ctx, notification);
                return;
            }

            try
            {
                serviceGame.gameOver(command.getGameID());

            }
            catch (Exception ex)
            {
                System.out.print(String.format("error : %s", ex));
            }


            var message = String.format("%s is a quitter. Game Over", username);
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcastViaGameID(null, notification, command.getGameID());
        }
        catch (Exception ex)
        {
            var message = String.format("error : Exception ", ex);
            var notification = new ServerMessage(ServerMessage.ServerMessageType.ERROR, message);
            connections.narrowcast(ctx, notification);
        }

    }

}