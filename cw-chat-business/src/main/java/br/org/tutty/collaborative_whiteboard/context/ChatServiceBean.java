package br.org.tutty.collaborative_whiteboard.context;

import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.Local;
import javax.enterprise.context.SessionScoped;
import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by drferreira on 27/11/14.
 */
@Local(ChatService.class)
public class ChatServiceBean implements ChatService, Serializable {

    @Override
    public void connect(Session session) {
       // TODO BUSCAR E TORNAR DISPONIVEL USUARIO
    }

    @Override
    public void disconect(Session session) {
        // TODO ANALIZAR LOGOUT USUARIO
    }

    public void broadcast(JSONObject jsonMessage, Session session){
        session.getOpenSessions().forEach(s -> {
            if(!s.getId().equals(session.getId()))
                try {
                    s.getBasicRemote().sendText(jsonMessage.toString());
                } catch (IOException e) {
                }
        });
    }
}