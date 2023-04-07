package cz.uhk.fim.projekt.EventManager.Domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @SequenceGenerator( name = "ticket_generator", sequenceName = "ticket_pk_ticketid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "ticket_generator")
    @Column(name = "pk_ticketid")
    private long id;

    @ManyToOne
    @JoinColumn(name = "fk_eventid")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "fk_userid")
    private User user;

    @Column(name = "state")
    private String state;

    @Column(name = "createdat")
    private LocalDateTime createdAt;


    public Ticket(Event event, User user, String state, LocalDateTime createdAt) {
        this.event = event;
        this.user = user;
        this.state = state;
        this.createdAt = createdAt;
    }

    public Ticket() {
    }

    public long getId() {
        return id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
