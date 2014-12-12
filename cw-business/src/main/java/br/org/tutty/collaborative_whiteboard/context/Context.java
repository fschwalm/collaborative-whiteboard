package br.org.tutty.collaborative_whiteboard.context;

import br.org.tutty.collaborative_whiteboard.model.LoggedUser;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by drferreira on 12/12/14.
 */
@SessionScoped
public class Context implements Serializable {

    private Set<LoggedUser> loggedUsers;

    public Context() {
        this.loggedUsers = new HashSet<>();
    }

    public void registerLogin(LoggedUser loggedUser) {
        loggedUsers.add(loggedUser);
    }

    public void unregisterLogin(LoggedUser loggedUser) {
        loggedUsers.remove(loggedUser);
    }


    public LoggedUser fetchByWebSocketSession(Session websocketSession) {
        return loggedUsers.stream()
                .filter(logged -> logged.getWebsocketSession().equals(websocketSession))
                .limit(1)
                .findFirst()
                .get();
    }

    public LoggedUser fetchByBrowserSession(HttpSession httpSession) {
        return loggedUsers.stream()
                .filter(logged -> logged.getHttpSession().equals(httpSession))
                .limit(1)
                .findFirst()
                .get();
    }
}
