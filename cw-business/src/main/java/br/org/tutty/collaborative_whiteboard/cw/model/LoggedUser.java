package br.org.tutty.collaborative_whiteboard.cw.model;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by drferreira on 12/12/14.
 */
public class LoggedUser {

    private User user;
    private String httpSessionId;
    private Set<String> websocketSessionIds;
    private Date loginDate;

    public LoggedUser(User user, HttpSession httpSession, Session websocketSession) {
        websocketSessionIds = new HashSet<>();

        this.user = user;
        this.httpSessionId = httpSession.getId();
        this.websocketSessionIds.add(websocketSession.getId());
        this.loginDate = new Date();
    }

    public LoggedUser(User user, HttpSession httpSession) {
        websocketSessionIds = new HashSet<>();

        this.user = user;
        this.httpSessionId = httpSession.getId();
        this.loginDate = new Date();
    }

    public User getUser() {
        return user;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public String getHttpSessionId() {
        return httpSessionId;
    }

    public Boolean hasWebsocketSessionId(String websocketSessionId) {
        return websocketSessionIds.contains(websocketSessionId);
    }

    public Boolean isActivityTransmition(){
        return ! websocketSessionIds.isEmpty();
    }

    public void activateTransmition(Session websocketSession) {
        this.websocketSessionIds.add(websocketSession.getId());
    }

    public void disableTransmition() {
        this.websocketSessionIds = new HashSet<>();
    }

}
