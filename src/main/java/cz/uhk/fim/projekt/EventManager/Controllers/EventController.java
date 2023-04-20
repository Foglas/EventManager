package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.*;
import cz.uhk.fim.projekt.EventManager.service.CommentService;
import cz.uhk.fim.projekt.EventManager.service.EventService;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping(value = "/auth/event/organization/{id}/save")
    public ResponseEntity<?> save(@RequestBody Map<String,String> body,
                                  @PathVariable("id") long organizationId, HttpServletRequest request) {
       return eventService.save(request,body, organizationId);
    }


    @GetMapping(value = "/events")
    public List<EventView> getEvents(){
        List<EventView> eventViews = eventService.getEvents();
      return eventViews;
    }
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") long id){
        return eventService.getEventById(id);
    }


    @DeleteMapping("/auth/event/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") long id, HttpServletRequest request){
      return   eventService.delete(id, request);
    }



    @PostMapping("auth/event/{id}/attend")
    public ResponseEntity<?> attend(@RequestBody Map<String, String> body, @PathVariable("id") long id,  HttpServletRequest httpServletRequest) {
        return eventService.attend(body,id,httpServletRequest);
    }

    @DeleteMapping("auth/event/{id}/cancelAttend")
    public ResponseEntity<?> cancelAttend(@PathVariable("id") long id, HttpServletRequest request){
     return eventService.cancelAttend(id, request);
    }

    @GetMapping("/auth/event/{id}/users")
    public ResponseEntity<?> getUserAttendedOnEvent(@PathVariable("id") long id){
        return eventService.getUserAttendedOnEvent(id);
    }


    @PostMapping("/auth/event/{id}/comment/save")
    public ResponseEntity<?> save(HttpServletRequest request,@RequestBody Comment comment, @PathVariable("id") long id){
        return commentService.save(request,comment, id);
    }

    @GetMapping("/events/search")
    public ResponseEntity<?> findEventByParameters(@RequestParam(name = "region", required = false) Optional<String> region,
                                                 @RequestParam(name = "destrict", required = false) Optional<String> destrict,
                                                 @RequestParam(name = "time", required = false) Optional<LocalDateTime> time,
                                                 @RequestParam(name = "city", required = false) Optional<String> city,
                                                 @RequestParam(name = "categories", required = false) Optional<String> categories){
    return eventService.findEventByParameters(region, destrict, time, city, categories);
    }
/*
    @GetMapping("/events/{id}/category")
    public List<Category> findEventsCategory(){

    }


 */
    @GetMapping("event/{id}/peoples")
    public ResponseEntity<Long> getNumberOfPeopleOnEvent(@PathVariable("id") long id){
        return eventService.getNumberOfPeopleOnEvent(id);
    }


    @GetMapping("/event/{id}/category")
    public ResponseEntity<?> getEventCategory(@PathVariable("id") long id){
        return eventService.getEventCategory(id);
    }


    @GetMapping("events/attendence")
    public ResponseEntity<?> getEventByAttendence(@RequestParam("number") String number){
        return eventService.getEventByAttendence(number);
    }
    @GetMapping("/events/{id}/getAttendedEvents")
    public ResponseEntity<?> findAllEventViews(@PathVariable("id") long id) {
        return eventService.getEventViewsFromUser(id);
    }
}
