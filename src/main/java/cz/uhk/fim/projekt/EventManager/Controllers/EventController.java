package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Event;
import cz.uhk.fim.projekt.EventManager.Domain.Place;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.service.EventService;
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

@Controller
@RequestMapping("/api")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/auth/organization/{id}/event/save")
    public ResponseEntity<?> save(@RequestBody Map<String,String> body,
                                  @PathVariable("id") long organizationId) {
       return eventService.save(body.get("description"), body.get("name"), LocalDateTime.parse(body.get("time")),Long.parseLong(body.get("placeId")),organizationId);
    }

    @DeleteMapping("/auth/event/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") long id, HttpServletRequest request){
      return   eventService.delete(id, request);
    }



    @PostMapping("auth/event/{id}/attend")
    public ResponseEntity<?> attend(@RequestBody Map<String, String> body, @PathVariable("id") long id,  HttpServletRequest httpServletRequest) {
        return eventService.attend(body,id,httpServletRequest);
    }
}
