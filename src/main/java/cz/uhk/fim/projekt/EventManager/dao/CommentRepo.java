package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface poskytuje metody pro práci s tabulkou comment
 */
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

}
