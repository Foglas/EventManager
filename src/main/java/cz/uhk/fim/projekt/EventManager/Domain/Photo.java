package cz.uhk.fim.projekt.EventManager.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import java.time.LocalDateTime;

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

    @JdbcType(VarbinaryJdbcType.class)
    @Column(name = "photo",columnDefinition="BYTEA")
    private byte[] photo;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "uploadat")
    private LocalDateTime uploadAt;

    @OneToOne
    @JoinColumn(name = "fk_userid")
    @JsonIgnore
    private User user;

    public Photo() {
    }

    public Photo( byte[] photo, String suffix, LocalDateTime uploadAt, User user) {
        this.photo = photo;
        this.suffix = suffix;
        this.uploadAt = uploadAt;
        this.user = user;
    }

    public Photo(long id, byte[] photo, String suffix, LocalDateTime uploadAt, User user) {
        this.id = id;
        this.photo = photo;
        this.suffix = suffix;
        this.uploadAt = uploadAt;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
