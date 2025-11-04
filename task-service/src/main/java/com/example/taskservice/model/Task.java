package com.example.taskservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ============================================
 * ENTIDAD TASK (Tarea)
 * ============================================
 * Esta clase representa una TAREA en la base de datos.
 * 
 * ANOTACIONES IMPORTANTES:
 * - @Entity: Le dice a Spring que esta clase es una tabla en la BD
 * - @Data: Lombok genera automáticamente getters, setters, toString, etc.
 * - @NoArgsConstructor: Crea un constructor vacío (requerido por JPA)
 * - @AllArgsConstructor: Crea un constructor con todos los parámetros
 */
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    
    /**
     * ID - Identificador único de cada tarea
     * @Id: marca este campo como clave primaria
     * @GeneratedValue: el ID se genera automáticamente (1, 2, 3...)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * TÍTULO de la tarea
     * @Column: configura la columna en la base de datos
     * nullable=false: Este campo es obligatorio
     */
    @Column(nullable = false, length = 100)
    private String title;
    
    /**
     * DESCRIPCIÓN detallada de la tarea
     * Puede ser más larga (500 caracteres)
     */
    @Column(length = 500)
    private String description;
    
    /**
     * ESTADO de la tarea
     * true = completada
     * false = pendiente
     * Por defecto es false (pendiente)
     */
    @Column(nullable = false)
    private Boolean completed = false;
    
    /**
     * PRIORIDAD de la tarea
     * HIGH = alta
     * MEDIUM = media
     * LOW = baja
     */
    @Column(nullable = false)
    private String priority = "MEDIUM";
    
    /**
     * FECHA DE CREACIÓN
     * Se guarda automáticamente cuando se crea la tarea
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * FECHA DE ÚLTIMA MODIFICACIÓN
     * Se actualiza cada vez que modificas la tarea
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Este método se ejecuta ANTES de guardar la tarea en la BD
     * Establece las fechas automáticamente
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Este método se ejecuta ANTES de actualizar la tarea en la BD
     * Actualiza solo la fecha de modificación
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
