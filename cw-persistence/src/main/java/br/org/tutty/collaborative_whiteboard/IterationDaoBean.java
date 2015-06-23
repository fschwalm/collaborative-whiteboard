package br.org.tutty.collaborative_whiteboard;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by drferreira on 23/06/15.
 */
@Stateless
@Local(IterationDao.class)
public class IterationDaoBean extends GenericDao implements IterationDao {
}
