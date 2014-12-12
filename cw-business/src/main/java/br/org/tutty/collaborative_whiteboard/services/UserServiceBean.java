package br.org.tutty.collaborative_whiteboard.services;

import br.org.tutty.collaborative_whiteboard.context.Context;
import br.org.tutty.collaborative_whiteboard.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.model.User;

import javax.ejb.Local;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Created by drferreira on 12/12/14.
 */
@Local(UserService.class)
public class UserServiceBean implements UserService {

    @Inject
    private Context context;

    @Override
    public User getLoggedUser(HttpSession httpSession){
        return context.fetchByBrowserSession(httpSession).getUser();
    }

    @Override
    public User getLoggedUser(Session websocketSession){
        return context.fetchByWebSocketSession(websocketSession).getUser();
    }

    @Override
    public void authorizedUser(HttpSession httpSession, Session websocketSession){
        // TODO Adicionar validação
        context.registerLogin(new LoggedUser(new User(), httpSession, websocketSession));
    }

    @Override
    public void unauthorizedUser(User user, HttpSession httpSession){
        // TODO Adicionar desvalidação
        context.unregisterLogin(context.fetchByBrowserSession(httpSession));
    }
}
