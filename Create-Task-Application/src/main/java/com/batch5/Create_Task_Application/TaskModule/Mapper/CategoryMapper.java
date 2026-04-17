package com.batch5.Create_Task_Application.TaskModule.Mapper;

import com.batch5.Create_Task_Application.TaskModule.dto.CategoryRequestDTO;
import com.batch5.Create_Task_Application.TaskModule.dto.CategoryResponseDTO;
import com.batch5.Create_Task_Application.TaskModule.entity.Category;

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
