package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Interface poskytuje metody pro práci s tabulkou ticket
 */
@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

    /**
     * Metoda vrátí ID ticketu, kterému náleží daný uživatel a daný event
     * @param eventid ID eventu
     * @param userid ID uživatele
     */
    @Query(value = "SELECT pk_ticketid FROM Ticket WHERE Ticket.fk_userid = ?1 AND Ticket.fk_eventid = ?2", nativeQuery = true)
   Optional<Long> findTicketIdByUserIdAndEventId(long userid, long eventid);

    /**
     * Metoda vrátí seznam uživatelů, přihlášených na event
     * @param eventid ID eventu
     */
    @Query(value = "SELECT fk_userid FROM Ticket WHERE Ticket.fk_eventid = ?1", nativeQuery = true)
    Optional<List<Long>> findUsersIdByEventId(long eventid);

    /**
     * Metoda vráti seznam eventů, na které je uživatel přihlášen
     * @param userid ID uživatele
     */
    @Query(value = "SELECT fk_eventid FROM Ticket WHERE Ticket.fk_userid = ?1", nativeQuery = true)
    Optional<List<Long>> findEventsIdByUserId(long userid);
}
