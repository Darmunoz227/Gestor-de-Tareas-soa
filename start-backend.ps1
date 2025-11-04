# ============================================
# SCRIPT PARA INICIAR EL BACKEND
# ============================================
# Este script inicia automáticamente el servicio de tareas

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  INICIANDO TASK SERVICE" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Configurar Java 25
Write-Host "☕ Configurando JDK 25..." -ForegroundColor Cyan
$env:JAVA_HOME = "$env:USERPROFILE\.jdks\openjdk-25"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Navegar al directorio del servicio
Set-Location -Path "C:\Users\Diego\task-manager-soa\task-service"

Write-Host "📂 Ubicación: $PWD" -ForegroundColor Green
Write-Host ""

# Verificar Java
Write-Host "☕ Verificando Java..." -ForegroundColor Cyan
$javaVersion = java -version 2>&1 | Select-String "version"
Write-Host $javaVersion -ForegroundColor Green
Write-Host ""

# Verificar si Maven Wrapper existe
if (Test-Path ".\mvnw.cmd") {
    Write-Host "🔨 Ejecutando con Maven Wrapper..." -ForegroundColor Cyan
    .\mvnw.cmd spring-boot:run
} else {
    Write-Host "⚠️  Maven Wrapper no encontrado" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "OPCIONES:" -ForegroundColor White
    Write-Host "1. Instalar Maven manualmente" -ForegroundColor White
    Write-Host "2. Descargar el proyecto desde Spring Initializr" -ForegroundColor White
    Write-Host "3. Abrir el proyecto en IntelliJ IDEA (ejecuta automáticamente)" -ForegroundColor White
    Write-Host ""
    Write-Host "Para crear Maven Wrapper, ejecuta:" -ForegroundColor Cyan
    Write-Host "mvn -N wrapper:wrapper" -ForegroundColor Yellow
    Write-Host ""
    
    # Intentar con Maven instalado
    $mavenExists = Get-Command mvn -ErrorAction SilentlyContinue
    if ($mavenExists) {
        Write-Host "✅ Maven encontrado en el sistema" -ForegroundColor Green
        Write-Host "Ejecutando con Maven..." -ForegroundColor Cyan
        mvn spring-boot:run
    } else {
        Write-Host "❌ Maven no está instalado en el sistema" -ForegroundColor Red
        Write-Host ""
        Write-Host "💡 ALTERNATIVA: Abrir el proyecto en IntelliJ IDEA o Eclipse" -ForegroundColor Yellow
        Write-Host "   Estos IDEs pueden ejecutar Spring Boot sin Maven instalado." -ForegroundColor Yellow
    }
}
