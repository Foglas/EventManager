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

    @ManyToOne
    @JoinColumn(name = "fk_addressid")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "fk_organizationid")
    private Organization organization;


    public Event(String description, String name, LocalDateTime dateAndTime) {
        this.description = description;
        this.name = name;
        this.dateAndTime = dateAndTime;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
