package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by drferreira on 10/07/15.
 */
public class StoryMailable implements Serializable {
    private Set<TaskMailable> tasks;

    private String code;
    private String branch;
    private Integer storyPoints;
    private Integer priority;
    private Date creationDate;
    private String subject;
    private String description;
    private String wikiPage;

    public StoryMailable(Set<TaskMailable> tasks) {
        this.tasks = tasks;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public String getWikiPage() {
        return wikiPage;
    }

    public void setWikiPage(String wikiPage) {
        this.wikiPage = wikiPage;
    }
}
