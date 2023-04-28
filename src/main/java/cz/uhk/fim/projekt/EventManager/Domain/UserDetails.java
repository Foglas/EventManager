package cz.uhk.fim.projekt.EventManager.Domain;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Třída reprezentuje tabulku UserDetails v databázi.
 * Pro generaci id je využita sekvence z databáze.
 */
@Entity
@Table(name = "userdetails")
public class UserDetails {

    @Id
    @SequenceGenerator( name = "userDetails_generator", sequenceName = "userdetails_pk_userdetailsid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "userDetails_generator")
    @Column(name = "pk_userdetailsid")
    private long id;

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;
    
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;
    
    @Column(name = "phone")
    private String phone;

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }
}
