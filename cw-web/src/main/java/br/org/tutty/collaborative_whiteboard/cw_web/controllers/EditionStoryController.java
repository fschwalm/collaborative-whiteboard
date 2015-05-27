package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.enums.StoryStatus;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryEdition;
import cw.exceptions.DataNotFoundException;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by drferreira on 07/05/15.
 */
@Named
@ViewScoped
public class EditionStoryController extends GenericController implements Serializable {

    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private StoryEdition storyEdition;

    @Inject
    private SessionContext sessionContext;

    public void save() throws IOException {
        Story story = storyEdition.toEntity();
        backlogManagerService.updateStory(story);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.changed_story");
    }

    public void provide() {
        backlogManagerService.provideStory(storyEdition.selectedStory);
    }

    public void restore() {
        backlogManagerService.restoreStory(storyEdition.selectedStory);
    }

    public String voting() {
        sessionContext.setStory(storyEdition.selectedStory);
        return GOT_TO_TASKS_PAGE;
    }

    public Boolean isPossibleProvide() throws DataNotFoundException {
        return !isRemoved();
    }

    public Boolean isPossibleRestore() throws DataNotFoundException {
        return isRemoved();
    }

    public Boolean isPossibleVoting() throws DataNotFoundException {
        return StoryStatus.AVAILABLE.equals(getStatus());
    }

    public Boolean isRemoved(){
        return StoryStatus.REMOVED.equals(getStatus());
    }

    public Boolean isSelected() {
        return storyEdition.selectedStory != null ? true : false;
    }

    public Story getSelectedStory() {
        return storyEdition.selectedStory;
    }

    public void setSelectedStory(Story selectedStory) {
        try{
            storyEdition.init(selectedStory);
        }catch (Exception e){}
    }

    public StoryStatus getStatus() {
        StoryStatusLog storyStatusLog;
        try {
            storyStatusLog = backlogManagerService.getStoryStatus(storyEdition.selectedStory);
            return storyStatusLog.getStoryStatus();

        } catch (DataNotFoundException e) {
            return  null;
        }
    }

    public StoryEdition getStoryEdition() {
        return storyEdition;
    }

    public void setStoryEdition(StoryEdition storyEdition) {
        this.storyEdition = storyEdition;
    }
}
