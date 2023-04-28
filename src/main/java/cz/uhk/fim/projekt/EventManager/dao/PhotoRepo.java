package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Photo;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface poskytuje metody pro práci s tabulkou photo
 */
@Repository
public interface PhotoRepo extends JpaRepository<Photo, Long> {
    /**
     * Metoda vrátí ID fotografie, která patří k zadanému ID uživatele
     * @param id ID uživatele
     */
    @Query(value = "SELECT pk_photoid FROM Photo WHERE fk_userid = ?1", nativeQuery = true)
    Optional<Long> findByUserId(long id);
}
