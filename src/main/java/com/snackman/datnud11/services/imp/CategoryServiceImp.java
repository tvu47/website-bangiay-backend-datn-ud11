package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.entity.Category;
import com.snackman.datnud11.entity.Materials;
import com.snackman.datnud11.repo.CategoryRepository;
import com.snackman.datnud11.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository repo;

    @Override
    public Category save(Category category) {
        return this.repo.save(category);
    }

    @Override
    public void delete(Category category) {
        this.repo.delete(category);
    }

    @Override
    public void delete(Long id) {
        this.repo.deleteById(id);
    }

    @Override
    public Category findById(Long id) throws Exception {
        Optional<Category> optional = this.repo.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Not found category " + id);
        }
        return optional.get();
    }

    @Override
    public Category findByName(List<Category> categoryList, String name) throws Exception {
        for(Category category : categoryList){
            if(category.getCategoryName().equals(name)){
                return category;
            }
        }
        throw new Exception();
    }

    @Override
    public List<Category> findAll() {
        System.out.println("find all");
        return this.repo.findAll();
    }

    @Override
    public Category findById(List<Category> categoryList, Long id) throws Exception {
        for(Category category : categoryList){
            if(category.getId() == id){
                return category;
            }
        }
        throw new Exception("not found category " + id);
    }
}
