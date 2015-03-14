package br.org.tutty.collaborative_whiteboard.cw.context;

import cw.dtos.LoggedUser;
import cw.exceptions.DataNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by drferreira on 06/02/15.
 */
@RunWith(PowerMockRunner.class)
public class UserGlobalContextTest {

    private UserGlobalContext userGlobalContext;

    @Mock
    private LoggedUser loggedUser;

    @Mock
    private LoggedUser otherLoggedUser;

    @Test
    public void addUserShouldAddUserToList(){
        userGlobalContext = new UserGlobalContext();
        userGlobalContext.setUp();

        userGlobalContext.addUser(loggedUser);
        Assert.assertEquals(1, userGlobalContext.countLoggedUsers());
    }

    @Test
    public void removeUserShouldRemoveUserToList(){
        userGlobalContext = new UserGlobalContext();
        userGlobalContext.setUp();

        userGlobalContext.addUser(loggedUser);
        userGlobalContext.removeUser(loggedUser);

        Assert.assertEquals(0, userGlobalContext.countLoggedUsers());
    }

    @Test
    public void fetchShouldReturnSpecificUserWithHttpSessionId() throws DataNotFoundException {
        String idLoggedUser = "loggedUser";
        String idOtherLoggedUser = "otherLoggedUser";

        Mockito.when(loggedUser.getHttpSessionId()).thenReturn(idLoggedUser);
        Mockito.when(otherLoggedUser.getHttpSessionId()).thenReturn(idOtherLoggedUser);

        userGlobalContext = new UserGlobalContext();
        userGlobalContext.setUp();

        userGlobalContext.addUser(loggedUser);
        userGlobalContext.addUser(otherLoggedUser);

        LoggedUser loggedFounded = userGlobalContext.fetch(idLoggedUser);
        Assert.assertTrue(loggedFounded.getHttpSessionId().equals(idLoggedUser));

        loggedFounded = userGlobalContext.fetch(idOtherLoggedUser);
        Assert.assertTrue(loggedFounded.getHttpSessionId().equals(idOtherLoggedUser));
    }

    @Test(expected = DataNotFoundException.class)
    public void fetchShouldThrowDataNotFoundWhenDontHaveSpecificUser() throws DataNotFoundException {
        String idLoggedUser = "loggedUser";

        userGlobalContext = new UserGlobalContext();
        userGlobalContext.setUp();

        userGlobalContext.fetch(idLoggedUser);
    }

    @Test
    public void countLoggedUsers(){
        userGlobalContext = new UserGlobalContext();
        userGlobalContext.setUp();

        userGlobalContext.addUser(loggedUser);
        userGlobalContext.addUser(loggedUser);
        userGlobalContext.addUser(loggedUser);
        userGlobalContext.addUser(loggedUser);

        Assert.assertEquals(4, userGlobalContext.countLoggedUsers());
    }
}
