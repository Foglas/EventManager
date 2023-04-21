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

        String categoriesid = body.get("categoriesid");
        if (categoriesid == null){
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "categories is null");
        }

        String[] categoryid = categoriesid.split(",");
        Set<Category> categories = new HashSet<>();
        for (String stringCateid : categoryid ){
            Optional<Category> category = categoryRepo.findById(Long.parseLong(stringCateid));
            if (category.isPresent()){
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

            Event event = new Event(description, name, time, place.get(), organization.get(), endTime);
            event.setCategories(categories);
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

        Optional<Long> ticketid = ticketRepo.findTicketIdByUserIdAndEventId(user.getId(), id);

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
        for (EventView eventView: eventViews) {
        eventView.setCategoryList(eventRepo.findById(eventView.getId()).get().getCategories().stream().toList());
        }
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

        Optional<Long> ticketid = ticketRepo.findTicketIdByUserIdAndEventId(user.getId(), id);
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
    public ResponseEntity<?> findEventByParameters(Optional<String> region, Optional<String> destrict, Optional<LocalDateTime> time, Optional<String> city, Optional<String> categories) {
        String query = "SELECT * FROM event_information WHERE";
        int count = 0;

        boolean queryState = false;


        if (region.isPresent()){
            if (count==0){
                query += " event_information.region = " + "'" + region.get() + "'";
                count++;

            }else {
                query += " AND event_information.region = " + "'" + region.get() + "'";

            }
            queryState = true;
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
            queryState = true;
        }



        if (destrict.isPresent()){
            if (count==0){
                query += " event_information.destrict = " + "'" + destrict.get() + "'";
                count++;

            }else {
                query += " AND event_information.destrict = " + "'" + destrict.get() + "'";

            }
            queryState = true;
        }

        if (city.isPresent()){
            if (count==0){
                query += " event_information.city = " + "'" + city.get() + "'";
                count++;

            }else {
                query += " AND event_information.city = " + "'" + city.get() + "'";
            }
            queryState = true;
        }

        List<Category> categoriesList = new ArrayList<>();;
        if (queryState == false){
            query = "SELECT * FROM event_information";
        }

        if (categories.isPresent()){
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

    public ResponseEntity<Long> getNumberOfPeopleOnEvent(long id) {
        if (eventRepo.existsById(id)) {
            return ResponseEntity.ok((eventRepo.getNumberOfPeopleOnEvent(id)));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    public ResponseEntity<?> getEventCategory(long id) {
        Optional<Event> event = eventRepo.findById(id);
        if (!event.isPresent()){
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "event not found");
        }
        Set<Category> categorySet = event.get().getCategories();
        List<Category> categories = new ArrayList<>();
        categories.addAll(categorySet);
        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<?> getEventByAttendence(String number) {
        if (number == null){
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "number is null");
        }
        int intNumber = Integer.parseInt(number);
        List< String> eventsname = eventRepo.getEventNameByAttendence(intNumber);
        return ResponseEntity.ok(eventsname);
    }

    public ResponseEntity<?> getUserAttendedOnEvent(long id) {
        Optional<List<Long>> userid = ticketRepo.findUsersIdByEventId(id);

        if (!userid.isPresent()){
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "ticket not found");
        }

        Optional<List<UserView>> users = userViewRepo.findAllById(userid.get());

        return ResponseEntity.ok(users);
    }

}
