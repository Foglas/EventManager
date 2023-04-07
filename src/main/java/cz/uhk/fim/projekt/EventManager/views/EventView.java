package cz.uhk.fim.projekt.EventManager.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity(name = "event_information")
@Immutable
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

    public EventView() {
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public LocalDateTime getEndDateAndTime() {
        return endDateAndTime;
    }

    public void setEndDateAndTime(LocalDateTime endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
    }

    public short[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(short[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
