package br.org.tutty.collaborative_whiteboard.backlog_manager.services;

import backlog_manager.entities.Story;
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
 * Created by drferreira on 10/08/15.
 */
@Stateless
@Local(TaskActionService.class)
public class TaskActionServiceBean implements TaskActionService{

    @Inject
    private SessionContext sessionContext;

    @Inject
    private TaskDao taskDao;

    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private WhiteboardService whiteboardService;

    @Inject
    private TaskManagerService taskManagerService;

    @Inject
    private WhiteboardDao whiteboardDao;


    private void changeStatusTask(Task task, TaskStatus taskStatus) {
        User user = sessionContext.getLoggedUser().getUser();
        TaskStatusLog taskStatusLog = new TaskStatusLog(taskStatus, user, task);

        taskDao.update(taskStatusLog);
        whiteboardService.refreshAllWhiteboards();
    }

    @Override
    public void end(String taskCode) throws DataNotFoundException, EndTaskException {
        Task task = taskDao.fetchByCode(taskCode);
        end(task);
    }

    @Override
    public void end(Task task) throws EndTaskException {
        if(taskManagerService.isPossibleEndTask(task)){
            changeStatusTask(task, TaskStatus.FINALIZED);
            Story story = task.getStory();

            if (!taskManagerService.hasNotFinalizedTask(story)) {
                backlogManagerService.finalizeStory(story);
                whiteboardService.refreshAllWhiteboards();
            }
        }else {
            throw new EndTaskException();
        }
    }

    @Override
    public void stop(Task task) throws DataNotFoundException, StopTaskException {
        if(taskManagerService.isPossibleStopTask(task)){
            changeStatusTask(task, TaskStatus.AVAILABLE);
        }else {
            throw new StopTaskException();
        }
    }

    @Override
    public void stop(String taskCode) throws DataNotFoundException, StopTaskException {
        Task task = taskDao.fetchByCode(taskCode);
        stop(task);
    }

    @Override
    public void init(Task task) throws DataNotFoundException, InitTaskException {
        if(taskManagerService.isPossibleInitTask(task)){
            changeStatusTask(task, TaskStatus.BUSY);
        }else {
            throw new InitTaskException();
        }
    }

    @Override
    public void init(String taskCode) throws DataNotFoundException, InitTaskException {
        Task task = taskDao.fetchByCode(taskCode);
        init(task);
    }

    @Override
    public void forward(Task task) throws LastStageException {
        try {
            Stage stageFounded = whiteboardService.fetchNextStage(task.getStage());
            task.setStage(stageFounded);
            changeStatusTask(task, TaskStatus.AVAILABLE);

        } catch (DataNotFoundException e) {
            throw new LastStageException();
        }
    }

    @Override
    public void forward(String taskCode) throws DataNotFoundException, LastStageException {
        Task task = taskDao.fetchByCode(taskCode);
        forward(task);
    }

    @Override
    public void backward(Task task) throws FirstStageException {
        try {
            Stage stageFounded = whiteboardService.fetchPreviousStage(task.getStage());
            task.setStage(stageFounded);

            changeStatusTask(task, TaskStatus.AVAILABLE);

        } catch (DataNotFoundException e) {
            throw new FirstStageException();
        }
    }

    @Override
    public void backward(String taskCode) throws DataNotFoundException, FirstStageException {
        Task task = taskDao.fetchByCode(taskCode);
        backward(task);
    }


    @Override
    public void enableWhiteboardTask(Task task) throws WhiteboardUninitializedException {
        Stage stage = whiteboardDao.fetchInitialStage();

        if (stage != null) {
            task.setStage(stage);
            taskDao.update(task);
            whiteboardService.refreshAllWhiteboards();
        } else {
            throw new WhiteboardUninitializedException();
        }
    }

    @Override
    public void disableWhiteboardTask(Task task) {
        task.setStage(null);
        taskDao.update(task);
        whiteboardService.refreshAllWhiteboards();
    }

    @Override
    public void remove(Task task) throws RemoveTaskException{
        if(taskManagerService.isPossibleRemoveTask(task)){
            changeStatusTask(task, TaskStatus.REMOVED);
        }else {
            throw new RemoveTaskException();
        }
    }

}
