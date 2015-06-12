package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Task;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.TaskEdition;
import cw.exceptions.DataNotFoundException;

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
    private TaskEdition taskEdition;

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

    public void setSelectedTask(Task selectedTask) throws DataNotFoundException {
        taskEdition.init(selectedTask);
    }

    public void clearSelectedTask(){
        taskEdition = new TaskEdition();
    }

    public Boolean isNotSelected(){
        return taskEdition.isNotSelected();
    }

}
