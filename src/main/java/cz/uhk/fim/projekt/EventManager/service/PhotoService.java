package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.dao.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private PhotoRepo photoRepo;

    @Autowired
    public PhotoService(PhotoRepo photoRepo) {
        this.photoRepo = photoRepo;
    }


}
