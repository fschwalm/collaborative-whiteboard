package br.org.tutty.collaborative_whiteboard;

import backlog_manager.entities.Iteration;
import backlog_manager.entities.Story;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.*;

/**
 * Created by drferreira on 23/06/15.
 */
@Stateless
@Local(IterationDao.class)
public class IterationDaoBean extends GenericDao implements IterationDao {

    @Override
    public List<Story> fetchStories(Iteration iteration) throws DataNotFoundException {
        Criteria criteria = createCriteria(Story.class);
        criteria.add(Restrictions.eq("iteration", iteration));

        return listNotWaitingEmpty(criteria);
    }

    @Override
    public Boolean existIterationInRange(Date init, Date end){
        List<Date> daysBetweenDates = getDaysBetweenDates(init, end);

        for (Date date : daysBetweenDates){
            try {
                fetchIterationBy(date);
                return Boolean.TRUE;
            } catch (DataNotFoundException e) {}
        }

        return Boolean.FALSE;
    }

    public Iteration fetchIterationBy(Date date) throws DataNotFoundException {
        Criteria criteria = createCriteria(Iteration.class);
        criteria.add(Restrictions.lt("initDate", date));
        criteria.add(Restrictions.gt("endDate", date));
        return (Iteration) uniqueResultNotWaitingEmpty(criteria);
    }

    @Override
    public Iteration getCurrentIteration() throws DataNotFoundException {
      return fetchIterationBy(new Date());
    }

    private List<Date> getDaysBetweenDates(Date init, Date end)
    {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(init);

        while (calendar.getTime().before(end))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
