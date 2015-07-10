package dtos;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 10/07/15.
 */
public class TaskMailable implements Serializable{
    private String code;
    private Date estimatedTime;
    private String subject;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Date estimatedTime) {
        this.estimatedTime = estimatedTime;
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
