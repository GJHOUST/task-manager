package com.example.taskmanager.contorller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Získá všechny úkoly, volitelně lze filtrovat pomocí ?completed=true/false")
    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) Boolean completed) {
        if (completed != null) {
            return taskService.getTasksByCompleted(completed);
        } else {
            return taskService.getAllTasks();
        }
    }

    @Operation(summary = "Získá úkol podle ID")
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Vytvoří nový úkol")
    @PostMapping
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    @Operation(summary = "Aktualizuje existující úkol podle ID")
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @Operation(summary = "Smaže úkol podle ID")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
