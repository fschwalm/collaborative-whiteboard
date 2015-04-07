package cw.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by drferreira on 07/04/15.
 */
@Entity
@Table(name = "project_area", catalog = "cw")
public class ProjectArea implements Serializable{
    @Id
    private String id;

    @ManyToOne
    private Project project;

    @Column
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String project_area_id) {
        this.id = project_area_id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project projectId) {
        this.project = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
