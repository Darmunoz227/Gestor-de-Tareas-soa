package com.example.taskservice.service;

import com.example.taskservice.model.Task;
import com.example.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ============================================
 * SERVICIO DE TAREAS (Lógica de Negocio)
 * ============================================
 * Esta clase contiene la LÓGICA DE NEGOCIO de las tareas.
 * 
 * ¿QUÉ ES LÓGICA DE NEGOCIO?
 * Son las reglas y operaciones que tu aplicación debe seguir.
 * Ejemplo: "No se puede crear una tarea sin título"
 * 
 * ¿POR QUÉ UNA CAPA DE SERVICIO?
 * - Separa la lógica de negocio del acceso a datos
 * - Hace el código más organizado y fácil de mantener
 * - Puedes reutilizar métodos en diferentes controladores
 * 
 * @Service: Marca esta clase como un servicio de Spring
 */
@Service
public class TaskService {
    
    /**
     * INYECCIÓN DE DEPENDENCIAS
     * @Autowired: Spring automáticamente crea e inyecta el TaskRepository
     * No necesitas hacer: new TaskRepository()
     * Spring lo hace por ti (esto es "magia" de Spring)
     */
    @Autowired
    private TaskRepository taskRepository;
    
    /**
     * ============================================
     * OBTENER TODAS LAS TAREAS
     * ============================================
     * Retorna una lista con todas las tareas en la base de datos
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    /**
     * ============================================
     * OBTENER TAREA POR ID
     * ============================================
     * Busca una tarea específica por su ID
     * 
     * @param id - El ID de la tarea a buscar
     * @return Optional<Task> - Puede contener la tarea o estar vacío
     * 
     * ¿QUÉ ES OPTIONAL?
     * Es un contenedor que puede tener un valor o estar vacío.
     * Evita errores de NullPointerException
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    /**
     * ============================================
     * CREAR NUEVA TAREA
     * ============================================
     * Guarda una nueva tarea en la base de datos
     * 
     * @param task - La tarea a crear
     * @return Task - La tarea guardada con su ID generado
     */
    public Task createTask(Task task) {
        // Validación: El título no puede estar vacío
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la tarea es obligatorio");
        }
        
        // Por defecto, las tareas nuevas están pendientes
        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }
        
        // Si no tiene prioridad, asigna MEDIUM por defecto
        if (task.getPriority() == null || task.getPriority().trim().isEmpty()) {
            task.setPriority("MEDIUM");
        }
        
        // Guarda en la base de datos y retorna la tarea con su ID
        return taskRepository.save(task);
    }
    
    /**
     * ============================================
     * ACTUALIZAR TAREA EXISTENTE
     * ============================================
     * Modifica los datos de una tarea existente
     * 
     * @param id - ID de la tarea a actualizar
     * @param taskDetails - Nuevos datos de la tarea
     * @return Task - La tarea actualizada
     */
    public Task updateTask(Long id, Task taskDetails) {
        // Busca la tarea existente
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
        
        // Actualiza los campos
        if (taskDetails.getTitle() != null) {
            task.setTitle(taskDetails.getTitle());
        }
        
        if (taskDetails.getDescription() != null) {
            task.setDescription(taskDetails.getDescription());
        }
        
        if (taskDetails.getCompleted() != null) {
            task.setCompleted(taskDetails.getCompleted());
        }
        
        if (taskDetails.getPriority() != null) {
            task.setPriority(taskDetails.getPriority());
        }
        
        // Guarda los cambios
        return taskRepository.save(task);
    }
    
    /**
     * ============================================
     * ALTERNAR ESTADO DE TAREA
     * ============================================
     * Cambia el estado de completada a pendiente (y viceversa)
     * 
     * @param id - ID de la tarea
     * @return Task - La tarea con estado actualizado
     */
    public Task toggleTaskStatus(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
        
        // Invierte el estado (true -> false, false -> true)
        task.setCompleted(!task.getCompleted());
        
        return taskRepository.save(task);
    }
    
    /**
     * ============================================
     * ELIMINAR TAREA
     * ============================================
     * Elimina permanentemente una tarea de la base de datos
     * 
     * @param id - ID de la tarea a eliminar
     */
    public void deleteTask(Long id) {
        // Verifica que la tarea exista antes de eliminarla
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Tarea no encontrada con ID: " + id);
        }
        
        taskRepository.deleteById(id);
    }
    
    /**
     * ============================================
     * OBTENER TAREAS POR ESTADO
     * ============================================
     * Filtra tareas completadas o pendientes
     * 
     * @param completed - true para completadas, false para pendientes
     * @return Lista de tareas filtradas
     */
    public List<Task> getTasksByStatus(Boolean completed) {
        return taskRepository.findByCompleted(completed);
    }
    
    /**
     * ============================================
     * BUSCAR TAREAS POR TÍTULO
     * ============================================
     * Busca tareas cuyo título contenga el texto especificado
     * 
     * @param title - Texto a buscar en los títulos
     * @return Lista de tareas que coinciden
     */
    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }
    
    /**
     * ============================================
     * OBTENER ESTADÍSTICAS
     * ============================================
     * Cuenta cuántas tareas hay en total, completadas y pendientes
     * 
     * @return String con las estadísticas
     */
    public String getStatistics() {
        Long total = taskRepository.count();
        Long completed = taskRepository.countByCompleted(true);
        Long pending = taskRepository.countByCompleted(false);
        
        return String.format(
            "📊 Estadísticas: Total: %d | Completadas: %d | Pendientes: %d",
            total, completed, pending
        );
    }
}
