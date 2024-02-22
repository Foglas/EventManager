package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.*;
import cz.uhk.fim.projekt.EventManager.dao.*;
import cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo.EventViewRepo;
import cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo.UserViewRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import cz.uhk.fim.projekt.EventManager.views.UserView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * Třída poskytuje metody pro obsluhu requestů týkajících se eventu
 */
@Service
public class EventService {

    private EventRepo eventRepo;
    private UserRepo userRepo;

    private PlaceRepo placeRepo;

    private OrganizationRepo organizationRepo;

    private CategoryRepo categoryRepo;
    private TicketRepo ticketRepo;
    private EventViewRepo eventViewRepo;
    private UserViewRepo userViewRepo;
    private JwtUtil jwtUtil;

    private CustomQueryEvent customQueryEvent;

    @Autowired
    public EventService(EventRepo eventRepo, UserRepo userRepo, JwtUtil jwtUtil, PlaceRepo placeRepo, OrganizationRepo organizationRepo, TicketRepo ticketRepo, CustomQueryEvent customQueryEvent, EventViewRepo eventViewRepo, CategoryRepo categoryRepo, UserViewRepo userViewRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.organizationRepo = organizationRepo;
        this.placeRepo = placeRepo;
        this.ticketRepo = ticketRepo;
        this.eventViewRepo = eventViewRepo;
        this.customQueryEvent = customQueryEvent;
        this.categoryRepo = categoryRepo;
        this.userViewRepo = userViewRepo;
    }

    /**
     * Metoda uloží event do databáze a přiřadí mu příslušnou organizaci
     *
     * @param body           Mapa obsahující informace o události
     * @param request        request, zjišťuje se z něho token
     * @param organizationId ID organizace, pod kterou událost bude
     * @return vrátí hlášku o úspěšném uložení, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<?> save(HttpServletRequest request, Map<String, String> body, long organizationId) {
        String description = body.get("description");
        String name = body.get("name");

        Place place = null;
        String coordinates = null;

        if (name == "") {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "name not fill");
        }

        if (description == "") {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "description not fill");
        }

        if (body.get("time") == "") {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "time is not selected");
        }

        if (body.get("endtime") == "") {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "endtime is not selected");
        }

        LocalDateTime time = LocalDateTime.parse(body.get("time"));
        LocalDateTime endTime = LocalDateTime.parse(body.get("endtime"));

        if (time.isAfter(endTime)) {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "end time is before time");
        }

        if ((body.get("placeId") == null && body.get("coordinates") == null) || (body.get("placeId") == null && body.get("coordinates") == "")) {
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "placeId and coordinates are null");
        }

        if (body.get("placeId") == "") {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "place dont selected");
        }
        if (body.get("placeId") != null) {
            long placeId = Long.parseLong(body.get("placeId"));
            Optional<Place> place1 = placeRepo.findById(placeId);
            if (!place1.isPresent()) {
                return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "Address not found");
            }
            place = place1.get();
        }

        if (body.get("coordinates") != null) {
            coordinates = body.get("coordinates");
        }

        Optional<Organization> organization = organizationRepo.findById(organizationId);

        if (!organization.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "Organization not found");
        }


        String categoriesid = body.get("categoriesid");
        if (categoriesid == "") {
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "category or categories not selected");
        }

        if (categoriesid == null) {
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "categories is null");
        }

        String[] categoryid = categoriesid.split(",");
        Set<Category> categories = new HashSet<>();
        for (String stringCateid : categoryid) {
            Optional<Category> category = categoryRepo.findById(Long.parseLong(stringCateid));
            if (category.isPresent()) {
                categories.add(category.get());
            } else {
                return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "category not found");
            }
        }

        User user = jwtUtil.getUserFromRequest(request, userRepo);

        if (organizationRepo.isUserInOrganization(user.getId(), organizationId)) {
            if (description == null) {
                return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "description is invalid");
            }
            if (name == null) {
                return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "name is invalid");
            }
            if (time == null) {
                return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "time is invalid");
            }

            if (endTime == null) {
                return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "time is invalid");
            }

            Event event = new Event(description, name, time, place, organization.get(), endTime, coordinates);
            event.setCategories(categories);
            eventRepo.save(event);

            return ResponseHelper.successMessage("Event added");
        }

        return ResponseHelper.errorMessage(Error.NO_ACCESS.name(), "user dont have access to save event in this organization");
    }

    /**
     * Metoda přihlásí uživatele na událost
     *
     * @param body    Mapa obsahující informace o ticketu
     * @param request request, zjišťuje se z něho token
     * @param id      ID události, na kterou se uživatel přihlašuje
     * @return vrátí hlášku o úspěšném přihlášení, případně chybovou hlášku, pokud dojde k chybě
     */

    public ResponseEntity<?> attend(Map<String, String> body, long id, HttpServletRequest request) {
        User user = jwtUtil.getUserFromRequest(request, userRepo);
        Optional<Event> event = eventRepo.findById(id);

        if (!event.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "event not found");
        }

        Optional<Long> ticketid = ticketRepo.findTicketIdByUserIdAndEventId(user.getId(), id);

        if (ticketid.isPresent()) {
            return ResponseHelper.errorMessage(Error.NO_ACCESS.name(), "User already have ticket on this event");
        }

        String state = body.get("state");

        if (state == null) {
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "state is null");
        }

        Ticket ticket = new Ticket(event.get(), user, state, LocalDateTime.now());
        ticketRepo.save(ticket);

        return ResponseHelper.successMessage("attended to event");
    }

    /**
     * Metoda smaže event z databáze. Event může smazat jen osoba, která je v organizaci, která event
     * pořádá
     *
     * @param id      ID události, která se má smazat
     * @param request request, zjišťuje se z něho token
     * @return vrátí hlášku o úspěšném smazání, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<?> delete(long id, HttpServletRequest request) {
        Optional<Event> event = eventRepo.findById(id);

        if (!event.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "event not found");
        }

        Organization organization = event.get().getOrganization();

        User user = jwtUtil.getUserFromRequest(request, userRepo);

        if (organizationRepo.isUserInOrganization(user.getId(), organization.getId())) {
            eventRepo.deleteById(id);
            return ResponseHelper.successMessage("event deleted");

        }

        return ResponseHelper.errorMessage(Error.NO_ACCESS.name(), "user dont have access to delete event");
    }

    /**
     * Metoda vrátí informace o všech událostech
     *
     * @return seznam pohledů EventView
     */
    public List<EventView> getEvents() {
        List<EventView> eventViews = eventViewRepo.findAll();
        for (EventView eventView : eventViews) {
            eventView.setCategoryList(eventRepo.findById(eventView.getId()).get().getCategories().stream().toList());
        }
        return eventViews;
    }

    /**
     * Metoda vyhledá událost podle jejího ID
     *
     * @param id ID hledané události
     * @return vrátí objekt události, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<Event> getEventById(long id) {
        Optional<Event> event = eventRepo.findById(id);
        if (!event.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(event.get());
    }

    /**
     * Metoda zruší účast uživatele na události
     *
     * @param id      ID události, ze které se má uživatel odhlásit
     * @param request request, zjišťuje se z něho token
     * @return vrátí hlášku o úspěšném odhlášení, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<?> cancelAttend(long id, HttpServletRequest request) {
        User user = jwtUtil.getUserFromRequest(request, userRepo);

        Optional<Long> ticketid = ticketRepo.findTicketIdByUserIdAndEventId(user.getId(), id);
        if (ticketid.isPresent()) {
            ticketRepo.deleteById(ticketid.get());
            return ResponseHelper.successMessage("ticket canceled");
        } else {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "ticket not found");
        }
    }

    /**
     * Metoda tvoří custom query pro vyhledávání v databázi.
     *
     * @param region   kraj
     * @param destrict okres
     * @param time     čas začátku
     * @param city     město konání
     * @return vrací seznam pohledů EventView, které odpovídají vyhledávacím parametrům
     */
    public ResponseEntity<?> findEventByParameters(Optional<String> region, Optional<String> destrict, Optional<LocalDateTime> time, Optional<String> city, Optional<String> categories) {
        String query = "SELECT * FROM event_information WHERE";
        int count = 0;
        boolean queryState = false;

        if (region.isPresent()) {
            if (count == 0) {
                query += " event_information.region = " + "'" + region.get() + "'";
                count++;

            } else {
                query += " AND event_information.region = " + "'" + region.get() + "'";

            }
            queryState = true;
        }

        Timestamp timestamp = null;
        if (time.isPresent()) {
            timestamp = new Timestamp(time.get().toInstant(ZoneOffset.of("+02:00")).toEpochMilli());
            if (count == 0) {
                query += " " + "'" + timestamp + "'" + " <= event_information.time ";
                count++;

            } else {
                query += " AND " + "'" + timestamp + "'" + " <= event_information.time ";

            }
            queryState = true;
        }


        if (destrict.isPresent()) {
            if (count == 0) {
                query += " event_information.destrict = " + "'" + destrict.get() + "'";
                count++;

            } else {
                query += " AND event_information.destrict = " + "'" + destrict.get() + "'";

            }
            queryState = true;
        }

        if (city.isPresent()) {
            if (count == 0) {
                query += " event_information.city = " + "'" + city.get() + "'";
                count++;

            } else {
                query += " AND event_information.city = " + "'" + city.get() + "'";
            }
            queryState = true;
        }

        List<Category> categoriesList = new ArrayList<>();
        ;
        if (queryState == false) {
            query = "SELECT * FROM event_information";
        }

        if (categories.isPresent()) {
            String[] ids = categories.get().split(",");
            for (String idS : ids) {
                long id = Integer.parseInt(idS);
                Optional<Category> category = categoryRepo.findById(id);
                if (category.isPresent()) {
                    categoriesList.add(category.get());
                } else {
                    return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "category not found");
                }
            }
        }


        query += ";";
        System.out.println(query);
        List<EventView> eventView = customQueryEvent.findEventByParameters(query);

        List<EventView> finalEventView = new ArrayList<>();
        for (EventView eventView1 : eventView) {
            Optional<Event> event = eventRepo.findById(eventView1.getId());
            if (categories.isPresent()) {
                for (Category category : categoriesList) {
                    if (eventRepo.iseventscatagory(category.getId(), eventView1.getId())) {
                        Set<Category> eventCategory = event.get().getCategories();
                        eventView1.setCategoryList(eventCategory.stream().toList());
                        finalEventView.add(eventView1);
                        break;
                    }
                }
            } else {
                Set<Category> eventCategory = event.get().getCategories();
                eventView1.setCategoryList(eventCategory.stream().toList());
                finalEventView.add(eventView1);
            }
        }
        eventView = finalEventView;

        return ResponseEntity.ok(eventView);
    }

    /**
     * Metoda spočítá počet lidí, kteří jsou přihlášení na danou událost
     *
     * @param id ID Eventu, pro který hledáme počet účastníků
     * @return vrátí počet lidí na události, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<Long> getNumberOfPeopleOnEvent(long id) {
        if (eventRepo.existsById(id)) {
            return ResponseEntity.ok((eventRepo.getNumberOfPeopleOnEvent(id)));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Metoda vrátí seznam kategorií, které má daná událost
     *
     * @param id ID události, pro kterou hledáme kategorie
     * @return vrátí seznam objektů Category, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<?> getEventCategory(long id) {
        Optional<Event> event = eventRepo.findById(id);
        if (!event.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "event not found");
        }
        Set<Category> categorySet = event.get().getCategories();
        List<Category> categories = new ArrayList<>();
        categories.addAll(categorySet);
        return ResponseEntity.ok(categories);
    }

    /**
     * Metoda vyhledá události, které mají alespoň určitý počet přihlášených uživatelů
     *
     * @param number Minimální počet uživatelů
     * @return vrátí názvy událostí, které mají alespoň určitý počet přihlášených uživatelů, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<?> getEventByAttendence(String number) {
        if (number == null) {
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "number is null");
        }
        int intNumber = Integer.parseInt(number);
        List<String> eventsname = eventRepo.getEventNameByAttendence(intNumber);
        return ResponseEntity.ok(eventsname);
    }

    /**
     * Metoda vyhledá všechny uživatele přihlášené na událost
     *
     * @param id ID události
     * @return vrátí seznam přihlášených uživatelů, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<?> getUsersAttendedOnEvent(long id) {
        Optional<List<Long>> userid = ticketRepo.findUsersIdByEventId(id);

        if (!userid.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "tickets not found");
        }

        Optional<List<UserView>> users = userViewRepo.findAllById(userid.get());

        return ResponseEntity.ok(users);
    }

    /**
     * Metoda vyhledá všechny události uživatele, na které je přihlášen
     *
     * @param id ID uživatele
     * @return vrátí seznam událostí, nebo chybovou hlášku, pokud dojde k chybě
     */
    public ResponseEntity<?> getEventViewsFromUser(long id) {
        Optional<List<Long>> eventid = ticketRepo.findEventsIdByUserId(id);

        if (!eventid.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "user has 0 tickets");
        }
        Optional<List<EventView>> events = eventViewRepo.findAllById(eventid.get());

        if (!events.isEmpty()) {
            for (EventView e : events.get()) {
                Iterable<Long> iterableList = Arrays.asList(e.getId());
                e.setCategoryList(categoryRepo.findAllById(iterableList));
            }
        }
        return ResponseEntity.ok(events);
    }

}
