package cz.uhk.fim.projekt.EventManager.Domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @SequenceGenerator(name = "comment_generator", sequenceName = "comment_pk_commentid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "comment_generator")
    @Column(name = "pk_commentid")
    private int id;
    @Column(name = "comment")
    private String comment;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "fk_eventid")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "fk_userid")
    private User user;


    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
