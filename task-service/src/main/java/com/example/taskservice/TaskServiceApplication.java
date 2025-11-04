package com.example.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================
 * CLASE PRINCIPAL DE LA APLICACIÓN
 * ============================================
 * Esta es la puerta de entrada de tu aplicación.
 * Cuando ejecutas el proyecto, esta clase se ejecuta primero.
 * 
 * @SpringBootApplication es una anotación "mágica" que configura
 * automáticamente Spring Boot.
 */
@SpringBootApplication
public class TaskServiceApplication {
    
    /**
     * Método main - El punto de inicio de cualquier programa Java
     * @param args - argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Este método inicia toda la aplicación Spring Boot
        SpringApplication.run(TaskServiceApplication.class, args);
        
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║   ✅ TASK SERVICE INICIADO EXITOSAMENTE  ║");
        System.out.println("║                                           ║");
        System.out.println("║   🌐 API REST: http://localhost:8080     ║");
        System.out.println("║   📊 H2 Console: http://localhost:8080/h2-console ║");
        System.out.println("╚═══════════════════════════════════════════╝\n");
    }
}
