# ============================================
# SCRIPT PARA ABRIR EL FRONTEND
# ============================================
# Este script abre la interfaz web del Task Manager

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  ABRIENDO TASK MANAGER FRONTEND" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Ruta al archivo HTML
$frontendPath = "C:\Users\Diego\task-manager-soa\frontend\index.html"

# Verificar que el archivo existe
if (Test-Path $frontendPath) {
    Write-Host "✅ Frontend encontrado" -ForegroundColor Green
    Write-Host "📂 Ubicación: $frontendPath" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "🌐 Abriendo en el navegador..." -ForegroundColor Yellow
    Write-Host ""
    
    # Abrir el archivo en el navegador predeterminado
    Start-Process $frontendPath
    
    Write-Host "✅ Frontend abierto exitosamente!" -ForegroundColor Green
    Write-Host ""
    Write-Host "💡 IMPORTANTE:" -ForegroundColor Yellow
    Write-Host "   Asegúrate de que el backend esté ejecutándose en:" -ForegroundColor White
    Write-Host "   http://localhost:8080" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "   Si el backend no está ejecutándose, usa:" -ForegroundColor White
    Write-Host "   .\start-backend.ps1" -ForegroundColor Cyan
} else {
    Write-Host "❌ Error: No se encontró index.html" -ForegroundColor Red
    Write-Host "   Ruta esperada: $frontendPath" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Presiona cualquier tecla para cerrar..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
