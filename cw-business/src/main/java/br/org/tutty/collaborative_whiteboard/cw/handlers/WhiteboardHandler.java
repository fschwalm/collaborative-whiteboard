package br.org.tutty.collaborative_whiteboard.cw.handlers;

import com.google.gson.Gson;
import dtos.WhiteboardMailable;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by drferreira on 20/05/15.
 */
@ApplicationScoped
public class WhiteboardHandler implements Serializable {
    private final Set<Session> sessions = new HashSet<>();

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void broadcast(WhiteboardMailable whiteboard) {
        getSessions().stream().forEach(session -> send(whiteboard, session));
    }

    public void send(WhiteboardMailable whiteboard, Session target) {
        try {
            target.getBasicRemote().sendObject(new Gson().toJson(whiteboard));
        } catch (IOException | EncodeException e) {
        }
    }
}
