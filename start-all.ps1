# ============================================
# SCRIPT MAESTRO - INICIAR TODO
# ============================================
# Este script inicia el backend y abre el frontend automáticamente

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  TASK MANAGER - INICIO COMPLETO" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Configurar Java 25
Write-Host "☕ Configurando JDK 25..." -ForegroundColor Cyan
$env:JAVA_HOME = "$env:USERPROFILE\.jdks\openjdk-25"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Verificar Java
Write-Host "☕ Verificando Java..." -ForegroundColor Yellow
java -version
Write-Host ""

# Navegar al directorio del servicio
Write-Host "📂 Cambiando al directorio del backend..." -ForegroundColor Cyan
Set-Location -Path "$PSScriptRoot\task-service"

# Verificar Maven Wrapper
if (Test-Path ".\mvnw.cmd") {
    Write-Host "✅ Maven Wrapper encontrado" -ForegroundColor Green
    Write-Host ""
    
    # Abrir el frontend en segundo plano
    Write-Host "🌐 Abriendo frontend en el navegador..." -ForegroundColor Yellow
    Start-Sleep -Seconds 2
    Start-Process "$PSScriptRoot\frontend\index.html"
    
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "  INICIANDO BACKEND..." -ForegroundColor Yellow
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "💡 El backend se iniciará ahora." -ForegroundColor Green
    Write-Host "   Una vez que veas el mensaje de éxito," -ForegroundColor White
    Write-Host "   podrás usar la aplicación en el navegador." -ForegroundColor White
    Write-Host ""
    Write-Host "🌐 Backend: http://localhost:8080" -ForegroundColor Cyan
    Write-Host "📊 H2 Console: http://localhost:8080/h2-console" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "⚠️  Para detener el servidor: Presiona Ctrl+C" -ForegroundColor Yellow
    Write-Host ""
    
    # Esperar 5 segundos antes de iniciar el backend
    Write-Host "Iniciando en 5 segundos..." -ForegroundColor Gray
    Start-Sleep -Seconds 5
    
    # Iniciar el backend
    .\mvnw.cmd spring-boot:run
} else {
    Write-Host "❌ Error: Maven Wrapper no encontrado" -ForegroundColor Red
    Write-Host ""
    Write-Host "Por favor ejecuta manualmente:" -ForegroundColor Yellow
    Write-Host "cd task-service" -ForegroundColor White
    Write-Host ".\mvnw.cmd spring-boot:run" -ForegroundColor White
}
