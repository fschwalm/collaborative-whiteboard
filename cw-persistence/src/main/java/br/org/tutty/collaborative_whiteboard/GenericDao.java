package br.org.tutty.collaborative_whiteboard;

import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by drferreira on 29/01/15.
 */
public abstract class GenericDao implements Dao, Serializable{

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void persist(Object entity) {
        entityManager.persist(entity);
    }

    public void remove(Object entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    public Criteria createCriteria(Class<?> clazz, String alias) {
        return getSession().createCriteria(clazz, alias);
    }

    public Criteria createCriteria(Class<?> clazz) {
        return getSession().createCriteria(clazz);
    }

    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public List listNotWaitingEmpty(Criteria criteria) throws DataNotFoundException {
        List result = criteria.list();

        if (result == null || result.isEmpty()) {
            throw new DataNotFoundException();
        } else {
            return result;
        }
    }

    public Object uniqueResultNotWaitingEmpty(Criteria criteria) throws DataNotFoundException {
        Object result = criteria.uniqueResult();

        if (result == null) {
            throw new DataNotFoundException();
        } else {
            return result;
        }
    }
}
