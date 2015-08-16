package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.entities.UploadedFile;
import backlog_manager.enums.StoryStatus;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.TaskManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryEdition;
import cw.exceptions.DataNotFoundException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Inject
    private TaskManagerService taskManagerService;

    public void save() throws IOException {
        Story story = storyEdition.toEntity();
        backlogManagerService.updateStory(story);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.changed_story");
    }

    public StreamedContent download(UploadedFile uploadedFile){
        InputStream inputStream = uploadedFile.getStreamFile();
        DefaultStreamedContent defaultStreamedContent = new DefaultStreamedContent(inputStream);
        defaultStreamedContent.setName(uploadedFile.getFileName());

        return defaultStreamedContent;
    }

    public void removeStory() throws IOException {
        Story selectedStory = storyEdition.selectedStory;
        backlogManagerService.removeStory(selectedStory);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.removed_story");
    }

    public void remove(UploadedFile uploadedFile) throws IOException {
        backlogManagerService.removeFile(uploadedFile);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.upload_file_remove");
    }

    public List<UploadedFile> fetchFiles(){
        try{
            return backlogManagerService.fetchFiles(storyEdition.selectedStory);
        }catch (DataNotFoundException e){
            return new ArrayList<>();
        }
    }

    public void uploadFile(FileUploadEvent event) throws IOException {
        try {
            org.primefaces.model.UploadedFile file = event.getFile();
            String fileName = file.getFileName();
            InputStream stream = file.getInputstream();

            backlogManagerService.uploadFile(storyEdition.selectedStory, stream, fileName);
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.upload_file_success");
        }catch (Exception e){
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "backlog.upload_file_error");
        }
    }

    public void openWiki() throws IOException {
        String wikiPage = storyEdition.getWikiPage();

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(wikiPage);
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

    public Boolean isPossibleProvide() {
        return !isRemoved();
    }

    public Boolean isPossibleRestore() {
        return isRemoved();
    }

    public Boolean isPossibleAnalyze() {
        return isPossibleInitAnalyze() || isPossibleEndAnalyze();
    }

    public Boolean isPossibleInitAnalyze() {
        return StoryStatus.AVAILABLE.equals(getStatus());
    }

    public Boolean isPossibleEndAnalyze() {
        return StoryStatus.IN_ANALYSIS.equals(getStatus());
    }

    public void initAnalysis() throws IOException {
        save();
        backlogManagerService.initAnalyzeStory(storyEdition.selectedStory);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.stories.init_analyze");
    }

    public void endAnalysis() throws IOException, DataNotFoundException {
        save();
        backlogManagerService.endAnalyzeStory(storyEdition.selectedStory);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.stories.end_analyze");
    }

    public Boolean isRemoved() {
        return StoryStatus.REMOVED.equals(getStatus());
    }

    public Boolean isSelected() {
        return storyEdition.selectedStory != null ? true : false;
    }

    public Story getSelectedStory() {
        return storyEdition.selectedStory;
    }

    public void setSelectedStory(Story selectedStory) {
        try {
            storyEdition.init(selectedStory);
        } catch (Exception e) {
        }
    }

    public StoryStatus getStatus() {
        StoryStatusLog storyStatusLog;
        try {
            storyStatusLog = backlogManagerService.getCurrentStoryStatusLog(storyEdition.selectedStory);
            return storyStatusLog.getStoryStatus();

        } catch (DataNotFoundException e) {
            return null;
        }
    }

    public StoryEdition getStoryEdition() {
        return storyEdition;
    }

    public void setStoryEdition(StoryEdition storyEdition) {
        this.storyEdition = storyEdition;
    }
}
