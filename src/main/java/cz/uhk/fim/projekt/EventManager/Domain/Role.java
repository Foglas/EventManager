package cz.uhk.fim.projekt.EventManager.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.uhk.fim.projekt.EventManager.enums.Roles;
import jakarta.persistence.*;
import jakarta.transaction.TransactionScoped;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
/**
 * Třída reprezentuje tabulku UserRole v databázi. Obsahuje i propojovací tabulku mezi UserRole a Permission
 * a userrole a users.
 * Pro generaci id je využita sekvence z databáze.
 */
@Entity
@Table(name = "userrole")
public class Role {

    @Id
    @SequenceGenerator( name = "role_generator", sequenceName = "userrole_pk_userroleid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "role_generator")
    @Column(name = "pk_userroleid")
    private long id;

    @Column(name = "type")
    private String type;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToMany()
    @JoinTable(name = "userrolepermission",
            joinColumns = {  @JoinColumn(name = "fk_userroleid", referencedColumnName = "pk_userroleid"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "fk_permissionid", referencedColumnName = "pk_permissionid")
            }
    )

    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<Permission> permissions = new HashSet<>();


    public Role() {
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Role(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public Roles getType() {
        return Roles.valueOf(type);
    }

    public void setType(Roles type) {
        this.type = type.name();
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
