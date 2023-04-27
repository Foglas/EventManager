package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Photo;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepo extends JpaRepository<Photo, Long> {
    @Query(value = "SELECT pk_photoid FROM Photo WHERE fk_userid = ?1", nativeQuery = true)
    Optional<Long> findByUserId(long id);
}
