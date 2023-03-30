package cz.uhk.fim.projekt.EventManager.service.serviceinf;

import cz.uhk.fim.projekt.EventManager.Domain.Comment;

import java.util.List;

public interface CommentSerInf {
    void addComment();
    void deleteComment();
    List<Comment> findAllComments();
}
