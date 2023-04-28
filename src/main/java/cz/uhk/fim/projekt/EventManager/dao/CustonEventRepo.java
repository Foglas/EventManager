package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo.EventViewRepo;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
/**
 * Interface poskytuje metody pro práci s pohledem EventView
 */
public interface CustonEventRepo extends Repository<EventView, Long> {
}
