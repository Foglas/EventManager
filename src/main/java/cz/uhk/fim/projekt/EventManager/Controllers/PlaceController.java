package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Place;
import cz.uhk.fim.projekt.EventManager.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlaceController {

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/places")

    public ResponseEntity<?> getAllPlaces(){
    return placeService.getAllPlaces();
    }
    @PostMapping("/auth/admin/place/save")
    public ResponseEntity<?> save(@RequestBody Place place){
       return placeService.save(place);
    }
}
