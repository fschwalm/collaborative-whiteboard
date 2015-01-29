package cw.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by drferreira on 19/12/14.
 */
@Entity
@Table(name = "project", catalog = "cw", schema = "cw")
public class Project implements Serializable{

    @Id
    private String id;

    @Transient
    private String identificationCode;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(nullable = false)
    private String nameProject;

    public Project(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getId() {
        return id;
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
