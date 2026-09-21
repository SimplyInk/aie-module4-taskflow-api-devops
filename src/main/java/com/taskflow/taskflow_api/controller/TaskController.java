package com.taskflow.taskflow_api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.taskflow_api.exceptions.TaskNotFoundException;
import com.taskflow.taskflow_api.model.Task;
import com.taskflow.taskflow_api.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    // -------------------------------------------------
    // Get Task(s)
    // -------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Task>> findAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        logger.info("Retrieved {} tasks", tasks.size());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.findTaskById(id);
            logger.info("Retrieved task id={}: {}", id, task);
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException e) {
            logger.error("Task not found with id: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // -------------------------------------------------
    // Create Task
    // -------------------------------------------------
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        logger.info("Created task id={}: {}", createdTask.getId(), createdTask);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // -------------------------------------------------
    // Update Task
    // -------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Long id, @RequestBody Task entity) {
        try {
            Task updatedTask = taskService.updateTask(id, entity);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            logger.error("Task not found with id: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Object> markTaskAsComplete(@PathVariable Long id) {
        try {
            Task updatedTask = taskService.markTaskAsCompleted(id);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            logger.error("Task not found with id: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // -------------------------------------------------
    // Delete Task
    // -------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            logger.info("Deleted task id={}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TaskNotFoundException e) {
            logger.error("Task not found with id: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // -------------------------------------------------
    // Miscellaneous
    // -------------------------------------------------
    @GetMapping("/summary")
    public String getTaskSummary() {
        return taskService.getTaskSummary();
    }

}