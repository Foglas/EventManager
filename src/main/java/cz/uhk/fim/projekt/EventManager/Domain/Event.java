package cz.uhk.fim.projekt.EventManager.Domain;


import jakarta.persistence.*;

import java.sql.Timestamp;
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

    @Column(name = "endtime")
    private LocalDateTime endDateAndTime;

    @Column(name = "coordinates")
    private short[] coordinates;


    @ManyToOne
    @JoinColumn(name = "fk_addressid")
    private Place place;
    @ManyToOne
    @JoinColumn(name = "fk_organizationid")
    private Organization organization;


    public Event(String description, String name, LocalDateTime dateAndTime, Place place, Organization organization, LocalDateTime endDateAndTime) {
        this.description = description;
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.organization = organization;
        this.place = place;
        this.endDateAndTime = endDateAndTime;
    }

    public Event() {
    }

    public short[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(short[] coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getEndDateAndTime() {
        return endDateAndTime;
    }

    public void setEndDateAndTime(LocalDateTime endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
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
