package com.batch5.Create_Task_Application.TaskModule.repository;

import com.batch5.Create_Task_Application.TaskModule.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
