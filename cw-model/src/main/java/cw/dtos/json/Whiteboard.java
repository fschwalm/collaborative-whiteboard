package cw.dtos.json;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by drferreira on 19/05/15.
 */
public class Whiteboard implements Serializable {
    private Set<JSonStage> jSonStages = new HashSet<>();

    public void addStage(JSonStage stage){
        jSonStages.add(stage);
    }

}
