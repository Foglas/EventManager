package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface poskytuje metody pro práci s tabulkou event
 */
@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
    /**
     * Metoda vrátí číselnou hodnotu, počet přihlášených uživatelů na event, jehož ID je parametr metody
     * @param id ID eventu, u kterého zjišťujeme počet přihlášených uživatelů
     */
    @Query(value = "SELECT numberofpeopleonevent(?1)", nativeQuery = true)
    Long getNumberOfPeopleOnEvent(long id);

    /**
     * Metoda vrátí názvy všech eventů, které mají alespoň určitý počet uživatelů
     * @param id nejmenší počet přihlášených uživatelů, které event musí mít, aby ho vrátila metoda
     */
    @Query(value = "SELECT eventsandattendance(?1)", nativeQuery = true)
    List<String> getEventNameByAttendence(int id);

    /**
     * Metoda vrátí boolean hodnotu, rozhodne, jestli zadaný event patří do zadané kategorie
     * @param categoryid ID kategorie
     * @param eventid ID uživatele, u kterého zjišťujeme, zda má danou kategorii
     */
    @Query(value = "SELECT iseventscategory(?1,?2)", nativeQuery = true)
    boolean iseventscatagory(long eventid, long categoryid);
}
