package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.ProjectDao;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.entities.Project;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import cw.exceptions.NameInUseException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

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
}
