package com.taskflow.taskflow_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskflow.taskflow_api.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}