package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.context.UserGlobalContext;
import cw.dtos.Security;
import cw.dtos.LoggedUser;
import cw.entities.User;
import cw.exceptions.AuthenticationException;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.LoginException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Created by drferreira on 16/12/14.
 */
@Local(SecurityService.class)
@Stateless
public class SecurityServiceBean implements SecurityService, Serializable {
    @Inject
    private UserGlobalContext userGlobalContext;

    @Inject
    private SessionContext sessionContext;

    @Inject
    private UserService userService;

    @Override
    public void login(Security security) throws LoginException {
        try{
            User foundUser = userService.fetch(security.getEmail());
            security.checkAuthentication(foundUser);

            LoggedUser loggedUser = new LoggedUser(foundUser, security.getHttpSession());

            userGlobalContext.addUser(loggedUser);
            sessionContext.setLoggedUser(loggedUser);

        }catch (DataNotFoundException | AuthenticationException exception){
            throw new LoginException(exception);
        }
    }

    @Override
    public void logout(HttpSession httpSession) {
        try {
            LoggedUser loggedUser = userGlobalContext.fetch(httpSession.getId());
            userGlobalContext.removeUser(loggedUser);

        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean isLogged(HttpSession httpSession) {
        try {
            userGlobalContext.fetch(httpSession.getId());
            return true;

        } catch (DataNotFoundException e) {
            return false;
        }
    }
}
