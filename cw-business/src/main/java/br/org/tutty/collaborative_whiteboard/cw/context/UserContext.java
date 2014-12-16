package br.org.tutty.collaborative_whiteboard.cw.context;

import br.org.tutty.collaborative_whiteboard.cw.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException;

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
public class UserContext implements Serializable {
    private Set<LoggedUser> loggedUsers;

    @PostConstruct
    public void setUp(){
        this.loggedUsers = new HashSet<>();
    }

    public void addUser(LoggedUser loggedUser){
        loggedUsers.add(loggedUser);
    }

    public void removeUser(LoggedUser loggedUser){
        loggedUsers.remove(loggedUser);
    }

    private LoggedUser fetch(Predicate<LoggedUser> predicate) throws DataNotFoundException {
        try{
            return loggedUsers.stream().filter(predicate).findFirst().get();
        }catch (NoSuchElementException exception){
            throw new DataNotFoundException();
        }
    }

    public LoggedUser fetch(Session websocketSession) throws DataNotFoundException {
        return fetch(lUser -> lUser.getWebsocketSessionId().equals(websocketSession.getId()));
//        return fetch(new Predicate<LoggedUser>() {
//            @Override
//            public boolean test(LoggedUser loggedUser) {
//                return loggedUser.getWebsocketSessionId().equals(websocketSession.getId());
//            }
//        });
    }

    public LoggedUser fetch(HttpSession httpSession) throws DataNotFoundException {
        return fetch(lUser -> lUser.getHttpSessionId().equals(httpSession.getId()));
//        return fetch(new Predicate<LoggedUser>() {
//            @Override
//            public boolean test(LoggedUser loggedUser) {
//                return loggedUser.getHttpSessionId().equals(httpSession.getId());
//            }
//        });
    }
}
