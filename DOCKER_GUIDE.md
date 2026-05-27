# 🏃 Guía de Ejecución - SportShop Full Stack

## ⚡ Inicio Rápido (Docker Compose)

```bash
# Desde la carpeta raíz del proyecto
docker-compose up --build

# En otra terminal (para ver logs)
docker-compose logs -f
```

**Acceso inmediato:**
- 🌐 **Frontend**: http://localhost:3000
- 🔌 **API Backend**: http://localhost:8080
- 💾 **Adminer (BD)**: http://localhost:8081

## 🛑 Detener los Servicios

```bash
docker-compose down
```

Para limpiar volúmenes también:
```bash
docker-compose down -v
```

---

## 📦 Arquitectura en Docker Compose

```
┌──────────────────────────────────────────────────┐
│           Docker Compose Network                 │
├──────────────────────────────────────────────────┤
│                                                  │
│  ┌─────────────┐        ┌──────────────┐       │
│  │  Frontend   │        │   Backend    │       │
│  │ (Node 3000) │───────▶│ (Spring 8080)│       │
│  └─────────────┘        └──────────────┘       │
│                               │                 │
│                               ▼                 │
│                         ┌──────────────┐       │
│                         │   MySQL      │       │
│                         │  (3307)      │       │
│                         └──────────────┘       │
│                                                  │
│  ┌──────────────────────────────────────────┐  │
│  │        Adminer (BD)  - Port 8081         │  │
│  └──────────────────────────────────────────┘  │
│                                                  │
└──────────────────────────────────────────────────┘
```

## 🔧 Configuración de Servicios

### MySQL (`mysql`)
- Puerto interno: `3306`
- Puerto externo: `3307`
- Base de datos: `tienda_deportiva`
- Usuario: `root`
- Contraseña: Configurada en docker-compose.yml

**Conectar desde Adminer:**
- Server: `mysql`
- Username: `root`
- Database: `tienda_deportiva`

### Backend (`backend`)
- Framework: Spring Boot
- Puerto: `8080`
- Endpoints: `/api/v1/*`
- Health check: `/api/v1/health`
- Depends on: MySQL (service_healthy)

### Frontend (`frontend`)
- Framework: React 18
- Puerto: `3000`
- Build: Node 20
- Depends on: Backend (service_healthy)
- API URL en Docker: `http://backend:8080`

### Adminer (`adminer`)
- Puerto: `8081`
- Para gestionar BD MySQL visualmente

---

## 🚀 Flujos de Uso

### 1️⃣ Primera Ejecución

```bash
docker-compose up --build
```

**Qué sucede:**
1. Se descarga/construye la imagen MySQL
2. Se initializa la BD con `mysql-init.sql`
3. Se compila el backend Java
4. Se construye la imagen del frontend (npm install + build)
5. Se inician todos los servicios

### 2️⃣ Ejecuciones Posteriores

```bash
docker-compose up
```

(Sin --build, reutiliza imágenes existentes)

### 3️⃣ Si Cambias Código Backend

```bash
docker-compose up --build backend
```

(Reconstruye solo el backend)

### 4️⃣ Si Cambias Código Frontend

```bash
docker-compose down
docker-compose up --build frontend
```

O simplemente fuerza rebuild de todo:

```bash
docker-compose up --build
```

---

## 🧪 Validación de Servicios

### Health Check del Backend

```bash
curl http://localhost:8080/api/v1/health
```

Deberías ver una respuesta similar a:
```json
{
  "status": "UP"
}
```

### Ver Logs de un Servicio Específico

```bash
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f mysql
```

### Entrar a un Contenedor

```bash
# Frontend
docker-compose exec frontend /bin/bash

# Backend (Java/Spring)
docker-compose exec backend /bin/bash

# MySQL
docker-compose exec mysql bash
```

---

## 📝 Flujo de Usuario Típico

1. **Accede a http://localhost:3000**
2. **Registrate** o usa acceso si ya existes
3. **Navega al catálogo** (`/productos`)
4. **Busca/filtra productos**
5. **Añade al carrito** (requiere estar autenticado)
6. **Visualiza el carrito** (`/carrito`)
7. **Gestiona cantidades** o vacía el carrito

---

## 🔄 Variables de Entorno

### Backend
- `SPRING_DATASOURCE_URL`: Conecta a MySQL
- `SPRING_DATASOURCE_USERNAME`: Usuario BD
- `SPRING_DATASOURCE_PASSWORD`: Contraseña BD

### Frontend  
- `REACT_APP_API_URL`: `http://backend:8080` (en Docker)
- `REACT_APP_API_URL`: `http://localhost:8080` (en desarrollo local)

---

## ⚙️ Personalizaciones Comunes

### Cambiar Puerto del Frontend

En `docker-compose.yml`:
```yaml
frontend:
  ports:
    - "3001:3000"  # Ahora accesible en localhost:3001
```

### Cambiar Puerto del Backend

En `docker-compose.yml`:
```yaml
backend:
  ports:
    - "8081:8080"  # Ahora accesible en localhost:8081
```

### Cambiar Credenciales MySQL

En `docker-compose.yml`:
```yaml
environment:
  MYSQL_ROOT_PASSWORD: tu_contraseña_nueva
```

---

## 🐛 Troubleshooting

### Error: "Cannot connect to Docker daemon"
```bash
# Asegúrate de que Docker Desktop esté ejecutándose
# O usa WSL2 en Windows
```

### Error: "Port 3000 already in use"
```bash
# Puerto en uso, cambia en docker-compose.yml o:
lsof -i :3000  # Identifica proceso
kill -9 <PID>  # Mata el proceso
```

### Frontend muestra "Cannot reach API"
```bash
# Verifica que el backend esté healthy
docker-compose logs backend

# Verifica conectividad entre contenedores
docker-compose exec frontend curl http://backend:8080/api/v1/health
```

### Base de datos no inicializa
```bash
# Borra volumen y reinicia
docker-compose down -v
docker-compose up --build
```

### Ver el SQL que se ejecutó
```bash
docker-compose logs mysql | grep -i "mysql-init"
```

---

## 📊 Monitoreo

### Ver todos los contenedores
```bash
docker-compose ps
```

### Ver recursos utilizados
```bash
docker stats
```

### Limpiar todo (prueba segura)
```bash
docker-compose down  # Detiene sin borrar datos
docker system prune  # Borra imágenes no usadas
```

---

## ✅ Checklist Pre-Deploy

- [ ] `docker-compose up --build` completa sin errores
- [ ] Frontend accesible en http://localhost:3000
- [ ] Backend responde en http://localhost:8080/api/v1/health
- [ ] Puedo registrar un usuario nuevo
- [ ] Puedo buscar productos
- [ ] Puedo añadir productos al carrito
- [ ] El carrito persiste entre páginas
- [ ] Adminer accesible en http://localhost:8081

---

## 📚 Documentación Adicional

- [Frontend README](./front/FRONTEND.md) - Detalles de React
- [API Endpoints](./API_ENDPOINTS.md) - Especificación de endpoints
- [Postman Collection](./CARRITO_ENDPOINTS_POSTMAN.md) - Tests HTTP

---

**¡Listo para desarrollar y deployar!** 🚀
