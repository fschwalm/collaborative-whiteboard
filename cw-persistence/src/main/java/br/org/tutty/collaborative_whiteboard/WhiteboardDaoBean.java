package br.org.tutty.collaborative_whiteboard;

import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by drferreira on 19/05/15.
 */
@Stateless
@Local(WhiteboardDao.class)
public class WhiteboardDaoBean extends GenericDao implements WhiteboardDao {

    @Override
    public List<Stage> fetchAllStages() throws DataNotFoundException {
        Criteria criteria = createCriteria(Stage.class);
        return (List<Stage>) listNotWaitingEmpty(criteria);
    }


}
