package cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo;

import cz.uhk.fim.projekt.EventManager.views.EventView;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Interface poskytuje metody pro práci s pohledem eventView
 */
public interface EventViewRepo extends ReadOnlyRepo<EventView, Long>{

    Optional<EventView> findById(long id);
    /**
     * Metoda vyhledá všechny pohledy EventView podle ID
     * @param id ID Eventu
     */
    Optional <List<EventView>> findAllById(Iterable <Long> id);


}
