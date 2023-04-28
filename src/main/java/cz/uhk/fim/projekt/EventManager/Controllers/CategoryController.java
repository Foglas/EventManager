package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import cz.uhk.fim.projekt.EventManager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  Třída obsahující metody na příjímaní požadavků na url týkajících se akcí ohledně kategorií.
 */
@RestController
@RequestMapping(value = "/api")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    /**
     * Přijme dotaz na url /api/category
     * @return vrátí všechny kategorie v databázi
     */
    @GetMapping("/category")
    public List<Category> getCategory(){
        return categoryService.getCategory();
    }

    /**
     * Přijme dotaz na url /api/category a namapuje parametry z body na objekt Category a objekt
     * pošle dále na zpracování do service vrstvy. Slouží pro uložení kategorie adminem
     * @return vrátí status a message frontendu
     */
    @PostMapping( "auth/admin/category/save")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
       return categoryService.saveCategory(category);
    }

    /**
     * Přijme dotaz na url /api/auth/admin/category/{id}/delete a pošle id obsažené v url
     * dále na service vrstvu. Slouží pro odstranění kategorie adminem
     * @return vrátí status a message frontendu
     */
    @DeleteMapping(value = "auth/admin/category/{id}/delete")
    public ResponseEntity<?> deleteCategory(@PathVariable long id){
        return categoryService.deleteCategory(id);
    }
}
