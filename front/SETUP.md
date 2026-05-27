# 🎯 Frontend - Proyecto Deportivo

## 📋 Configuración de Desarrollo Local

### Requisitos
- Node.js 20+
- npm o yarn

### 🚀 Instalación Rápida (Windows)

```powershell
cd front
.\init-dev.bat
npm start
```

### 🚀 Instalación Rápida (Linux/Mac)

```bash
cd front
bash init-dev.sh
npm start
```

### 📝 Configuración Manual

Si prefieres hacerlo manualmente:

```bash
# 1. Limpiar dependencias anteriores
rm -rf node_modules
npm cache clean --force

# 2. Instalar dependencias
npm install

# 3. Crear archivo de configuración
echo "REACT_APP_API_URL=http://localhost:8080" > .env.local
echo "REACT_APP_API_TIMEOUT=10000" >> .env.local
echo "REACT_APP_RETRY_ATTEMPTS=3" >> .env.local

# 4. Iniciar servidor de desarrollo
npm start
```

## 🐳 Docker (Producción)

El Dockerfile está optimizado para:
- Build multi-stage (builder + runtime)
- Imagen Alpine optimizada (pequeña y rápida)
- Healthcheck automático
- Configuración correcta para conexión con backend en Docker

```bash
# Construir imagen
docker build -t proyecto-frontend:latest .

# Ejecutar contenedor
docker run -p 3000:3000 proyecto-frontend:latest
```

## 🔌 Configuración de API

### Archivos de Configuración

**`.env.local`** - Variables de entorno locales (desarrollo)
- `REACT_APP_API_URL=http://localhost:8080`
- `REACT_APP_API_TIMEOUT=10000`
- `REACT_APP_RETRY_ATTEMPTS=3`

**`src/config/api.config.js`** - Configuración centralizada
- Lee variables de entorno
- Valida configuración
- Loguea configuración en desarrollo

**`src/utils/apiClient.js`** - Cliente mejorado
- Reintentos automáticos (hasta 3 veces)
- Timeout personalizado
- Backoff exponencial
- Logging detallado

### ✨ Características del Cliente

```javascript
import { apiCall } from './services/api';

// Uso simple (con reintentos automáticos)
try {
  const data = await apiCall('/usuarios');
  console.log(data);
} catch (error) {
  console.error(error.message);
}
```

**Ventajas:**
✅ Reconecta automáticamente si falla  
✅ Timeout de 10 segundos para evitar cuelgues  
✅ Logging detallado para debugging  
✅ Backoff exponencial entre reintentos  
✅ Compatible con desarrollo local y Docker  

## 📱 URLs

### Desarrollo Local
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **API Base**: http://localhost:8080/api/v1

### Docker Compose
- **Frontend**: http://localhost:3000
- **Backend API**: http://backend:8080 (dentro de la red Docker)
- **Adminer (BD)**: http://localhost:8081

## 🛠️ Desarrollo

### Scripts Disponibles

```bash
# Desarrollo
npm start          # Iniciar servidor de desarrollo

# Build
npm run build      # Crear build de producción

# Testing
npm test           # Ejecutar tests
npm run eject      # Eject (irreversible)
```

## 🐛 Troubleshooting

### Error: "Failed to fetch" / "ERR_NAME_NOT_RESOLVED"

**Solución:**
1. Verifica que el backend esté corriendo en `localhost:8080`
2. Verifica el archivo `.env.local`:
   ```
   REACT_APP_API_URL=http://localhost:8080
   ```
3. Reinicia el servidor React: `npm start`

### Los archivos de configuración no se cargan

**Solución:**
1. Borra `node_modules`: `rm -rf node_modules`
2. Limpia el caché de npm: `npm cache clean --force`
3. Reinstala: `npm install`
4. Inicia: `npm start`

## 📦 Estructura de Archivos

```
front/
├── public/                 # Archivos estáticos
├── src/
│   ├── config/
│   │   └── api.config.js  # ⭐ Configuración centralizada
│   ├── utils/
│   │   └── apiClient.js   # ⭐ Cliente mejorado con reintentos
│   ├── services/
│   │   └── api.js         # Definición de endpoints
│   ├── pages/             # Componentes de página
│   ├── components/        # Componentes reutilizables
│   ├── context/           # Context API (Auth, Cart)
│   ├── styles/            # Estilos CSS
│   ├── App.js
│   └── index.js
├── .env.local             # ⭐ Variables de entorno (local)
├── .dockerignore           # ⭐ Archivos ignorados en Docker
├── .gitignore
├── Dockerfile             # ⭐ Multi-stage optimizado
├── package.json
└── init-dev.bat           # ⭐ Script de inicialización (Windows)
```

## ✅ Checklist de Setup

- [ ] Node.js 20+ instalado
- [ ] `npm install` ejecutado
- [ ] `.env.local` creado con `REACT_APP_API_URL=http://localhost:8080`
- [ ] Backend corriendo en `localhost:8080`
- [ ] `npm start` ejecutado
- [ ] http://localhost:3000 accesible en navegador
- [ ] Console sin errores de API

---

**Última actualización:** Mayo 2026
