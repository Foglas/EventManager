package cz.uhk.fim.projekt.EventManager.Domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "pk_eventid")
    @SequenceGenerator( name = "event_generator", sequenceName = "event_pk_eventid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "event_generator")
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private LocalDateTime dateAndTime;

    @Column(name = "endtime",nullable = true)
    private LocalDateTime endDateAndTime;

    @Column(name = "coordinates")
    private String coordinates;

    @ManyToOne
    @JoinColumn(name = "fk_addressid")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "fk_organizationid")
    private Organization organization;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToMany()
    @JoinTable(name = "eventcategory",
            joinColumns = {  @JoinColumn(name = "fk_eventid", referencedColumnName = "pk_eventid"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "fk_categoryid", referencedColumnName = "pk_categoryid")
            }
    )
    private Set<Category> categories = new HashSet<>();


    public Event(String description, String name, LocalDateTime dateAndTime, Place place, Organization organization, LocalDateTime endDateAndTime,String coordinates) {
        this.description = description;
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.organization = organization;
        this.place = place;
        this.endDateAndTime = endDateAndTime;
        this.coordinates = coordinates;
    }
    public Event(long pk_eventid,String description, String name, LocalDateTime dateAndTime, Place place, Organization organization, LocalDateTime endDateAndTime, String coordinates) {
        this.id = pk_eventid;
        this.description = description;
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.organization = organization;
        this.place = place;
        this.endDateAndTime = endDateAndTime;
        this.coordinates = coordinates;
    }

    public Event() {
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
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
