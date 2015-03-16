package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.UserDao;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.entities.Project;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by drferreira on 16/12/14.
 */
@Stateless
@Local(UserService.class)
public class UserServiceBean implements UserService, Serializable {

    @Inject
    private UserDao userDao;
    @Inject
    private SessionContext sessionContext;

    @Override
    public User fetch(String email) throws DataNotFoundException {
        return userDao.fetch(email);
    }

    @Override
    public List<Project> fetchProjects() throws DataNotFoundException {
        User user = sessionContext.getLoggedUser().getUser();
        return user.getProjects();
    }

    @Override
    public Boolean hasSomeProject(){
        User user = sessionContext.getLoggedUser().getUser();
        return user.hasSomeProject();
    }
}
