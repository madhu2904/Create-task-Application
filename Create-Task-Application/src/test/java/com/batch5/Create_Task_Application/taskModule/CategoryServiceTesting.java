package com.batch5.Create_Task_Application.taskModule;

import com.batch5.Create_Task_Application.taskModule.entity.Category;
import com.batch5.Create_Task_Application.taskModule.exceptions.DataConflictException;
import com.batch5.Create_Task_Application.taskModule.exceptions.InvalidCategoryException;
import com.batch5.Create_Task_Application.taskModule.repository.CategoryRepository;
import com.batch5.Create_Task_Application.taskModule.service.CategoryServiceImple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImple categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setup() {
        category = new Category();
        category.setCategoryName("Work");
    }

    // ===== POSITIVE =====

    @Test
    void saveCategory_success() {
        when(categoryRepository.save(any())).thenReturn(category);
        assertNotNull(categoryService.saveCategory(category));
    }

    @Test
    void getAllCategories_success() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        assertFalse(categoryService.getAllCategories().isEmpty());
    }

    @Test
    void saveCategory_validName() {
        when(categoryRepository.save(any())).thenReturn(category);
        assertEquals("Work", categoryService.saveCategory(category).getCategoryName());
    }

    @Test
    void multipleCategories_success() {
        when(categoryRepository.findAll()).thenReturn(List.of(category, new Category()));
        assertTrue(categoryService.getAllCategories().size() > 1);
    }

    @Test
    void saveCategory_notNull() {
        when(categoryRepository.save(any())).thenReturn(category);
        assertNotNull(categoryService.saveCategory(category));
    }

    // ===== NEGATIVE =====

    @Test
    void saveCategory_nullCategory() {
        assertThrows(InvalidCategoryException.class,
                () -> categoryService.saveCategory(null));
    }

    @Test
    void saveCategory_emptyName() {
        category.setCategoryName("");
        assertThrows(InvalidCategoryException.class,
                () -> categoryService.saveCategory(category));
    }

    @Test
    void getAllCategories_empty() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(categoryService.getAllCategories().isEmpty());
    }

    @Test
    void saveCategory_duplicate() {
        when(categoryRepository.existsByCategoryName("Work")).thenReturn(true);

        assertThrows(DataConflictException.class,
                () -> categoryService.saveCategory(category));
    }

    @Test
    void saveCategory_nullName() {
        category.setCategoryName(null);
        assertThrows(InvalidCategoryException.class,
                () -> categoryService.saveCategory(category));
    }
}
