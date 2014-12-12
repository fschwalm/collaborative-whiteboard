package br.org.tutty.collaborative_whiteboard.services;

import br.org.tutty.collaborative_whiteboard.model.User;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Created by drferreira on 12/12/14.
 */
public interface UserService {
    User getLoggedUser(HttpSession httpSession);

    User getLoggedUser(Session websocketSession);

    void authorizedUser(HttpSession httpSession, Session websocketSession);

    void unauthorizedUser(User user, HttpSession httpSession);
}
