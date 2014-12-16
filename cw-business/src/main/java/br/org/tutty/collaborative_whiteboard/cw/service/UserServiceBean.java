package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.cw.exceptions.DataNotFoundException;
import br.org.tutty.collaborative_whiteboard.cw.exceptions.EncryptedException;
import br.org.tutty.collaborative_whiteboard.cw.model.User;

import javax.ejb.Local;

/**
 * Created by drferreira on 16/12/14.
 */
@Local(UserService.class)
public class UserServiceBean implements UserService {

    @Override
    public User fetch(String email) throws DataNotFoundException {
        try {
            User user = new User
                    (email, "password", "Joao");
            return user;
        } catch (EncryptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
