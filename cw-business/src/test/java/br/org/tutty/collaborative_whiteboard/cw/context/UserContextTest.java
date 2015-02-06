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
public class UserContextTest {

    private UserContext userContext;

    @Mock
    private LoggedUser loggedUser;

    @Mock
    private LoggedUser otherLoggedUser;

    @Test
    public void addUserShouldAddUserToList(){
        userContext = new UserContext();
        userContext.setUp();

        userContext.addUser(loggedUser);
        Assert.assertEquals(1, userContext.countLoggedUsers());
    }

    @Test
    public void removeUserShouldRemoveUserToList(){
        userContext = new UserContext();
        userContext.setUp();

        userContext.addUser(loggedUser);
        userContext.removeUser(loggedUser);

        Assert.assertEquals(0, userContext.countLoggedUsers());
    }

    @Test
    public void fetchShouldReturnSpecificUserWithHttpSessionId() throws DataNotFoundException {
        String idLoggedUser = "loggedUser";
        String idOtherLoggedUser = "otherLoggedUser";

        Mockito.when(loggedUser.getHttpSessionId()).thenReturn(idLoggedUser);
        Mockito.when(otherLoggedUser.getHttpSessionId()).thenReturn(idOtherLoggedUser);

        userContext = new UserContext();
        userContext.setUp();

        userContext.addUser(loggedUser);
        userContext.addUser(otherLoggedUser);

        LoggedUser loggedFounded = userContext.fetch(idLoggedUser);
        Assert.assertTrue(loggedFounded.getHttpSessionId().equals(idLoggedUser));

        loggedFounded = userContext.fetch(idOtherLoggedUser);
        Assert.assertTrue(loggedFounded.getHttpSessionId().equals(idOtherLoggedUser));
    }

    @Test(expected = DataNotFoundException.class)
    public void fetchShouldThrowDataNotFoundWhenDontHaveSpecificUser() throws DataNotFoundException {
        String idLoggedUser = "loggedUser";

        userContext = new UserContext();
        userContext.setUp();

        userContext.fetch(idLoggedUser);
    }

    @Test
    public void countLoggedUsers(){
        userContext = new UserContext();
        userContext.setUp();

        userContext.addUser(loggedUser);
        userContext.addUser(loggedUser);
        userContext.addUser(loggedUser);
        userContext.addUser(loggedUser);

        Assert.assertEquals(4, userContext.countLoggedUsers());
    }
}
