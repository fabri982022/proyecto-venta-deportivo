#!/bin/bash
# Script para inicializar el frontend en desarrollo

echo "🚀 Inicializando frontend..."

# Limpiar node_modules si existen
if [ -d "node_modules" ]; then
    echo "🧹 Limpiando node_modules..."
    rm -rf node_modules
fi

# Instalar dependencias
echo "📦 Instalando dependencias..."
npm install

# Verificar que la configuración está en lugar
if [ ! -f ".env.local" ]; then
    echo "⚠️ Archivo .env.local no encontrado, creando..."
    cat > .env.local << EOF
REACT_APP_API_URL=http://localhost:8080
REACT_APP_API_TIMEOUT=10000
REACT_APP_RETRY_ATTEMPTS=3
EOF
fi

echo "✅ Frontend listo para iniciar"
echo "📝 Para iniciar el servidor de desarrollo, ejecuta: npm start"
