package com.example.taskservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Integration Tests - TaskServiceApplication")
class TaskServiceApplicationTests {

    @Test
    @DisplayName("Should load Spring application context successfully")
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Should have @SpringBootApplication annotation")
    void shouldHaveSpringBootApplicationAnnotation() {
        assertTrue(TaskServiceApplication.class.isAnnotationPresent(
            org.springframework.boot.autoconfigure.SpringBootApplication.class
        ));
    }

    @Test
    @DisplayName("Should have main class")
    void shouldHaveMainClass() {
        assertNotNull(TaskServiceApplication.class);
        assertEquals("TaskServiceApplication", TaskServiceApplication.class.getSimpleName());
    }
}