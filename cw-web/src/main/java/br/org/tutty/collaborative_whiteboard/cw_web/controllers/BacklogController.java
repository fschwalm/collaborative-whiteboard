package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
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
        try{
            List<Story> storiesUnordered = backlogManagerService.fetchAllStories();
            return backlogManagerService.sortStoriesByPriority(storiesUnordered);
        }catch (DataNotFoundException e){
            populateEmptyStoryList();
            showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "backlog.not_found");
            return stories;
        }
    }

    public List<Project> fetchProjects() {
        // TODO DUMMY
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("LINDA"));
        projects.add(new Project("ELSA"));
        projects.add(new Project("CCEM"));

        return projects;
    }

    private void populateEmptyStoryList(){
        stories = new ArrayList<Story>();
        Story story = new Story(sessionContext.getLoggedUser().getUser());
        story.setCode("SMK1");
        story.setPriority(1);
        stories.add(story);


        Story story1 = new Story(sessionContext.getLoggedUser().getUser());
        story1.setCode("SMK2");
        story1.setPriority(2);
        stories.add(story1);


        Story story2 = new Story(sessionContext.getLoggedUser().getUser());
        story2.setCode("SMK3");
        story2.setPriority(3);
        stories.add(story2);


        Story story3 = new Story(sessionContext.getLoggedUser().getUser());
        story3.setCode("SMK4");
        story3.setPriority(4);
        stories.add(story3);


        Story story4 = new Story(sessionContext.getLoggedUser().getUser());
        story4.setCode("SMK5");
        story4.setPriority(5);
        stories.add(story4);
    }

    public void onRowReorder(ReorderEvent event) throws IOException {
        showGlobalMessageWithoutDetail(FacesMessage.SEVERITY_INFO, "backlog.change_priority");
    }

    public void createStory(){
        // TODO Nao esta preenchendo campo projeto
        Story story = new Story(sessionContext.getLoggedUser().getUser());
        story.setProject(projectForNewStory);
        story.setSubject(subjectForNewStory);
        story.setDescription(descriptionForNewStory);

        stories.add(story);
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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
