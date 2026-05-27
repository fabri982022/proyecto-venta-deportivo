@echo off
REM Script para cargar datos de prueba
REM Uso: load-test-data.bat

echo 🔄 Compilando y ejecutando LoadTestData...
echo.

cd back

REM Compilar
javac LoadTestData.java 2>nul

REM Ejecutar
java LoadTestData

echo.
echo ✅ Datos de prueba cargados
echo.
echo Usuarios disponibles para prueba:
echo   👤 Admin: usuario1 / pass123
echo   👤 Cliente: usuario4 / pass123
echo   👤 Vendedor: usuario52 / pass123
