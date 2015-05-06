package backlog_manager.entities;

import backlog_manager.enums.StoryStatus;
import cw.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 06/05/15.
 */
@Entity
@Table(name = "story_status", catalog = "cw")
@SequenceGenerator(name = "StoryStatusSequence", sequenceName = "story_status_seq", initialValue = 1, allocationSize = 1)
public class StoryStatusLog implements Serializable{

    @Id
    @GeneratedValue(generator = "StoryStatusSequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Story story;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoryStatus storyStatus;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private User user;

    public StoryStatusLog() {
    }

    public StoryStatusLog(StoryStatus storyStatus, User user) {
        this.storyStatus = storyStatus;
        this.date = new Date();
        this.user = user;
    }

    public StoryStatus getStoryStatus() {
        return storyStatus;
    }

    public Date getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
