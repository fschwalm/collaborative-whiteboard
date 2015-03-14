package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.entities.Project;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;

import javax.annotation.PostConstruct;
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
public class BacklogController implements Serializable {
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
        initPriorityCounter();
        stories = fetchStories();
    }

    private void initPriorityCounter() {
        priorityControl = 1;
    }

    public List<Story> fetchStories() throws DataNotFoundException {
        return backlogManagerService.fetchAllStories();
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
