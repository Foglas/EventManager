package cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo;

import cz.uhk.fim.projekt.EventManager.views.EventView;

import java.util.Optional;

public interface EventViewRepo extends ReadOnlyRepo<EventView, Long>{

    Optional<EventView> findById(long id);
}
