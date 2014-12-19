package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.cw.context.UserContext;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.AuthenticationException;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.LoginException;
import br.org.tutty.collaborative_whiteboard.cw.model.LoggedUser;
import br.org.tutty.collaborative_whiteboard.cw.model.Security;
import br.org.tutty.collaborative_whiteboard.cw.model.User;

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
     * Caso não exista {@link br.org.tutty.collaborative_whiteboard.cw.exceptions.LoginException}
     * composto por {@link br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException}.
     *
     * Sera feita a comparação entre senhas (valores já encriptados). Caso não seja positiva a igualdade
     * {@link br.org.tutty.collaborative_whiteboard.cw.exceptions.LoginException} composto por
     * {@link br.org.tutty.collaborative_whiteboard.cw.exceptions.AuthenticationException}
     *
     * Após as devidas verificações o resultado positivo será adicionado ao contexto para consulta posterior.
     * @param security
     * @throws LoginException
     */
    @Override
    public void login(Security security) throws LoginException {
        try{
            User foundUser = userService.fetch(security.getEmail());

            if(! security.checkPassword(foundUser.getPassword())){
                throw new AuthenticationException();
            }

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
