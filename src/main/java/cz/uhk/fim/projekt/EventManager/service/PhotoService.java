package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Photo;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.PhotoRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Třída poskytuje metody pro obsluhu požadavků týkajících se fotky - tabulka Photo
 */
@Service
public class PhotoService {

    private PhotoRepo photoRepo;
    private UserRepo userRepo;
    private JwtUtil jwtUtil;

    @Autowired
    public PhotoService(PhotoRepo photoRepo, JwtUtil jwtUtil, UserRepo userRepo) {
        this.photoRepo = photoRepo;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }
    /**
     * Metoda vrátí fotku podle requestu
     * @param id id uživatele, jehož fotka má být vytažena
     * @return vrací objekt Photo, nebo chybovou hlášku pokud dojde k chybě
     */
    public ResponseEntity<?> getPhoto(long id){
        Optional<Long> photoid = photoRepo.findByUserId(id);
        Optional<Photo> photo;

        if (photoid.isPresent()){
            photo = photoRepo.findById(photoid.get());
            return ResponseEntity.ok(photo.get());
        } else {
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "photo not found");
        }
    }
    /**
     * Metoda slouží k nahrání fotky uživatele
     * @param request request, zjišťuje se z něho token
     * @param body Objekt obsahující fotku
     * @return vrací hlášku o úspěchu, nebo chybovou hlášku pokud dojde k chybě
     */
    public ResponseEntity<?> uploadPhoto(Map<String, String> body, HttpServletRequest request){
        String photo = body.get("photo");
        if (photo == null){
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "photo is null");
        }
        if (photo == ""){
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "photo is not fill");
        }
        byte[] photoByte = photo.getBytes();

        String suffix = body.get("suffix");
        if (suffix == null){
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "suffix is null");
        }
        if (suffix == ""){
            return ResponseHelper.errorMessage(Error.INVALID_ARGUMENTS.name(), "suffix is not fill");
        }

        LocalDateTime uploadedAt = LocalDateTime.now();

        User user = jwtUtil.getUserFromRequest(request,userRepo);
        Optional<Long> photoId = photoRepo.findByUserId(user.getId());

        if (photoId.isPresent()){
            Optional<Photo> photo1 = photoRepo.findById(photoId.get());
            Photo photo2 = new Photo(photo1.get().getId(),photoByte,suffix,uploadedAt, user);
            photoRepo.save(photo2);
        } else {
            Photo photo2 = new Photo(photoByte, suffix,uploadedAt,user);
            photoRepo.save(photo2);
        }
        return ResponseHelper.successMessage("photo uploaded");
    }


}
