package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;
import br.org.tutty.backlog_manager.BacklogDao;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.dtos.LoggedUser;
import cw.entities.Project;
import cw.exceptions.DataNotFoundException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by drferreira on 11/03/15.
 */
@Local(BacklogManagerService.class)
@Stateless
public class BacklogManagerServiceBean implements BacklogManagerService {
    @Inject
    private SessionContext sessionContext;

    @Inject
    private BacklogDao backlogDao;

    @Override
    public List<Story> fetchAllStories() throws DataNotFoundException {
        return backlogDao.fetchAllStories();
    }

    @Override
    public Story getEmptyStory(Project project) {
        LoggedUser loggedUser = sessionContext.getLoggedUser();
        Story story = new Story(loggedUser.getUser());
        story.setProject(project);
        story.setCode("TESTE CODE");
        return story;
    }

    @Override
    public List<Story> reformulatePriorities(List<Story> stories){
        stories.stream().forEach(story ->
                {
                    Integer priorityCounter = 1;
                    story.setPriority(priorityCounter);
                    priorityCounter++;
                }
        );

        return stories;
    }

    @Override
    public List<Story> sortStoriesByPriority(List<Story> stories) {
        Comparator<Story> byPriority = (elementOne, elementTwo) -> Integer.compare(
                elementOne.getPriority(), elementTwo.getPriority());

        return stories.stream().sorted(byPriority).collect(Collectors.toList());
    }

    @Override
    public void updateBacklog(List<Story> stories) {
        backlogDao.updateStories(stories);
    }

}
