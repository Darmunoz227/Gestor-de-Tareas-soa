# 🚀 INICIO RÁPIDO

## ✅ Tu proyecto está listo!

### 📁 Ubicación:
```
C:\Users\Diego\task-manager-soa
```

---

## 🎯 PASOS PARA EJECUTAR (MUY IMPORTANTE)

### **Opción 1: Usando IntelliJ IDEA (MÁS FÁCIL) ⭐ RECOMENDADO**

1. **Descargar IntelliJ IDEA Community (GRATIS):**
   - https://www.jetbrains.com/idea/download/
   - Instala la versión Community (es gratis)

2. **Abrir el proyecto:**
   - IntelliJ IDEA → Open → Selecciona `task-service`
   - Espera a que descargue las dependencias (primera vez tarda 2-5 minutos)

3. **Ejecutar:**
   - Busca `TaskServiceApplication.java`
   - Click derecho → Run 'TaskServiceApplication'
   - ¡Listo! El servidor estará en http://localhost:8080

---

### **Opción 2: Usando VS Code con Extension Pack for Java**

1. **Instalar extensiones en VS Code:**
   - Extension Pack for Java (Microsoft)
   - Spring Boot Extension Pack

2. **Abrir el proyecto:**
   - VS Code → Open Folder → `task-manager-soa`

3. **Ejecutar:**
   - Presiona `F5` o busca el botón "Run" arriba de `main()`
   - O usa el Spring Boot Dashboard

---

### **Opción 3: Terminal (si Maven está instalado)**

```powershell
cd C:\Users\Diego\task-manager-soa\task-service
mvn clean install
mvn spring-boot:run
```

---

## 🌐 DESPUÉS DE INICIAR EL BACKEND

### 1. Verificar que el backend funciona:
Abre tu navegador en:
```
http://localhost:8080/api/tasks
```

Deberías ver un JSON con 5 tareas de ejemplo.

### 2. Abrir el Frontend:
- Navega a: `C:\Users\Diego\task-manager-soa\frontend`
- Doble click en `index.html`
- Se abre en tu navegador

### 3. Probar la aplicación:
- ✅ Crear nuevas tareas
- ✅ Marcar como completadas
- ✅ Buscar tareas
- ✅ Filtrar por estado
- ✅ Eliminar tareas

---

## 📚 ARCHIVOS IMPORTANTES

| Archivo | Qué hace |
|---------|----------|
| `README.md` | Documentación completa del proyecto |
| `GUIA-PRINCIPIANTES.md` | Explicación detallada de conceptos |
| `TaskServiceApplication.java` | Archivo principal para ejecutar |
| `TaskController.java` | Define los endpoints de la API |
| `TaskService.java` | Lógica de negocio |
| `Task.java` | Modelo de datos |
| `index.html` | Interfaz web |
| `application.properties` | Configuración del backend |
| `data.sql` | Datos de ejemplo iniciales |

---

## 🔍 VERIFICAR QUE TODO ESTÁ BIEN

### ✅ Java instalado:
```powershell
java -version
```
Debe mostrar: `openjdk version "17.0.9"`

### ✅ Backend corriendo:
```powershell
curl http://localhost:8080/api/tasks
```
O abre en el navegador

### ✅ Ver base de datos:
Abre en el navegador:
```
http://localhost:8080/h2-console
```

Configuración:
- JDBC URL: `jdbc:h2:mem:taskdb`
- User: `sa`
- Password: (vacío)

---

## ❓ PROBLEMAS COMUNES

### Problema: "Puerto 8080 ya en uso"
**Solución:**
```powershell
# Ver qué está usando el puerto
Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess

# Matar el proceso
Stop-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess -Force
```

### Problema: "Cannot find symbol Task"
**Solución:** 
- Asegúrate de abrir la carpeta `task-service` como proyecto
- No abrir la carpeta raíz `task-manager-soa` en el IDE

### Problema: Frontend no se conecta al backend
**Solución:**
- Verifica que el backend esté corriendo en `http://localhost:8080`
- Abre la consola del navegador (F12) para ver errores

---

## 🎓 APRENDIZAJE PASO A PASO

### Día 1: Familiarización (HOY)
- [x] Crear el proyecto ✅
- [ ] Ejecutar el backend
- [ ] Abrir el frontend
- [ ] Crear una tarea de prueba

### Día 2: Entender el código
- [ ] Leer GUIA-PRINCIPIANTES.md
- [ ] Identificar las capas (Controller, Service, Repository)
- [ ] Entender cómo fluyen los datos

### Día 3: Modificar
- [ ] Cambiar un mensaje en el frontend
- [ ] Agregar un campo nuevo (ejemplo: "categoria")
- [ ] Crear un nuevo endpoint

### Día 4: Expandir
- [ ] Crear un UserService
- [ ] Implementar autenticación básica
- [ ] Usar MySQL en lugar de H2

---

## 📞 COMANDOS ÚTILES

### Ver logs del backend:
Aparecen automáticamente en la terminal donde ejecutaste la app

### Limpiar y recompilar:
```powershell
cd task-service
mvn clean package
```

### Generar archivo JAR ejecutable:
```powershell
mvn clean package
java -jar target/task-service-1.0.0.jar
```

---

## 🎉 ¡LISTO PARA COMENZAR!

Tu proyecto tiene:
- ✅ Backend completo con 9 endpoints
- ✅ Frontend interactivo con diseño moderno
- ✅ Base de datos H2 con 5 tareas de ejemplo
- ✅ Documentación completa
- ✅ Guía para principiantes

**Siguiente paso:** Ejecutar el backend con tu IDE favorito

---

## 📖 RECURSOS ADICIONALES

- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **JPA Tutorial:** https://www.baeldung.com/learn-jpa-hibernate
- **REST API Guide:** https://restfulapi.net/
- **Java Basics:** https://docs.oracle.com/javase/tutorial/

---

**¿Necesitas ayuda?** Revisa los archivos:
- `README.md` → Documentación técnica
- `GUIA-PRINCIPIANTES.md` → Conceptos explicados

**¡Buena suerte en tu aprendizaje! 🚀**
