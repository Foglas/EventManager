package cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo;

import cz.uhk.fim.projekt.EventManager.views.EventView;
import cz.uhk.fim.projekt.EventManager.views.UserView;

import java.util.List;
import java.util.Optional;


public interface UserViewRepo extends ReadOnlyRepo<UserView, Long> {

    Optional<List<UserView>> findAllById(Iterable<Long> id);
}
