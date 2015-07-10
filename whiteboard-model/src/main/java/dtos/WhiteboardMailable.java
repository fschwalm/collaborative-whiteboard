package dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by drferreira on 10/07/15.
 */
public class WhiteboardMailable implements Serializable {
    private Set<StagesMailable> stages;
    private Date updateDate;

    public WhiteboardMailable() {
        updateDate = new Date();
    }

    public void addStage(StagesMailable stagesMailable){
        stages.add(stagesMailable);

    }

    public Set<StagesMailable> getStages() {
        return stages;
    }
}
