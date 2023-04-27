package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import cz.uhk.fim.projekt.EventManager.dao.CategoryRepo;
import cz.uhk.fim.projekt.EventManager.enums.Error;
import cz.uhk.fim.projekt.EventManager.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Třídá starající se o obsluhu requestů týkajících se kategorií
 */
@Service
public class CategoryService {

    CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo){
        this.categoryRepo = categoryRepo;
    }


    /**
     * @return vrátí všechny kategorie v databázi
     */
    public List<Category> getCategory(){
        List<Category> categoryList = categoryRepo.findAll();
        return categoryList;
    }

    /**
     * Ukládá categorii do databáze a kontroluje proměnné
     * @param category kategorie k uložení
     * @return vrací uspěšnost
     */
    public ResponseEntity<?> saveCategory(Category category){
        if(category.getDescription() == null){
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "description null");
        }
        if (category.getName() == null){
            return ResponseHelper.errorMessage(Error.NULL_ARGUMENT.name(), "category null");
        }
        categoryRepo.save(category);
        return ResponseHelper.successMessage("category added");
    }

    /**
     * Vymaže kategorii v databází a kontroluje proměnné
     * @param id id categorie
     * @return vrací úspěšnost
     */
    public ResponseEntity<?> deleteCategory(long id){
        if (!categoryRepo.existsById(id)){
         return ResponseHelper.errorMessage(Error.NOT_FOUND.name(), "category not found");
        }
        categoryRepo.deleteById(id);
        return ResponseHelper.successMessage("Category deleted");
    }
}
