package com.kaiburr.taskapi.service;

import com.kaiburr.taskapi.model.Task;
import com.kaiburr.taskapi.model.TaskExecution;
import com.kaiburr.taskapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public List<Task> findByName(String name) {
        return taskRepository.findByNameContainingIgnoreCase(name);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    public Task executeTask(String id) throws IOException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new IllegalArgumentException("Task not found with ID: " + id);
        }

        Task task = optionalTask.get();

        if (task.getCommand() == null || task.getCommand().isEmpty()) {
            throw new IllegalArgumentException("Command is empty for task with ID: " + id);
        }

        Date startTime = new Date();

        // Use this for Windows
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("cmd.exe", "/c", task.getCommand());
        builder.redirectErrorStream(true);

        Process process = builder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Date endTime = new Date();

        TaskExecution execution = new TaskExecution(
                output.toString().trim(),
                "COMPLETED",
                startTime,
                endTime
        );

        if (task.getTaskExecutions() == null) {
            task.setTaskExecutions(new ArrayList<>());
        }

        task.getTaskExecutions().add(execution);
        return taskRepository.save(task);
    }

    // ✅ NEW: Method to handle PUT request
    public Task updateTask(String id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));

        existingTask.setName(updatedTask.getName());
        existingTask.setOwner(updatedTask.getOwner());
        existingTask.setCommand(updatedTask.getCommand());
        existingTask.setTaskExecutions(updatedTask.getTaskExecutions());

        return taskRepository.save(existingTask);
    }
}
