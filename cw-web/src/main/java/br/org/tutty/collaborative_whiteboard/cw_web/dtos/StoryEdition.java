package br.org.tutty.collaborative_whiteboard.cw_web.dtos;

import backlog_manager.entities.Story;
import cw.entities.User;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 25/03/15.
 */
public class StoryEdition implements Serializable{
    public Story selectedStory;

    private String code;
    private Date creationDate;
    private User author;
    private String subjectChanges;
    private String descriptionChanges;

    public void init(Story selectedStory){
        this.selectedStory = selectedStory;
    }

    public Story getSelectedStory() {
        return selectedStory;
    }

    public void setSelectedStory(Story selectedStory) {
        this.selectedStory = selectedStory;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getSubjectChanges() {
        return subjectChanges;
    }

    public void setSubjectChanges(String subjectChanges) {
        this.subjectChanges = subjectChanges;
    }

    public String getDescriptionChanges() {
        return descriptionChanges;
    }

    public void setDescriptionChanges(String descriptionChanges) {
        this.descriptionChanges = descriptionChanges;
    }
}
