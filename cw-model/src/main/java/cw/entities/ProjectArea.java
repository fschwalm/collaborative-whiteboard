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

    @Column
    private String projectAreaAbbreviation;

    @ManyToOne
    private Project project;

    public ProjectArea() {
    }

    public ProjectArea(String projectAreaName, String projectAreaAbbreviation) {
        this.name = projectAreaName;
        this.projectAreaAbbreviation = projectAreaAbbreviation;
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

    public String getProjectAreaAbbreviation() {
        return projectAreaAbbreviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectArea that = (ProjectArea) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        return result;
    }
}
