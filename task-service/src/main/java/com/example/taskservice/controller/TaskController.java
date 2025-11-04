package com.example.taskservice.controller;

import com.example.taskservice.model.Task;
import com.example.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ============================================
 * CONTROLADOR REST DE TAREAS
 * ============================================
 * Este es el PUNTO DE ENTRADA de tu API REST.
 * 
 * ¿QUÉ ES UN CONTROLADOR?
 * Es la capa que recibe las peticiones HTTP desde el frontend
 * y retorna respuestas.
 * 
 * ¿QUÉ ES REST?
 * Es un estilo de arquitectura para crear APIs web.
 * Usa métodos HTTP estándar: GET, POST, PUT, DELETE
 * 
 * ANOTACIONES:
 * - @RestController: Marca esta clase como controlador REST
 * - @RequestMapping: Define la ruta base (/api/tasks)
 * - @CrossOrigin: Permite peticiones desde otros dominios (frontend)
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    
    /**
     * Inyecta el servicio de tareas
     * Este servicio contiene toda la lógica de negocio
     */
    @Autowired
    private TaskService taskService;
    
    /**
     * ============================================
     * ENDPOINT 1: OBTENER TODAS LAS TAREAS
     * ============================================
     * Método HTTP: GET
     * URL: http://localhost:8080/api/tasks
     * 
     * ¿QUÉ HACE?
     * Retorna una lista con todas las tareas en formato JSON
     * 
     * EJEMPLO DE RESPUESTA:
     * [
     *   {
     *     "id": 1,
     *     "title": "Comprar leche",
     *     "description": "Ir al supermercado",
     *     "completed": false,
     *     "priority": "HIGH"
     *   },
     *   {
     *     "id": 2,
     *     "title": "Hacer ejercicio",
     *     "description": "30 minutos de cardio",
     *     "completed": true,
     *     "priority": "MEDIUM"
     *   }
     * ]
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * ============================================
     * ENDPOINT 2: OBTENER UNA TAREA POR ID
     * ============================================
     * Método HTTP: GET
     * URL: http://localhost:8080/api/tasks/{id}
     * Ejemplo: http://localhost:8080/api/tasks/1
     * 
     * @param id - El ID de la tarea (viene en la URL)
     * 
     * RESPUESTA SI EXISTE:
     * Status: 200 OK
     * Body: { "id": 1, "title": "...", ... }
     * 
     * RESPUESTA SI NO EXISTE:
     * Status: 404 NOT FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * ============================================
     * ENDPOINT 3: CREAR NUEVA TAREA
     * ============================================
     * Método HTTP: POST
     * URL: http://localhost:8080/api/tasks
     * 
     * @param task - Los datos de la tarea (vienen en el body como JSON)
     * 
     * EJEMPLO DE PETICIÓN (Body JSON):
     * {
     *   "title": "Nueva tarea",
     *   "description": "Descripción de la tarea",
     *   "priority": "HIGH"
     * }
     * 
     * RESPUESTA:
     * Status: 201 CREATED
     * Body: La tarea creada con su ID generado
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (IllegalArgumentException e) {
            // Si hay un error de validación, retorna 400 BAD REQUEST
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * ============================================
     * ENDPOINT 4: ACTUALIZAR TAREA EXISTENTE
     * ============================================
     * Método HTTP: PUT
     * URL: http://localhost:8080/api/tasks/{id}
     * Ejemplo: http://localhost:8080/api/tasks/1
     * 
     * @param id - ID de la tarea a actualizar
     * @param taskDetails - Nuevos datos de la tarea
     * 
     * EJEMPLO DE PETICIÓN:
     * PUT http://localhost:8080/api/tasks/1
     * Body: { "title": "Título actualizado", "completed": true }
     * 
     * RESPUESTA:
     * Status: 200 OK
     * Body: La tarea actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task taskDetails) {
        try {
            Task updatedTask = taskService.updateTask(id, taskDetails);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * ============================================
     * ENDPOINT 5: ALTERNAR ESTADO DE TAREA
     * ============================================
     * Método HTTP: PATCH
     * URL: http://localhost:8080/api/tasks/{id}/toggle
     * 
     * ¿QUÉ HACE?
     * Cambia el estado de la tarea:
     * - Si está completada -> la marca como pendiente
     * - Si está pendiente -> la marca como completada
     * 
     * EJEMPLO:
     * PATCH http://localhost:8080/api/tasks/1/toggle
     * 
     * No necesita body, solo el ID en la URL
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Task> toggleTaskStatus(@PathVariable Long id) {
        try {
            Task updatedTask = taskService.toggleTaskStatus(id);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * ============================================
     * ENDPOINT 6: ELIMINAR TAREA
     * ============================================
     * Método HTTP: DELETE
     * URL: http://localhost:8080/api/tasks/{id}
     * Ejemplo: http://localhost:8080/api/tasks/1
     * 
     * ¿QUÉ HACE?
     * Elimina permanentemente la tarea de la base de datos
     * 
     * RESPUESTA:
     * Status: 204 NO CONTENT (éxito, sin contenido en la respuesta)
     * o
     * Status: 404 NOT FOUND (si la tarea no existe)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * ============================================
     * ENDPOINT 7: FILTRAR POR ESTADO
     * ============================================
     * Método HTTP: GET
     * URL: http://localhost:8080/api/tasks/status/{completed}
     * 
     * EJEMPLOS:
     * - GET /api/tasks/status/true  -> Tareas completadas
     * - GET /api/tasks/status/false -> Tareas pendientes
     */
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Boolean completed) {
        List<Task> tasks = taskService.getTasksByStatus(completed);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * ============================================
     * ENDPOINT 8: BUSCAR TAREAS POR TÍTULO
     * ============================================
     * Método HTTP: GET
     * URL: http://localhost:8080/api/tasks/search?title=texto
     * 
     * EJEMPLO:
     * GET /api/tasks/search?title=comprar
     * Retorna todas las tareas cuyo título contiene "comprar"
     */
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String title) {
        List<Task> tasks = taskService.searchTasksByTitle(title);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * ============================================
     * ENDPOINT 9: OBTENER ESTADÍSTICAS
     * ============================================
     * Método HTTP: GET
     * URL: http://localhost:8080/api/tasks/stats
     * 
     * RESPUESTA:
     * "📊 Estadísticas: Total: 10 | Completadas: 3 | Pendientes: 7"
     */
    @GetMapping("/stats")
    public ResponseEntity<String> getStatistics() {
        String stats = taskService.getStatistics();
        return ResponseEntity.ok(stats);
    }
}
