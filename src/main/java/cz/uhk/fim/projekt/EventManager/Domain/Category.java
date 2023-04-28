package cz.uhk.fim.projekt.EventManager.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Třída reprezentuje tabulku Category v databázi. Obsahuje i propojovací tabulku mezi Event a Category.
 * Pro generaci id je využita sekvence z databáze.
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    @SequenceGenerator( name = "category_generator", sequenceName = "category_pk_categoryid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "category_generator")
    @Column(name = "pk_categoryid", nullable = false)
    private long id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column
    private String name;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();

    public Category() {
    }

    public Category(String description, String name) {
        this.description = description;
        this.name = name;
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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
