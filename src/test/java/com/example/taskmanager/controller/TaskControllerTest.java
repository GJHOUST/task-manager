package com.example.taskmanager.controller;

import com.example.taskmanager.configuration.ValidationConfig;
import com.example.taskmanager.controller.TaskController;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.exception.GlobalExceptionHandler;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import({GlobalExceptionHandler.class, ValidationConfig.class})
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void shouldReturnAllTasks() throws Exception {
        TaskResponse task = new TaskResponse();
        task.setId(1L);
        task.setTitle("Test úkol");
        task.setDescription("Popis");
        task.setCompleted(false);
        task.setCreatedAt(LocalDateTime.now());

        Mockito.when(taskService.getAllTasks()).thenReturn(List.of(task));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test úkol"));
    }

    @Test
    void shouldCreateNewTask() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setTitle("Nakoupit");
        request.setDescription("Koupit mléko");
        request.setCompleted(false);

        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("Nakoupit");
        response.setDescription("Koupit mléko");
        response.setCompleted(false);
        response.setCreatedAt(LocalDateTime.now());

        Mockito.when(taskService.createTask(any(TaskRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Nakoupit"))
                .andExpect(jsonPath("$.description").value("Koupit mléko"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void shouldFailOnMissingTitle() throws Exception {
        TaskRequest request = new TaskRequest();
        request.setTitle(""); // invalidní
        request.setDescription("Bez názvu");
        request.setCompleted(false);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title is mandatory"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        Long taskId = 1L;

        TaskRequest request = new TaskRequest("Upravený úkol", "Aktualizovaný popis", true);

        TaskResponse response = new TaskResponse();
        response.setId(taskId);
        response.setTitle("Upravený úkol");
        response.setDescription("Aktualizovaný popis");
        response.setCompleted(true);
        response.setCreatedAt(LocalDateTime.now());

        Mockito.when(taskService.updateTask(Mockito.eq(taskId), Mockito.any(TaskRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Upravený úkol"))
                .andExpect(jsonPath("$.description").value("Aktualizovaný popis"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        Long taskId = 1L;

        Mockito.doNothing().when(taskService).deleteTask(taskId);

        mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andExpect(status().isOk()); // nebo isNoContent() pokud to tak vracíš
    }
}
