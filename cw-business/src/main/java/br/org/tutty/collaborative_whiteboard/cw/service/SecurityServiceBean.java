package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.cw.context.UserContext;
import cw.dtos.Security;
import cw.entities.LoggedUser;
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
    private UserContext userContext;

    @Inject
    private UserService userService;

    /**
     * Verifica se existe um usuário com o email informado.
     * Caso não exista {@link cw.exceptions.LoginException}
     * composto por {@link cw.exceptions.DataNotFoundException}.
     *
     * Sera feita a comparação entre senhas (valores já encriptados). Caso não seja positiva a igualdade
     * {@link cw.exceptions.LoginException} composto por
     * {@link cw.exceptions.AuthenticationException}
     *
     * Após as devidas verificações o resultado positivo será adicionado ao contexto para consulta posterior.
     * @param security
     * @throws LoginException
     */
    @Override
    public void login(Security security) throws LoginException {
        try{
            User foundUser = userService.fetch(security.getEmail());
            security.checkAuthentication(foundUser);
            userContext.addUser(new LoggedUser(foundUser, security.getHttpSession()));

        }catch (DataNotFoundException | AuthenticationException exception){
            throw new LoginException(exception);
        }
    }

    @Override
    public void logout(HttpSession httpSession) {
        try {
            LoggedUser loggedUser = userContext.fetch(httpSession.getId());
            userContext.removeUser(loggedUser);

        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean isLogged(HttpSession httpSession) {
        try {
            userContext.fetch(httpSession.getId());
            return true;

        } catch (DataNotFoundException e) {
            return false;
        }
    }
}
