package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.ProjectAreaDao;
import br.org.tutty.collaborative_whiteboard.ProjectDao;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.entities.Project;
import cw.entities.ProjectArea;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.NameInUseException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drferreira on 20/03/15.
 */
@Local(ProjectService.class)
@Stateless
public class ProjectServiceBean implements ProjectService {

    @Inject
    private SessionContext sessionContext;

    @Inject
    private ProjectDao projectDao;

    @Inject
    private ProjectAreaDao projectAreaDao;

    public Boolean checkAvailabilityName(String projectName){
        try {
            projectDao.fetch(projectName);
            return Boolean.FALSE;

        } catch (DataNotFoundException e) {
            return Boolean.TRUE;
        }
    }

    @Override
    public Project createProject(String projectName) throws NameInUseException{
        if(checkAvailabilityName(projectName)){
            User user = sessionContext.getLoggedUser().getUser();

            Project project = new Project(projectName, user);
            projectDao.persist(project);
            user.addProject(project);

            return project;

        }else {
            throw new NameInUseException();
        }
    }

    @Override
    public void update(Project selectedProjectCloned) {
        projectDao.update(selectedProjectCloned);
    }

    @Override
    public List<Project> fetchProjects() throws DataNotFoundException {
        User user = sessionContext.getLoggedUser().getUser();
        return projectDao.fetchAll(user);
    }

    @Override
    public List<ProjectArea> filterProjectAreas(Project project, String queryName){
        List<ProjectArea> projectAreas = projectAreaDao.filterProjectAreas(project, queryName);
        return projectAreas;
    }
}
