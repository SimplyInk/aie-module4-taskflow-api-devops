package com.taskflow.taskflow_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskflow.taskflow_api.exceptions.TaskNotFoundException;
import com.taskflow.taskflow_api.model.Task;
import com.taskflow.taskflow_api.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ChatClient chatClient;

    @Autowired // Constructor injection for TaskRepository and ChatClient.Builder
    public TaskService(TaskRepository taskRepository, ChatClient.Builder chatClientBuilder) {
        this.taskRepository = taskRepository;
        this.chatClient = chatClientBuilder.build();
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        // JSON bodies without "completed" leave the field null, which violates the NOT NULL column
        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }
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

    /**
     * Take your list of tasks, send it to the model, and return a short
     * plain-English summary of what is pending and what is done.
     */
    public String getTaskSummary() {
        List<Task> tasks = findAllTasks();

        if (tasks.isEmpty()) {
            return "No tasks available to summarize.";
        }

        // Format tasks into a readable list for the model prompt
        String taskListString = tasks.stream()
                .map(task -> String.format("- Title: %s | Description: %s | Status: %s | Priority: %s",
                        task.getTitle(),
                        task.getDescription() != null ? task.getDescription() : "None",
                        task.getCompleted() ? "Completed" : "Pending",
                        task.getPriority()))
                .collect(Collectors.joining("\n"));

        String userPrompt = "Here is a list of tasks:\n" + taskListString
                + "\n\nPlease provide a short plain-English summary of what is pending and what is done.";

        // Call Spring AI ChatClient
        return chatClient.prompt()
                .user(userPrompt)
                .call()
                .content();
    }
}