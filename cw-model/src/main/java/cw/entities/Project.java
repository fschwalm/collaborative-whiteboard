package cw.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 19/12/14.
 */
@Entity
@Table(name = "project", catalog = "cw")
public class Project implements Serializable{

    @Id
    private Long projectId;

    @Transient
    private String identificationCode;

    @ManyToOne
    @NotNull
    private User owner;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(nullable = false)
    private String nameProject;

    public Project(String nameProject) {
        this.nameProject = nameProject;
    }

    protected Project() {}

    public Long getId() {
        return projectId;
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
