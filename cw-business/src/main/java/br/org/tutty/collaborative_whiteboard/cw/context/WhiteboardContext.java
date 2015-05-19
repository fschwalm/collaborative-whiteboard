package br.org.tutty.collaborative_whiteboard.cw.context;

import javax.enterprise.context.SessionScoped;
import javax.websocket.Session;
import java.io.Serializable;
import java.util.List;

/**
 * Created by drferreira on 19/05/15.
 */
@SessionScoped
public class WhiteboardContext implements Serializable {

    private List<Session> conectedSessions;

    public void connect(Session session){
        conectedSessions.add(session);
    }

    public void disconnect(Session session){
        //todo Desconectar - remover da lista
    }
}
