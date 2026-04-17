package com.batch5.Create_Task_Application.taskModule.service;

import com.batch5.Create_Task_Application.taskModule.entity.Category;
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
        return categoryRepository.save(category);
    }
    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

}
