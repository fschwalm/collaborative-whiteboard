package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.cw.context.UserContext;
import cw.dtos.LoggedUser;
import cw.dtos.Security;
import cw.entities.User;
import cw.exceptions.AuthenticationException;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.LoginException;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpSession;

import static junit.framework.Assert.*;

/**
 * Created by drferreira on 06/02/15.
 */
@RunWith(PowerMockRunner.class)
public class SecurityServiceBeanTest {

    @InjectMocks
    private SecurityServiceBean securityServiceBean;

    @Mock
    private UserContext userContext;

    @Mock
    private UserServiceBean userServiceBean;

    @Mock
    private Security security;

    @Mock
    private User user;

    @Mock
    private String email;

    @Mock
    private HttpSession httpSession;

    @Mock
    private LoggedUser loggedUser;

    @Test
    public void loginShouldFetchUserByEmail() throws LoginException, DataNotFoundException {
        Mockito.when(security.getEmail()).thenReturn(email);
        Mockito.when(security.getHttpSession()).thenReturn(httpSession);
        Mockito.when(userServiceBean.fetch(email)).thenReturn(user);

        securityServiceBean.login(security);

        Mockito.verify(userServiceBean).fetch(email);
    }

    @Test
    public void loginShouldAuthenticateUser() throws DataNotFoundException, LoginException, AuthenticationException {
        Mockito.when(security.getEmail()).thenReturn(email);
        Mockito.when(security.getHttpSession()).thenReturn(httpSession);
        Mockito.when(userServiceBean.fetch(email)).thenReturn(user);

        securityServiceBean.login(security);

        Mockito.verify(security).checkAuthentication(user);
    }

    @Test
    public void loginShouldAddFoundedUserToContext() throws DataNotFoundException, LoginException {
        Mockito.when(security.getEmail()).thenReturn(email);
        Mockito.when(security.getHttpSession()).thenReturn(httpSession);
        Mockito.when(userServiceBean.fetch(email)).thenReturn(user);

        securityServiceBean.login(security);

        Mockito.verify(userContext).addUser(Matchers.any());
    }

    @Test(expected = LoginException.class)
    public void loginThrowLoginExceptionWhenDontHaveSpecificUser() throws DataNotFoundException, LoginException {
        Mockito.when(security.getEmail()).thenReturn(email);
        Mockito.when(userServiceBean.fetch(email)).thenThrow(DataNotFoundException.class);

        securityServiceBean.login(security);
    }

    @Test(expected = LoginException.class)
    public void loginThrowLoginExceptionWhenCheckAuthenticationIsFail() throws DataNotFoundException, LoginException {
        Mockito.when(security.getEmail()).thenReturn(email);
        Mockito.when(userServiceBean.fetch(email)).thenReturn(user);
        Mockito.when(security.getHttpSession()).thenThrow(AuthenticationException.class);

        securityServiceBean.login(security);
    }

    @Test
    public void logoutShouldFetchSpecificUser() throws DataNotFoundException {
        String httpSessionId = "httpSessionId";

        Mockito.when(userContext.fetch(httpSessionId)).thenReturn(loggedUser);
        Mockito.when(httpSession.getId()).thenReturn(httpSessionId);

        securityServiceBean.logout(httpSession);

        Mockito.verify(userContext).fetch(httpSessionId);
    }

    @Test
    public void logoutShouldRemoveUserInContext() throws DataNotFoundException {
        String httpSessionId = "httpSessionId";

        Mockito.when(userContext.fetch(httpSessionId)).thenReturn(loggedUser);
        Mockito.when(httpSession.getId()).thenReturn(httpSessionId);

        securityServiceBean.logout(httpSession);

        Mockito.verify(userContext).removeUser(loggedUser);
    }

    @Test
    public void logoutShouldCatchAndPrintDataNotFoundExceptionWhenDontHaveUserInContext() throws DataNotFoundException {
        String httpSessionId = "httpSessionId";

        Mockito.when(userContext.fetch(httpSessionId)).thenThrow(DataNotFoundException.class);
        Mockito.when(httpSession.getId()).thenReturn(httpSessionId);

        securityServiceBean.logout(httpSession);
    }

    @Test
    public void isLoggedShouldReturnTrueWhenUserExistInContext() throws DataNotFoundException {
        String httpSessionId = "httpSessionId";

        Mockito.when(httpSession.getId()).thenReturn(httpSessionId);
        Mockito.when(userContext.fetch(httpSessionId)).thenReturn(loggedUser);

        assertTrue(securityServiceBean.isLogged(httpSession));
    }

    @Test
    public void isLoggedShouldReturnFalseWhenUserDontExistInContext() throws DataNotFoundException {
        String httpSessionId = "httpSessionId";

        Mockito.when(httpSession.getId()).thenReturn(httpSessionId);
        Mockito.when(userContext.fetch(httpSessionId)).thenThrow(DataNotFoundException.class);

        assertFalse(securityServiceBean.isLogged(httpSession));
    }
}
