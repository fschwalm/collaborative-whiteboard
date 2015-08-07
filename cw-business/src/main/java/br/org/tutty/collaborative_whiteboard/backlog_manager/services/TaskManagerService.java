package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;
import backlog_manager.entities.Task;
import backlog_manager.entities.TaskStatusLog;
import cw.exceptions.*;

/**
 * Created by drferreira on 27/07/15.
 */
public interface TaskManagerService {

    Boolean isPossibleEndTask(Task task);

    Boolean isPossibleStopTask(Task task);

    Boolean isPossibleInitTask(Task task);

    void end(Task task);

    Boolean hasNotFinalizedTask(Story story);

    void stop(String taskcode) throws DataNotFoundException, StopTaskException;

    void stop(Task task);

    void init(String taskcode) throws DataNotFoundException, InitTaskException;

    void init(Task task);

    TaskStatusLog fetchStatusLog(Task task) throws DataNotFoundException;

    void enableWhiteboardTask(Task task) throws WhiteboardUninitializedException;

    void disableWhiteboardTask(Task task);

    void forward(String taskcode) throws DataNotFoundException, LastStageException;

    void forward(Task selectedTask) throws LastStageException;

    void backward(String taskcode) throws DataNotFoundException, FirstStageException;

    void backward(Task selectedTask) throws FirstStageException;
}
