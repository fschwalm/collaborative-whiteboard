package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.cw.exceptions.LoginException;
import br.org.tutty.collaborative_whiteboard.cw.model.Security;

import javax.servlet.http.HttpSession;

/**
 * Created by drferreira on 16/12/14.
 */
public interface SecurityService {
    void login(Security security) throws LoginException;

    void logout(HttpSession httpSession);

    Boolean isLogged(HttpSession httpSession);
}
