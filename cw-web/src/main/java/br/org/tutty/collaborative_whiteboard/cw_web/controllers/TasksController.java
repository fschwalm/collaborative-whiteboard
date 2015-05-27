package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import backlog_manager.entities.Task;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.exceptions.DataNotFoundException;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drferreira on 25/05/15.
 */
@Named
@ViewScoped
public class TasksController extends GenericController implements Serializable {

    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private SessionContext sessionContext;

    private List<Task> tasks;

    private Task selectedTask;

    public List<Task> getTasks() {
        try {
            this.tasks = backlogManagerService.fetchTasks(getSelectedStory());
            return tasks;
        } catch (DataNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void removeTask() throws IOException {
        backlogManagerService.removeTask(selectedTask);
        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "tasks.removed_task");
        selectedTask = null;
    }

    public Story getSelectedStory() {
        return sessionContext.getStory();
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public Boolean isNotSelected(){
        return selectedTask == null;
    }
}
