package br.org.tutty.backlog_manager;

import backlog_manager.entities.Story;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
public interface BacklogDao {
    List<Story> fetchStories() throws DataNotFoundException;
}
