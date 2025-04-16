package com.example.taskmanager.contorller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.dto.TaskRequest;
import com.example.taskmanager.model.dto.TaskResponse;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Získat všechny úkoly
    @GetMapping
    public List<TaskResponse> getAllTasks(@RequestParam(required = false) Boolean completed) {
        List<Task> tasks = (completed != null) ?
                taskService.getTasksByCompleted(completed) :
                taskService.getAllTasks();

        return tasks.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // Získat úkol podle ID
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return mapToResponse(task);
    }

    // Vytvořit nový úkol
    @PostMapping
    public TaskResponse createTask(@Valid @RequestBody TaskRequest request) {
        Task task = mapToEntity(request);
        Task created = taskService.createTask(task);
        return mapToResponse(created);
    }

    // Aktualizovat úkol
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        Task task = mapToEntity(request);
        Task updated = taskService.updateTask(id, task);
        return mapToResponse(updated);
    }

    // Smazat úkol
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    // === Pomocné metody pro převod ===

    private TaskResponse mapToResponse(Task task) {
        TaskResponse dto = new TaskResponse();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }

    private Task mapToEntity(TaskRequest dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.isCompleted());
        return task;
    }
}
