package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import cw.entities.Project;
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

    private Project projectForNewStory;
    private String subjectForNewStory;
    private String descriptionForNewStory;

    @PostConstruct
    public void setUp() throws EncryptedException, DataNotFoundException, IOException {
        stories = fetchStoriesInOrder();
        projects = fetchProjects();
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
        Story story = new Story(sessionContext.getLoggedUser().getUser());
        story.setProject(projectForNewStory);
        story.setSubject(subjectForNewStory);
        story.setDescription(descriptionForNewStory);

        stories.add(story);
    }

    public String getProjectForNewStory() {
        if (projectForNewStory != null) {
            return projectForNewStory.getNameProject();
        } else {
            return null;
        }
    }

    public void setProjectForNewStory(String projectForNewStory) {
        for (Project project : projects) {
            if (project.getNameProject().equals(projectForNewStory)) {
                this.projectForNewStory = project;
            }
        }
    }

    public Boolean isSelected(){
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

    public void setSelectedStory(Story selectedStory) {
        this.selectedStory = selectedStory;
        RequestContext.getCurrentInstance().update("storyDetailPanel");
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
}
