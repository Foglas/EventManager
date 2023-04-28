package cz.uhk.fim.projekt.EventManager.Domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
/**
 * Třída reprezentuje tabulku Photo v databázi.
 * Pro generaci id je využita sekvence z databáze.
 */
@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @SequenceGenerator( name = "photo_generator", sequenceName = "photo_pk_photoid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "photo_generator")
    @Column(name = "pk_photoid")
    private long id;

    @Column(name = "data")
    private byte[] data;

    @Column(name = "suffix")
    private String suffix;


    @Column(name = "uploadat")
    private LocalDateTime uploadAt;

    @OneToOne
    @JoinColumn(name = "fk_userid")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Photo(long id, byte[] data, String suffix,  User user) {
        this.id = id;
        this.data = data;
        this.suffix = suffix;
        this.user = user;
    }

    public Photo() {

    }

    public Photo(byte[] data, String suffix, LocalDateTime uploadAt, User user) {
        this.data = data;
        this.suffix = suffix;
        this.uploadAt = uploadAt;
        this.user = user;
    }
}
