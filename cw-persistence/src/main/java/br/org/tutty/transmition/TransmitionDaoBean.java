package br.org.tutty.transmition;

import br.org.tutty.collaborative_whiteboard.GenericDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import transmition.entities.SentMessage;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by drferreira on 29/04/15.
 */
@Stateless
@Local(TransmitionDao.class)
public class TransmitionDaoBean extends GenericDao implements TransmitionDao {
    @Override
    public List<SentMessage> fetchLastMessages(Integer defaultSizeLastMessages) {
        Criteria criteria = createCriteria(SentMessage.class);
        criteria.setMaxResults(defaultSizeLastMessages);
        criteria.addOrder(Order.desc("date"));

        return criteria.list();
    }
}
