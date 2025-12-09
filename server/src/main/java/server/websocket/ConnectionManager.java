package server.websocket;

import com.google.gson.Gson;
import io.javalin.websocket.WsMessageContext;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import org.junit.jupiter.api.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.websocket.*;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<WsMessageContext, WsMessageContext> connections = new ConcurrentHashMap<>();

    public void add(WsMessageContext ctx) {
        connections.put(ctx, ctx);
    }

    public void remove(WsMessageContext ctx) {
        connections.remove(ctx);
    }

    public void broadcast(WsMessageContext excludeSession, ServerMessage notification) throws IOException {
        System.out.print("Broadcasting\n");



        String msg = notification.toString();
        for (WsMessageContext c : connections.values()) {
            System.out.print("Broadcasting " + c + "\n");
            if (c.session.isOpen()) {
                if (!c.equals(excludeSession)) {
                    System.out.print("Sending String " + msg + "\n");
                    try {
                        // c.getRemote().sendString(msg);

                        // var serializer = new Gson();
                        // var json = serializer.toJson(msg);

                        // System.out.print("As json " + json + "\n");
                        // c.getRemote().sendString(json);

                        // c.getBasicRemote().sendText(new Gson().toJson(msg));

                        // c.getRemote().sendString(new Gson().toJson(msg));

//                    jakarta.websocket.Session d = (jakarta.websocket.Session) c;
//
//                    d.getBasicRemote().sendText(new Gson().toJson(msg));

//                        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//                        // jakarta.websocket.Session d = container.connectToServer(this, new URI("ws://localhost:8080/ws"));
//                        jakarta.websocket.Session d = container.
//                            connectToServer(this, new URI("ws://localhost:8080/ws"));
//                        d.getBasicRemote().sendText(new Gson().toJson(msg));
//                        // container.connectToServer(c.getRemote(), )

                        c.send(new Gson().toJson(notification));

                    }
                    catch (Exception ex)
                    {
//                        System.out.print("Json = " + new Gson().toJson(msg) + "\n");
//                        c.getRemote().sendString(new Gson().toJson(msg));

                        System.out.print("Json = " + new Gson().toJson(msg) + "\n");
                        c.session.getRemote().sendString(new Gson().toJson(msg));

                    }



                }
            }
        }
    }

    public void narrowcast(WsMessageContext ctx, ServerMessage notification) throws IOException {
        System.out.print("Narrowcasting\n");

        ctx.send(new Gson().toJson(notification));

    }


}
