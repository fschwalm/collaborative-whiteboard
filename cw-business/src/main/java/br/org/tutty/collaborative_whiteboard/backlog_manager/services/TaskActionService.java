package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Task;
import cw.exceptions.*;

/**
 * Created by drferreira on 10/08/15.
 */
public interface TaskActionService {
    void end(String taskcode) throws DataNotFoundException, EndTaskException;

    void end(Task task) throws EndTaskException;

    void stop(Task task) throws DataNotFoundException, StopTaskException;

    void stop(String taskCode) throws DataNotFoundException, StopTaskException;

    void init(Task task) throws DataNotFoundException, InitTaskException;

    void init(String taskCode) throws DataNotFoundException, InitTaskException;

    void forward(Task task) throws LastStageException;

    void forward(String taskcode) throws DataNotFoundException, LastStageException;

    void backward(Task task) throws FirstStageException;

    void backward(String taskcode) throws DataNotFoundException, FirstStageException;

    void enableWhiteboardTask(Task task) throws WhiteboardUninitializedException;

    void disableWhiteboardTask(Task task);

    void remove(Task selectedTask) throws RemoveTaskException;
}
