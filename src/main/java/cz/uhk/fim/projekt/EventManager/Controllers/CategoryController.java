package cz.uhk.fim.projekt.EventManager.Controllers;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import cz.uhk.fim.projekt.EventManager.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/auth/category")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategory(){
        return categoryService.getCategory();
    }

    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        Category saveCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(saveCategory);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteCategory(@PathVariable long id){
    categoryService.deleteCategory(id);
    }

}
