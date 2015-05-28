package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Story;
import backlog_manager.entities.Task;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.BacklogManagerService;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import br.org.tutty.collaborative_whiteboard.cw_web.dtos.TaskCreation;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by drferreira on 25/05/15.
 */
@Named
@ViewScoped
public class TaskCreationController extends GenericController implements Serializable {
    @Inject
    private BacklogManagerService backlogManagerService;

    @Inject
    private SessionContext sessionContext;

    @Inject
    private TaskCreation taskCreation;

    public void createTask() throws IOException {
        Story story = sessionContext.getStory();
        Task task = taskCreation.toEntity(story);
        backlogManagerService.createTask(task);

        facesMessageUtil.showGlobalMessage(FacesMessage.SEVERITY_INFO, "backlog.task.create");
        taskCreation = new TaskCreation();
    }

    public TaskCreation getTaskCreation() {
        return taskCreation;
    }

    public void setTaskCreation(TaskCreation taskCreation) {
        this.taskCreation = taskCreation;
    }
}
