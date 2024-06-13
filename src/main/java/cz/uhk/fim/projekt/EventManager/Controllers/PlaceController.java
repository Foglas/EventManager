package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Place;
import cz.uhk.fim.projekt.EventManager.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  Třída obsahující metody na příjímaní požadavků na url týkajících se akcí ohledně místa.
 */
@RestController
@RequestMapping("/api")
public class PlaceController {

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }


    /**
     * Přijme dotaz na url /api/places. Slouží pro získání všech míst, která jsou v databázi.
     * @return http status a list míst
     */
    @GetMapping("/places")
    public ResponseEntity<?> getAllPlaces(){
    return placeService.getAllPlaces();
    }

    @GetMapping("/cities")
    public ResponseEntity<?> getAllCities(){
        return placeService.getAllCities();
    }

    @GetMapping("/destricts")
    public ResponseEntity<?> getAlldestricts(){
        return placeService.getAllDestricts();
    }

    @GetMapping("/regions")
    public ResponseEntity<?> getAllRegions(){
        return placeService.getAllRegions();
    }

    /**
     * Přijme dotaz na url /api/auth/admin/place/save. Slouží pro uložení nových míst do databáze.
     * Vyžaduje autorizaci a roli admina. Informace se předávají v body requestu.
     * @return http status a message
     */
    @PostMapping("/auth/admin/place/save")
    public ResponseEntity<?> save(@RequestBody Place place){
       return placeService.save(place);
    }
}
