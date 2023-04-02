package cz.uhk.fim.projekt.EventManager.Domain;

import jakarta.persistence.*;

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @SequenceGenerator( name = "permission_generator", sequenceName = "permission_pk_permissionid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "permission_generator")
    @Column(name = "pk_permissionid")
    private int id;

    @Column(name = "description")
    private String destricption;


    public int getId() {
        return id;
    }

    public String getDestricption() {
        return destricption;
    }

    public void setDestricption(String destricption) {
        this.destricption = destricption;
    }
}
