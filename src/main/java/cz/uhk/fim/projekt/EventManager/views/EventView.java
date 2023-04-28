package cz.uhk.fim.projekt.EventManager.views;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Pohled EventView zobrazuje rozšířené informace o Eventu z tabulek Event a Address
 */

@Entity(name = "event_information")
@Immutable
public class EventView {

    @Column(name = "pk_eventid")
    @Id
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private LocalDateTime dateAndTime;

    @Column(name = "endtime")
    private LocalDateTime endDateAndTime;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;

    @Column(name = "addressid")
    private int addressId;

    @Column(name = "destrict")
    private String destrict;
    @Column(name = "region")
    private String region;

    @Transient
    private List<Category> categoryList;


    public EventView() {
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public long getId() {
        return id;
    }

    public String getDestrict() {
        return destrict;
    }

    public String getRegion() {
        return region;
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


    public LocalDateTime getEndDateAndTime() {
        return endDateAndTime;
    }


    public String getCoordinates() {
        return coordinates;
    }


    public String getCity() {
        return city;
    }


    public String getStreet() {
        return street;
    }


    public int getAddressId() {
        return addressId;
    }

   }
