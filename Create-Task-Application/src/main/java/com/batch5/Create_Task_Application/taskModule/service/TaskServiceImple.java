package com.batch5.Create_Task_Application.taskModule.service;

import com.batch5.Create_Task_Application.projectModule.entity.Project;
import com.batch5.Create_Task_Application.projectModule.exceptions.ProjectNotFoundException;
import com.batch5.Create_Task_Application.projectModule.repository.ProjectRepository;
import com.batch5.Create_Task_Application.taskModule.exceptions.*;
import com.batch5.Create_Task_Application.taskModule.mapper.CategoryMapper;
import com.batch5.Create_Task_Application.taskModule.mapper.TaskMapper;
import com.batch5.Create_Task_Application.taskModule.dto.CategoryResponseDTO;
import com.batch5.Create_Task_Application.taskModule.dto.TaskRequestDTO;
import com.batch5.Create_Task_Application.taskModule.dto.TaskResponseDTO;
import com.batch5.Create_Task_Application.taskModule.entity.Category;
import com.batch5.Create_Task_Application.taskModule.entity.Task;
import com.batch5.Create_Task_Application.taskModule.repository.CategoryRepository;
import com.batch5.Create_Task_Application.taskModule.repository.TaskRepository;
import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.exceptions.UserNotFoundException;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImple implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Task saveTask(TaskRequestDTO dto) {

        if (dto.getTaskName() == null || dto.getTaskName().isBlank()) {
            throw new InvalidTaskException("Task name cannot be empty");
        }

        if (dto.getUserId() == null) {
            throw new InvalidTaskException("User ID is required");
        }

        if (dto.getProjectId() == null) {
            throw new InvalidTaskException("Project ID is required");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + dto.getUserId()
                ));

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project not found with id: " + dto.getProjectId()
                ));
        Task task = new Task();
        task.setTaskName(dto.getTaskName());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());
        task.setDueDate(dto.getDueDate());
        task.setUser(user);
        task.setProject(project);
        Set<Category> categories = new HashSet<>(
                categoryRepository.findAllById(dto.getCategoryIds())
        );

        task.setCategories(categories);
        return taskRepository.save(task);
    }

    @Override
    public TaskResponseDTO getTask(Integer taskId){
        Task task = taskRepository.findByTaskId(taskId)
                .orElseThrow(()-> new TaskNotFoundException("Task assigned is " +
                        "not found"+" "+taskId));
        return TaskMapper.toDTO(task);
    }
    @Override
    public List<TaskResponseDTO> getAllTask(){
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponseDTO> dtoList = new ArrayList<>();
        for(Task task : tasks){
            dtoList.add(TaskMapper.toDTO(task));
        }
        if (tasks.isEmpty()) {
            throw new NoDataFoundException("No tasks available");
        }
        return dtoList;
    }

    @Override
    public void deleteTask(Integer taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->new TaskNotFoundException("Task not found"+taskId));
        Set<Category> categories = new HashSet<>(task.getCategories());
        for (Category category : categories) {
            category.getTasks().remove(task);
        }
        task.getCategories().clear();
        taskRepository.delete(task);
    }

    @Override
    public TaskResponseDTO updateTask(Integer taskId, TaskRequestDTO dto){
        Task task = taskRepository.findById(taskId).orElseThrow(()->new TaskNotFoundException("Task not found"+taskId));
        if (dto == null) {
            throw new InvalidTaskException("Update data cannot be null");
        }
        if (dto.getTaskName() == null ||
                dto.getDescription() == null ||
                dto.getPriority() == null ||
                dto.getStatus() == null ||
                dto.getDueDate() == null) {

            throw new InvalidTaskException("At least one field must be provided for update");
        }
        Set<Category> categories = new HashSet<>(task.getCategories());
        if(dto.getTaskName()!=null){
            task.setTaskName(dto.getTaskName());
        }
        if(dto.getDescription()!=null){
            task.setDescription(dto.getDescription());
        }
        if(dto.getPriority()!=null){
            task.setPriority(dto.getPriority());
        }
        if(dto.getDueDate()!=null){
            task.setDueDate(dto.getDueDate());
        }
        if(dto.getStatus()!=null){
            task.setStatus(dto.getStatus());
        }
        Task t = taskRepository.save(task);
        return TaskMapper.toDTO(t);
    }
    @Override
    public List<TaskResponseDTO> getTaskByProjectId(Integer projectId){
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException("Project not found with id: " + projectId);
        }
        List<Task> task = taskRepository.findByProject_ProjectId(projectId);
        if (task.isEmpty()) {
            throw new NoDataFoundException("No tasks found for project id: " + projectId);
        }
        return TaskMapper.toDTOList(task);
    }
    @Override
    public List<TaskResponseDTO> getTaskByUserId(Long userId){
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        List<Task> task = taskRepository.findByUser_UserId(userId);
        if (task.isEmpty()) {
            throw new NoDataFoundException("No tasks found for user id: " + userId);
        }
        return TaskMapper.toDTOList(task);
    }
    @Override
    public List<TaskResponseDTO> getTaskByStatus(String status){
        if (status == null || status.isBlank()) {
            throw new InvalidTaskException("Status cannot be empty");
        }
        List<Task> task = taskRepository.findByStatus(status);
        List<TaskResponseDTO> dto = new ArrayList<TaskResponseDTO>();
        for(Task t : task){
            dto.add(TaskMapper.toDTO(t));
        }
        if (task.isEmpty()) {
            throw new NoDataFoundException("No tasks found with status: " + status);
        }
        return dto;
    }
    @Override
    public List<TaskResponseDTO> getTaskByPriority(String priority){
        if (priority == null || priority.isBlank()) {
            throw new InvalidTaskException("Priority cannot be empty");
        }
        List<Task> task = taskRepository.findByPriority(priority);
        List<TaskResponseDTO> dto = new ArrayList<TaskResponseDTO>();
        for(Task t : task){
            dto.add(TaskMapper.toDTO(t));
        }
        if (task.isEmpty()) {
            throw new NoDataFoundException("No tasks found with priority: " + priority);
        }
        return dto;
    }
    @Override
    public void addCategoryToTask(Integer taskId, Integer categoryId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->
                new TaskNotFoundException("Task not Found "+taskId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new CategoryNotFoundException("Category Not Found "+categoryId));
        if (task.getCategories().contains(category)) {
            throw new CategoryAlreadyAssignedException("Category already assigned to task");
        }
        task.getCategories().add(category);
        taskRepository.save(task);
    }
    @Override
    public void deleteCategoryFromTask(Integer taskId, Integer categoryId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->
                new TaskNotFoundException("Task not Found "+taskId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new CategoryNotFoundException("Category Not Found "+categoryId));
        if (!task.getCategories().contains(category)) {
            throw new CategoryNotMappedException("Category is not mapped to this task");
        }
        task.getCategories().remove(category);
        taskRepository.save(task);
    }
    @Override
    public List<CategoryResponseDTO> getCategoryFromTask(Integer taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()->
                new TaskNotFoundException("Task not Found "+taskId));
        Set<Category> categories = task.getCategories();
        if (categories.isEmpty()) {
            throw new NoDataFoundException("No categories assigned to this task");
        }
        List<CategoryResponseDTO> dto = new ArrayList<CategoryResponseDTO>();
        for(Category c : categories){
            dto.add(CategoryMapper.toDTO(c));
        }
        return dto;
    }
}
