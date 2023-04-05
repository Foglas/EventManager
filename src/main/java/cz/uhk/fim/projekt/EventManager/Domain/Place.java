package cz.uhk.fim.projekt.EventManager.Domain;


import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Place {

    @Id
    @SequenceGenerator(name = "address_generator", sequenceName = "address_pk_addressid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "address_generator")
    @Column(name = "pk_addressid")
    private int id;

    @Column(name = "destrict")
    private String destrict;
    @Column(name = "region")
    private String region;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;

    @Column(name = "bin")
    private String bin;

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public void setDestrict(String destrict) {
        this.destrict = destrict;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getId() {
        return id;
    }

    public String getDestrict() {
        return destrict;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}
