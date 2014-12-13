package br.org.tutty.collaborative_whiteboard.model;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Date;

/**
 * Created by drferreira on 12/12/14.
 */
public class LoggedUser {

    private User user;
    private HttpSession httpSession;
    private Session websocketSession;
    private Date loginDate;

    public LoggedUser(User user, HttpSession httpSession, Session websocketSession) {
        this.user = user;
        this.httpSession = httpSession;
        this.websocketSession = websocketSession;
        this.loginDate = new Date();
    }

    public User getUser() {
        return user;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public Session getWebsocketSession() {
        return websocketSession;
    }

    public Boolean isActivityTransmition(){
        return websocketSession != null && websocketSession.isOpen();
    }

    public void activateTransmition(Session websocketSession) {
        this.websocketSession = websocketSession;
    }

    public void disableTransmition() {
        this.websocketSession = null;
    }

}
