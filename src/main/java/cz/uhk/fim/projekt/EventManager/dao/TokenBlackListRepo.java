package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Token.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenBlackListRepo extends JpaRepository<TokenBlackList, Long> {

    @Query(value = "SELECT pk_tokenid FROM TokenBlackList WHERE TokenBlackList.token = ?1", nativeQuery = true)
    Optional<Long> existsInBlackListByToken(String token);
}
