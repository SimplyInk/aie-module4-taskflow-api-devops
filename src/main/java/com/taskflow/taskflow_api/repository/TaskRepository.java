package com.taskflow.taskflow_api.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskflow.taskflow_api.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Map<Long, Task> allTasks = new HashMap<>();

    public static Map<Long, Task> getAllTasks() {
        return allTasks;
    }
}