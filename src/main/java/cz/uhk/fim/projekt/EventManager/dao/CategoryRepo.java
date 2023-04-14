package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {


    @Query(value = "SELECT pk_category FROM eventcategory WHERE eventcategory.fk_eventid = ?1", nativeQuery = true)
    Optional <List<Long>> getcategoriesfromevent(long eventid);

}
