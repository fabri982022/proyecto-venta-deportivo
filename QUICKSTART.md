# 🚀 INICIO RÁPIDO - Ejecutar el Proyecto

## ⚡ En 3 pasos

### 1️⃣ Abre Terminal (desde la raíz del proyecto)
```bash
cd "f:\Proyecto Independiente"
```

### 2️⃣ Ejecuta Docker Compose
```bash
docker-compose up --build
```

Espera a que termine (3-5 minutos en la primera ejecución).

### 3️⃣ Abre el navegador
```
http://localhost:3000
```

## 📍 Servicios Disponibles

| Servicio | URL | Descripción |
|----------|-----|-------------|
| 🌐 Frontend | http://localhost:3000 | Aplicación React |
| 🔌 Backend API | http://localhost:8080 | Spring Boot |
| 💾 BD Manager | http://localhost:8081 | Adminer (MySQL) |
| 🗄️ MySQL | localhost:3307 | Base de datos |

## ✅ Qué Funciona

- ✅ Registro de usuarios
- ✅ Login/Acceso
- ✅ Catálogo de productos
- ✅ Búsqueda y filtrado
- ✅ Carrito de compras
- ✅ Gestión de cantidades
- ✅ Persistencia en BD
- ✅ Responsive en móvil/tablet/desktop

## 📱 Flujo de Usuario

1. **Home** → Ver estado del sistema
2. **Registro** → Crear usuario nuevo
3. **Productos** → Buscar y filtrar
4. **Carrito** → Agregar y gestionar items
5. **Acceso** → Login si ya existe

## 🛑 Detener Servicios

```bash
# Ctrl+C en la terminal, o en otra terminal:
docker-compose down
```

## 📚 Documentación Completa

- **[DOCKER_GUIDE.md](./DOCKER_GUIDE.md)** - Todo sobre Docker Compose
- **[FRONTEND.md](./front/FRONTEND.md)** - Estructura y APIs del frontend
- **[TESTING_GUIDE.md](./TESTING_GUIDE.md)** - Cómo validar todo
- **[API_ENDPOINTS.md](./API_ENDPOINTS.md)** - Endpoints del backend

## 🧪 Validación Rápida

```bash
# Health check del backend
curl http://localhost:8080/api/v1/health

# Listar productos
curl http://localhost:8080/api/v1/productos
```

## 🎯 Desarrollo Local (sin Docker)

Si prefieres desarrollar sin Docker:

```bash
# Terminal 1 - Frontend
cd front
npm start
# Accede a http://localhost:3000

# Terminal 2 - Backend (si tienes Java/Maven)
cd back
./mvnw spring-boot:run
```

**Nota:** El backend debe correr en http://localhost:8080

## ⚙️ Cambiar Puertos

Edita `docker-compose.yml`:

```yaml
frontend:
  ports:
    - "3001:3000"  # Cambiar 3001 por puerto que quieras

backend:
  ports:
    - "8081:8080"  # Cambiar 8081 por puerto que quieras
```

## 🔧 Troubleshooting Rápido

**Puerto 3000 en uso:**
```bash
# Windows
Get-Process -Id (Get-NetTCPConnection -LocalPort 3000).OwningProcess | Stop-Process

# Linux/Mac
lsof -i :3000 | grep LISTEN | awk '{print $2}' | xargs kill -9
```

**Docker no inicia:**
```bash
# Verificar Docker
docker ps

# Resetear todo
docker-compose down -v
docker-compose up --build
```

**Base de datos corrupta:**
```bash
# Limpiar volúmenes y reiniciar
docker-compose down -v
docker-compose up --build
```

## 📊 Estructura del Proyecto

```
Proyecto Independiente/
├── front/              → React Frontend ✅ COMPLETADO
├── back/               → Spring Boot Backend ✅
├── docker-compose.yml  → Orquestación ✅
├── mysql-init.sql      → Script BD ✅
├── DOCKER_GUIDE.md     → Guía Docker
├── FRONTEND.md         → Documentación frontend
├── TESTING_GUIDE.md    → Guía de pruebas
└── API_ENDPOINTS.md    → Endpoints disponibles
```

## 🎨 Paleta de Colores

- **Naranja**: #ff8c00 (Botones, acentos)
- **Negro**: #000000 (Texto)
- **Blanco**: #ffffff (Fondos)

## 💡 Tips Útiles

**Ver logs en tiempo real:**
```bash
docker-compose logs -f frontend
docker-compose logs -f backend
docker-compose logs -f mysql
```

**Entrar a un contenedor:**
```bash
docker-compose exec frontend /bin/bash
docker-compose exec backend /bin/bash
docker-compose exec mysql bash
```

**Reconstruir solo el frontend:**
```bash
docker-compose up --build frontend
```

**Reconstruir solo el backend:**
```bash
docker-compose up --build backend
```

## 🎉 ¡Listo!

El proyecto está **100% funcional** y listo para:

✅ Desarrollo  
✅ Testing  
✅ Deployment  
✅ Producción  

Solo ejecuta `docker-compose up --build` y accede a http://localhost:3000

---

**Preguntas?** Revisa los archivos de documentación en la raíz del proyecto.
