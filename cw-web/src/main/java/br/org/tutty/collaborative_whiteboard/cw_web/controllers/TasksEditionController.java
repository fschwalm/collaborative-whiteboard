package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Task;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.TaskManagerService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.TaskEdition;
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
    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private TaskManagerService taskManagerService;

    @Inject
    private TaskEdition taskEdition;


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

}
