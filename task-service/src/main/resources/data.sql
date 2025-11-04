-- ============================================
-- DATOS DE EJEMPLO PARA LA BASE DE DATOS H2
-- ============================================
-- Este archivo inserta tareas de ejemplo automáticamente
-- cuando inicias la aplicación por primera vez

-- Tarea 1: Alta prioridad, pendiente
INSERT INTO tasks (title, description, completed, priority, created_at, updated_at) 
VALUES ('Estudiar Spring Boot', 'Aprender los conceptos básicos de Spring Boot y JPA', false, 'HIGH', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Tarea 2: Media prioridad, completada
INSERT INTO tasks (title, description, completed, priority, created_at, updated_at) 
VALUES ('Configurar proyecto', 'Crear estructura de carpetas y archivos iniciales', true, 'MEDIUM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Tarea 3: Baja prioridad, pendiente
INSERT INTO tasks (title, description, completed, priority, created_at, updated_at) 
VALUES ('Leer documentación', 'Revisar la documentación oficial de Spring', false, 'LOW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Tarea 4: Media prioridad, pendiente
INSERT INTO tasks (title, description, completed, priority, created_at, updated_at) 
VALUES ('Probar API REST', 'Hacer pruebas de todos los endpoints con Postman', false, 'MEDIUM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Tarea 5: Alta prioridad, completada
INSERT INTO tasks (title, description, completed, priority, created_at, updated_at) 
VALUES ('Instalar Java y VS Code', 'Configurar entorno de desarrollo', true, 'HIGH', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
