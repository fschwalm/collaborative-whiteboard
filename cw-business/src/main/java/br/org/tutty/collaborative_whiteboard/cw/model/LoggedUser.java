package br.org.tutty.collaborative_whiteboard.cw.model;

import br.org.tutty.collaborative_whiteboard.transmition.model.Transmition;

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
    private Date loginDate;

    public LoggedUser(User user) {
        this.user = user;
        this.loginDate = new Date();
    }

    public User getUser() {
        return user;
    }

    public Date getLoginDate() {
        return loginDate;
    }
}
