package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import backlog_manager.exceptions.IterationAlreadySet;
import cw.exceptions.DataNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 23/06/15.
 */
public interface IterationService {

    List<Iteration> fetchIterations();

    Iteration getCurrentIteration() throws DataNotFoundException;

    List<Story> fetchStoriesAvailableForIteration();

    List<Story> fetchStories(Iteration iteration) throws DataNotFoundException;

    void create(List<Story> stories, String name, Date init, Date end) throws IterationAlreadySet;

    Float getProgressIteration(Iteration iteration);
}
