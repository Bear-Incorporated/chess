package server.websocket;

import com.google.gson.Gson;
import io.javalin.websocket.WsMessageContext;
import servermodel.SessionInfo;
import websocket.messages.ServerMessage;

// import jakarta.websocket.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<WsMessageContext, SessionInfo> connections = new ConcurrentHashMap<>();

    public void add(WsMessageContext ctx, SessionInfo sessionInfo) {
        connections.put(ctx, sessionInfo);
    }

    public boolean checkUserName(WsMessageContext ctx, SessionInfo sessionInfo) {
        for (ConcurrentHashMap.Entry<WsMessageContext, SessionInfo> c : connections.entrySet()) {
            System.out.print("Broadcasting " + c + "\n");
            if (c.getValue().username().equals(sessionInfo.username())) {
                return true;
            }
        }
        return false;
    }

    public void remove(WsMessageContext ctx) {
        connections.remove(ctx);
    }



    public void broadcastViaGameID(WsMessageContext excludeSession, ServerMessage notification, int gameID) throws IOException {
        System.out.print("Broadcasting\n");

        if (excludeSession == null)
        {
            String msg = notification.toString();



            for (ConcurrentHashMap.Entry<WsMessageContext, SessionInfo> c : connections.entrySet()) {
                System.out.print("Broadcasting " + c + "\n");
                if (c.getKey().session.isOpen()) {
                    if (c.getValue().gameID() == gameID)
                    {
                        c.getKey().send(new Gson().toJson(notification));
                    }
                }
            }
            return;

        }


        for (ConcurrentHashMap.Entry<WsMessageContext, SessionInfo> c : connections.entrySet()) {
            System.out.print("Broadcasting " + c + "\n");
            if (c.getKey().session.isOpen()) {
                if (!c.getKey().equals(excludeSession))
                {
                    if (c.getValue().gameID() == gameID)
                    {
                        c.getKey().send(new Gson().toJson(notification));
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
