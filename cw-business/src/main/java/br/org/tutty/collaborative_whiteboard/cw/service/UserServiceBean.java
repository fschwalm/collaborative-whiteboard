package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.UserDao;
import cw.exceptions.DataNotFoundException;
import cw.entities.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by drferreira on 16/12/14.
 */
@Stateless
@Local(UserService.class)
public class UserServiceBean implements UserService, Serializable {

    @Inject
    private UserDao userDao;

    @Override
    public User fetch(String email) throws DataNotFoundException {
        return userDao.fetch(email);
    }
}
