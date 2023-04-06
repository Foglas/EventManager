package cz.uhk.fim.projekt.EventManager.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.uhk.fim.projekt.EventManager.enums.Roles;
import jakarta.persistence.*;
import jakarta.transaction.TransactionScoped;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

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


    public Role() {
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
