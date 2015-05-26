package br.org.tutty.collaborative_whiteboard;

import backlog_manager.entities.Story;
import cw.dtos.json.JSonStage;
import cw.dtos.json.JSonStory;
import cw.entities.Stage;
import cw.exceptions.DataNotFoundException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by drferreira on 19/05/15.
 */
@Stateless
@Local(WhiteboardDao.class)
public class WhiteboardDaoBean extends GenericDao implements WhiteboardDao {

    @Override
    public Set<Stage> fetchAllStages() throws DataNotFoundException {
        Criteria criteria = createCriteria(Stage.class);
        criteria.addOrder(Order.asc("position"));
        return new HashSet(listNotWaitingEmpty(criteria));
    }

    @Override
    public Set<Story> fetchStories() {
        // TODO Deve buscar todas as estorias preparadas - Estorias que ja foram analisadas
        return new HashSet<>();
    }

    @Override
    public Set<JSonStage> mountJsonStages(){
        Set<JSonStage> stages = new HashSet<>();

        try{
            fetchAllStages().forEach(new Consumer<Stage>() {
                @Override
                public void accept(Stage stage) {
                    stages.add(stage.toJson());
                }
            });
        }catch (DataNotFoundException e){}

        return stages;
    }

    @Override
    public Set<JSonStory> mountJsonStories(){
        Set<JSonStory> stories = new HashSet<>();
        fetchStories().forEach(new Consumer<Story>() {
            @Override
            public void accept(Story story) {
                stories.add(story.toJson());
            }
        });

        return stories;
    }
}
