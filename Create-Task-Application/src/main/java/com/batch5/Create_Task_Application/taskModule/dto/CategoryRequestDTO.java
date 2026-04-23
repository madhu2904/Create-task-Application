package com.batch5.Create_Task_Application.taskModule.dto;
public class CategoryRequestDTO {
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(String categoryName) {
        this.categoryName = categoryName;
    }
}
