package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.*;
import cz.uhk.fim.projekt.EventManager.service.CommentService;
import cz.uhk.fim.projekt.EventManager.service.EventService;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Třída obsahující metody na příjímaní požadavků na url týkajících se akcí ohledně eventu.
 */
@RestController
@RequestMapping("/api")
public class EventController {

    private EventService eventService;
    private CommentService commentService;

    @Autowired
    public EventController(EventService eventService, CommentService commentService) {
        this.eventService = eventService;
        this.commentService = commentService;
    }

    /**
     * Přijme dotaz na url /api/auth/event/organization/{id}/save a přepošle příslušné parametry na
     * service vrstvu pro zpracování.Vyžaduje autorizaci. id je id organizace, která pořádá event.
     * Slouží pro ukládání eventu do databáze. Informace o eventu se nachází v body requestu a uživatel
     * který event zakládá se zjistí z headeru.
     * @return  message na frontend s http statusem
     */
    @PostMapping(value = "/auth/event/organization/{id}/save")
    public ResponseEntity<?> save(@RequestBody Map<String,String> body,
                                  @PathVariable("id") long organizationId, HttpServletRequest request) {
       return eventService.save(request,body, organizationId);
    }

    /**
     * Přijme dotaz na url /api/events. Slouží pro získání přehledu všech eventů z databáze.
     * @return  všechny eventview v databázi
     */
    @GetMapping(value = "/events")
    public List<EventView> getEvents(){
        List<EventView> eventViews = eventService.getEvents();
      return eventViews;
    }

    /**
     * Přijme dotaz na url /api/events/{id}. Id je id eventu. Parametry předá přislušné service vrstvě
     * pro zpracování. Slouží pro získání konkretního eventu podle id.
     * @return http status a event nebo jen http status
     */
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") long id){
        return eventService.getEventById(id);
    }


    /**
     * Přijme dotaz na url /api/auth/event/{id}/delete. Id je id eventu. Parametry předá přislušné
     * service vrstvě pro zpracování. Slouží pro vymazání eventu.
     * @return http status a message
     */
    @DeleteMapping("/auth/event/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") long id, HttpServletRequest request){
      return   eventService.delete(id, request);
    }

    /**
     * Přijme dotaz na url /api/auth/event/{id}/attend. Vyžaduje autorizaci. Id je id eventu.
     * Parametry předá přislušné service vrstvě pro zpracování. Slouží pro přihlášení uživatele
     * na event. Uživatel se získává z tokenu v headeru
     * @return http status a message
     */
    @PostMapping("auth/event/{id}/attend")
    public ResponseEntity<?> attend(@RequestBody Map<String, String> body, @PathVariable("id") long id,
                                    HttpServletRequest httpServletRequest) {
        return eventService.attend(body,id,httpServletRequest);
    }

    /**
     * Přijme dotaz na url /api/auth/event/{id}/cancelAttend. Vyžaduje autorizaci. Id je id eventu.
     * Parametry předá přislušné service vrstvě pro zpracování. Slouží pro zrušení přihlášení
     * uživatele na event. Uživatel se získává z tokenu v headeru
     * @return http status a message
     */
    @DeleteMapping("auth/event/{id}/cancelAttend")
    public ResponseEntity<?> cancelAttend(@PathVariable("id") long id, HttpServletRequest request){
     return eventService.cancelAttend(id, request);
    }

    /**
     * Přijme dotaz na url /api/auth/event/{id}/users. Vyžaduje autorizaci. Id je id eventu.
     * Parametry předá přislušné service vrstvě pro zpracování. Slouží pro získání všech uživatelů
     * přihlášených na event.
     * @return http status a message, pokud je request neúspěšný nebo http status a seznam uživatelů
     * pokud je úspěšný.
     */
    @GetMapping("/auth/event/{id}/users")
    public ResponseEntity<?> getUsersAttendedOnEvent(@PathVariable("id") long id){
        return eventService.getUsersAttendedOnEvent(id);
    }

    /**
     * Přijme dotaz na url /api/auth/event/{id}/comment/save. Vyžaduje autorizaci. Id je id eventu.
     * Parametry předá přislušné service vrstvě pro zpracování. Slouží pro uložení commentu
     * k příslušnému eventu. Obsah komentáře se získává z body reguestu
     * @return http status a message
     */
    @PostMapping("/auth/event/{id}/comment/save")
    public ResponseEntity<?> save(HttpServletRequest request,@RequestBody Comment comment, @PathVariable("id") long id){
        return commentService.save(request,comment, id);
    }

    /**
     * Přijme dotaz na url /api/events/search. Parametry předá přislušné service vrstvě
     * pro zpracování. Slouží pro vyhledání eventu podle příchozích parametrů. Parametry se získávají
     * přímo z url.
     * @return http status a message, pokud je request neúspěšný nebo http status a list eventview
     * pokud je request úspěšný
     */
    @GetMapping("/events/search")
    public ResponseEntity<?> findEventByParameters(@RequestParam(name = "region", required = false) Optional<String> region,
                                                 @RequestParam(name = "destrict", required = false) Optional<String> destrict,
                                                 @RequestParam(name = "time", required = false) Optional<LocalDateTime> time,
                                                 @RequestParam(name = "city", required = false) Optional<String> city,
                                                 @RequestParam(name = "categories", required = false) Optional<String> categories){
    return eventService.findEventByParameters(region, destrict, time, city, categories);
    }

    /**
     * Přijme dotaz na url /api/event/{id}/people. Id je id eventu Parametry předá přislušné service
     * vrstvě pro zpracování. Slouží pro získání počtu lidí příhlašených na event.
     * @return http status, pokud je request neúspěšný nebo http status a počet lidí, pokud je
     * úspěšný
     */
    @GetMapping("event/{id}/people")
    public ResponseEntity<Long> getNumberOfPeopleOnEvent(@PathVariable("id") long id){
        return eventService.getNumberOfPeopleOnEvent(id);
    }

    /**
     * Přijme dotaz na url /api/event/{id}/category. Id je id eventu Parametry předá přislušné service
     * vrstvě pro zpracování. Slouží pro získání kategorii příslušného eventu.
     * @return http status a message, pokud je neúspěšný nebo http status a list kategorií eventu,
     * pokud je úspěšný
     */
    @GetMapping("/event/{id}/category")
    public ResponseEntity<?> getEventCategory(@PathVariable("id") long id){
        return eventService.getEventCategory(id);
    }

    /**
     * Přijme dotaz na url /api/events/attendence. Parametry předá přislušné service vrstvě
     * pro zpracování. Slouží pro získání jmen eventů, které mají počet přihlášených lidí vyšší
     * než zadané číslo. Číslo se předává v body
     * @return http status a message, pokud je úspěšný nebo http status a příslušné jména eventů
     */
    @GetMapping("events/attendence")
    public ResponseEntity<?> getEventByAttendence(@RequestParam("number") String number){
        return eventService.getEventByAttendence(number);
    }

    /**
     * Přijme dotaz na url /api/user/{id}/getAttendedEvents. Parametry předá přislušné service vrstvě
     * pro zpracování. Slouží pro získání všech eventview na které je uživatel přihlášen. Id je id usera
     * @return http status a message, pokud je request neúspěšný nebo http status a přislušné eventview,
     * pokud je úspěšný
     */
    @GetMapping("/auth/user/{id}/getAttendedEvents")
    public ResponseEntity<?> findAllEventViews(@PathVariable("id") long id) {
        return eventService.getEventViewsFromUser(id);
    }
}
