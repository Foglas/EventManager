package cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo;

import cz.uhk.fim.projekt.EventManager.views.EventView;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventViewRepo extends ReadOnlyRepo<EventView, Long>{

    Optional<EventView> findById(long id);

    Optional <List<EventView>> findAllById(Iterable <Long> id);


}
