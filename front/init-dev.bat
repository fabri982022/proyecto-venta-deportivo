@echo off
REM Script para inicializar el frontend en desarrollo (Windows)

echo 🚀 Inicializando frontend...

REM Limpiar node_modules si existen
if exist node_modules (
    echo 🧹 Limpiando node_modules...
    rmdir /s /q node_modules
)

REM Instalar dependencias
echo 📦 Instalando dependencias...
call npm install

REM Verificar que la configuración está en lugar
if not exist ".env.local" (
    echo ⚠️ Archivo .env.local no encontrado, creando...
    (
        echo REACT_APP_API_URL=http://localhost:8080
        echo REACT_APP_API_TIMEOUT=10000
        echo REACT_APP_RETRY_ATTEMPTS=3
    ) > .env.local
)

echo ✅ Frontend listo para iniciar
echo 📝 Para iniciar el servidor de desarrollo, ejecuta: npm start
