package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import br.org.tutty.backlog_manager.StoryDaoBean;
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
    private StoryDaoBean storyDaoBean;

    @Test
    public void fetchAllStoriesShouldCallDaoWithoutFilters() throws DataNotFoundException {
        backlogManagerServiceBean.fetchAllStories();

        Mockito.verify(storyDaoBean).fetchAllStories();
    }

    @Test(expected = DataNotFoundException.class)
    public void fetchAllStoriesShouldThrowDataNotFoundWhenNotFoundStories() throws DataNotFoundException {
        Mockito.when(storyDaoBean.fetchAllStories()).thenThrow(DataNotFoundException.class);

        backlogManagerServiceBean.fetchAllStories();
    }

}
