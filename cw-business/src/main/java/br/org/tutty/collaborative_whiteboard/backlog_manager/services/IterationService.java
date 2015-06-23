package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;

import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 23/06/15.
 */
public interface IterationService {
    void create(List<Story> stories, String name, Date init, Date end);
}
