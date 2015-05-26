package br.org.tutty.backlog_manager;

import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.enums.StoryStatus;
import br.org.tutty.collaborative_whiteboard.Dao;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
public interface StoryDao extends Dao {
    List<Story> fetchAllStories() throws DataNotFoundException;

    Long getNextSequenceStory(Project project);

    List<Story> fetchStories(ProjectArea projectArea) throws DataNotFoundException;

    StoryStatusLog getStoryStatus(Story story) throws DataNotFoundException;
}
