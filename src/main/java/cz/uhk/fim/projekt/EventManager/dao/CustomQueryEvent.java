package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
/**
 * Třída poskytuje implementaci interfacu CustomEventRepo
 */
@Component
public class CustomQueryEvent implements CustonEventRepo {

    @PersistenceContext
    private EntityManager entityManager;
    /**
     * Metoda vrátí seznam eventů, které odpovídají vloženým parametrům
     * @param query seznam parametrů
     */
    public List<EventView> findEventByParameters(String query){
        List<EventView> events = (List<EventView>) entityManager.createNativeQuery(query, EventView.class).getResultList();
        return events;
    }
}
