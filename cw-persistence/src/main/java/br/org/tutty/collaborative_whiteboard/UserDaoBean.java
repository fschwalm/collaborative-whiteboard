package br.org.tutty.collaborative_whiteboard;

import cw.entities.User;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by drferreira on 29/01/15.
 */
@Stateless
@Local(UserDao.class)
public class UserDaoBean extends GenericDao implements UserDao {

    @Override
    public User fetch(String email) throws DataNotFoundException {
        Criteria criteria = createCriteria(User.class);

        criteria.add(Restrictions.eq("email", email));

        return (User) uniqueResultNotWaitingEmpty(criteria);
    }
}
