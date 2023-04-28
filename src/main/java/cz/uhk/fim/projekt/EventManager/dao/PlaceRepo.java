package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface poskytuje metody pro práci s tabulkou place
 */
@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {
}
