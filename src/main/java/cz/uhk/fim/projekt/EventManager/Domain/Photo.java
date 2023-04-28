package cz.uhk.fim.projekt.EventManager.Domain;

import jakarta.persistence.*;

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

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "data")
    private Byte[] data;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "type")
    private String type;

    @Column(name = "uploadat")
    private LocalDateTime uploadAt;

    @OneToOne
    @JoinColumn(name = "fk_userid")
    private User user;


}
