package br.org.tutty.backlog_manager;

import backlog_manager.entities.Story;
import backlog_manager.entities.Task;
import br.org.tutty.collaborative_whiteboard.Dao;
import cw.exceptions.DataNotFoundException;

import java.util.List;

/**
 * Created by drferreira on 25/05/15.
 */
public interface TaskDao extends Dao{
    Long getNextSequenceTask(Story project);

    List<Task> fetchByStory(Story selectedStory) throws DataNotFoundException;
}
