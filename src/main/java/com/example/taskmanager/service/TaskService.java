package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService {

        List<Task> getTasksByCompleted(boolean completed);

        List<Task> getAllTasks();
        Task getTaskById(Long id);
        Task createTask(Task task);
        Task updateTask(Long id, Task task);
        void deleteTask(Long id);
}
