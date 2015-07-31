package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Task;
import backlog_manager.entities.TaskStatusLog;
import backlog_manager.enums.TaskStatus;
import br.org.tutty.backlog_manager.TaskDao;
import br.org.tutty.collaborative_whiteboard.WhiteboardDao;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw.service.WhiteboardService;
import cw.entities.Stage;
import cw.entities.User;
import cw.exceptions.*;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by drferreira on 27/07/15.
 */
@Stateless
@Local(TaskManagerService.class)
public class TaskManagerServiceBean implements TaskManagerService {

    @Inject
    private TaskDao taskDao;

    @Inject
    private WhiteboardDao whiteboardDao;

    @Inject
    private WhiteboardService whiteboardService;

    @Inject
    private SessionContext sessionContext;

    @Override
    public Boolean isPossibleEndTask(Task task) {
        try {
            TaskStatusLog taskStatusLog = fetchStatusLog(task);
            Stage lastStage = whiteboardService.fetchLastStage();

            Boolean inUse = TaskStatus.BUSY.equals(taskStatusLog.getTaskStatus());
            Boolean inLastStage = lastStage.getName().equals(task.getStage().getName());

            return (inUse && inLastStage);

        } catch (DataNotFoundException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public void end(Task task) throws TaskNotInitializedException, TaskAlreadyStoppedException {
        // TODO Adicionar regras para poder finalizar
        changeStatusTask(task, TaskStatus.FINALIZED);
    }

    @Override
    public void stop(Task task) throws TaskNotInitializedException, TaskAlreadyStoppedException {
        try {
            TaskStatusLog taskStatusLog = fetchStatusLog(task);

            if (TaskStatus.BUSY.equals(taskStatusLog.getTaskStatus())) {
                changeStatusTask(task, TaskStatus.AVAILABLE);
            } else {
                throw new TaskAlreadyStoppedException();
            }
        } catch (DataNotFoundException e) {
            throw new TaskNotInitializedException();
        }
    }

    @Override
    public void init(Task task) throws TaskInUseException, TaskNotInitializedException {
        try {
            TaskStatusLog taskStatusLog = fetchStatusLog(task);
            if (TaskStatus.AVAILABLE.equals(taskStatusLog.getTaskStatus())) {
                changeStatusTask(task, TaskStatus.BUSY);
            } else {
                throw new TaskInUseException();
            }
        } catch (DataNotFoundException e) {
            throw new TaskNotInitializedException();
        }
    }

    @Override
    public TaskStatusLog fetchStatusLog(Task task) throws DataNotFoundException {
        return taskDao.fetchTaskStatusLog(task);
    }

    @Override
    public void enableWhiteboardTask(Task task) throws WhiteboardUninitializedException {
        Stage stage = whiteboardDao.fetchInitialStage();
        if (stage != null) {
            task.setStage(stage);
            taskDao.update(task);
        } else {
            throw new WhiteboardUninitializedException();
        }
    }

    @Override
    public void disableWhiteboardTask(Task task) {
        task.setStage(null);
        taskDao.update(task);
    }

    @Override
    public void forward(Task task) {
        try {
            Stage stageFounded = whiteboardService.fetchNextStage(task.getStage());
            task.setStage(stageFounded);

            openTask(task);

            taskDao.update(task);

        } catch (DataNotFoundException e) {
        }
    }

    @Override
    public void backward(Task task) {
        try {
            Stage stageFounded = whiteboardService.fetchPreviousStage(task.getStage());
            task.setStage(stageFounded);

            openTask(task);

            taskDao.update(task);

        } catch (DataNotFoundException e) {
        }
    }

    public void openTask(Task task) {
        changeStatusTask(task, TaskStatus.AVAILABLE);
    }

    private void changeStatusTask(Task task, TaskStatus taskStatus) {
        User user = sessionContext.getLoggedUser().getUser();
        TaskStatusLog taskStatusLog = new TaskStatusLog(taskStatus, user, task);

        taskDao.update(taskStatusLog);
    }
}
