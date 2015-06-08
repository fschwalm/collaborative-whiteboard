package backlog_manager.entities;

import cw.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 08/06/15.
 */
@Entity
@Table(name = "story", catalog = "cw")
@SequenceGenerator(name = "AnalysisSequence", sequenceName = "analysis_seq", initialValue = 1, allocationSize = 1)
public class Analysis implements Serializable {

    @Id
    @GeneratedValue(generator = "AnalysisSequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User author;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Analysis() {
    }

    public Analysis(User author, Date date) {
        this.author = author;
        this.date = date;
    }
}
