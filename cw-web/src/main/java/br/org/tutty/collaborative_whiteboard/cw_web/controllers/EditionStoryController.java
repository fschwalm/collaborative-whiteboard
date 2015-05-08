package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.enums.StoryStatus;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryEdition;
import cw.entities.User;

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

    private Story selectedStory;

    public void save() throws IOException {
        Story story = storyEdition.toEntity();
        backlogManagerService.updateStory(story);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "backlog.changed_story", "save.pending");
    }

    public void provide(){
        User user = sessionContext.getLoggedUser().getUser();
        this.storyEdition.setStoryStatusLog(new StoryStatusLog(StoryStatus.AVAILABLE,user));
    }

    public void restore(){
        User user = sessionContext.getLoggedUser().getUser();
        this.storyEdition.setStoryStatusLog(new StoryStatusLog(StoryStatus.WAITING,user));
    }

    public Boolean isPossibleProvide(){
        return isInitialized() && !isRemoved();
    }

    public Boolean isPossibleRestore(){
        return isInitialized() && !isRemoved();
    }

    public Boolean isInitialized(){
        return storyEdition.getCode() == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public Boolean isRemoved(){
        StoryStatusLog storyStatusLog = storyEdition.getStoryStatusLog();

        if (storyStatusLog != null){
            return StoryStatus.REMOVED.equals(storyStatusLog.getStoryStatus());
        }
        return Boolean.FALSE;
    }

    public Boolean isSelected() {
        return selectedStory != null ? true : false;
    }


    public Story getSelectedStory() {
        return selectedStory;
    }

    public void setSelectedStory(Story selectedStory) {
        this.selectedStory = selectedStory;
        storyEdition.init(selectedStory);
    }

    public String getStatus(){
        StoryStatusLog storyStatusLog = storyEdition.getStoryStatusLog();

        if(storyStatusLog != null){
            return storyStatusLog.getStoryStatus().toString();
        }else {
            return "";
        }
    }

    public StoryEdition getStoryEdition() {
        return storyEdition;
    }

    public void setStoryEdition(StoryEdition storyEdition) {
        this.storyEdition = storyEdition;
    }
}
