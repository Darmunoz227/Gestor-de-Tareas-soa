package com.example.taskservice.service;

import com.example.taskservice.model.Task;
import com.example.taskservice.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setCompleted(false);
        task.setPriority("MEDIUM");
    }

    @Test
    void getAllTasks_ShouldReturnTaskList() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_WhenExists_ShouldReturnTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        Optional<Task> result = taskService.getTaskById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void createTask_ShouldReturnSavedTask() {
        // Arrange
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        Task result = taskService.createTask(task);

        // Assert
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void createTask_WithEmptyTitle_ShouldThrowException() {
        // Arrange
        task.setTitle("");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(task);
        });

        assertEquals("El título de la tarea es obligatorio", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }
}
