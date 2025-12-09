package websocket.messages;

import chess.ChessBoard;

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

    ChessBoard chessBoard;

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerMessage(ServerMessageType type) {
        System.out.print("ServerMessage 1 type\n");
        this.serverMessageType = type;
    }



    public ServerMessage(ServerMessageType type, String message) {
        System.out.print("ServerMessage 2 types\n");
        System.out.print("Type = " + type + "\n");
        System.out.print("Message = " + message + "\n");
        this.serverMessageType = type;
        this.message = message;
    }

    public ServerMessage(ServerMessageType type, String message, ChessBoard chessBoard) {
        System.out.print("ServerMessage 3 types\n");
        System.out.print("Type = " + type + "\n");
        System.out.print("Message = " + message + "\n");
        System.out.print("ChessBoard = " + chessBoard + "\n");
        this.serverMessageType = type;
        this.message = message;
        this.chessBoard = chessBoard;
    }

    @Override
    public String toString()
    {
        return "ServerMessage{" +
                "serverMessageType=" + serverMessageType +
                ", message='" + message + '\'' +
                '}';
    }
    public String getMessage() {
        return this.message;
    }

    public ServerMessageType getServerMessageType() {
        return this.serverMessageType;
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
