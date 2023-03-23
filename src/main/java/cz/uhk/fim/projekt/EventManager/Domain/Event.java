package cz.uhk.fim.projekt.EventManager.Domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "pk_eventid")
    @SequenceGenerator( name = "event_generator", sequenceName = "event_pk_eventid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "event_generator")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private LocalDateTime dateAndTime;

    @Column(name = "fk_addressid")
    private long fk_addressId;

    @Column(name = "fk_organizationid")
    private long fk_organizationId;

    public Event(String description, String name, LocalDateTime dateAndTime, long fk_addressId, long fk_organizationId) {
        this.description = description;
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.fk_addressId = fk_addressId;
        this.fk_organizationId = fk_organizationId;
    }

    public Event() {
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public long getFk_addressId() {
        return fk_addressId;
    }

    public long getFk_organizationId() {
        return fk_organizationId;
    }
}
