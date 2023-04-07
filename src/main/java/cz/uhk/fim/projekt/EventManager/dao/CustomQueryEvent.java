package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomQueryEvent implements CustonEventRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<EventView> findEventByParameters(String query){
        List<EventView> events = (List<EventView>) entityManager.createNativeQuery(query, EventView.class).getResultList();
      //  System.out.println(events.get(0).getOrganization());
        return events;
    }
}
