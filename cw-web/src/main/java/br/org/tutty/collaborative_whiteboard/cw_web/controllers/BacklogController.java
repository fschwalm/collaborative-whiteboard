package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
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

    private List<Story> stories;
    private Story selectedStory;

    private Integer priorityControl;

    @PostConstruct
    public void setUp() throws EncryptedException {
        initPriorityCounter();
        User user = new User("email@email", "senha", "Joao");
        Project project = new Project("Linda");

        stories = fetchStories();
        Story story = new Story(user,project);
        story.setCode("SMK01");
        stories.add(story);
        story.setCode("SMK02");
        stories.add(story);
        story.setCode("SMK03");
        stories.add(story);
        story.setCode("SMK04");
        stories.add(story);
    }

    private void initPriorityCounter(){
        priorityControl = 1;
    }

    public List<Story> fetchStories(){
        try {
            return backlogManagerService.fetchAllStories();

        } catch (DataNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void orderBacklog(){

    }

    public Integer getPriority(){
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
}
