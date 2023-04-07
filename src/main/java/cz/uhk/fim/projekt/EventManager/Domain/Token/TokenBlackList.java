package cz.uhk.fim.projekt.EventManager.Domain.Token;


import jakarta.persistence.*;

@Entity
@Table(name = "tokenblacklist")
public class TokenBlackList {
    @Id
    @Column(name = "pk_tokenid")
    @SequenceGenerator( name = "token_generator", sequenceName = "tokenblacklist_pk_tokenid_seq", allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "token_generator")
    private long id;


    @Column(name = "token")
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
