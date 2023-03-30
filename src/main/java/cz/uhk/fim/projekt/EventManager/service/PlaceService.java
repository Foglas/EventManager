package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Place;
import cz.uhk.fim.projekt.EventManager.dao.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class PlaceService {

    private PlaceRepo placeRepo;

    @Autowired
    public PlaceService(PlaceRepo placeRepo) {
        this.placeRepo = placeRepo;
    }

    public void save(Place place){
        placeRepo.save(place);
    }


}
