package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import cw.exceptions.DataNotFoundException;

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


    @PostConstruct
    public void setUp(){
        stories = fetchStories();
    }

    public List<Story> fetchStories(){
        try {
            return backlogManagerService.fetchAllStories();

        } catch (DataNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void createStory(){

    }

    public void removeStory(){

    }

    public void orderBacklog(){

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
