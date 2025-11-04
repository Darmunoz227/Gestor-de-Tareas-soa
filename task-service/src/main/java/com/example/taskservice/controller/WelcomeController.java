package com.example.taskservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador para la página principal
 * Muestra información útil cuando visitas http://localhost:8080
 */
@Controller
public class WelcomeController {
    
    @GetMapping("/")
    @ResponseBody
    public String welcome() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Task Service - API REST</title>
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        max-width: 900px;
                        margin: 50px auto;
                        padding: 20px;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                    }
                    .container {
                        background: white;
                        color: #333;
                        border-radius: 15px;
                        padding: 40px;
                        box-shadow: 0 10px 30px rgba(0,0,0,0.3);
                    }
                    h1 { color: #667eea; margin-top: 0; }
                    h2 { color: #764ba2; border-bottom: 2px solid #667eea; padding-bottom: 10px; }
                    .endpoint {
                        background: #f8f9fa;
                        padding: 15px;
                        margin: 10px 0;
                        border-radius: 8px;
                        border-left: 4px solid #667eea;
                    }
                    .method {
                        display: inline-block;
                        padding: 5px 10px;
                        border-radius: 5px;
                        font-weight: bold;
                        margin-right: 10px;
                    }
                    .get { background: #51cf66; color: white; }
                    .post { background: #4dabf7; color: white; }
                    .put { background: #ffa500; color: white; }
                    .patch { background: #ff6b6b; color: white; }
                    .delete { background: #ff0000; color: white; }
                    code {
                        background: #e9ecef;
                        padding: 2px 6px;
                        border-radius: 3px;
                        font-family: 'Courier New', monospace;
                    }
                    .success { color: #51cf66; font-weight: bold; }
                    .link {
                        display: inline-block;
                        margin: 10px 0;
                        padding: 12px 24px;
                        background: #667eea;
                        color: white;
                        text-decoration: none;
                        border-radius: 8px;
                        font-weight: bold;
                        transition: all 0.3s;
                    }
                    .link:hover {
                        background: #5568d3;
                        transform: translateY(-2px);
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>📋 Task Service API REST</h1>
                    <p class="success">✅ Servicio funcionando correctamente</p>
                    
                    <h2>🌐 Enlaces Rápidos</h2>
                    <a href="/api/tasks" class="link">📋 Ver Todas las Tareas (JSON)</a>
                    <a href="/h2-console" class="link">🗄️ Consola H2 Database</a>
                    <br><br>
                    <p><strong>💡 Frontend:</strong> Abre el archivo <code>frontend/index.html</code> en tu navegador</p>
                    
                    <h2>📡 Endpoints Disponibles</h2>
                    
                    <div class="endpoint">
                        <span class="method get">GET</span>
                        <code>/api/tasks</code>
                        <p>Obtener todas las tareas</p>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method get">GET</span>
                        <code>/api/tasks/{id}</code>
                        <p>Obtener una tarea específica por ID</p>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method post">POST</span>
                        <code>/api/tasks</code>
                        <p>Crear una nueva tarea</p>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method put">PUT</span>
                        <code>/api/tasks/{id}</code>
                        <p>Actualizar una tarea existente</p>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method patch">PATCH</span>
                        <code>/api/tasks/{id}/toggle</code>
                        <p>Alternar estado completado/pendiente</p>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method delete">DELETE</span>
                        <code>/api/tasks/{id}</code>
                        <p>Eliminar una tarea</p>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method get">GET</span>
                        <code>/api/tasks/status/{completed}</code>
                        <p>Filtrar por estado (true/false)</p>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method get">GET</span>
                        <code>/api/tasks/search?title=texto</code>
                        <p>Buscar tareas por título</p>
                    </div>
                    
                    <div class="endpoint">
                        <span class="method get">GET</span>
                        <code>/api/tasks/stats</code>
                        <p>Obtener estadísticas de tareas</p>
                    </div>
                    
                    <h2>🗄️ Base de Datos H2</h2>
                    <p><strong>JDBC URL:</strong> <code>jdbc:h2:mem:taskdb</code></p>
                    <p><strong>Usuario:</strong> <code>sa</code></p>
                    <p><strong>Contraseña:</strong> (vacía)</p>
                    
                    <h2>📚 Documentación</h2>
                    <p>Esta es una API REST construida con Spring Boot para gestión de tareas.</p>
                    <p><strong>Arquitectura:</strong> SOA (Service Oriented Architecture)</p>
                    <p><strong>Java Version:</strong> 21</p>
                    <p><strong>Spring Boot:</strong> 3.1.5</p>
                </div>
            </body>
            </html>
            """;
    }
}
