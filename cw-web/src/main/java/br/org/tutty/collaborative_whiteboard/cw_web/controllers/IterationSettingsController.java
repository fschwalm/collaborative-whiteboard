package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.IterationService;
import cw.exceptions.DataNotFoundException;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by drferreira on 30/06/15.
 */
@Named
@ViewScoped
public class IterationSettingsController extends GenericController implements Serializable {

    private Iteration iterationSelected;

    @Inject
    private IterationService iterationService;

    @Inject
    private BacklogManagerService backlogManagerService;

    private List<Iteration> iterations;

    private DualListModel<Story> stories = new DualListModel<>();
    private List<Story> allStories;
    private List<Story> target;
    private List<Story> source;

    @PostConstruct
    public void setUp(){
        iterations = iterationService.fetchIterations();
    }

    public void mountStoriesInsideIteration() {
        allStories = fetchSourceStories();

        target = fetchTargetStories();
        source = allStories.stream().filter(story -> !target.contains(story)).collect(Collectors.toList());

        stories = new DualListModel<>(source, target);
    }

    private List<Story> fetchSourceStories() {
        try {
            return backlogManagerService.fetchAnalyzedStories();
        } catch (DataNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private List<Story> fetchTargetStories() {
        try {
            return iterationService.fetchStories(iterationSelected);
        } catch (DataNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public String getIterationSelected() {
        if(iterationSelected != null){
            return iterationSelected.getName();
        }else {
            return null;
        }
    }

    public void setIterationSelected(String iterationSelectedName) {
        List<Iteration> collect = iterations.stream().filter(iteration -> iteration.getName().equals(iterationSelectedName)).collect(Collectors.toList());
        this.iterationSelected = collect.get(0);
    }

    public DualListModel<Story> getStories() {
        return stories;
    }

    public void setStories(DualListModel<Story> stories) {
        this.stories = stories;
    }

    public List<String> getIterations() {
        List<String> iterationsOnlyNames = new ArrayList<>();
        iterations.forEach(iteration -> iterationsOnlyNames.add(iteration.getName()));

        return iterationsOnlyNames;
    }
}
