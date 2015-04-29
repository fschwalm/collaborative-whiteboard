package br.org.tutty.transmition;

import br.org.tutty.collaborative_whiteboard.GenericDao;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by drferreira on 29/04/15.
 */
@Stateless
@Local(TransmitionDao.class)
public class TransmitionDaoBean extends GenericDao implements TransmitionDao {
}
