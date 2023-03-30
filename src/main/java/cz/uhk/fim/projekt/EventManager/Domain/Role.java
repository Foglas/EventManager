package cz.uhk.fim.projekt.EventManager.Domain;

import jakarta.persistence.*;

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
    private int id;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
