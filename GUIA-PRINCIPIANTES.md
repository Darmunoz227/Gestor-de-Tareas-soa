# 🎓 GUÍA PARA PRINCIPIANTES - Entendiendo el Código

## 📌 Introducción

Esta guía te explica **línea por línea** qué hace cada parte del código, usando ejemplos del mundo real.

---

## 1️⃣ ¿Qué es una CLASE en Java?

**Analogía:** Una clase es como un **molde para hacer galletas**.

```java
public class Task {
    private String title;
    private Boolean completed;
}
```

- `Task` es el molde
- Cada tarea individual (como "Comprar leche") es una **galleta hecha con ese molde**

---

## 2️⃣ ¿Qué son las ANOTACIONES (@)?

**Analogía:** Son **etiquetas mágicas** que le dicen a Spring cómo usar tus clases.

### Ejemplo:

```java
@Entity  // "Esta clase representa una tabla en la base de datos"
@Table(name = "tasks")  // "La tabla se llama 'tasks'"
public class Task {
    @Id  // "Este campo es la clave primaria"
    @GeneratedValue  // "El valor se genera automáticamente"
    private Long id;
}
```

**¿Por qué es "mágico"?**
- Sin estas etiquetas, tendrías que escribir SQL manualmente
- Con ellas, Spring lo hace automáticamente

---

## 3️⃣ ENTIDAD (Task.java)

### ¿Qué es?
Una **representación de una tabla** en la base de datos.

### Ejemplo Visual:

**Código Java:**
```java
@Entity
public class Task {
    @Id
    private Long id;
    private String title;
    private Boolean completed;
}
```

**Se convierte en esta tabla:**

| id | title | completed |
|----|-------|-----------|
| 1  | Comprar leche | false |
| 2  | Hacer ejercicio | true |

---

## 4️⃣ REPOSITORIO (TaskRepository.java)

### ¿Qué es?
Es tu **asistente para hablar con la base de datos**.

### Analogía:
Imagina que la base de datos es una **biblioteca**:
- El repositorio es el **bibliotecario**
- Tú le pides: "Dame el libro con ID 5"
- El bibliotecario lo busca y te lo trae

### Código:

```java
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring genera automáticamente estos métodos:
    
    save(task)        // "Guardar este libro en la biblioteca"
    findById(id)      // "Buscar el libro número 5"
    findAll()         // "Dame todos los libros"
    deleteById(id)    // "Eliminar el libro número 3"
}
```

**¡No necesitas escribir código SQL! Spring lo hace por ti.**

---

## 5️⃣ SERVICIO (TaskService.java)

### ¿Qué es?
Contiene las **reglas de tu negocio**.

### Analogía:
Un servicio es como un **chef de cocina**:
- Recibe los ingredientes (datos)
- Aplica recetas (reglas de negocio)
- Entrega el plato terminado (resultado)

### Ejemplo:

```java
@Service
public class TaskService {
    
    public Task createTask(Task task) {
        // REGLA 1: El título no puede estar vacío
        if (task.getTitle().isEmpty()) {
            throw new Exception("¡Necesitas un título!");
        }
        
        // REGLA 2: Por defecto, las tareas están pendientes
        task.setCompleted(false);
        
        // Guardar en la base de datos
        return repository.save(task);
    }
}
```

---

## 6️⃣ CONTROLADOR (TaskController.java)

### ¿Qué es?
Es la **puerta de entrada** a tu aplicación.

### Analogía:
El controlador es como un **mesero en un restaurante**:
- Recibe pedidos (peticiones HTTP)
- Los lleva a la cocina (servicio)
- Trae la comida (respuesta)

### Ejemplo:

```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @GetMapping  // Cliente hace pedido: "Dame todas las tareas"
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();  // Mesero va a la cocina
    }
    
    @PostMapping  // Cliente hace pedido: "Crea una tarea nueva"
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
}
```

---

## 7️⃣ FLUJO COMPLETO DE UNA PETICIÓN

### Ejemplo: Crear una tarea

```
1. FRONTEND (JavaScript)
   Usuario hace click en "Crear Tarea"
   ↓
   fetch('http://localhost:8080/api/tasks', {
       method: 'POST',
       body: JSON.stringify({ title: "Estudiar Java" })
   })

2. CONTROLADOR (TaskController.java)
   Recibe la petición HTTP POST
   ↓
   @PostMapping
   public Task createTask(@RequestBody Task task) {
       return taskService.createTask(task);
   }

3. SERVICIO (TaskService.java)
   Aplica reglas de negocio
   ↓
   public Task createTask(Task task) {
       if (task.getTitle().isEmpty()) {
           throw new Exception("Título obligatorio");
       }
       return taskRepository.save(task);
   }

4. REPOSITORIO (TaskRepository.java)
   Guarda en la base de datos
   ↓
   save(task) → INSERT INTO tasks VALUES (...)

5. BASE DE DATOS
   Guarda el registro y genera un ID
   ↓
   Retorna: { id: 1, title: "Estudiar Java", completed: false }

6. RESPUESTA
   Viaja de vuelta al frontend
   ↓
   Frontend muestra la nueva tarea en pantalla
```

---

## 8️⃣ CONCEPTOS CLAVE

### 🔹 **HTTP Methods (Métodos HTTP)**

| Método | Propósito | Ejemplo |
|--------|-----------|---------|
| GET | Obtener datos | Ver lista de tareas |
| POST | Crear nuevo | Crear una tarea |
| PUT | Actualizar todo | Cambiar toda la tarea |
| PATCH | Actualizar parcial | Solo cambiar el estado |
| DELETE | Eliminar | Borrar una tarea |

### 🔹 **JSON (JavaScript Object Notation)**

Es el lenguaje que usan el frontend y backend para comunicarse.

**Ejemplo:**
```json
{
    "id": 1,
    "title": "Comprar leche",
    "description": "Ir al supermercado",
    "completed": false,
    "priority": "HIGH"
}
```

Es como una carta que envías y recibes entre el frontend y backend.

### 🔹 **REST API**

Una API REST es como un **menú de restaurante**:
- Tiene opciones disponibles (endpoints)
- Cada opción tiene un nombre (URL)
- Puedes pedir cosas específicas (parámetros)

**Ejemplo de menú (endpoints):**
```
GET  /api/tasks          → "Dame todas las tareas"
GET  /api/tasks/1        → "Dame la tarea número 1"
POST /api/tasks          → "Crea una nueva tarea"
PUT  /api/tasks/1        → "Actualiza la tarea número 1"
DELETE /api/tasks/1      → "Elimina la tarea número 1"
```

---

## 9️⃣ SINTAXIS JAVA - Lo Básico

### 🔸 Variables

```java
String nombre = "Diego";     // Texto
int edad = 25;               // Número entero
boolean esEstudiante = true; // Verdadero o falso
Long id = 1L;                // Número largo (para IDs)
```

### 🔸 Métodos (Funciones)

```java
// Definir un método
public String saludar(String nombre) {
    return "Hola " + nombre;
}

// Usar el método
String mensaje = saludar("Diego");  // "Hola Diego"
```

### 🔸 Condicionales

```java
if (tarea.getCompleted()) {
    System.out.println("✅ Tarea completada");
} else {
    System.out.println("⏳ Tarea pendiente");
}
```

### 🔸 Listas

```java
List<Task> tareas = new ArrayList<>();
tareas.add(nuevaTarea);           // Agregar
Task primera = tareas.get(0);     // Obtener
int cantidad = tareas.size();     // Contar
```

---

## 🔟 ARQUITECTURA EN CAPAS

```
┌─────────────────────────────────────┐
│         FRONTEND (HTML/JS)          │  ← Lo que ve el usuario
│         index.html                  │
└──────────────┬──────────────────────┘
               │ HTTP Requests
┌──────────────▼──────────────────────┐
│         CONTROLLER                  │  ← Recibe peticiones
│         TaskController.java         │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         SERVICE                     │  ← Lógica de negocio
│         TaskService.java            │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         REPOSITORY                  │  ← Acceso a datos
│         TaskRepository.java         │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         DATABASE (H2)               │  ← Almacena los datos
│         Tabla: tasks                │
└─────────────────────────────────────┘
```

**¿Por qué separar en capas?**
- Más organizado
- Más fácil de mantener
- Puedes cambiar una capa sin afectar las otras

---

## 1️⃣1️⃣ GLOSARIO DE TÉRMINOS

| Término | Significado | Ejemplo |
|---------|-------------|---------|
| **Entity** | Clase que representa una tabla | Task.java |
| **Controller** | Maneja peticiones HTTP | TaskController.java |
| **Service** | Contiene lógica de negocio | TaskService.java |
| **Repository** | Accede a la base de datos | TaskRepository.java |
| **DTO** | Objeto para transferir datos | TaskDTO |
| **Endpoint** | URL de la API | /api/tasks |
| **HTTP Status** | Código de respuesta | 200 OK, 404 Not Found |
| **JSON** | Formato de datos | {"id": 1} |
| **CRUD** | Crear, Leer, Actualizar, Eliminar | Operaciones básicas |
| **JPA** | Java Persistence API | Para trabajar con BD |
| **Hibernate** | Implementación de JPA | ORM (Object-Relational Mapping) |

---

## 1️⃣2️⃣ PRÓXIMOS PASOS

### 📚 Para seguir aprendiendo:

1. **Ejecuta el proyecto** y observa cómo funciona
2. **Lee los comentarios** en cada archivo .java
3. **Modifica algo pequeño** (ej: cambiar un mensaje)
4. **Agrega un campo nuevo** (ej: "fechaLimite")
5. **Crea un nuevo endpoint** (ej: /api/tasks/important)

### 🎯 Ejercicios sugeridos:

- [ ] Agregar campo "dueño" a las tareas
- [ ] Crear filtro por prioridad en el frontend
- [ ] Agregar fecha de vencimiento
- [ ] Implementar categorías de tareas
- [ ] Agregar paginación a la lista

---

## ❓ Preguntas Frecuentes

**P: ¿Por qué usar Spring Boot?**
R: Porque hace el 80% del trabajo por ti. Sin Spring, tendrías que configurar todo manualmente.

**P: ¿Qué es JPA?**
R: Es una herramienta que convierte objetos Java en tablas de base de datos automáticamente.

**P: ¿Por qué usar H2?**
R: Para aprender es perfecta porque no necesitas instalar nada. Para producción usarías MySQL o PostgreSQL.

**P: ¿Qué son las anotaciones @?**
R: Son instrucciones especiales que le dicen a Spring cómo usar tus clases.

---

**¡Felicidades! Ya entiendes los conceptos básicos. 🎉**

Recuerda: **La práctica hace al maestro.** Experimenta, rompe cosas, y aprende de los errores.
