package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Comment;
import cz.uhk.fim.projekt.EventManager.Domain.Event;
import cz.uhk.fim.projekt.EventManager.Domain.User;
import cz.uhk.fim.projekt.EventManager.dao.CommentRepo;
import cz.uhk.fim.projekt.EventManager.dao.EventRepo;
import cz.uhk.fim.projekt.EventManager.dao.UserRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.util.JwtUtil;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
/**
 * Service třída pro obsluhu requestů týkajících se komentářů
 */
public class CommentService {

    private CommentRepo commentRepo;
    private JwtUtil jwtUtil;

    private UserRepo userRepo;
    private EventRepo eventRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo, JwtUtil jwtUtil, UserRepo userRepo, EventRepo eventRepo) {
        this.commentRepo = commentRepo;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    /**
     * Uloži komentář, v případě špatně zadanách argumentů vrací chybovou hlášku, v případě úspěchu vrátí oznámení o uložení
     * @param request request, zjišťuje se z něho token
     * @param comment komentář k uložení
     * @param id id eventu, ke kterému komentář patří
     * @return Hláška o zvládnutí operace v případě úspěchu, chyba v případě neúspěchu
     */
    public ResponseEntity<?> save(HttpServletRequest request, Comment comment, long id){

        if (comment.getComment() == null){
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "Comment is null");
        }
        Optional<Event> event = eventRepo.findById(id);

        if (!event.isPresent()){
            return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "event not found");
        }

        User user = jwtUtil.getUserFromRequest(request, userRepo);
        comment.setUser(user);
        comment.setEvent(event.get());
        comment.setCreatedAt(LocalDateTime.now());
        commentRepo.save(comment);
        return ResponseHelper.successMessage("Comment added");
    }

}
