package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

    @Query(value = "SELECT numberofpeopleonevent(?1)", nativeQuery = true)
    Long getNumberOfPeopleOnEvent(long id);

    @Query(value = "SELECT eventsandattendance(?1)", nativeQuery = true)
    List<String> getEventNameByAttendence(int id);

}
