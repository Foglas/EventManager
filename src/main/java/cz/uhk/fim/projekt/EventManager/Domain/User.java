package cz.uhk.fim.projekt.EventManager.Domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
//@SecondaryTable(name = "userdetails", pkJoinColumns = @PrimaryKeyJoinColumn(name = "pk_userdetailsid", referencedColumnName = "fk_userdetailsid"))
public class User {

    @Id
    @SequenceGenerator( name = "user_generator", sequenceName = "User_pk_userid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_generator")
    @Column(name = "pk_userid")
    private long id;

    @Column(unique=true, name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne()
    @JoinColumn(name = "fk_userdetailsid")
    private UserDetails userDetails;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Organization> organization;

    @ManyToMany
    @JoinTable(name = "userroleuser",
            joinColumns = {  @JoinColumn(name = "fk_userid", referencedColumnName = "pk_userid"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "fk_userroleid", referencedColumnName = "pk_userroleid")
            }
    )
    private Set<Role> roles = new HashSet<>();

    @Transient
    private String passwordAgain;
    
    public User() {
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
    @JsonIgnore
    public Set<Organization> getOrganization() {
        return organization;
    }


    public void setOrganization(Set<Organization> organization) {
        this.organization = organization;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
