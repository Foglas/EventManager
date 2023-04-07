package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
    Optional<Event> findEventByOrganizationId(Long id);

}
