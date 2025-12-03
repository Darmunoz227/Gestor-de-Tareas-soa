package com.example.taskservice.controller;

import com.example.taskservice.model.Task;
import com.example.taskservice.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    void getAllTasks_ShouldReturnTaskList() throws Exception {
        // Arrange
        given(taskService.getAllTasks()).willReturn(Arrays.asList(task));

        // Act & Assert
        mockMvc.perform(get("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"))
                .andExpect(jsonPath("$[0].priority").value("MEDIUM"));
    }

    @Test
    void getTaskById_WhenExists_ShouldReturnTask() throws Exception {
        // Arrange
        given(taskService.getTaskById(1L)).willReturn(Optional.of(task));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void getTaskById_WhenNotExists_ShouldReturn404() throws Exception {
        // Arrange
        given(taskService.getTaskById(1L)).willReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {
        // Arrange
        given(taskService.createTask(any(Task.class))).willReturn(task);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Task\",\"description\":\"Test Description\",\"priority\":\"MEDIUM\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void updateTask_WhenExists_ShouldReturnUpdatedTask() throws Exception {
        // Arrange
        given(taskService.updateTask(eq(1L), any(Task.class))).willReturn(task);

        // Act & Assert
        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void updateTask_WhenNotExists_ShouldReturn404() throws Exception {
        // Arrange
        given(taskService.updateTask(eq(1L), any(Task.class))).willThrow(new RuntimeException("Task not found"));

        // Act & Assert
        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Task\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTask_WhenExists_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(taskService).deleteTask(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTask_WhenNotExists_ShouldReturn404() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Task not found")).when(taskService).deleteTask(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void toggleTaskStatus_ShouldReturnUpdatedTask() throws Exception {
        // Arrange
        task.setCompleted(true);
        given(taskService.toggleTaskStatus(1L)).willReturn(task);

        // Act & Assert
        mockMvc.perform(patch("/api/tasks/1/toggle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void getTasksByStatus_ShouldReturnFilteredList() throws Exception {
        // Arrange
        given(taskService.getTasksByStatus(false)).willReturn(Arrays.asList(task));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/status/false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    void searchTasks_ShouldReturnMatchingTasks() throws Exception {
        // Arrange
        given(taskService.searchTasksByTitle("Test")).willReturn(Arrays.asList(task));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/search?title=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void getStatistics_ShouldReturnStatsString() throws Exception {
        // Arrange
        String stats = "📊 Estadísticas: Total: 1";
        given(taskService.getStatistics()).willReturn(stats);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/stats"))
                .andExpect(status().isOk())
                .andExpect(content().string(stats));
    }
}
