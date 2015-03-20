package br.org.tutty.collaborative_whiteboard.cw.context;

import br.org.tutty.collaborative_whiteboard.GenericDao;
import cw.dtos.LoggedUser;
import cw.entities.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by drferreira on 13/03/15.
 */
@SessionScoped
public class SessionContext implements Serializable {

    @Inject
    private GenericDao genericDao;

    private LoggedUser loggedUser;

    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }
}
