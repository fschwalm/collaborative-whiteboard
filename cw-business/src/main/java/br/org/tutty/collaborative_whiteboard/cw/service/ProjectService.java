package br.org.tutty.collaborative_whiteboard.cw.service;

import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.NameInUseException;

import java.util.List;

/**
 * Created by root on 16/03/15.
 */
public interface ProjectService {
    public Project createProject(String projectName) throws NameInUseException;

    void update(Project selectedProjectCloned);

    List<Project> fetchProjects() throws DataNotFoundException;

    List<ProjectArea> filterProjectAreas(Project project, String queryName);
}
