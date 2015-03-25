package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryCreation;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryEdition;
import cw.entities.Project;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ReorderEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
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
    @Inject
    private UserService userService;

    private List<Story> stories;
    private List<Project> projects;
    private Story selectedStory;

    @Inject
    private StoryCreation storyCreation;

    @Inject
    private StoryEdition storyEdition;


    @PostConstruct
    public void setUp() throws EncryptedException, DataNotFoundException, IOException {
        stories = fetchStoriesInOrder();
        projects = fetchProjects();
    }

    public void prepareNewStory(){
        User user = sessionContext.getLoggedUser().getUser();
        storyCreation.setUser(user);
    }

    public void prepareEditionStory(){
        storyEdition.init(selectedStory);
    }

    public List<Story> fetchStoriesInOrder() throws IOException {
        try {
            List<Story> storiesUnordered = backlogManagerService.fetchAllStories();
            return backlogManagerService.sortStoriesByPriority(storiesUnordered);
        } catch (DataNotFoundException e) {
            stories = new ArrayList<>();
            return stories;
        }
    }

    public List<Project> fetchProjects() throws DataNotFoundException {
        return userService.fetchProjects();
    }

    public void onRowReorder(ReorderEvent event) throws IOException {
        showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "backlog.change_priority");
    }

    public void createStory() {
        Story story = storyCreation.getStory();
        stories.add(story);
    }


    public Boolean isSelected() {
        return selectedStory != null ? true : false;
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

    public void setSelectedStory(Story selectedStory){
        this.selectedStory = selectedStory;
        RequestContext.getCurrentInstance().update("storyDetailPanel");
    }

    public List<String> getProjects() {
        List<String> projectsNames = new ArrayList<>();

        for (Project project : projects) {
            projectsNames.add(project.getNameProject());
        }
        return projectsNames;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public StoryCreation getStoryCreation() {
        return storyCreation;
    }

    public void setStoryCreation(StoryCreation storyCreation) {
        this.storyCreation = storyCreation;
    }

    public StoryEdition getStoryEdition() {
        return storyEdition;
    }

    public void setStoryEdition(StoryEdition storyEdition) {
        this.storyEdition = storyEdition;
    }

    public String getProjectForNewStory() {
        Project selectedProject = storyCreation.getSelectedProject();
        if (selectedProject != null) {
            return selectedProject.getNameProject();
        } else {
            return null;
        }
    }

    public void setProjectForNewStory(String projectForNewStory) {
        for (Project project : projects) {
            if (project.getNameProject().equals(projectForNewStory)) {
                this.storyCreation.setSelectedProject(project);
            }
        }
    }
}
