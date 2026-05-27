#!/bin/bash
# Script para cargar datos de prueba
# Uso: bash load-test-data.sh

echo "🔄 Compilando y ejecutando LoadTestData..."
echo ""

cd back

# Compilar
javac -cp ".:mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout" LoadTestData.java 2>/dev/null

# Ejecutar
java LoadTestData

echo ""
echo "✅ Datos de prueba cargados"
echo ""
echo "Usuarios disponibles para prueba:"
echo "  👤 Admin: usuario1 / pass123"
echo "  👤 Cliente: usuario4 / pass123"
echo "  👤 Vendedor: usuario52 / pass123"
