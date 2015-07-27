package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Task;
import backlog_manager.entities.TaskStatusLog;
import br.org.tutty.backlog_manager.TaskDao;
import br.org.tutty.collaborative_whiteboard.WhiteboardDao;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.WhiteboardUninitializedException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by drferreira on 27/07/15.
 */
@Stateless
@Local(TaskManagerService.class)
public class TaskManagerServiceBean implements TaskManagerService{

    @Inject
    private TaskDao taskDao;

    @Inject
    private WhiteboardDao whiteboardDao;

    @Override
    public TaskStatusLog fetchStatusLog(Task task) throws DataNotFoundException {
        return taskDao.fetchTaskStatusLog(task);
    }

    @Override
    public void enableWhiteboardTask(Task task) throws WhiteboardUninitializedException {
        Stage stage = whiteboardDao.fetchInitialStage();
        if(stage != null){
            task.setStage(stage);
            taskDao.update(task);
        }else {
            throw new WhiteboardUninitializedException();
        }
    }

    @Override
    public void disableWhiteboardTask(Task task) {
        task.setStage(null);
        taskDao.update(task);
    }
}
