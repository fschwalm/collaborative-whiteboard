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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    private Project project;

    public ProjectArea() {
    }

    public ProjectArea(String projectAreaName) {
        this.name = projectAreaName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long project_area_id) {
        this.id = project_area_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
