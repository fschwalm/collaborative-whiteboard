package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.enums.StoryStatus;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import cw.exceptions.DataNotFoundException;
import org.primefaces.event.ReorderEvent;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
@Named
@ViewScoped
public class BacklogController extends GenericController implements Serializable {
    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private SessionContext sessionContext;

    @Inject
    private UserService userService;

    @Inject
    private EditionStoryController editionStoryController;

    private List<Story> stories;

    private List<Story> storiesFiltered;

    public void onRowReorder(ReorderEvent event) throws IOException {
        Collections.swap(stories, event.getFromIndex(), event.getToIndex());
        backlogManagerService.updateBacklog(stories);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.change_priority");
    }

    public void removeStory() throws IOException {
        Story selectedStory = editionStoryController.getSelectedStory();
        backlogManagerService.removeStory(selectedStory);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.removed_story");
    }

    public List<Story> fetchStories() {
        try {
            this.stories = backlogManagerService.fetchAllStories();
            return stories;

        } catch (Exception e) {
            stories = new ArrayList();
            return stories;
        }
    }

    public Boolean isRemoved(Story story) throws DataNotFoundException {
        return StoryStatus.REMOVED.equals(getStatus(story));
    }

    public StoryStatus getStatus(Story story) {
        StoryStatusLog storyStatusLog;
        try {
            storyStatusLog = backlogManagerService.getStoryStatus(story);
            return storyStatusLog.getStoryStatus();

        } catch (DataNotFoundException e) {
            return  null;
        }
    }

    public List<Story> getStoriesFiltered() {
        return storiesFiltered;
    }

    public void setStoriesFiltered(List<Story> storiesFiltered) {
        this.storiesFiltered = storiesFiltered;
    }
}
