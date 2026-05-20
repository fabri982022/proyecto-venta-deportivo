# Script para levantar el backend correctamente - VERSIÓN SIMPLIFICADA

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "  LEVANTANDO PROYECTO DEPORTIVO" -ForegroundColor Green
Write-Host "========================================`n" -ForegroundColor Cyan

Write-Host "🔧 Limpiando contenedores anteriores..." -ForegroundColor Yellow
docker-compose down -v 2>$null

Write-Host "🗑️  Eliminando imágenes antiguas..." -ForegroundColor Yellow
docker rmi proyecto-indepndiente-deportivo-backend -f 2>$null
docker rmi proyecto-indepndiente-deportivo-frontend -f 2>$null

Write-Host "`n📦 Compilando Maven (clean package)..." -ForegroundColor Cyan
Push-Location ".\back"
Write-Host "Ejecutando: mvnw.cmd clean package -DskipTests" -ForegroundColor Gray

# Ejecutar Maven en modo silencioso
.\mvnw.cmd clean package -DskipTests -q
$mvnExitCode = $LASTEXITCODE
Pop-Location

if ($mvnExitCode -ne 0) {
    Write-Host "`n❌ Error compilando Maven (código: $mvnExitCode)" -ForegroundColor Red
    exit 1
}

Write-Host "✅ Maven compilado exitosamente" -ForegroundColor Green

Write-Host "`n🏗️  Construyendo imágenes Docker..." -ForegroundColor Green
docker-compose build --no-cache

Write-Host "`n🚀 Levantando contenedores..." -ForegroundColor Green
docker-compose up -d

Write-Host "`n⏳ Esperando que Spring Boot inicie (hasta 180 segundos)..." -ForegroundColor Cyan
$elapsed = 0
$maxWait = 180
while ($elapsed -lt $maxWait) {
    $status = docker exec spring-backend curl -sf http://localhost:8080/api/v1/health 2>/dev/null
    if ($?) {
        Write-Host "✅ Backend está listo!" -ForegroundColor Green
        break
    }
    Write-Host "." -NoNewline -ForegroundColor Yellow
    Start-Sleep -Seconds 3
    $elapsed += 3
}

if ($elapsed -ge $maxWait) {
    Write-Host "`n⚠️  Timeout esperando backend" -ForegroundColor Yellow
}

Write-Host "`n`n📊 Estado de los contenedores:" -ForegroundColor Blue
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

Write-Host "`n✅ SETUP COMPLETADO - Accede a:" -ForegroundColor Green
Write-Host "   🌐 Frontend:    http://localhost:3000" -ForegroundColor Cyan
Write-Host "   🔗 Backend API: http://localhost:8080" -ForegroundColor Cyan
Write-Host "   📊 Adminer DB:  http://localhost:8081" -ForegroundColor Cyan
Write-Host "   🏥 Health:      http://localhost:8080/api/v1/health" -ForegroundColor Cyan

Write-Host "`n`n📋 Últimos logs del backend:" -ForegroundColor Magenta
docker logs --tail 50 spring-backend
