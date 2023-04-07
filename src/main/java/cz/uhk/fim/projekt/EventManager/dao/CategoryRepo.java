package cz.uhk.fim.projekt.EventManager.dao;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {


}
