# ============================================
# ETAPA 1: CONSTRUCCIÓN (BUILD)
# ============================================
# Usamos Maven con Java 21 para compilar el proyecto
FROM maven:3.9-eclipse-temurin-21 AS builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo pom.xml primero (para aprovechar caché de Docker)
COPY pom.xml .

# Descargamos las dependencias (se cachean si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiamos todo el código fuente
COPY src ./src

# Compilamos el proyecto y creamos el JAR
# -DskipTests: No ejecuta los tests para agilizar
RUN mvn clean package -DskipTests

# ============================================
# ETAPA 2: EJECUCIÓN (RUNTIME)
# ============================================
# Usamos una imagen más ligera solo con Java para ejecutar
FROM eclipse-temurin:21-jre-alpine

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el JAR compilado de la etapa anterior
# El JAR está en target/ después de hacer mvn package
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto donde corre la aplicación
EXPOSE 9090

# Comando para ejecutar la aplicación
# --spring.profiles.active=docker: Activa el perfil de Docker
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
