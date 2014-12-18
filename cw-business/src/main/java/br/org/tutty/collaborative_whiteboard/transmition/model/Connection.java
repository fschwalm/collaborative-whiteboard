package br.org.tutty.collaborative_whiteboard.transmition.model;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drferreira on 18/12/14.
 */
public class Connection {
    private List<String> webSocketSessionIds;
    private String httpSessionId;

    public Connection(Session webSocketSession, HttpSession httpSession) {
        this.httpSessionId = httpSession.getId();
        this.webSocketSessionIds = new ArrayList<>();

        addSocket(webSocketSession);
    }

    public void addSocket(Session webSocketSession){
        this.webSocketSessionIds.add(webSocketSession.getId());
    }

    public String getHttpSession() {
        return httpSessionId;
    }
}
