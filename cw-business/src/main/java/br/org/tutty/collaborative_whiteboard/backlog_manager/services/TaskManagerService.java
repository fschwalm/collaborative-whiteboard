package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Task;
import backlog_manager.entities.TaskStatusLog;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.TaskInUseException;
import cw.exceptions.TaskNotInitializedException;
import cw.exceptions.WhiteboardUninitializedException;

/**
 * Created by drferreira on 27/07/15.
 */
public interface TaskManagerService {

    void stop(Task task) throws TaskNotInitializedException;

    void init(Task task) throws TaskInUseException, TaskNotInitializedException;

    TaskStatusLog fetchStatusLog(Task task) throws DataNotFoundException;

    void enableWhiteboardTask(Task task) throws WhiteboardUninitializedException;

    void disableWhiteboardTask(Task task);

    void forward(Task selectedTask);

    void backward(Task selectedTask);
}
