package transmition.entities;

import cw.entities.User;
import transmition.messages.TypeMessage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by drferreira on 29/04/15.
 */
@Entity
@Table(name = "sent_message", catalog = "cw")
@SequenceGenerator(name = "SentMessageSequence", sequenceName = "sent_message_seq", initialValue = 1, allocationSize = 1)
public class SentMessage implements Serializable{

    @Id
    @GeneratedValue(generator = "SentMessageSequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_message", nullable = false)
    private TypeMessage typeMessage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private User sender;

    @Column(name = "message_data", nullable = false)
    private String messageData;

    public SentMessage(User user, Date date, TypeMessage typeMessage, String message) {
        this.typeMessage = typeMessage;
        this.date = date;
        this.sender = user;
        this.messageData = message;
    }

    public SentMessage() {
    }

    public Long getId() {
        return id;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public Date getDate() {
        return date;
    }

    public User getSender() {
        return sender;
    }

    public String getMessageData() {
        return messageData;
    }
}
