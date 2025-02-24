package cz.uhk.fim.projekt.EventManager.Domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
/**
 * Třída reprezentuje tabulku Organization v databázi. Obsahuje i propojovací tabulku mezi Organization a Users.
 * Pro generaci id je využita sekvence z databáze.
 */
@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @SequenceGenerator( name = "organization_generator", sequenceName = "organization_pk_organizationid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "organization_generator")
    @Column(name = "pk_organizationid")
    private long id;

    @Column(name = "name")
    private String name;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "organizationuser",
         joinColumns = {  @JoinColumn(name = "fk_organizationid", referencedColumnName = "pk_organizationid"),
         },
            inverseJoinColumns = {
            @JoinColumn(name = "fk_userid", referencedColumnName = "pk_userid")
            }
    )
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Organization() {
    }

    public Organization(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Organization(int id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
