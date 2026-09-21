# **Module 3: End of Module Assignment**

## **TaskFlow \- Task Management API ✅**

### **Overview**

**Type:** Mandatory Assignment  
**Estimated Time:** 1-2 hours

### **Introduction**

Build a simple TaskFlow API \- a task management backend. You'll create a Spring Boot project and complete 3 tasks.

## **Project Setup**

### **Step 1: Create Spring Boot Project**

1\. Go to https://start.spring.io/

2\. Configure:  
   • Project: Maven  
   • Language: Java  
   • Spring Boot: 3.2.2 (or latest 3.x)  
   • Group: com.taskflow  
   • Artifact: taskflow-api  
   • Packaging: Jar  
   • Java: 21

3\. Add Dependencies:  
   • Spring Web  
   • Spring Data JPA  
   • H2 Database  
   • Lombok

4\. Click "Generate" and download  
5\. Extract the ZIP file to your computer  
6\. Open the extracted folder in VS Code

## **Your Tasks**

### **TASK 1: Create Task Entity and Repository**

**Create file:** src/main/java/com/taskflow/entity/Task.java

Copy this code:

package com.taskflow.entity;

import jakarta.persistence.\*;

import lombok.\*;

import java.time.LocalDate;

@Entity

@Table(name \= "tasks")

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

public class Task {

    

    @Id

    @GeneratedValue(strategy \= GenerationType.IDENTITY)

    private Long id;

    

    @Column(nullable \= false)

    private String title;

    

    private String description;

    

    private String priority;

    

    private LocalDate dueDate;

    

    @Column(nullable \= false)

    private Boolean completed \= false;

}

**Create file:** src/main/java/com/taskflow/repository/TaskRepository.java

Copy this code:

package com.taskflow.repository;

import com.taskflow.entity.Task;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface TaskRepository extends JpaRepository\<Task, Long\> {

}

### **TASK 2: Create TaskService with TODO**

**Create file:** src/main/java/com/taskflow/service/TaskService.java

**Copy this code and COMPLETE THE TODO:**

package com.taskflow.service;

import com.taskflow.entity.Task;

import com.taskflow.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TaskService {

    

    @Autowired

    private TaskRepository taskRepository;

    

    public List\<Task\> findAllTasks() {

        return taskRepository.findAll();

    }

    

    // TODO: Complete this method

    // Use taskRepository.findById(id)

    // If not found, throw new RuntimeException("Task not found with id: " \+ id)

    public Task findTaskById(Long id) {

        // YOUR CODE HERE

        return null;

    }

    

    public Task createTask(Task task) {

        return taskRepository.save(task);

    }

}

**HINT for the TODO:**

return taskRepository.findById(id).orElseThrow(() \-\> new RuntimeException("Task not found with id: " \+ id));

### **TASK 3: Create TaskController with TODO**

**Create file:** src/main/java/com/taskflow/controller/TaskController.java

**Copy this code and COMPLETE THE TODO:**

package com.taskflow.controller;

import com.taskflow.entity.Task;

import com.taskflow.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.\*;

import java.util.List;

@RestController

@RequestMapping("/api/tasks")

public class TaskController {

    

    @Autowired

    private TaskService taskService;

    

    @GetMapping

    public ResponseEntity\<List\<Task\>\> getAllTasks() {

        List\<Task\> tasks \= taskService.findAllTasks();

        return ResponseEntity.ok(tasks);

    }

    

    // TODO: Complete this method

    // Call taskService.findTaskById(id)

    // Return ResponseEntity.ok(task)

    @GetMapping("/{id}")

    public ResponseEntity\<Task\> getTaskById(@PathVariable Long id) {

        // YOUR CODE HERE

        return null;

    }

    

    @PostMapping

    public ResponseEntity\<Task\> createTask(@RequestBody Task task) {

        Task createdTask \= taskService.createTask(task);

        return new ResponseEntity\<\>(createdTask, HttpStatus.CREATED);

    }

}

**HINT for the TODO:**

Task task \= taskService.findTaskById(id);

return ResponseEntity.ok(task);

### **TASK 4: Configure Database**

**Edit file:** src/main/resources/application.properties

Add these lines:

\# Database configuration

spring.datasource.url=jdbc:h2:mem:taskflowdb

spring.datasource.driverClassName=org.h2.Driver

spring.datasource.username=sa

spring.datasource.password=

\# JPA configuration

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

\# H2 Console

spring.h2.console.enabled=true

## **Testing Your Work**

### **Step 1: Run the Application**

mvn spring-boot:run

### **Step 2: Create a Task**

curl \-X POST http://localhost:8080/api/tasks \\

  \-H "Content-Type: application/json" \\

  \-d '{

    "title": "Complete Module 3",

    "description": "Finish assignment",

    "priority": "HIGH",

    "dueDate": "2026-05-20",

    "completed": false

  }'

### **Step 3: Get Task by ID**

curl http://localhost:8080/api/tasks/1

Expected response:

{

  "id": 1,

  "title": "Complete Module 3",

  "description": "Finish assignment",

  "priority": "HIGH",

  "dueDate": "2026-05-20",

  "completed": false

}

## **Submission**

### **Step 1: Create GitHub Repository**

1\. Go to github.com  
2\. Click "New repository"  
3\. Name it: module3-taskflow-api (or any name)  
4\. Make it PUBLIC  
5\. Click "Create repository"

### **Step 2: Push Your Code**

**Open Terminal/Command Prompt in VS Code:**  
1\. In VS Code, open Terminal (View → Terminal or Ctrl+\`)

**2\. Make sure you're in your Spring Boot project folder:**  
   The terminal should show the path to your taskflow-api folder  
   Example: C:\\Users\\YourName\\taskflow-api

**3\. Run these Git commands one by one:**

git init

   (This initializes Git in your project folder)

git add .

   (This adds all your files)

git commit \-m "Complete Module 3 assignment"

   (This commits your files)

git branch \-M main

   (This renames the branch to main)

git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPO-NAME.git

   (Replace YOUR-USERNAME and YOUR-REPO-NAME with your actual values)

git push \-u origin main

   (This uploads your code to GitHub)

### **Step 3: Submit to NTU Learn**

1\. Copy your GitHub repository URL  
2\. Submit it in the assignment submission on NTU Learn

**Done\! 🎉**