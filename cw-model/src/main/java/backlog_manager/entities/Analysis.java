package backlog_manager.entities;

import cw.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 08/06/15.
 */
@Entity
@Table(name = "analysis", catalog = "cw")
@SequenceGenerator(name = "AnalysisSequence", sequenceName = "analysis_seq", initialValue = 1, allocationSize = 1)
public class Analysis implements Serializable {

    @Id
    @GeneratedValue(generator = "AnalysisSequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Story story;

    @ManyToOne
    private User author;

    @ManyToOne
    private User finisher;

    @Temporal(TemporalType.TIMESTAMP)
    private Date initDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public Analysis() {
    }

    public Analysis(User author, Story story) {
        this.author = author;
        this.story = story;
        this.initDate = new Date();
    }

    public void finish(User finisher){
        this.finisher = finisher;
        this.endDate = new Date();
    }

    public Boolean inProgress(){
        return initDate != null && endDate == null ? Boolean.TRUE : Boolean.FALSE;
    }


}
