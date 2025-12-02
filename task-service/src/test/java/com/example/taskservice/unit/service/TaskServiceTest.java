package com.example.taskservice.unit.service;

import com.example.taskservice.model.Task;
import com.example.taskservice.repository.TaskRepository;
import com.example.taskservice.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * ============================================
 * UNIT TESTS FOR TaskService
 * ============================================
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Tests - TaskService")
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task taskExample;

    @BeforeEach
    void setUp() {
        taskExample = new Task();
        taskExample.setId(1L);
        taskExample.setTitle("Test task");
        taskExample.setDescription("Test description");
        taskExample.setCompleted(false);
        taskExample.setPriority("MEDIUM");
    }

    @Test
    @DisplayName("Should create a task successfully")
    void shouldCreateTaskSuccessfully() {
        when(taskRepository.save(any(Task.class))).thenReturn(taskExample);

        Task createdTask = taskService.createTask(taskExample);

        assertNotNull(createdTask, "Created task should not be null");
        assertEquals("Test task", createdTask.getTitle());
        assertEquals("Test description", createdTask.getDescription());
        assertFalse(createdTask.getCompleted());
        assertEquals("MEDIUM", createdTask.getPriority());
        
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw exception when creating task without title")
    void shouldThrowExceptionWhenTitleIsEmpty() {
        Task invalidTask = new Task();
        invalidTask.setTitle("");
        invalidTask.setDescription("Valid description");

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(invalidTask);
        }, "Should throw IllegalArgumentException when title is empty");

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw exception when creating task with null title")
    void shouldThrowExceptionWhenTitleIsNull() {
        Task invalidTask = new Task();
        invalidTask.setTitle(null);
        invalidTask.setDescription("Valid description");

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(invalidTask);
        });

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should return list of all tasks")
    void shouldReturnAllTasks() {
        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Second task");
        task2.setDescription("Second description");
        task2.setCompleted(true);

        List<Task> taskList = Arrays.asList(taskExample, task2);
        when(taskRepository.findAll()).thenReturn(taskList);

        List<Task> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test task", result.get(0).getTitle());
        assertEquals("Second task", result.get(1).getTitle());
        
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no tasks exist")
    void shouldReturnEmptyListWhenNoTasks() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<Task> result = taskService.getAllTasks();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find a task by ID")
    void shouldFindTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskExample));

        Optional<Task> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Test task", result.get().getTitle());
        
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty Optional when task does not exist")
    void shouldReturnEmptyWhenTaskNotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(999L);

        assertFalse(result.isPresent());
        
        verify(taskRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should update an existing task")
    void shouldUpdateTask() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated task");
        updatedTask.setDescription("New description");
        updatedTask.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskExample));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals("Updated task", result.getTitle());
        assertEquals("New description", result.getDescription());
        assertTrue(result.getCompleted());
        
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent task")
    void shouldThrowExceptionWhenUpdatingNonExistentTask() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated task");
        
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            taskService.updateTask(999L, updatedTask);
        });

        verify(taskRepository, times(1)).findById(999L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should delete a task by ID")
    void shouldDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent task")
    void shouldThrowExceptionWhenDeletingNonExistentTask() {
        when(taskRepository.existsById(999L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            taskService.deleteTask(999L);
        });

        verify(taskRepository, times(1)).existsById(999L);
        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should toggle task status from pending to completed")
    void shouldToggleTaskStatus() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskExample));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.toggleTaskStatus(1L);

        assertTrue(result.getCompleted());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should filter tasks by completion status")
    void shouldFilterTasksByStatus() {
        Task completedTask = new Task();
        completedTask.setId(2L);
        completedTask.setTitle("Completed task");
        completedTask.setCompleted(true);

        when(taskRepository.findByCompleted(true)).thenReturn(Arrays.asList(completedTask));

        List<Task> result = taskService.getTasksByStatus(true);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getCompleted());
        
        verify(taskRepository, times(1)).findByCompleted(true);
    }


    @DisplayName("Should search tasks by title")
    void shouldSearchTasksByTitle() {
        when(taskRepository.findByTitleContainingIgnoreCase("test"))
            .thenReturn(Arrays.asList(taskExample));

        List<Task> result = taskService.searchTasksByTitle("test");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getTitle().toLowerCase().contains("test"));
        
        verify(taskRepository, times(1)).findByTitleContainingIgnoreCase("test");
    }


    @Test
    @DisplayName("Should return correct statistics")
    void shouldReturnCorrectStatistics() {
        when(taskRepository.count()).thenReturn(10L);
        when(taskRepository.countByCompleted(true)).thenReturn(6L);
        when(taskRepository.countByCompleted(false)).thenReturn(4L);

        String stats = taskService.getStatistics();

        assertNotNull(stats);
        assertTrue(stats.contains("Total: 10"));
        assertTrue(stats.contains("Completadas: 6"));
        assertTrue(stats.contains("Pendientes: 4"));
        
        verify(taskRepository, times(1)).count();
        verify(taskRepository, times(1)).countByCompleted(true);
        verify(taskRepository, times(1)).countByCompleted(false);
    }

    @Test
    @DisplayName("Should assign default MEDIUM priority")
    void shouldAssignDefaultPriority() {
        Task taskWithoutPriority = new Task();
        taskWithoutPriority.setTitle("Task without priority");
        taskWithoutPriority.setPriority(null);

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.createTask(taskWithoutPriority);

        assertEquals("MEDIUM", result.getPriority());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should verify multiple task properties")
    void shouldVerifyMultipleTaskProperties() {
        when(taskRepository.save(any(Task.class))).thenReturn(taskExample);

        Task result = taskService.createTask(taskExample);

        assertAll("Task properties",
            () -> assertNotNull(result),
            () -> assertEquals(1L, result.getId()),
            () -> assertEquals("Test task", result.getTitle()),
            () -> assertFalse(result.getCompleted()),
            () -> assertEquals("MEDIUM", result.getPriority()),
            () -> assertNotNull(result.getDescription())
        );
    }

    @Test
    @DisplayName("Should set completed to false by default")
    void shouldSetCompletedToFalseByDefault() {
        Task newTask = new Task();
        newTask.setTitle("New task");
        newTask.setCompleted(null);

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.createTask(newTask);

        assertFalse(result.getCompleted());
    }

    @Test
    @DisplayName("Should update only provided fields")
    void shouldUpdateOnlyProvidedFields() {
        Task partialUpdate = new Task();
        partialUpdate.setTitle("Updated title only");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskExample));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.updateTask(1L, partialUpdate);

        assertEquals("Updated title only", result.getTitle());
        assertEquals("Test description", result.getDescription());
        assertFalse(result.getCompleted());
    }

    @Test
    @DisplayName("Should return empty list when search finds no matches")
    void shouldReturnEmptyListWhenSearchFindsNoMatches() {
        when(taskRepository.findByTitleContainingIgnoreCase("nonexistent"))
            .thenReturn(Collections.emptyList());

        List<Task> result = taskService.searchTasksByTitle("nonexistent");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}