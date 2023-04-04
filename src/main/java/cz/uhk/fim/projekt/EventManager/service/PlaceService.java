package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Place;
import cz.uhk.fim.projekt.EventManager.dao.PlaceRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

  private PlaceRepo placeRepo;

  @Autowired
  public PlaceService(PlaceRepo placeRepo) {
    this.placeRepo = placeRepo;
  }

  public ResponseEntity<?> save(Place place) {

    if (place.getCity() == null){
    return   ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "city is null");
    }

    if (place.getDestrict() == null){
      return   ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "destrict is null");
    }

    if (place.getRegion() == null){
      return   ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "region is null");
    }

    if (place.getStreet() == null){
      return   ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "street is null");
    }

    placeRepo.save(place);
   return    ResponseHelper.successMessage("place added");
  }
}
