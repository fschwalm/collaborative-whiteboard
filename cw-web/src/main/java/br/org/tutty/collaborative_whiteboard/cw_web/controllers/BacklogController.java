package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.entities.Project;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
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

    private List<Story> stories;
    private Story selectedStory;
    private Integer priorityControl;

    private Project projectForNewStory;
    private String subjectForNewStory;
    private String descriptionForNewStory;


    @PostConstruct
    public void setUp() throws EncryptedException, DataNotFoundException {
        stories = fetchStories();
    }

    public List<Story> fetchStories(){
        try{
            return backlogManagerService.fetchAllStories();
        }catch (DataNotFoundException e){
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "backlog.not_found");
            populateEmptyStoryList();
            return stories;
        }
    }

    private void populateEmptyStoryList(){
        stories = new ArrayList<Story>();
        Story story = new Story(sessionContext.getLoggedUser().getUser());
        stories.add(story);
        stories.add(story);
        stories.add(story);
        stories.add(story);
        stories.add(story);
        stories.add(story);
        stories.add(story);
        stories.add(story);
        stories.add(story);
        stories.add(story);
        stories.add(story);
        stories.add(story);
    }


    public List<Project> fetchProjects() {
        // TODO DUMMY
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("LINDA"));
        projects.add(new Project("ELSA"));
        projects.add(new Project("CCEM"));

        return projects;
    }


    public void orderBacklog() {

    }

    public void createStory(){
        // TODO Nao esta preenchendo campos
        Story story = new Story(sessionContext.getLoggedUser().getUser());
        story.setProject(projectForNewStory);
        story.setSubject(subjectForNewStory);
        story.setDescription(descriptionForNewStory);

        stories.add(story);
    }

    public Integer getPriority() {
        return priorityControl++;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public Story getSelectedStory() {
        return selectedStory;
    }

    public void setSelectedStory(Story selectedStory) {
        this.selectedStory = selectedStory;
        RequestContext.getCurrentInstance().update("storyDetailPanel");
    }

    public void setProjectForNewStory(Project projectForNewStory) {
        this.projectForNewStory = projectForNewStory;
    }

    public Project getProjectForNewStory() {
        return projectForNewStory;
    }

    public String getSubjectForNewStory() {
        return subjectForNewStory;
    }

    public void setSubjectForNewStory(String subjectForNewStory) {
        this.subjectForNewStory = subjectForNewStory;
    }

    public String getDescriptionForNewStory() {
        return descriptionForNewStory;
    }

    public void setDescriptionForNewStory(String descriptionForNewStory) {
        this.descriptionForNewStory = descriptionForNewStory;
    }
}
