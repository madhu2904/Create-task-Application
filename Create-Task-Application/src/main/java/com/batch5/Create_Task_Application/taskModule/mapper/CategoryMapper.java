package com.batch5.Create_Task_Application.taskModule.mapper;

import com.batch5.Create_Task_Application.taskModule.dto.CategoryResponseDTO;
import com.batch5.Create_Task_Application.taskModule.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryResponseDTO toDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    public static List<CategoryResponseDTO> toDTOList(List<Category> categories) {
        List<CategoryResponseDTO> dtoList = new ArrayList<>();

        for (Category c : categories) {
            dtoList.add(toDTO(c)); // reuse method
        }

        return dtoList;
    }
}
