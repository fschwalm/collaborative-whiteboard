package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;
import backlog_manager.exceptions.StoryAlreadyIdentifiedException;
import br.org.tutty.backlog_manager.BacklogDao;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.dtos.LoggedUser;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;
import org.hibernate.id.IdentifierGenerationException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
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
        return story;
    }

    @Override
    public void updateBacklog(List<Story> stories) {
        List<Story> prioritizedStories = reformulatePriorities(stories);
        updateStories(prioritizedStories);
    }

    @Override
    public Boolean projectAreaIsAssignedToStory(ProjectArea projectArea){
        try{
            backlogDao.fetchStories(projectArea);
            return Boolean.TRUE;

        }catch (DataNotFoundException e){
            return Boolean.FALSE;
        }
    }

    private String getAvailableCode(Project project, ProjectArea projectArea) {
        Long sequence = (backlogDao.getNextSequenceStory(project) + 1);

        return mountStoryCode(projectArea, sequence);
    }

    private String mountStoryCode(ProjectArea projectArea, Long sequence) {
        String separatorId = "-";
        String projectAreaId = projectArea.getName().toUpperCase().replace(" ", separatorId);

        StringBuffer code = new StringBuffer(projectAreaId);
        code.append(separatorId);
        code.append(sequence.toString());

        return code.toString();
    }

    public List<Story> reformulatePriorities(List<Story> stories) {
        for (Story story : stories){
            int indexOf = stories.indexOf(story);
            story.setPriority(indexOf);
        }

        return stories;
    }

    public Story populateStoryCode(Story story) throws StoryAlreadyIdentifiedException {
        Project project = story.getProject();
        ProjectArea projectArea = story.getProjectArea();

        if(story.getCode() == null){
            String availableCode = getAvailableCode(project, projectArea);
            story.setCode(availableCode);
        }else {
            throw new StoryAlreadyIdentifiedException();
        }

        return story;
    }

    public Story populateBranch(Story story){
        story.setBranch(story.getCode());
        return story;
    }

    private void updateStories(List<Story> stories) {
        stories.forEach(new Consumer<Story>() {
            @Override
            public void accept(Story story) {
                try{
                    Story storyWithNewCode = populateStoryCode(story);
                    Story storyWithDefaultBranch = populateBranch(storyWithNewCode);

                    backlogDao.update(storyWithDefaultBranch);

                }catch (StoryAlreadyIdentifiedException e){
                    backlogDao.update(story);
                }
            }
        });
    }
}
