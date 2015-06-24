package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import cw.exceptions.DataNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 23/06/15.
 */
public interface IterationService {

    Iteration getCurrentIteration() throws DataNotFoundException;

    List<Story> fetchStoriesForIteration();
    void create(List<Story> stories, String name, Date init, Date end);

    Float getProgressIteration(Iteration iteration);
}
