package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.ProjectService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.StoryCreation;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;

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
 * Created by drferreira on 07/05/15.
 */
@Named
@ViewScoped
public class CreationStoryController extends GenericController implements Serializable{

    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private ProjectService projectService;

    @Inject
    private SessionContext sessionContext;

    @Inject
    private StoryCreation storyCreation;

    private List<Project> projects;

    @PostConstruct
    public void setUp() throws IOException {
        try {
            projects = projectService.fetchProjects();
        } catch (DataNotFoundException e) {
            facesMessageUtil.showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_ERROR, "backlog.projects_not_found");
        }
    }

    public List<String> fetchProjectAreas(String query){
        Project selectedProject = storyCreation.getSelectedProject();
        List<ProjectArea> projectAreas = projectService.filterProjectAreas(selectedProject, query);

        List<String> convertedAreas = new ArrayList<>();
        projectAreas.forEach(area -> convertedAreas.add(area.getName()));

        return convertedAreas;
    }


    public void create() throws IOException {
        User user = sessionContext.getLoggedUser().getUser();
        Story story = storyCreation.toEntity(user);
        backlogManagerService.createStory(story);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "backlog.created_story", "save.pending");
    }

    public void cancel(){
        storyCreation.init();
    }

    public void setProject(String projectName){
        for (Project project : projects) {
            if (project.getNameProject().equals(projectName)) {
                this.storyCreation.setSelectedProject(project);
            }
        }
    }

    public String getProject(){
        Project selectedProject = storyCreation.getSelectedProject();
        if (selectedProject != null) {
            return selectedProject.getNameProject();
        } else {
            return null;
        }
    }

    public List<String> getProjects(){
        List<String> projectsNames = new ArrayList<>();

        if(projects != null){
            projects.stream().forEach(project -> projectsNames.add(project.getNameProject()));
        }

        return projectsNames;
    }

    public void setProjectArea(String projectAreaName) throws DataNotFoundException {
        ProjectArea projectArea = projectService.fetchProjectArea(storyCreation.getSelectedProject(), projectAreaName);
        storyCreation.setProjectArea(projectArea);
    }

    public String getProjectArea(){
        ProjectArea projectArea = storyCreation.getProjectArea();

        if(projectArea != null){
            return storyCreation.getProjectArea().getName();
        }else {
            return "";
        }
    }

    public StoryCreation getStoryCreation() {
        return storyCreation;
    }

    public void setStoryCreation(StoryCreation storyCreation) {
        this.storyCreation = storyCreation;
    }
}
