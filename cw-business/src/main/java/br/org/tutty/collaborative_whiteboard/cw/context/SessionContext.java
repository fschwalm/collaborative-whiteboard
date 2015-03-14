package br.org.tutty.collaborative_whiteboard.cw.context;

import cw.dtos.LoggedUser;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created by drferreira on 13/03/15.
 */
@SessionScoped
public class SessionContext implements Serializable {
    private LoggedUser loggedUser;

    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }
}
