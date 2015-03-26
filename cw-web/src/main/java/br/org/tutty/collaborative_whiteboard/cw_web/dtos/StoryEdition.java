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

    public void init(Story selectedStory) {
        this.selectedStory = selectedStory;
        this.code = selectedStory.getCode();
        this.creationDate = selectedStory.getCreationDate();
        this.author = selectedStory.getAuthor();
        this.subjectChanges = selectedStory.getSubject();
        this.descriptionChanges = selectedStory.getDescription();
    }

    public void save(){
        selectedStory.setCode(code);
        selectedStory.setSubject(subjectChanges);
        selectedStory.setDescription(descriptionChanges);
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
