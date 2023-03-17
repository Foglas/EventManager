package cz.uhk.fim.projekt.EventManager.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @SequenceGenerator( name = "category_generator", sequenceName = "category_sequence", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "category_generator")
    @Column(name = "pk_categoryid")
    private int id;
    @Column
    private String description;
    @Column
    private String name;
    public Category() {
    }

    public Category(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public int getId() {
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
}
