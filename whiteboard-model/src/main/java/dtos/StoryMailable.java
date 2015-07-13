package dtos;

import br.org.tutty.Equalization;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by drferreira on 10/07/15.
 */
public class StoryMailable implements Serializable {
    private Set<TaskMailable> tasks;

    @Equalization(name = "story_code")
    private String code;

    @Equalization(name = "story_branch")
    private String branch;

    @Equalization(name = "story_points")
    private Integer storyPoints;

    @Equalization(name = "story_priority")
    private Integer priority;

    @Equalization(name = "story_creation_date")
    private Date creationDate;

    @Equalization(name = "story_subject")
    private String subject;

    @Equalization(name = "story_description")
    private String description;

    @Equalization(name = "story_wiki")
    private String wikiPage;

    public StoryMailable() {
        tasks = new HashSet<>();
    }

    public void addTask(TaskMailable taskMailable){
        tasks.add(taskMailable);
    }

    public Boolean existTask(String taskCode){
        return tasks.stream().anyMatch(task -> task.getCode().equals(taskCode));
    }

    public TaskMailable getTaskByCode(String taskCode){
        return tasks.stream().filter(task -> task.getCode().equals(taskCode)).findAny().get();
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
