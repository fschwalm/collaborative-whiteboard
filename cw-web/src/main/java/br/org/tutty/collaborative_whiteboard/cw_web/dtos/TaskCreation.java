package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import backlog_manager.entities.Story;
import backlog_manager.entities.Task;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 25/05/15.
 */
public class TaskCreation implements Serializable {
    private String code;
    private Date time;
    private String subject;
    private String description;

    public Task toEntity(Story story){
        Task task = new Task();
        task.setCode(code);
        task.setEstimatedTime(time);
        task.setSubject(subject);
        task.setDescription(description);
        task.setStory(story);

        return task;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
