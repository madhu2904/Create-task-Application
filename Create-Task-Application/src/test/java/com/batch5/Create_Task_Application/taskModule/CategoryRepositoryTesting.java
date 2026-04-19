package com.batch5.Create_Task_Application.taskModule;

import com.batch5.Create_Task_Application.taskModule.entity.Category;
import com.batch5.Create_Task_Application.taskModule.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTesting {
    @Autowired
    private CategoryRepository categoryRepository;

    // ========== POSITIVE TEST CASES ==========

    @Test
    @DisplayName("Save Category - Success")
    void testSaveCategory() {
        Category category = new Category();
        category.setCategoryName("Work");

        Category saved = categoryRepository.save(category);

        assertNotNull(saved.getCategoryId());
    }

    @Test
    @DisplayName("Find Category By ID - Success")
    void testFindById() {
        Category category = new Category();
        category.setCategoryName("Test");
        categoryRepository.save(category);

        Optional<Category> found = categoryRepository.findById(category.getCategoryId());

        assertTrue(found.isPresent());
    }

    @Test
    @DisplayName("Find All Categories - Success")
    void testFindAll() {
        Category c1 = new Category();
        c1.setCategoryName("Work");

        Category c2 = new Category();
        c2.setCategoryName("Personal");

        categoryRepository.save(c1);
        categoryRepository.save(c2);

        List<Category> list = categoryRepository.findAll();

        assertTrue(list.size() >= 2);
    }

    // ========== NEGATIVE TEST CASES ==========

    @Test
    @DisplayName("Find Category By Invalid ID - Fail")
    void testFindInvalidId() {
        Optional<Category> category = categoryRepository.findById(999);

        assertFalse(category.isPresent());
    }

    @Test
    @DisplayName("Delete Non-existing Category - Fail")
    void testDeleteInvalidCategory() {
        assertDoesNotThrow(() -> categoryRepository.deleteById(999));
    }
}
