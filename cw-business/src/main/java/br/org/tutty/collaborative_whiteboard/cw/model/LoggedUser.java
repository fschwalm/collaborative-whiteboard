package br.org.tutty.collaborative_whiteboard.cw.model;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by drferreira on 12/12/14.
 */
public class LoggedUser {
    private User user;
    private Date loginDate;
    private String httpSessionId;

    public LoggedUser(User user, HttpSession httpSession) {
        this.user = user;
        this.httpSessionId = httpSession.getId();
        this.loginDate = new Date();
    }

    public User getUser() {
        return user;
    }

    public String getHttpSessionId() {
        return httpSessionId;
    }
}
