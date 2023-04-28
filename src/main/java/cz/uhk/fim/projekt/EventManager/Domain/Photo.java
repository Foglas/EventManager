package cz.uhk.fim.projekt.EventManager.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JoinColumn(name = "fk_userid")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Photo(long id, byte[] data, String suffix, LocalDateTime uploadAt,  User user) {
        this.id = id;
        this.uploadAt = uploadAt;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public LocalDateTime getUploadAt() {
        return uploadAt;
    }

    public void setUploadAt(LocalDateTime uploadAt) {
        this.uploadAt = uploadAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
