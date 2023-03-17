package cz.uhk.fim.projekt.EventManager.service;

import cz.uhk.fim.projekt.EventManager.Domain.Category;
import cz.uhk.fim.projekt.EventManager.dao.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo){
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getCategory(){
        List<Category> categoryList = categoryRepo.findAll();
        return categoryList;
    }

    public Category saveCategory(Category category){
       Category saveCategory = categoryRepo.save(category);
        return saveCategory;
    }

    public void deleteCategory(long id){
       categoryRepo.deleteById(id);
    }
}
