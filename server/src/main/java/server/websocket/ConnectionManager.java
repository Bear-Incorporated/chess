package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.websocket.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Session, Session> connections = new ConcurrentHashMap<>();

    public void add(Session session) {
        connections.put(session, session);
    }

    public void remove(Session session) {
        connections.remove(session);
    }

    public void broadcast(Session excludeSession, ServerMessage notification) throws IOException {
        System.out.print("Broadcasting\n");

        String msg = notification.toString();
        for (Session c : connections.values()) {
            System.out.print("Broadcasting " + c + "\n");
            if (c.isOpen()) {
                if (!c.equals(excludeSession)) {
                    // var serializer = new Gson();
                    // var json = serializer.toJson(msg);
                    System.out.print("Sending String " + msg + "\n");
                    // System.out.print("As json " + json + "\n");
                    // c.getRemote().sendString(json);
                    c.getRemote().sendString(msg);
                    // c.getBasicRemote().sendText(new Gson().toJson(msg));
                }
            }
        }
    }


}
