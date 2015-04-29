package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.ProjectService;
import br.org.tutty.collaborative_whiteboard.cw.service.UserService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryCreation;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryEdition;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.EncryptedException;
import org.json.JSONException;
import org.json.JSONObject;
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

    @Inject
    private StoryCreation storyCreation;

    @Inject
    private StoryEdition storyEdition;

    @Inject
    private ProjectService projectService;

    private List<Story> stories;
    private List<Project> projects;
    private Story selectedStory;

    @PostConstruct
    public void setUp() throws EncryptedException, DataNotFoundException, IOException {
        stories = fetchStories();
        projects = fetchProjects();
    }

    public void saveDetailChanges(){
        storyEdition.save();
    }

    public void prepareEditionStory(){
        storyEdition.init(selectedStory);
    }

    public void prepareCreationStory(){
        storyCreation.init();
    }

    public List<Story> fetchStories() throws IOException {
        try {
            return backlogManagerService.fetchAllStories();

        } catch (DataNotFoundException e) {
            stories = new ArrayList<>();
            return stories;

        } catch (Exception e){
            facesMessageUtil.showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "backlog.stories.error");

            stories = new ArrayList<>();
            return stories;
        }
    }

    public List<Project> fetchProjects() throws DataNotFoundException {
        return projectService.fetchProjects();
    }

    public void onRowReorder(ReorderEvent event) throws IOException {
        facesMessageUtil.showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "backlog.change_priority");
    }

    public void createStory() throws IOException, DataNotFoundException {
        Project selectedProject = storyCreation.getSelectedProject();

        Story story = backlogManagerService.getEmptyStory(selectedProject);
        story.setSubject(storyCreation.getSubject());
        story.setDescription(storyCreation.getDescription());

        ProjectArea selectedProjectArea = projectService.fetchProjectArea(selectedProject, storyCreation.getProjectArea());
        story.setProjectArea(selectedProjectArea);

        stories.add(story);

        storyCreation = new StoryCreation();
        facesMessageUtil.showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "backlog.created_story");
    }

    public void updateBacklog() throws IOException {
        backlogManagerService.updateBacklog(stories);
        facesMessageUtil.showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "backlog.update");
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

    public List<String> fetchProjectAreas(String query){
        Project selectedProject = storyCreation.getSelectedProject();
        List<ProjectArea> projectAreas = projectService.filterProjectAreas(selectedProject, query);

        List<String> convertedAreas = new ArrayList<>();
        projectAreas.forEach(area -> convertedAreas.add(area.getName()));

        return convertedAreas;
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
