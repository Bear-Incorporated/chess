package websocket.messages;

import chess.ChessBoard;
import chess.ChessGame;

import java.util.Objects;

/**
 * Represents a Message the server can send through a WebSocket
 * <p>
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class ServerMessage {
    ServerMessageType serverMessageType;
    String message;

    String errorMessage;

    ChessGame game;

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerMessage(ServerMessageType type) {
        // System.out.print("ServerMessage 1 type\n");
        this.serverMessageType = type;
    }



    public ServerMessage(ServerMessageType type, String message) {
        // System.out.print("ServerMessage 2 types\n" + "Type = " + type + "\n" + "Message = " + message + "\n");

        this.serverMessageType = type;
        if (serverMessageType == ServerMessageType.ERROR)
        {
            // System.out.print("Error = " + message + "\n");
            if (message == null)
            {
                this.errorMessage = null;
            }
            else
            {
                this.errorMessage = checkError(message);
            }

        } else if (serverMessageType == ServerMessageType.NOTIFICATION)
        {
            // System.out.print("Notification = " + message + "\n");
            this.message = message;
        }


    }



    public ServerMessage(ServerMessageType type, String message, ChessGame chessGame) {
        // System.out.print("ServerMessage 3 types\n" + "Type = " + type + "\n" + "Message = " + message + "\n" + "ChessGame = " + game + "\n");

        this.serverMessageType = type;
        if (message == null)
        {
            this.errorMessage = null;
        }
        else
        {
            this.errorMessage = checkError(message);
        }
        this.game = chessGame;
    }

    private String checkError (String errorMessageIn)
    {
        if (errorMessageIn.contains("400"))
        {
            return "Error: bad request - You cannot access that!";
        } else if (errorMessageIn.contains("401"))
        {
            return "Error: unauthorized";
        } else if (errorMessageIn.contains("403"))
        {
            return "Error: already taken";
        } else if (errorMessageIn.contains("500"))
        {
            return "Error: server error";
        } else
        {
            return errorMessageIn;
        }
    }

    @Override
    public String toString()
    {
        return "ServerMessage{" +
                "serverMessageType=" + serverMessageType +
                ", message='" + message + '\'' +
                ", game='" + game + '\'' +
                '}';
    }

    public ServerMessageType getServerMessageType() {
        return this.serverMessageType;
    }
    public String getMessage() {
        return this.message;
    }

    public String getErrorMessage() {
        return checkError(this.errorMessage);
    }

    public ChessGame getGame() {
        return this.game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServerMessage that)) {
            return false;
        }
        return getServerMessageType() == that.getServerMessageType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerMessageType());
    }
}
