package cz.uhk.fim.projekt.EventManager.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity(name = "event_information")
public class EventView {

    @Column(name = "pk_eventid")
    @Id
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

    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;

    @Column(name = "addressid")
    private int addressId;

    @Column(name = "category")
    private String category;

    @Column(name = "categoryid")
    private int categoryId;


}
