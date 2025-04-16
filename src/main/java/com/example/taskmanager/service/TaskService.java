package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService {

        List<TaskResponse> getAllTasks();
        List<TaskResponse> getTasksByCompleted(boolean completed);
        TaskResponse getTaskById(Long id);
        TaskResponse createTask(TaskRequest request);
        TaskResponse updateTask(Long id, TaskRequest request);
        void deleteTask(Long id);
}
