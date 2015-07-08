package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import backlog_manager.enums.StoryStatus;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import cw.exceptions.DataNotFoundException;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

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

    private List<Story> allStories;

    public List<Story> fetchStories() {
        try {
            this.allStories = backlogManagerService.fetchAllStories();

            return allStories;

        } catch (Exception e) {
            allStories = new ArrayList();
            return allStories;
        }
    }

    public boolean filterByStatus(Object value, Object filter, Locale locale){
        List<String> filters = Arrays.asList((String[]) filter);

        if(filters.isEmpty()) {
            return true;
        }

        if(value == null) {
            return false;
        }

        StoryStatus storyStatus = getStatus(((Story) value));
        return filters.contains(storyStatus.name());
    }

    public List<StoryStatus> fetchStatus(){
        return Arrays.asList(StoryStatus.values());
    }

    public Boolean isRemoved(Story story) throws DataNotFoundException {
        return StoryStatus.REMOVED.equals(getStatus(story));
    }

    public StoryStatus getStatus(Story story) {
        try {
            return  backlogManagerService.getCurrentStatus(story);
        } catch (DataNotFoundException e) {
            return  null;
        }
    }
}
