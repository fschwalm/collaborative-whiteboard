package br.org.tutty.collaborative_whiteboard.cw.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by drferreira on 19/12/14.
 */
public class Project implements Serializable{
    private Date creationDate;
    private String nameProject;
    private String identificationCode;

    public Project(String nameProject) {
        this.creationDate = new Date();
        this.nameProject = nameProject;
        this.identificationCode = UUID.randomUUID().toString();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }
}
