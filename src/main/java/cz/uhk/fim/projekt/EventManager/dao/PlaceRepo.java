package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface poskytuje metody pro práci s tabulkou place
 */
@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {


    @Query(value = "SELECT DISTINCT city FROM address", nativeQuery = true)
    Optional<List<String>> getAllCities();

    @Query(value = "SELECT DISTINCT region FROM address", nativeQuery = true)
    Optional<List<String>> getAllRegions();

    @Query(value = "SELECT DISTINCT destrict FROM address", nativeQuery = true)
    Optional<List<String>> getAllDestricts();
}
