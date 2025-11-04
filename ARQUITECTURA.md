# 🏗️ ARQUITECTURA DEL SISTEMA

## 📊 Diagrama de Alto Nivel

```
┌─────────────────────────────────────────────────────────────────┐
│                         USUARIO                                  │
│                    (Navegador Web)                               │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ Interactúa con
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                          │
│                                                                   │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │              frontend/index.html                          │  │
│  │                                                           │  │
│  │  • HTML5 (Estructura)                                    │  │
│  │  • CSS3 (Estilos)                                        │  │
│  │  • JavaScript (Lógica del cliente)                       │  │
│  │                                                           │  │
│  │  Funciones:                                              │  │
│  │  - Renderizar interfaz                                   │  │
│  │  - Capturar eventos de usuario                           │  │
│  │  - Hacer peticiones HTTP al backend                      │  │
│  │  - Actualizar la vista dinámicamente                     │  │
│  └───────────────────────────────────────────────────────────┘  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ HTTP Requests
                             │ (GET, POST, PUT, PATCH, DELETE)
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                    CAPA DE CONTROLADORES                         │
│                        (REST API)                                │
│                                                                   │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │         TaskController.java                               │  │
│  │         @RestController                                   │  │
│  │         @RequestMapping("/api/tasks")                     │  │
│  │                                                           │  │
│  │  Endpoints:                                              │  │
│  │  • GET    /api/tasks          → Listar todas            │  │
│  │  • GET    /api/tasks/{id}     → Obtener una             │  │
│  │  • POST   /api/tasks          → Crear nueva             │  │
│  │  • PUT    /api/tasks/{id}     → Actualizar              │  │
│  │  • PATCH  /api/tasks/{id}/toggle → Cambiar estado       │  │
│  │  • DELETE /api/tasks/{id}     → Eliminar                │  │
│  │  • GET    /api/tasks/status/{completed} → Filtrar       │  │
│  │  • GET    /api/tasks/search?title=X → Buscar            │  │
│  │  • GET    /api/tasks/stats    → Estadísticas            │  │
│  └───────────────────────────────────────────────────────────┘  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ Delega al servicio
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                  CAPA DE LÓGICA DE NEGOCIO                       │
│                       (Servicios)                                │
│                                                                   │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │         TaskService.java                                  │  │
│  │         @Service                                          │  │
│  │                                                           │  │
│  │  Responsabilidades:                                      │  │
│  │  • Validar datos de entrada                             │  │
│  │  • Aplicar reglas de negocio                            │  │
│  │  • Coordinar operaciones complejas                       │  │
│  │  • Manejar transacciones                                 │  │
│  │                                                           │  │
│  │  Métodos principales:                                    │  │
│  │  - getAllTasks()                                         │  │
│  │  - getTaskById(id)                                       │  │
│  │  - createTask(task)                                      │  │
│  │  - updateTask(id, task)                                  │  │
│  │  - toggleTaskStatus(id)                                  │  │
│  │  - deleteTask(id)                                        │  │
│  │  - getTasksByStatus(completed)                           │  │
│  │  - searchTasksByTitle(title)                             │  │
│  │  - getStatistics()                                       │  │
│  └───────────────────────────────────────────────────────────┘  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ Usa el repositorio
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                  CAPA DE ACCESO A DATOS                          │
│                      (Repositorios)                              │
│                                                                   │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │         TaskRepository.java                               │  │
│  │         @Repository                                       │  │
│  │         extends JpaRepository<Task, Long>                 │  │
│  │                                                           │  │
│  │  Métodos automáticos (JPA):                              │  │
│  │  • save(task)         → INSERT/UPDATE                    │  │
│  │  • findById(id)       → SELECT WHERE id                  │  │
│  │  • findAll()          → SELECT *                         │  │
│  │  • deleteById(id)     → DELETE WHERE id                  │  │
│  │  • count()            → COUNT(*)                         │  │
│  │  • existsById(id)     → EXISTS                           │  │
│  │                                                           │  │
│  │  Métodos personalizados:                                 │  │
│  │  • findByCompleted(boolean)                              │  │
│  │  • findByPriority(string)                                │  │
│  │  • findByTitleContainingIgnoreCase(string)               │  │
│  │  • countByCompleted(boolean)                             │  │
│  └───────────────────────────────────────────────────────────┘  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ JDBC/Hibernate
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                  CAPA DE PERSISTENCIA                            │
│                     (Base de Datos)                              │
│                                                                   │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │         H2 Database (En Memoria)                          │  │
│  │         jdbc:h2:mem:taskdb                                │  │
│  │                                                           │  │
│  │  Tabla: TASKS                                            │  │
│  │  ┌────────────────────────────────────────────────────┐  │  │
│  │  │ id          BIGINT (PK, AUTO_INCREMENT)           │  │  │
│  │  │ title       VARCHAR(100) NOT NULL                 │  │  │
│  │  │ description VARCHAR(500)                          │  │  │
│  │  │ completed   BOOLEAN NOT NULL DEFAULT FALSE        │  │  │
│  │  │ priority    VARCHAR(10) NOT NULL DEFAULT 'MEDIUM' │  │  │
│  │  │ created_at  TIMESTAMP NOT NULL                    │  │  │
│  │  │ updated_at  TIMESTAMP                             │  │  │
│  │  └────────────────────────────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🔄 Flujo de una Petición Completa

### Ejemplo: Crear una nueva tarea

```
1️⃣ USUARIO
   │
   ├─► Hace clic en botón "Crear Tarea"
   │
   └─► Ingresa: "Estudiar Java"

2️⃣ FRONTEND (JavaScript)
   │
   ├─► Captura el evento del botón
   ├─► Lee los valores del formulario
   ├─► Crea objeto JSON:
   │   {
   │     "title": "Estudiar Java",
   │     "description": "Aprender POO",
   │     "priority": "HIGH"
   │   }
   │
   └─► Envía petición HTTP POST

        fetch('http://localhost:8080/api/tasks', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(taskData)
        })

3️⃣ CONTROLADOR (TaskController.java)
   │
   ├─► Recibe la petición HTTP POST
   ├─► Endpoint: @PostMapping
   │
        @PostMapping
        public ResponseEntity<Task> createTask(@RequestBody Task task) {
            Task created = taskService.createTask(task);
            return ResponseEntity.status(201).body(created);
        }
   │
   └─► Delega al servicio

4️⃣ SERVICIO (TaskService.java)
   │
   ├─► Recibe el objeto Task
   ├─► Aplica validaciones:
   │   • ¿Tiene título? ✅
   │   • ¿Título no está vacío? ✅
   │
   ├─► Establece valores por defecto:
   │   • completed = false
   │   • Si no hay prioridad → "MEDIUM"
   │
        public Task createTask(Task task) {
            if (task.getTitle().isEmpty()) {
                throw new Exception("Título obligatorio");
            }
            task.setCompleted(false);
            return taskRepository.save(task);
        }
   │
   └─► Llama al repositorio para guardar

5️⃣ REPOSITORIO (TaskRepository.java)
   │
   ├─► Recibe el objeto Task
   ├─► Usa JPA para convertir a SQL
   │
        save(task) → Hibernate genera:
        
        INSERT INTO tasks 
        (title, description, completed, priority, created_at, updated_at)
        VALUES 
        ('Estudiar Java', 'Aprender POO', false, 'HIGH', NOW(), NOW())
   │
   └─► Envía query a la base de datos

6️⃣ BASE DE DATOS (H2)
   │
   ├─► Ejecuta el INSERT
   ├─► Genera ID automático: 6
   ├─► Guarda el registro
   │
   └─► Retorna el registro guardado con ID

7️⃣ VUELTA - Del repositorio al servicio
   │
   └─► Task con ID: 6

8️⃣ VUELTA - Del servicio al controlador
   │
   └─► Task completa

9️⃣ VUELTA - Del controlador al frontend
   │
   ├─► HTTP Status: 201 CREATED
   └─► Body: JSON con la tarea creada
       {
         "id": 6,
         "title": "Estudiar Java",
         "description": "Aprender POO",
         "completed": false,
         "priority": "HIGH",
         "createdAt": "2025-11-04T13:45:00",
         "updatedAt": "2025-11-04T13:45:00"
       }

🔟 FRONTEND (JavaScript)
   │
   ├─► Recibe la respuesta JSON
   ├─► Extrae los datos de la tarea
   ├─► Actualiza la interfaz:
   │   • Limpia el formulario
   │   • Recarga la lista de tareas
   │   • Muestra la nueva tarea en pantalla
   │
   └─► Usuario ve la tarea creada ✅
```

---

## 🎯 Principios de Arquitectura Aplicados

### 1. **Separación de Responsabilidades (SoC)**
Cada capa tiene una función específica:
- Controller → Maneja HTTP
- Service → Lógica de negocio
- Repository → Acceso a datos

### 2. **Inyección de Dependencias (DI)**
Spring automáticamente conecta las clases:
```java
@Autowired
private TaskService taskService;  // Spring lo inyecta
```

### 3. **Inversión de Control (IoC)**
No creas objetos manualmente, Spring los gestiona.

### 4. **Single Responsibility Principle (SRP)**
Cada clase hace una sola cosa bien.

### 5. **RESTful Architecture**
API sigue los principios REST:
- Recursos (tasks)
- Métodos HTTP estándar
- Respuestas JSON
- Códigos de estado HTTP

---

## 📦 Componentes del Sistema

| Componente | Tecnología | Puerto | Propósito |
|------------|------------|--------|-----------|
| Frontend | HTML/CSS/JS | - | Interfaz de usuario |
| Backend API | Spring Boot | 8080 | Servicios REST |
| Base de Datos | H2 | 8080 | Persistencia |
| H2 Console | Web UI | 8080 | Admin de BD |

---

## 🔐 Flujo de Datos

```
CREATE:  Frontend → Controller → Service → Repository → Database
READ:    Frontend ← Controller ← Service ← Repository ← Database
UPDATE:  Frontend → Controller → Service → Repository → Database
DELETE:  Frontend → Controller → Service → Repository → Database
```

---

## 🚀 Escalabilidad Futura (SOA Completo)

En una arquitectura SOA completa, tendrías:

```
Frontend
    ↓
┌─────────────┬──────────────┬──────────────┐
│             │              │              │
Task Service  User Service  Auth Service  Notification Service
    ↓             ↓              ↓              ↓
  TaskDB       UserDB        AuthDB        QueueDB
```

Cada servicio:
- Corre en su propio puerto
- Tiene su propia base de datos
- Es independiente
- Se comunica con otros via HTTP/REST

**Ejemplo:**
- Task Service: `localhost:8081`
- User Service: `localhost:8082`
- Auth Service: `localhost:8083`

---

**Este proyecto es la base perfecta para entender estos conceptos! 🎓**
