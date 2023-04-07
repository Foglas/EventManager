package cz.uhk.fim.projekt.EventManager.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.uhk.fim.projekt.EventManager.enums.Permissions;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @SequenceGenerator( name = "permission_generator", sequenceName = "permission_pk_permissionid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "permission_generator")
    @Column(name = "pk_permissionid")
    private long id;

    @Column(name = "description")
    private String destricption;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public Permission(Permissions destricption) {
        this.destricption = destricption.name();
    }

    public Permission() {
    }

    public long getId() {
        return id;
    }

    public Permissions getDestricption() {
        return Permissions.valueOf(destricption);
    }

    public void setDestricption(Permissions destricption) {
        this.destricption = destricption.name();
    }
}
