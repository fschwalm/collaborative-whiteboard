package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import backlog_manager.entities.Task;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 25/05/15.
 */
public class TaskEdition implements Serializable {
    private Date time;
    private String subject;
    private String description;
    private Task selectedTask;
    private Stage stage;
    private Boolean initFlag;

    public void init(Task selectedTask) {
        if(selectedTask != null) {
            this.selectedTask = selectedTask;
            this.time = selectedTask.getEstimatedTime();
            this.subject = selectedTask.getSubject();
            this.description = selectedTask.getDescription();
            this.stage = selectedTask.getStage();
            this.initFlag = readInitFlag(selectedTask.getStage());
        }
    }

    public Task toEntity(){
        this.selectedTask.setEstimatedTime(time);
        this.selectedTask.setSubject(subject);
        this.selectedTask.setDescription(description);
        this.selectedTask.setStage(stage);

        return selectedTask;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isNotSelected(){
        return selectedTask == null;
    }

    public Task getSelectedTask() {
        return selectedTask;
    }

    public Stage getStage() {
        return stage;
    }

    private Boolean readInitFlag(Stage stage){
       return stage == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public Boolean getInitFlag() {
        return initFlag;
    }

    public void setInitFlag(Boolean initFlag) {
        this.initFlag = initFlag;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
