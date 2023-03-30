package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import cz.uhk.fim.projekt.EventManager.Domain.Organization;
import cz.uhk.fim.projekt.EventManager.Domain.Place;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.EventRepo;
import cz.uhk.fim.projekt.EventManager.dao.OrganizationRepo;
import cz.uhk.fim.projekt.EventManager.dao.PlaceRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.service.serviceinf.EventSerInf;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService {

    private EventRepo eventRepo;
    private UserRepo userRepo;

    private PlaceRepo placeRepo;

    private OrganizationRepo organizationRepo;
    private JwtUtil jwtUtil;

    @Autowired
    public EventService(EventRepo eventRepo, UserRepo userRepo, JwtUtil jwtUtil, PlaceRepo placeRepo, OrganizationRepo organizationRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.organizationRepo = organizationRepo;
        this.placeRepo = placeRepo;
    }

    public ResponseEntity<?> save(String description, String name, LocalDateTime time, long placeId, long organizationId) {
        ;
        Optional<Place> place = placeRepo.findById(placeId);
        Optional<Organization> organization = organizationRepo.findById(organizationId);


        if (description == null) {
            return ResponseHelper.errorMessage("null argument", "description is invalid");
        }
        if (name == null) {
            return ResponseHelper.errorMessage("null argument", "name is invalid");
        }
        if (time == null) {
            return ResponseHelper.errorMessage("null argument", "time is invalid");
        }

        if (!place.isPresent()) {
            return ResponseHelper.errorMessage("Not found", "Address not found");
        }
        if (!organization.isPresent()) {
            return ResponseHelper.errorMessage("Not found", "Organization not found");
        }


        Event event = new Event(description, name, time, place.get(), organization.get());
        eventRepo.save(event);

        return ResponseHelper.successMessage("Event added");
    }

    //TODO dodelat
    public ResponseEntity<?> attend(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");

        String email = jwtUtil.getEmailFromToken(token);
        User user = userRepo.findUserByEmailIgnoreCase(email);
        return ResponseHelper.successMessage(user.getUsername());
    }

    public ResponseEntity<?> delete(long id, HttpServletRequest request) {
        Optional<Event> event = eventRepo.findById(id);

        if (!event.isPresent()) {
            return ResponseHelper.errorMessage("event null", "event not found");
        }

        Organization organization = event.get().getOrganization();
        System.out.println(organization);

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        if (token == null) {
            return ResponseHelper.errorMessage("no token", "no token");
        }

        String email = jwtUtil.getEmailFromToken(token);

        User user = userRepo.findUserByEmailIgnoreCase(email);

        Set<User> userSet = organization.getUsers();
        List<User> users = new ArrayList<>();

        users.addAll(userSet);

        for (User eachUser : users) {
            if (eachUser.getId() == user.getId()) {
                eventRepo.deleteById(id);
                return ResponseHelper.successMessage("event deleted");
            }
        }
        return ResponseHelper.errorMessage("no access", "user dont have access to delete event");


    }
}
