package com.batch5.Create_Task_Application.taskModule.service;

import com.batch5.Create_Task_Application.taskModule.entity.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    List<Category> getAllCategories();
}
