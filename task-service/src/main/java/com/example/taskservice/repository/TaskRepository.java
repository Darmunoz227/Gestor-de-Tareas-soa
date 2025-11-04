package com.example.taskservice.repository;

import com.example.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ============================================
 * REPOSITORIO DE TAREAS
 * ============================================
 * Esta interfaz maneja TODAS las operaciones con la base de datos.
 * 
 * ¿QUÉ HACE?
 * - Guardar tareas
 * - Buscar tareas
 * - Actualizar tareas
 * - Eliminar tareas
 * 
 * MAGIA DE SPRING DATA JPA:
 * No necesitas escribir código SQL, Spring lo hace automáticamente.
 * Solo defines el nombre del método y Spring entiende qué hacer.
 * 
 * @Repository: Marca esta clase como un repositorio de datos
 * JpaRepository<Task, Long>: 
 *    - Task: La entidad que manejamos
 *    - Long: El tipo de dato del ID
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * MÉTODOS AUTOMÁTICOS (heredados de JpaRepository):
     * 
     * - save(Task task): Guarda o actualiza una tarea
     * - findById(Long id): Busca una tarea por su ID
     * - findAll(): Obtiene todas las tareas
     * - deleteById(Long id): Elimina una tarea por su ID
     * - count(): Cuenta cuántas tareas hay
     * - existsById(Long id): Verifica si existe una tarea
     */
    
    /**
     * MÉTODOS PERSONALIZADOS
     * Spring genera automáticamente el código SQL
     * solo leyendo el nombre del método
     */
    
    /**
     * Busca tareas por estado (completadas o pendientes)
     * SQL equivalente: SELECT * FROM tasks WHERE completed = ?
     */
    List<Task> findByCompleted(Boolean completed);
    
    /**
     * Busca tareas por prioridad
     * SQL equivalente: SELECT * FROM tasks WHERE priority = ?
     */
    List<Task> findByPriority(String priority);
    
    /**
     * Busca tareas cuyo título contenga un texto
     * SQL equivalente: SELECT * FROM tasks WHERE title LIKE %texto%
     * Útil para hacer búsquedas
     */
    List<Task> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Busca tareas completadas con cierta prioridad
     * SQL equivalente: SELECT * FROM tasks WHERE completed = ? AND priority = ?
     */
    List<Task> findByCompletedAndPriority(Boolean completed, String priority);
    
    /**
     * Cuenta cuántas tareas están completadas
     * SQL equivalente: SELECT COUNT(*) FROM tasks WHERE completed = ?
     */
    Long countByCompleted(Boolean completed);
}
