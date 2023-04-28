package cz.uhk.fim.projekt.EventManager.views;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
/**
 * Pohled UserView zobrazuje rozšířené informace o uživateli z tabulek Users a UserDetails
 */

@Entity(name = "user_information")
@Immutable
public class UserView {

    @Id
    @Column(name = "pk_userid")
    private long id;

    @Column(unique=true, name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private String phone;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
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
