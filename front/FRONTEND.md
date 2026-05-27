# SportShop Frontend - React SPA

## 📱 Descripción General

Frontend responsivo desarrollado con **React 18** que consume la API REST del backend Spring Boot. La aplicación implementa:

- ✅ **Navegación con React Router v6** - Rutas para todas las páginas principales
- ✅ **Autenticación local** - Sesión basada en localStorage con contexto de React
- ✅ **Carrito persistente** - Sincronizado con la API del backend
- ✅ **Catálogo dinámico** - Productos cargados desde `/api/v1/productos`
- ✅ **Diseño responsive** - Mobile-first, funciona en todos los tamaños
- ✅ **Paleta de colores** - Naranja (#ff8c00), Negro (#000000), Blanco (#ffffff)

## 📁 Estructura de Directorios

```
src/
├── components/              # Componentes reutilizables
│   ├── Header.jsx          # Navegación con menú hamburguesa responsive
│   ├── Footer.jsx          # Pie de página con enlaces
│   └── ProductCard.jsx     # Tarjeta de producto con agregar al carrito
├── context/                # Estado global con React Context API
│   ├── AuthContext.jsx     # Gestión de sesión del usuario
│   └── CartContext.jsx     # Estado del carrito y operaciones
├── pages/                  # Páginas de la aplicación
│   ├── HomePage.jsx        # Dashboard con estadísticas
│   ├── ProductsPage.jsx    # Catálogo con búsqueda y filtros
│   ├── CartPage.jsx        # Carrito con gestión de cantidades
│   ├── AccessPage.jsx      # Login con ID de usuario
│   └── RegisterPage.jsx    # Registro de nuevo cliente
├── services/               # Llamadas a la API
│   └── api.js             # Configuración de fetch y endpoints
├── styles/                 # Módulos CSS responsivos
│   ├── global.css         # Variables CSS y estilos base
│   ├── layout.css         # Grid y estructura de páginas
│   ├── header.css         # Navegación sticky
│   ├── footer.css         # Footer grid
│   ├── product-card.css   # Tarjetas de producto
│   ├── forms.css          # Estilos de formularios
│   └── pages.css          # Estilos específicos de páginas
├── App.js                  # Router principal
└── index.js               # Entry point
```

## 🔌 Integración con el Backend

### Variables de Entorno

- **Desarrollo local**: `REACT_APP_API_URL=http://localhost:8080`
- **Docker**: `REACT_APP_API_URL=http://backend:8080` (usa el nombre del servicio)

### Endpoints Consumidos

| Recurso | Método | Path |
|---------|--------|------|
| Health | GET | `/api/v1/health` |
| Usuarios | POST/GET/PUT | `/api/v1/usuarios/cliente` |
| Productos | GET | `/api/v1/productos` |
| Carrito | GET/POST/PUT/DELETE | `/api/v1/carrito/cliente/{id}` |

### Flujo de Datos

```
Usuario → Frontend (React) → API (Spring Boot) → MySQL
```

## 🚀 Cómo Ejecutar

### Desarrollo Local

```bash
cd front
npm install
npm start
```

Accede a `http://localhost:3000`

**Nota**: Asegúrate de que el backend esté corriendo en `http://localhost:8080`

### Producción con Docker

```bash
cd .. # Vuelve a la carpeta raíz
docker-compose up --build
```

Accede a `http://localhost:3000`

**Los servicios disponibles serán:**
- **Frontend**: http://localhost:3000
- **Backend**: http://localhost:8080
- **Adminer (DB)**: http://localhost:8081
- **MySQL**: localhost:3307

## 🎨 Paleta de Colores

```css
--color-primary: #ff8c00      /* Naranja */
--color-secondary: #000000    /* Negro */
--color-light: #ffffff        /* Blanco */
--color-dark-bg: #0d0d0d      /* Negro muy oscuro */
--color-surface: #141414      /* Gris oscuro */
```

## 📱 Responsive Design

La aplicación es **100% responsive** utilizando:

- **CSS Grid** - Layouts dinámicos
- **Flexbox** - Alineación flexible
- **Media Queries** - Puntos de quiebre:
  - `1024px` - Tablets
  - `768px` - Móviles medianos
  - `480px` - Móviles pequeños

### Ejemplo de Breakpoints

```css
@media (max-width: 1024px) {
  /* Layouts de una columna en tablets */
}

@media (max-width: 768px) {
  /* Menú hamburguesa, tipografía más pequeña */
}

@media (max-width: 480px) {
  /* Botones full-width, spacing reducido */
}
```

## 🔐 Autenticación

**Sin JWT/OAuth** - El frontend usa autenticación simplificada:

1. Usuario entra un **ID de cliente** en `/acceso`
2. Se valida contra `/api/v1/usuarios/{id}`
3. Los datos se guardan en **localStorage**
4. El carrito se obtiene automáticamente al cambiar de sesión

**Datos almacenados:**
- `userId` - ID del usuario
- `userName` - Nombre completo
- `userEmail` - Email
- `userRole` - Rol del usuario

## 🛒 Carrito

**Características:**
- ✅ Agregar/eliminar productos
- ✅ Cambiar cantidades
- ✅ Vaciar carrito
- ✅ Cálculo automático de totales
- ✅ Sincronización en tiempo real con el backend

**Persistencia:**
El carrito se obtiene de la API en cada acceso y se refresca al:
- Cambiar de página
- Agregar/eliminar productos
- Cambiar de usuario

## 🧪 Build para Producción

```bash
npm run build
```

Genera una carpeta `build/` lista para deployment con:
- CSS minificado
- JavaScript optimizado
- Bundle size: ~60KB (gzipped)

## 📋 Dependencias Principales

```json
{
  "react": "^18.2.0",
  "react-router-dom": "^6.26.2",
  "react-dom": "^18.2.0"
}
```

## ✨ Features Implementados

- ✅ Home con estado de la API
- ✅ Catálogo dinámico con búsqueda
- ✅ Filtrado por categoría
- ✅ Carrito persistente
- ✅ Registro de nuevos clientes
- ✅ Acceso con ID existente
- ✅ Header sticky con menú responsivo
- ✅ Footer con enlaces
- ✅ Mensajes de error/éxito
- ✅ Loading states

## 🐛 Troubleshooting

### El frontend no conecta al backend

**Local:**
```bash
# Verifica que el backend esté en http://localhost:8080
curl http://localhost:8080/api/v1/health
```

**Docker:**
```bash
# Los contenedores deben estar en la misma red
docker network ls
docker inspect app-network
```

### CORS Error

Si el backend falla con CORS, asegúrate de que el backend tenga configurado:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
      .allowedOrigins("*")
      .allowedMethods("*");
  }
}
```

## 📝 Notas de Desarrollo

- El carrito se vacía al cambiar de usuario (logout)
- Los productos sin stock muestran un badge "Sin stock"
- Las imágenes tienen fallback a placeholders
- Los formularios validan antes de enviar
- Todos los estilos son CSS vanilla (sin Tailwind/Bootstrap)

---

**Desarrollado para funcionar perfectamente con Docker Compose** 🐳
