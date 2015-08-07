package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Task;
import backlog_manager.entities.TaskStatusLog;
import backlog_manager.enums.TaskStatus;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.TaskManagerService;
import br.org.tutty.collaborative_whiteboard.cw.service.WhiteboardService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.TaskEdition;
import cw.entities.Stage;
import cw.exceptions.*;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by drferreira on 11/06/15.
 */
@Named
@ViewScoped
public class TasksEditionController extends GenericController implements Serializable {
    private String EMPTY_STAGE_PREVIOUS = "EMPTY_STAGE_PREVIOUS";
    private String EMPTY_STAGE_NEXT = "EMPTY_STAGE_NEXT";

    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private TaskManagerService taskManagerService;

    @Inject
    private TaskEdition taskEdition;

    @Inject
    private WhiteboardService whiteboardService;

    public Boolean isAvaliableStop() {
        Task selectedTask = taskEdition.getSelectedTask();
        return taskManagerService.isPossibleStopTask(selectedTask);
    }

    public Boolean isAvaliableEnd() {
        Task selectedTask = taskEdition.getSelectedTask();
        return taskManagerService.isPossibleEndTask(selectedTask);
    }

    public Boolean isAvaliableInit() {
        Task selectedTask = taskEdition.getSelectedTask();
        return taskManagerService.isPossibleInitTask(selectedTask);
    }

    public void end() {
        Task selectedTask = taskEdition.getSelectedTask();
        taskManagerService.end(selectedTask);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.end", "task.edition.end.success");
    }

    public void stop() {
        Task selectedTask = taskEdition.getSelectedTask();
        taskManagerService.stop(selectedTask);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.stop", "task.edition.stop.success");
    }

    public void init() {
        Task selectedTask = taskEdition.getSelectedTask();
        taskManagerService.init(selectedTask);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.init.success", "task.edition.init");
    }

    public String previousStageName() {
        Stage stage = taskEdition.getStage();
        try {
            return whiteboardService.fetchPreviousStage(stage).getName();
        } catch (DataNotFoundException e) {
            return EMPTY_STAGE_PREVIOUS;
        }
    }

    public String nextStageName() {
        Stage stage = taskEdition.getStage();
        try {
            return whiteboardService.fetchNextStage(stage).getName();
        } catch (DataNotFoundException e) {
            return EMPTY_STAGE_NEXT;
        }
    }

    public void nextStage() {
        try {
            taskManagerService.forward(taskEdition.getSelectedTask());
            taskEdition.init(taskEdition.getSelectedTask());
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.stages.changed", "task.edition.stages.next.confirmation.detail");

        } catch (LastStageException e) {
            e.printStackTrace();
        }
    }

    public void previousStage() {
        try {
            taskManagerService.backward(taskEdition.getSelectedTask());
            taskEdition.init(taskEdition.getSelectedTask());
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.stages.changed", "task.edition.stages.previous.confirmation.detail");

        } catch (FirstStageException e) {
            e.printStackTrace();
        }
    }

    public void changeInitFlag() {
        try {
            if (taskEdition.getInitFlag()) {
                taskManagerService.enableWhiteboardTask(taskEdition.getSelectedTask());
                facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.whiteboard_enable");
            } else {
                taskManagerService.disableWhiteboardTask(taskEdition.getSelectedTask());
                facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.whiteboard_disable");
            }
        } catch (WhiteboardUninitializedException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_WARN, "task.edition.uninitialized");
        }

        taskEdition.init(taskEdition.getSelectedTask());
    }

    public void save() throws IOException {
        backlogManagerService.updateTask(taskEdition.toEntity());
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "tasks.changed_task");
    }

    public TaskEdition getTaskEdition() {
        return taskEdition;
    }

    public void setTaskEdition(TaskEdition taskEdition) {
        this.taskEdition = taskEdition;
    }

    public Task getSelectedTask() {
        return taskEdition.getSelectedTask();
    }

    public void setSelectedTask(Task selectedTask) {
        taskEdition.init(selectedTask);
    }

    public void clearSelectedTask() {
        taskEdition = new TaskEdition();
    }

    public Boolean isNotSelected() {
        return taskEdition.isNotSelected();
    }

    public String getEMPTY_STAGE_PREVIOUS() {
        return EMPTY_STAGE_PREVIOUS;
    }

    public String getEMPTY_STAGE_NEXT() {
        return EMPTY_STAGE_NEXT;
    }
}
