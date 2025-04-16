package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllTasks() {
        Task task = new Task();
        task.setTitle("Úkol");

        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskResponse> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Úkol", result.get(0).getTitle());
    }

    @Test
    void shouldReturnTasksByCompletedStatus() {
        Task task = new Task();
        task.setTitle("Hotový úkol");
        task.setCompleted(true);
        task.setCreatedAt(LocalDateTime.now());

        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskResponse> result = taskService.getTasksByCompleted(true);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isCompleted());
        assertEquals("Hotový úkol", result.get(0).getTitle());
    }

    @Test
    void shouldReturnTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse result = taskService.getTaskById(1L);

        assertEquals("Test", result.getTitle());
    }

    @Test
    void shouldThrowIfTaskNotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(999L));
    }

    @Test
    void shouldCreateTask() {
        Task task = new Task();
        task.setTitle("Nový");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Simulace vstupu
        var request = new com.example.taskmanager.dto.TaskRequest("Nový", "Popis", false);
        TaskResponse result = taskService.createTask(request);

        assertEquals("Nový", result.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository).delete(task);
    }

    @Test
    void shouldUpdateTaskSuccessfully() {
        Long id = 1L;
        Task original = new Task();
        original.setId(id);
        original.setTitle("Starý název");
        original.setDescription("Původní popis");
        original.setCompleted(false);

        Task updated = new Task();
        updated.setId(id);
        updated.setTitle("Nový název");
        updated.setDescription("Nový popis");
        updated.setCompleted(true);

        TaskRequest request = new TaskRequest("Nový název", "Nový popis", true);

        when(taskRepository.findById(id)).thenReturn(Optional.of(original));
        when(taskRepository.save(any(Task.class))).thenReturn(updated);

        TaskResponse result = taskService.updateTask(id, request);

        assertEquals("Nový název", result.getTitle());
        assertEquals("Nový popis", result.getDescription());
        assertTrue(result.isCompleted());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentTask() {
        Long invalidId = 999L;
        TaskRequest request = new TaskRequest("X", "Y", false);

        when(taskRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(invalidId, request);
        });
    }



}
