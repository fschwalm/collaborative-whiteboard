package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Task;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.TaskManagerService;
import br.org.tutty.collaborative_whiteboard.cw.service.WhiteboardService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.TaskEdition;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.TaskInUseException;
import cw.exceptions.TaskNotInitializedException;
import cw.exceptions.WhiteboardUninitializedException;

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
public class TasksEditionController extends GenericController implements Serializable{
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

    public void init(){
        try {
            Task selectedTask = taskEdition.getSelectedTask();
            taskManagerService.init(selectedTask);
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.init.success", "task.edition.init");

        } catch (TaskInUseException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_ERROR, "task.edition.init.error", "task.edition.in_use.detail");

        } catch (TaskNotInitializedException e) {
            facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.init.error", "task.edition.in_not_initialized");
        }
    }

    public String previousStageName(){
        Stage stage = taskEdition.getStage();
        try {
            return whiteboardService.fetchPreviousStage(stage).getName();
        } catch (DataNotFoundException e) {
            return EMPTY_STAGE_PREVIOUS;
        }
    }

    public String nextStageName(){
        Stage stage = taskEdition.getStage();
        try {
            return whiteboardService.fetchNextStage(stage).getName();
        } catch (DataNotFoundException e) {
            return EMPTY_STAGE_NEXT;
        }
    }

    public void nextStage(){
        taskManagerService.forward(taskEdition.getSelectedTask());
        taskEdition.init(taskEdition.getSelectedTask());
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO,"task.edition.stages.changed", "task.edition.stages.next.confirmation.detail");
    }

    public void previousStage(){
        taskManagerService.backward(taskEdition.getSelectedTask());
        taskEdition.init(taskEdition.getSelectedTask());
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.stages.changed", "task.edition.stages.previous.confirmation.detail");
    }

    public void changeInitFlag(){
        try{
            if(taskEdition.getInitFlag()){
                taskManagerService.enableWhiteboardTask(taskEdition.getSelectedTask());
                facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.whiteboard_enable");
            }else {
                taskManagerService.disableWhiteboardTask(taskEdition.getSelectedTask());
                facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "task.edition.whiteboard_disable");
            }
        }catch (WhiteboardUninitializedException e){
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

    public void setSelectedTask(Task selectedTask){
        taskEdition.init(selectedTask);
    }

    public void clearSelectedTask(){
        taskEdition = new TaskEdition();
    }

    public Boolean isNotSelected(){
        return taskEdition.isNotSelected();
    }

    public String getEMPTY_STAGE_PREVIOUS() {
        return EMPTY_STAGE_PREVIOUS;
    }

    public String getEMPTY_STAGE_NEXT() {
        return EMPTY_STAGE_NEXT;
    }
}
