package com.taskflow.taskflow_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskflow.taskflow_api.entity.Task;
import com.taskflow.taskflow_api.repository.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
    
    // TODO: Complete this method
    // Use taskRepository.findById(id)
    // If not found, throw new RuntimeException("Task not found with id: " + id)
    public Task findTaskById(Long id) {
        // YOUR CODE HERE
        return null;
    }
    
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
}