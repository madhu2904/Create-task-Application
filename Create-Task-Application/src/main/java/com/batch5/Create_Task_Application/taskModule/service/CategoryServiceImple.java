package com.batch5.Create_Task_Application.taskModule.service;

import com.batch5.Create_Task_Application.taskModule.entity.Category;
import com.batch5.Create_Task_Application.taskModule.exceptions.DataConflictException;
import com.batch5.Create_Task_Application.taskModule.exceptions.InvalidCategoryException;
import com.batch5.Create_Task_Application.taskModule.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImple implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category){
        if (category == null) {
            throw new InvalidCategoryException("Category cannot be null");
        }

        if (category.getCategoryName() == null || category.getCategoryName().isBlank()) {
            throw new InvalidCategoryException("Category name cannot be empty");
        }

        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new DataConflictException("Category already exists with name: " + category.getCategoryName());
        }
        return categoryRepository.save(category);
    }
    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

}
