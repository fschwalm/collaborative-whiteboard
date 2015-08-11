package br.org.tutty.collaborative_whiteboard.cw_web.controllers;

import backlog_manager.entities.Task;
import backlog_manager.entities.TaskStatusLog;
import br.org.tutty.collaborative_whiteboard.backlog_manager.services.TaskManagerService;
import cw.exceptions.DataNotFoundException;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by drferreira on 11/08/15.
 */
@ViewScoped
@Named
public class TaskDetailController extends GenericController implements Serializable {
    private Task selectedTask;

    @Inject
    private TaskManagerService taskManagerService;

    public void initByJS(){
        try {
            String taskCode = getTaskCode();
            selectedTask = taskManagerService.fetchTaskByCode(taskCode);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<TaskStatusLog> fetchStatus(){
        try {
            return taskManagerService.fetchAllStatusLog(selectedTask);
        } catch (DataNotFoundException e) {
            return new ArrayList();
        }
    }

    public String getStoryCode(){
        return selectedTask != null ? selectedTask.getStory().getCode() : "";
    }

    public String getStorySubject(){
        return selectedTask != null ? selectedTask.getStory().getSubject() : "";
    }

    public String getCode(){
        return selectedTask != null ? selectedTask.getCode() : "";
    }

    public String getStage(){
        return selectedTask != null ? selectedTask.getStage().getName() : "";
    }

    public Date getEstimatedTime(){
        return selectedTask != null ? selectedTask.getEstimatedTime() : null;
    }

    public String getDescription(){
        return selectedTask != null ? selectedTask.getDescription() : "";
    }

    public String getAuthor(){
        return selectedTask != null ? selectedTask.getAuthor().getFullName() : "";
    }

    public String getSubject(){
        return selectedTask != null ? selectedTask.getSubject() : "";
    }

    private String getTaskCode() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("task_code");
    }
}
