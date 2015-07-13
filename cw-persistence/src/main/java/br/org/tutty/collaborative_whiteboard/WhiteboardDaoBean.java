package br.org.tutty.collaborative_whiteboard;

import cw.entities.Stage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by drferreira on 19/05/15.
 */
@Stateless
@Local(WhiteboardDao.class)
public class WhiteboardDaoBean extends GenericDao implements WhiteboardDao {

    @Override
    public Set<Stage> fetchAllStages(){
        Criteria criteria = createCriteria(Stage.class);
        criteria.addOrder(Order.asc("position"));
        return new HashSet(list(criteria));
    }

    @Override
    public Stage fetchInitialStage(){
        Criteria criteria = createCriteria(Stage.class);
        criteria.add(Restrictions.eq("position", 0l));
        return (Stage) uniqueResult(criteria);
    }

}
