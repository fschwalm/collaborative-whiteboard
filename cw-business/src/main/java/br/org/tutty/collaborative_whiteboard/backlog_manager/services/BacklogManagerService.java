package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;
import backlog_manager.entities.StoryStatusLog;
import backlog_manager.entities.Task;
import backlog_manager.enums.StoryStatus;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 11/03/15.
 */
public interface BacklogManagerService {
    List<Story> fetchAllStories() throws DataNotFoundException;

    List<Story> fetch(ProjectArea projectArea) throws DataNotFoundException;

    void updateBacklog(List<Story> stories);

    Boolean projectAreaIsAssignedToStory(ProjectArea projectArea);

    void createStory(Story story);

    void updateStory(Story story);

    void restoreStory(Story story);

    void removeStory(Story story);

    void provideStory(Story story);

    void finalizeStory(Story story);

    StoryStatusLog getStoryStatus(Story story) throws DataNotFoundException;

    void changeStoryStatus(Story story, StoryStatus storyStatus);

    void createTask(Task task);
}
