package br.org.tutty.collaborative_whiteboard.cw.model;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Date;

/**
 * Created by drferreira on 12/12/14.
 */
public class LoggedUser {

    private User user;
    private String httpSessionId;
    private String websocketSessionId;
    private Date loginDate;

    public LoggedUser(User user, HttpSession httpSession, Session websocketSession) {
        this.user = user;
        this.httpSessionId = httpSession.getId();
        this.websocketSessionId = websocketSession.getId();
        this.loginDate = new Date();
    }

    public LoggedUser(User user, HttpSession httpSession) {
        this.user = user;
        this.httpSessionId = httpSession.getId();
        this.websocketSessionId = null;
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

    public String getWebsocketSessionId() {
        return websocketSessionId;
    }

    public Boolean isActivityTransmition(){
        return websocketSessionId != null;
    }

    public void activateTransmition(Session websocketSession) {
        this.websocketSessionId = websocketSession.getId();
    }

    public void disableTransmition() {
        this.websocketSessionId = null;
    }

}
