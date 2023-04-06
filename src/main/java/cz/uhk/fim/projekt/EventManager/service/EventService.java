package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.*;
import cz.uhk.fim.projekt.EventManager.dao.*;
import cz.uhk.fim.projekt.EventManager.dao.readOnlyRepo.EventViewRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.service.serviceinf.EventSerInf;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.lang.model.type.ErrorType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Autowired
    public EventService(EventRepo eventRepo, UserRepo userRepo, JwtUtil jwtUtil, PlaceRepo placeRepo, OrganizationRepo organizationRepo, TicketRepo ticketRepo, EventViewRepo eventViewRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.organizationRepo = organizationRepo;
        this.placeRepo = placeRepo;
        this.ticketRepo = ticketRepo;
        this.eventViewRepo = eventViewRepo;
    }

    public ResponseEntity<?> save(HttpServletRequest request, String description, String name, LocalDateTime time,LocalDateTime endTime , long placeId, long organizationId) {
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

/*
        Set<User> userSet = organization.get().getUsers();
        List<User> users = new ArrayList<>();

        users.addAll(userSet);


        for (User eachUser : users) {
            if (eachUser.getId() == user.getId()) {
                if (description == null) {
                    return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "description is invalid");
                }
                if (name == null) {
                    return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "name is invalid");
                }
                if (time == null) {
                    return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "time is invalid");
                }


                Event event = new Event(description, name, time, place.get(), organization.get());
                eventRepo.save(event);

                return ResponseHelper.successMessage("Event added");
                }
        }

 */
        return ResponseHelper.errorMessage(Error.NO_ACCESS.name(), "user dont have access to save event in this organization");
    }

    public ResponseEntity<?> attend(Map<String, String> body, long id, HttpServletRequest request) {
        User user = jwtUtil.getUserFromRequest(request, userRepo);
        Optional<Event> event = eventRepo.findById(id);
        if (!event.isPresent()) {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "event not found");
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
}
