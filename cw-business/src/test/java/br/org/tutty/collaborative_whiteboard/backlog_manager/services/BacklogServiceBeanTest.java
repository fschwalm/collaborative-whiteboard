package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import br.org.tutty.backlog_manager.BacklogDaoBean;
import cw.exceptions.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by drferreira on 11/03/15.
 */
@RunWith(PowerMockRunner.class)
public class BacklogServiceBeanTest {

    @InjectMocks
    private BacklogManagerServiceBean backlogManagerServiceBean;

    @Mock
    private BacklogDaoBean backlogDaoBean;

    @Test
    public void fetchAllStoriesShouldCallDaoWithoutFilters() throws DataNotFoundException {
        backlogManagerServiceBean.fetchAllStories();

        Mockito.verify(backlogDaoBean).fetchStories();
    }

    @Test(expected = DataNotFoundException.class)
    public void fetchAllStoriesShouldThrowDataNotFoundWhenNotFoundStories() throws DataNotFoundException {
        Mockito.when(backlogDaoBean.fetchStories()).thenThrow(DataNotFoundException.class);

        backlogManagerServiceBean.fetchAllStories();
    }

}
