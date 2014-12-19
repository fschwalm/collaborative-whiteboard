package br.org.tutty.collaborative_whiteboard.cw.context;

import br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.cw.model.LoggedUser;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.*;

/**
 * Created by drferreira on 12/12/14.
 */
@javax.enterprise.context.ApplicationScoped
public class UserContext implements Serializable {
    private List<LoggedUser> loggedUsers;

    @PostConstruct
    public void setUp(){
        this.loggedUsers = new ArrayList<>();
    }

    public void addUser(LoggedUser loggedUser){
        loggedUsers.add(loggedUser);
    }

    public void removeUser(LoggedUser loggedUser){
        loggedUsers.remove(loggedUser);
    }

    public LoggedUser fetch(String httpSessionId) throws DataNotFoundException {
        for (LoggedUser loggedUser : loggedUsers){
            if(loggedUser.getHttpSessionId().equals(httpSessionId)){
                return loggedUser;
            }
        }

        throw new DataNotFoundException();
    }
}
