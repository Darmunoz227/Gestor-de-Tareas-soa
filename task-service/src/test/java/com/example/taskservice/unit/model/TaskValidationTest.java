package com.example.taskservice.unit.model;

import com.example.taskservice.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Tests - Task Model Validation")
class TaskValidationTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setCompleted(false);
        task.setPriority("MEDIUM");
    }

    @Test
    @DisplayName("Should create task with valid data")
    void shouldCreateTaskWithValidData() {
        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertFalse(task.getCompleted());
        assertEquals("MEDIUM", task.getPriority());
    }

    @Test
    @DisplayName("Should set default completed to false")
    void shouldSetDefaultCompletedToFalse() {
        Task newTask = new Task();
        assertFalse(newTask.getCompleted());
    }

    @Test
    @DisplayName("Should set default priority to MEDIUM")
    void shouldSetDefaultPriorityToMedium() {
        Task newTask = new Task();
        assertEquals("MEDIUM", newTask.getPriority());
    }

    @Test
    @DisplayName("Should accept HIGH priority")
    void shouldAcceptHighPriority() {
        task.setPriority("HIGH");
        assertEquals("HIGH", task.getPriority());
    }

    @Test
    @DisplayName("Should accept LOW priority")
    void shouldAcceptLowPriority() {
        task.setPriority("LOW");
        assertEquals("LOW", task.getPriority());
    }

    @Test
    @DisplayName("Should set and get ID correctly")
    void shouldSetAndGetIdCorrectly() {
        task.setId(1L);
        assertEquals(1L, task.getId());
    }

    @Test
    @DisplayName("Should set createdAt timestamp")
    void shouldSetCreatedAtTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        task.setCreatedAt(now);
        assertEquals(now, task.getCreatedAt());
    }

    @Test
    @DisplayName("Should set updatedAt timestamp")
    void shouldSetUpdatedAtTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        task.setUpdatedAt(now);
        assertEquals(now, task.getUpdatedAt());
    }

    @Test
    @DisplayName("Should toggle completed status")
    void shouldToggleCompletedStatus() {
        task.setCompleted(false);
        assertFalse(task.getCompleted());
        
        task.setCompleted(true);
        assertTrue(task.getCompleted());
    }

    @Test
    @DisplayName("Should accept long descriptions")
    void shouldAcceptLongDescriptions() {
        String longDescription = "A".repeat(500);
        task.setDescription(longDescription);
        assertEquals(500, task.getDescription().length());
    }

    @Test
    @DisplayName("Should accept title up to 100 characters")
    void shouldAcceptTitleUpTo100Characters() {
        String longTitle = "A".repeat(100);
        task.setTitle(longTitle);
        assertEquals(100, task.getTitle().length());
    }

    @Test
    @DisplayName("Should create task using all args constructor")
    void shouldCreateTaskUsingAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Task fullTask = new Task(1L, "Title", "Description", true, "HIGH", now, now);
        
        assertEquals(1L, fullTask.getId());
        assertEquals("Title", fullTask.getTitle());
        assertEquals("Description", fullTask.getDescription());
        assertTrue(fullTask.getCompleted());
        assertEquals("HIGH", fullTask.getPriority());
        assertEquals(now, fullTask.getCreatedAt());
        assertEquals(now, fullTask.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create task using no args constructor")
    void shouldCreateTaskUsingNoArgsConstructor() {
        Task emptyTask = new Task();
        assertNotNull(emptyTask);
        assertFalse(emptyTask.getCompleted());
        assertEquals("MEDIUM", emptyTask.getPriority());
    }

    @Test
    @DisplayName("Should set null description")
    void shouldSetNullDescription() {
        task.setDescription(null);
        assertNull(task.getDescription());
    }

    @Test
    @DisplayName("Should set empty description")
    void shouldSetEmptyDescription() {
        task.setDescription("");
        assertEquals("", task.getDescription());
    }

    @Test
    @DisplayName("Should use equals and hashCode correctly")
    void shouldUseEqualsAndHashCodeCorrectly() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Same Task");
        
        Task task2 = new Task();
        task2.setId(1L);
        task2.setTitle("Same Task");
        
        assertEquals(task1, task2);
        assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    @DisplayName("Should generate toString with all fields")
    void shouldGenerateToStringWithAllFields() {
        task.setId(1L);
        String toString = task.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("title=Test Task"));
        assertTrue(toString.contains("description=Test Description"));
    }

    @Test
    @DisplayName("Should handle null priority")
    void shouldHandleNullPriority() {
        task.setPriority(null);
        assertNull(task.getPriority());
    }

    @Test
    @DisplayName("Should handle null completed")
    void shouldHandleNullCompleted() {
        task.setCompleted(null);
        assertNull(task.getCompleted());
    }

    @Test
    @DisplayName("Should accept empty title")
    void shouldAcceptEmptyTitle() {
        task.setTitle("");
        assertEquals("", task.getTitle());
    }

    @Test
    @DisplayName("Should accept null title")
    void shouldAcceptNullTitle() {
        task.setTitle(null);
        assertNull(task.getTitle());
    }
}