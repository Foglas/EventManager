package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.*;
import cz.uhk.fim.projekt.EventManager.dao.*;
import cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo.EventViewRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EventService {

    private EventRepo eventRepo;
    private UserRepo userRepo;

    private PlaceRepo placeRepo;

    private OrganizationRepo organizationRepo;

    private TicketRepo ticketRepo;
    private EventViewRepo eventViewRepo;
    private JwtUtil jwtUtil;

    private CustomQueryEvent customQueryEvent;
    @Autowired
    public EventService(EventRepo eventRepo, UserRepo userRepo, JwtUtil jwtUtil, PlaceRepo placeRepo, OrganizationRepo organizationRepo, TicketRepo ticketRepo, CustomQueryEvent customQueryEvent, EventViewRepo eventViewRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.organizationRepo = organizationRepo;
        this.placeRepo = placeRepo;
        this.ticketRepo = ticketRepo;
        this.eventViewRepo = eventViewRepo;
        this.customQueryEvent = customQueryEvent;
    }

    public ResponseEntity<?> save(HttpServletRequest request, Map<String, String> body, long organizationId) {
       String description = body.get("description");
       String name = body.get("name");
       LocalDateTime time = LocalDateTime.parse(body.get("time"));
        LocalDateTime endTime = null;

       if (body.get("endtime") != null) {
        endTime = LocalDateTime.parse(body.get("endtime"));
       }
       long placeId = Long.parseLong(body.get("placeId"));

        Optional<Organization> organization = organizationRepo.findById(organizationId);

        if (!organization.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "Organization not found");
        }

        Optional<Place> place = placeRepo.findById(placeId);
        if (!place.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "Address not found");
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

            Event event = new Event(description, name, time, place.get(), organization.get(), endTime);
            eventRepo.save(event);

            return ResponseHelper.successMessage("Event added");
        }

        return ResponseHelper.errorMessage(Error.NO_ACCESS.name(), "user dont have access to save event in this organization");
    }

    //TODO dodelat aby user nemohl mit dve prihlaseni
    public ResponseEntity<?> attend(Map<String, String> body, long id, HttpServletRequest request) {
        User user = jwtUtil.getUserFromRequest(request, userRepo);
        Optional<Event> event = eventRepo.findById(id);

        if (!event.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "event not found");
        }

        Optional<Long> ticketid = ticketRepo.findTicketIdByUserId(user.getId(), id);

        if (ticketid.isPresent()){
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

    public List<EventView> getEvents() {
        List<EventView> eventViews = eventViewRepo.findAll();
    return eventViews;
    }

    public ResponseEntity<Event> getEventById(long id) {
        Optional<Event> event = eventRepo.findById(id);
        if (!event.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(event.get());
    }


    public ResponseEntity<?> cancelAttend(long id, HttpServletRequest request) {
        User user = jwtUtil.getUserFromRequest(request, userRepo);

        Optional<Long> ticketid = ticketRepo.findTicketIdByUserId(user.getId(), id);
        if (ticketid.isPresent()){
            ticketRepo.deleteById(ticketid.get());
            return ResponseHelper.successMessage("ticket canceled");
        } else {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "ticket not found");
        }
    }

    /**
     * Metoda tvori custom query pro vyhledavani v databazi
     * @param region kraj
     * @param destrict okres
     * @param time cas zacatku
     * @param city mesto konani
     * @return list EventView, ktere odpovida vyhledavacim parametrum
     */
    public List<EventView> findEventByParameters(Optional<String> region, Optional<String> destrict, Optional<LocalDateTime> time, Optional<String> city) {
        String query = "SELECT * FROM event_information WHERE";
        int count = 0;


        if (region.isPresent()){
            if (count==0){
                query += " event_information.region = " + "'" + region.get() + "'";
                count++;

            }else {
                query += " AND event_information.region = " + "'" + region.get() + "'";

            }
        }

        Timestamp timestamp = null;
        if (time.isPresent()){
            timestamp =new Timestamp(time.get().toInstant(ZoneOffset.UTC).toEpochMilli());
            if (count==0){
                query += " " + "'"+ timestamp + "'"+ " <= event_information.time ";
                count++;

            }else {
                query += " AND "  + "'"+timestamp  + "'"+  " <= event_information.time ";

            }
        }


        if (destrict.isPresent()){
            if (count==0){
                query += " event_information.destrict = " + "'" + destrict.get() + "'";
                count++;

            }else {
                query += " AND event_information.destrict = " + "'" + destrict.get() + "'";

            }
        }

        if (city.isPresent()){
            if (count==0){
                query += " event_information.city = " + "'" + city.get() + "'";
                count++;

            }else {
                query += " AND event_information.city = " + "'" + city.get() + "'";

            }
        }

        query+= ";";
        System.out.println(query);
        List<EventView> eventView = customQueryEvent.findEventByParameters(query);
        return eventView;
    }

    public ResponseEntity<Long> getNumberOfPeopleOnEvent(long id) {
        if (eventRepo.existsById(id)) {
            return ResponseEntity.ok((eventRepo.getNumberOfPeopleOnEvent(id)));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
}
