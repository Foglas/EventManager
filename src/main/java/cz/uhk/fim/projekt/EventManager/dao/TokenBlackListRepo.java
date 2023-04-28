package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Token.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Interface poskytuje metody pro práci s tabulkou tokenBlackList
 */
public interface TokenBlackListRepo extends JpaRepository<TokenBlackList, Long> {
    /**
     * Metoda vrátí hodnotu ID tokenu, který se nachází na blackListu
     * @param token hodnota tokenu
     */
    @Query(value = "SELECT pk_tokenid FROM TokenBlackList WHERE TokenBlackList.token = ?1", nativeQuery = true)
    Optional<Long> existsInBlackListByToken(String token);
}
