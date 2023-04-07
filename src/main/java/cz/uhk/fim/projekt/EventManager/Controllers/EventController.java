package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Comment;
import cz.uhk.fim.projekt.EventManager.Domain.Event;
import cz.uhk.fim.projekt.EventManager.Domain.Place;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.CommentService;
import cz.uhk.fim.projekt.EventManager.service.EventService;
import cz.uhk.fim.projekt.EventManager.views.EventView;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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


    @PostMapping("/auth/event/{id}/comment/save")
    public ResponseEntity<?> save(HttpServletRequest request,@RequestBody Comment comment, @PathVariable("id") long id){
        return commentService.save(request,comment, id);
    }

}
