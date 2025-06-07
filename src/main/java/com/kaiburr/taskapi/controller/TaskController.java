package com.kaiburr.taskapi.controller;

import com.kaiburr.taskapi.model.Task;
import com.kaiburr.taskapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/search")
    public List<Task> searchTasksByName(@RequestParam String name) {
        return taskService.findByName(name);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/{id}/execute")
    public Task executeTask(@PathVariable String id) throws IOException {
        return taskService.executeTask(id);
    }

    // ✅ NEW: PUT mapping to update a task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }
}
