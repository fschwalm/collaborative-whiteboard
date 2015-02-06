package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.UserDaoBean;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by drferreira on 06/02/15.
 */
@RunWith(PowerMockRunner.class)
public class UserServiceBeanTest {

    @InjectMocks
    private UserServiceBean userServiceBean;

    @Mock
    private UserDaoBean userDaoBean;

    @Mock
    private User user;

    @Test(expected = DataNotFoundException.class)
    public void fetchShouldThrowDataNotFoundExceptionWhenDontHaveSpecificUser() throws DataNotFoundException {
        String email = "email";

        Mockito.when(userDaoBean.fetch(email)).thenThrow(DataNotFoundException.class);

        userServiceBean.fetch(email);
    }

    @Test
    public void fetchShouldReturnUser() throws DataNotFoundException {
        String email = "email";

        Mockito.when(userDaoBean.fetch(email)).thenReturn(user);
        userServiceBean.fetch(email);

        Mockito.verify(userDaoBean).fetch(email);
    }
}
