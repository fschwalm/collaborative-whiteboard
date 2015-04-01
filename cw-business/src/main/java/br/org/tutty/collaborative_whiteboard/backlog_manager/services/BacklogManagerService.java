package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;
import cw.entities.Project;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
public interface BacklogManagerService {
    List<Story> fetchAllStories() throws DataNotFoundException;

    Story getEmptyStory(Project project);

    List<Story> sortStoriesByPriority(List<Story> stories);

    void updateBacklog(List<Story> stories);
}
