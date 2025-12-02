# Síntesis del Proceso de Integración Continua y Despliegue

## Proyecto: Gestor-de-Tareas-soa

### 1. Contexto y Objetivo
Este proyecto es una aplicación de gestión de tareas desarrollada en Java (Spring Boot) para el backend y HTML/NGINX para el frontend. El objetivo fue implementar un pipeline de integración continua (CI) usando Jenkins, Docker y Docker Compose, logrando la automatización del build, pruebas y despliegue de la aplicación.

### 2. Pasos Realizados (según el chat y el código)

#### a) Preparación del Entorno Jenkins
- Se inspeccionó el contenedor Jenkins para asegurar que el socket Docker (`/var/run/docker.sock`) estuviera montado, permitiendo a Jenkins ejecutar comandos Docker.
- Se recreó el contenedor Jenkins con los volúmenes y permisos adecuados.
- Se instaló Docker CLI y Docker Compose dentro del contenedor Jenkins para habilitar la construcción y orquestación de contenedores desde los pipelines.

#### b) Configuración de Permisos y Pruebas de Usuario
- Se verificó que el usuario Jenkins tuviera los permisos necesarios para ejecutar builds y acceder a los recursos requeridos.
- Se resolvieron problemas de permisos (403) y se validó la autenticación del usuario.

#### c) Ejecución del Pipeline CI/CD
- Jenkins ejecutó el pipeline definido en el `Jenkinsfile`, que incluye:
  - **Build del backend:**
    - Uso de Maven para descargar dependencias, compilar el código, empaquetar el JAR y reempaquetar con Spring Boot.
    - El proceso mostró logs de descarga de dependencias y terminó con `BUILD SUCCESS`.
  - **Construcción de imágenes Docker:**
    - Dockerfile multi-stage para el backend: primero construye el JAR, luego lo copia a una imagen final lista para producción.
    - Dockerfile para el frontend: copia el `index.html` a una imagen basada en NGINX.
  - **Orquestación con Docker Compose:**
    - Se levantaron los servicios: base de datos Postgres, backend y frontend.
    - Se crearon redes y volúmenes necesarios.
    - Se mapearon los puertos para acceso local (backend: 9090, frontend: 3000, postgres: 5432).
    - Se verificó el estado de los contenedores (`Up` y `Healthy`).

#### d) Validación y Resultados
- El pipeline terminó exitosamente (`Finished: SUCCESS`).
- Los logs mostraron que las imágenes se construyeron y los contenedores se desplegaron correctamente.
- Se proporcionaron comandos para verificar el estado y funcionamiento de la aplicación:
  - `curl http://localhost:3000` (frontend)
  - `curl http://localhost:9090` (backend)
  - `docker compose ps` y `docker compose logs` para diagnóstico.

### 3. Recomendaciones y Aprendizajes
- Quitar la clave `version:` de `docker-compose.yml` para evitar advertencias.
- Añadir healthchecks personalizados para backend y frontend en Docker Compose.
- Mover credenciales sensibles a variables de entorno o secretos.
- Añadir una etapa de pruebas automáticas en el pipeline antes del build de imágenes.
- Aprender sobre Dockerfile multi-stage y optimización de capas para builds más rápidos.

### 4. Conclusión
El proceso realizado permitió automatizar la construcción, prueba y despliegue de la aplicación Gestor-de-Tareas-soa usando Jenkins y Docker. Se resolvieron problemas de permisos, se validó la integración de herramientas y se logró un pipeline funcional de CI/CD, sentando las bases para prácticas DevOps modernas.

---

_Este documento resume los pasos y aprendizajes clave del proceso realizado, útil como bitácora y referencia para futuros proyectos de integración continua._
