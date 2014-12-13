package br.org.tutty.collaborative_whiteboard.context;

import br.org.tutty.collaborative_whiteboard.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.exceptions.DataNotFoundException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.Serializable;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by drferreira on 12/12/14.
 */
@ApplicationScoped
public class Context implements Serializable {
    private Set<LoggedUser> loggedUsers;

    @PostConstruct
    public void setUp(){
        this.loggedUsers = new HashSet<>();
    }

    private LoggedUser fetch(Predicate predicate) throws DataNotFoundException {
        try{
            return (LoggedUser) loggedUsers.stream().filter(predicate).findFirst().get();
        }catch (NoSuchElementException exception){
            throw new DataNotFoundException();
        }
    }

    public LoggedUser fetch(Session websocketSession) throws DataNotFoundException {
        return fetch(new Predicate<LoggedUser>() {
            @Override
            public boolean test(LoggedUser loggedUser) {
                return loggedUser.getWebsocketSession().equals(websocketSession);
            }
        });
    }

    public LoggedUser fetch(HttpSession httpSession) throws DataNotFoundException {
        return fetch(new Predicate<LoggedUser>() {
            @Override
            public boolean test(LoggedUser loggedUser) {
                return loggedUser.getHttpSession().equals(httpSession);
            }
        });
    }
}
