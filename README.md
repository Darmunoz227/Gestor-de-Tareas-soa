# 📋 Gestor de Tareas - Arquitectura SOA

## 🎯 ¿Qué es este proyecto?

Un sistema de gestión de tareas construido con **arquitectura SOA** (Service-Oriented Architecture) usando:
- **Backend:** Spring Boot (Java 17)
- **Frontend:** HTML + CSS + JavaScript
- **Base de Datos:** H2 (en memoria)

---

## 📁 Estructura del Proyecto

```
task-manager-soa/
├── task-service/              # Servicio backend (Spring Boot)
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/taskservice/
│   │       │       ├── TaskServiceApplication.java    # Clase principal
│   │       │       ├── controller/
│   │       │       │   └── TaskController.java        # Endpoints REST
│   │       │       ├── service/
│   │       │       │   └── TaskService.java           # Lógica de negocio
│   │       │       ├── repository/
│   │       │       │   └── TaskRepository.java        # Acceso a datos
│   │       │       └── model/
│   │       │           └── Task.java                  # Entidad Task
│   │       └── resources/
│   │           └── application.properties             # Configuración
│   └── pom.xml                                        # Dependencias Maven
│
└── frontend/                  # Interfaz web
    └── index.html             # Aplicación web completa
```

---

## 🚀 Cómo Ejecutar el Proyecto

### **Paso 1: Abrir en VS Code**

1. Abre VS Code
2. Ve a: `File` → `Open Folder`
3. Selecciona la carpeta: `C:\Users\Diego\task-manager-soa`

### **Paso 2: Ejecutar el Backend**

Opción A - Usando Java directamente (recomendado para principiantes):

```bash
# Navega al directorio del servicio
cd task-service

# Compila el proyecto (primera vez solamente)
javac -d bin src/main/java/com/example/taskservice/*.java

# Ejecuta la aplicación
java -cp bin com.example.taskservice.TaskServiceApplication
```

Opción B - Usando Maven (si ya tienes Maven instalado):

```bash
cd task-service
mvn spring-boot:run
```

### **Paso 3: Abrir el Frontend**

1. Navega a: `C:\Users\Diego\task-manager-soa\frontend`
2. Haz doble clic en `index.html`
3. Se abrirá en tu navegador web

**O** desde VS Code:
- Click derecho en `index.html`
- Selecciona "Open with Live Server" (si tienes la extensión instalada)

---

## 🌐 URLs Importantes

Una vez que la aplicación esté corriendo:

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **API REST** | http://localhost:8080/api/tasks | Endpoint principal de tareas |
| **H2 Console** | http://localhost:8080/h2-console | Interfaz de base de datos |
| **Frontend** | Abrir index.html en navegador | Interfaz de usuario |

### **Configuración de H2 Console:**
- JDBC URL: `jdbc:h2:mem:taskdb`
- User Name: `sa`
- Password: (dejar en blanco)

---

## 📚 Endpoints de la API

### **1. Obtener todas las tareas**
```http
GET http://localhost:8080/api/tasks
```

### **2. Obtener una tarea por ID**
```http
GET http://localhost:8080/api/tasks/1
```

### **3. Crear nueva tarea**
```http
POST http://localhost:8080/api/tasks
Content-Type: application/json

{
  "title": "Mi primera tarea",
  "description": "Descripción de la tarea",
  "priority": "HIGH"
}
```

### **4. Actualizar tarea**
```http
PUT http://localhost:8080/api/tasks/1
Content-Type: application/json

{
  "title": "Título actualizado",
  "completed": true
}
```

### **5. Cambiar estado (completada/pendiente)**
```http
PATCH http://localhost:8080/api/tasks/1/toggle
```

### **6. Eliminar tarea**
```http
DELETE http://localhost:8080/api/tasks/1
```

### **7. Filtrar por estado**
```http
GET http://localhost:8080/api/tasks/status/true   # Completadas
GET http://localhost:8080/api/tasks/status/false  # Pendientes
```

### **8. Buscar por título**
```http
GET http://localhost:8080/api/tasks/search?title=comprar
```

### **9. Estadísticas**
```http
GET http://localhost:8080/api/tasks/stats
```

---

## 🧪 Probar la API con PowerShell

Puedes probar los endpoints sin el frontend:

### Obtener todas las tareas:
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/tasks" -Method GET
```

### Crear una tarea:
```powershell
$body = @{
    title = "Tarea de prueba"
    description = "Creada desde PowerShell"
    priority = "HIGH"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/tasks" -Method POST -Body $body -ContentType "application/json"
```

### Eliminar tarea (ID 1):
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/tasks/1" -Method DELETE
```

---

## 🎓 Conceptos para Aprender

### **1. Arquitectura del Proyecto**

```
Frontend (HTML/JS)
      ↓ HTTP Requests
  Controller (REST API)
      ↓
   Service (Lógica)
      ↓
  Repository (Base de Datos)
      ↓
   Database (H2)
```

### **2. Capas del Backend**

- **Controller:** Recibe peticiones HTTP
- **Service:** Contiene la lógica de negocio
- **Repository:** Accede a la base de datos
- **Model/Entity:** Define la estructura de datos

### **3. Métodos HTTP**

- **GET:** Obtener datos
- **POST:** Crear nuevos datos
- **PUT:** Actualizar datos completos
- **PATCH:** Actualizar datos parciales
- **DELETE:** Eliminar datos

---

## 🔧 Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje de programación |
| Spring Boot | 3.1.5 | Framework backend |
| Spring Web | 3.1.5 | APIs REST |
| Spring Data JPA | 3.1.5 | Persistencia de datos |
| H2 Database | Runtime | Base de datos en memoria |
| Lombok | Latest | Reduce código repetitivo |
| HTML5 | - | Estructura del frontend |
| CSS3 | - | Estilos visuales |
| JavaScript | ES6+ | Lógica del frontend |

---

## 📖 Próximos Pasos para Aprender

1. **Explora el código:**
   - Lee los comentarios en cada archivo
   - Identifica cómo se conectan las clases

2. **Experimenta:**
   - Crea nuevas tareas desde el frontend
   - Prueba los filtros y búsquedas
   - Observa los logs en la consola

3. **Modifica:**
   - Agrega un campo "dueño" a las tareas
   - Cambia los colores del frontend
   - Agrega un campo "fecha límite"

4. **Expande:**
   - Crea un User Service (servicio de usuarios)
   - Implementa autenticación
   - Usa MySQL en lugar de H2

---

## ❓ Troubleshooting

### Problema: "Cannot connect to localhost:8080"
**Solución:** Verifica que el backend esté corriendo. Revisa la consola.

### Problema: "CORS error"
**Solución:** Ya está configurado en `@CrossOrigin(origins = "*")` en el Controller.

### Problema: "Package does not exist"
**Solución:** Asegúrate de estar en el directorio correcto y que la estructura de carpetas sea exacta.

---

## 📞 Comandos Útiles

### Ver procesos Java corriendo:
```powershell
Get-Process java
```

### Matar proceso en puerto 8080:
```powershell
Stop-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess -Force
```

### Verificar Java instalado:
```powershell
java -version
```

---

## 🎉 ¡Listo!

Tu proyecto está configurado. Ahora puedes:
1. ✅ Ejecutar el backend
2. ✅ Abrir el frontend
3. ✅ Crear, editar y eliminar tareas
4. ✅ Aprender cómo funciona SOA en Java

**¡Disfruta aprendiendo! 🚀**
