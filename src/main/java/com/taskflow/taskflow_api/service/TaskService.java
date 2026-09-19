package com.taskflow.taskflow_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskflow.taskflow_api.exceptions.TaskNotFoundException;
import com.taskflow.taskflow_api.model.Task;
import com.taskflow.taskflow_api.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
                
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setCompleted(updatedTask.getCompleted());
        existingTask.setPriority(updatedTask.getPriority());
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        Task existingTask = findTaskById(id);
        taskRepository.delete(existingTask);
    }

    public Task markTaskAsCompleted(Long id) {
        Task existingTask = findTaskById(id);
        existingTask.setCompleted(true);
        taskRepository.save(existingTask);
        return existingTask;
    }
    
}