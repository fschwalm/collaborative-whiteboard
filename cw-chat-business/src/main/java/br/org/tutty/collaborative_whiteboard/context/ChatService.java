package br.org.tutty.collaborative_whiteboard.context;

import org.json.JSONObject;

import javax.websocket.Session;

/**
 * Created by drferreira on 27/11/14.
 */
public interface ChatService {

    public void connect(Session session);

    public void disconect(Session session);

    public void broadcast(JSONObject jsonMessage, Session session);
}
