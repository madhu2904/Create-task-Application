package com.batch5.Create_Task_Application.taskModule.dto;

import java.util.List;

public class CategoryResponseDTO {
    private Integer categoryId;
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryResponseDTO() {
    }

    public CategoryResponseDTO(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
