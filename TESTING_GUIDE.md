# 🧪 Guía de Pruebas - Validación del Frontend

## ✅ Checklist de Validación

Usa este documento para verificar que todo funciona correctamente después de ejecutar `docker-compose up --build`.

---

## 🚀 Fase 1: Validación de Servicios

### 1.1 Frontend Disponible
```bash
curl http://localhost:3000
```
**Esperado:** Respuesta HTML de la aplicación React (200 OK)

### 1.2 Backend Disponible
```bash
curl http://localhost:8080/api/v1/health
```
**Esperado:** 
```json
{
  "status": "UP"
}
```

### 1.3 MySQL Disponible
```bash
docker-compose exec mysql mysql -u root -p -e "SELECT 1;"
```
**Esperado:** Respuesta positiva

### 1.4 Adminer Disponible
Abre en navegador: `http://localhost:8081`
**Esperado:** Panel de login de Adminer

---

## 🌐 Fase 2: Navegación del Frontend

### 2.1 Home Page (`http://localhost:3000`)
- [ ] Se carga sin errores
- [ ] Se ve el header con logo "S"
- [ ] Se ve el hero section
- [ ] Se ve el panel de estadísticas
- [ ] Se carga el footer al final
- [ ] Los enlaces de navegación funcionan

### 2.2 Acceso (`http://localhost:3000/acceso`)
- [ ] Se carga la página de acceso
- [ ] Campo de input visible
- [ ] Botón "Acceder" visible

### 2.3 Registro (`http://localhost:3000/registro`)
- [ ] Se carga la página de registro
- [ ] Todos los campos aparecen:
  - [ ] Nombre
  - [ ] Apellido
  - [ ] DNI
  - [ ] Email
  - [ ] Teléfono
  - [ ] Nombre de usuario
  - [ ] Contraseña
  - [ ] Dirección
- [ ] Botón "Registrarse" visible

### 2.4 Productos (`http://localhost:3000/productos`)
- [ ] Se cargan productos desde el backend
- [ ] Se ve la barra de búsqueda
- [ ] Se ve el filtro de categoría
- [ ] Se muestran tarjetas de producto
- [ ] Cada tarjeta tiene:
  - [ ] Imagen
  - [ ] Nombre
  - [ ] Categoría
  - [ ] Precio
  - [ ] Stock disponible
  - [ ] Selector de cantidad
  - [ ] Botón "Agregar"

### 2.5 Carrito (`http://localhost:3000/carrito`)
- [ ] Si no está autenticado: redirige a `/acceso`
- [ ] Si está autenticado: muestra el carrito

---

## 👤 Fase 3: Flujo de Registro

### 3.1 Registrarse
1. Ve a `http://localhost:3000/registro`
2. Completa el formulario con:
   ```
   Nombre: Juan
   Apellido: Pérez
   DNI: 12345678
   Email: juan@example.com
   Teléfono: 123456789
   Usuario: juan_perez
   Contraseña: password123
   Dirección: Calle Principal 123
   ```
3. Click en "Registrarse"

**Esperado:**
- [ ] No muestra errores
- [ ] Redirige automáticamente a `/productos`
- [ ] El usuario está autenticado (se refleja en el header)

### 3.2 Verificar Datos en Adminer
1. Ve a `http://localhost:8081`
2. Login: `Server: mysql`, `User: root`, Database: `tienda_deportiva`
3. Tabla `usuarios`

**Esperado:**
- [ ] Existe el nuevo usuario registrado
- [ ] Todos los campos están correctos

---

## 🛒 Fase 4: Flujo del Carrito

### 4.1 Agregar Producto al Carrito
1. Desde `/productos`
2. Selecciona cantidad (ej: 2)
3. Click en botón "🛒 Agregar"

**Esperado:**
- [ ] Mensaje de éxito
- [ ] El carrito se actualiza

### 4.2 Ver Carrito
1. Ve a `http://localhost:3000/carrito`
2. O click en "Carrito" en el header

**Esperado:**
- [ ] Se muestra el producto añadido
- [ ] La cantidad es correcta
- [ ] El precio total se calcula correctamente
- [ ] Total de items es correcto

### 4.3 Modificar Carrito
**Cambiar cantidad:**
1. Click en + para aumentar cantidad
2. Click en - para disminuir cantidad

**Esperado:**
- [ ] La cantidad se actualiza
- [ ] El total se recalcula
- [ ] Los cambios se persisten en la BD

**Eliminar item:**
1. Click en botón "Eliminar" de un producto

**Esperado:**
- [ ] El item desaparece del carrito
- [ ] El total se recalcula

**Vaciar carrito:**
1. Click en "Vaciar Carrito"

**Esperado:**
- [ ] Todos los items desaparecen
- [ ] Carrito vacío se confirma

---

## 🔐 Fase 5: Autenticación

### 5.1 Logout
1. Click en usuario en el header
2. Click en "Logout" (si existe botón)

**Esperado:**
- [ ] Sesión se cierra
- [ ] Redirige a `/acceso`
- [ ] localStorage se limpia

### 5.2 Protección de Rutas
1. Sin autenticación, intenta ir a `/carrito`

**Esperado:**
- [ ] Redirige a `/acceso` automáticamente

### 5.3 Acceso Existente
1. Ve a `/acceso`
2. Ingresa el ID del usuario creado (ej: 1)
3. Click en "Acceder"

**Esperado:**
- [ ] Se valida contra el backend
- [ ] Redirige a `/productos`
- [ ] El usuario aparece en el header

---

## 🔍 Fase 6: Búsqueda y Filtrado

### 6.1 Búsqueda
1. Ve a `/productos`
2. Escribe en la barra de búsqueda (ej: "zapatillas")

**Esperado:**
- [ ] Los productos se filtran en tiempo real
- [ ] Solo aparecen productos con "zapatillas" en el nombre o descripción

### 6.2 Filtro por Categoría
1. Ve a `/productos`
2. Selecciona una categoría del dropdown

**Esperado:**
- [ ] Los productos se filtran por categoría
- [ ] El dropdown mantiene la selección

### 6.3 Búsqueda + Filtro
1. Escribe en búsqueda
2. Selecciona una categoría

**Esperado:**
- [ ] Ambos filtros se aplican simultáneamente
- [ ] Solo aparecen productos que coincidan con ambos

---

## 📱 Fase 7: Responsividad

### 7.1 Desktop (1920x1080)
1. Abre `http://localhost:3000`
2. Observa el layout

**Esperado:**
- [ ] Header con navegación horizontal
- [ ] Productos en grid de 3 columnas
- [ ] Footer en 3 columnas
- [ ] Carrito en 2 columnas

### 7.2 Tablet (768px)
1. Abre DevTools (F12)
2. Cambia a tablet view (768x1024)

**Esperado:**
- [ ] Header con hamburguesa (menú colapsado)
- [ ] Productos en grid de 2 columnas
- [ ] Footer en 1-2 columnas
- [ ] Botones más grandes
- [ ] Espaciado adaptado

### 7.3 Móvil (480px)
1. Abre DevTools
2. Cambia a móvil (480x800)

**Esperado:**
- [ ] Header con menú hamburguesa
- [ ] Click en hamburguesa abre menú vertical
- [ ] Productos en grid de 1 columna
- [ ] Footer en 1 columna
- [ ] Inputs full-width
- [ ] Botones full-width
- [ ] Tipografía legible
- [ ] Sin scroll horizontal

---

## 🎨 Fase 8: Estilos y Paleta

### 8.1 Colores
1. Inspecciona elementos con DevTools

**Esperado - Paleta:**
- [ ] Naranja #ff8c00 - Botones, logo
- [ ] Negro #000000 - Texto principal
- [ ] Blanco #ffffff - Fondos claros
- [ ] Coherencia en toda la aplicación

### 8.2 Tipografía
**Esperado:**
- [ ] Titles en fuente más grande
- [ ] Body text legible
- [ ] Buena jerarquía visual

### 8.3 Espaciado
**Esperado:**
- [ ] Padding consistente
- [ ] Márgenes proporcionales
- [ ] Elementos no abarrotados

---

## 🔗 Fase 9: API Integration

### 9.1 Health Check
```bash
# En otra terminal
curl http://localhost:8080/api/v1/health
```

**Esperado:** Status UP

### 9.2 Obtener Productos
```bash
curl http://localhost:8080/api/v1/productos
```

**Esperado:** Array de productos en JSON

### 9.3 Crear Usuario
```bash
curl -X POST http://localhost:8080/api/v1/usuarios/cliente \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Test",
    "apellido": "User",
    "email": "test@example.com",
    "nombre_usuario": "testuser",
    "contrasena": "pass123"
  }'
```

**Esperado:** Usuario creado con ID

### 9.4 Obtener Usuario
```bash
curl http://localhost:8080/api/v1/usuarios/1
```

**Esperado:** Datos del usuario

### 9.5 Carrito
```bash
# Crear carrito para usuario 1
curl -X POST http://localhost:8080/api/v1/carrito/cliente/1

# Obtener carrito
curl http://localhost:8080/api/v1/carrito/cliente/1
```

**Esperado:** Carrito vacío o con items

---

## 🐛 Fase 10: Manejo de Errores

### 10.1 Producto sin Stock
1. Ve a `/productos`
2. Busca un producto sin stock

**Esperado:**
- [ ] Botón "Agregar" deshabilitado
- [ ] Badge "Sin stock" visible
- [ ] No se puede añadir al carrito

### 10.2 Error de Conexión
1. Detén el backend: `docker-compose stop backend`
2. Intenta cargar `/productos`

**Esperado:**
- [ ] Muestra mensaje de error
- [ ] No muestra contenido roto
- [ ] Permite reintentar

### 10.3 Campo Obligatorio
1. Ve a `/registro`
2. Deja campos vacíos
3. Click en "Registrarse"

**Esperado:**
- [ ] Muestra error de validación
- [ ] Indica cuál campo falta

---

## 💾 Fase 11: Persistencia

### 11.1 localStorage - Sesión
1. Registrate o accede
2. Abre DevTools (F12) → Application → localStorage
3. Busca: `userId`, `userName`, `userEmail`

**Esperado:**
- [ ] Los datos aparecen en localStorage
- [ ] Al recargar la página, la sesión se mantiene

### 11.2 Carrito en BD
1. Añade productos al carrito
2. Abre Adminer: `http://localhost:8081`
3. Ve la tabla `carrito_items`

**Esperado:**
- [ ] Los items aparecen en la BD
- [ ] Al recargar la página, el carrito se mantiene

### 11.3 Logout
1. Logout desde el header
2. Abre DevTools → localStorage

**Esperado:**
- [ ] Los datos se eliminan de localStorage
- [ ] El carrito se vacía

---

## 📊 Fase 12: Performance

### 12.1 Bundle Size
```bash
cd front
npm run build
```

Observa el output:

**Esperado:**
- [ ] Main bundle < 100KB (gzipped)
- [ ] CSS bundle < 10KB (gzipped)
- [ ] Chunks adecuados

### 12.2 Tiempo de Carga
1. Abre DevTools → Network
2. Recarga `http://localhost:3000`

**Esperado:**
- [ ] Inicial load < 3 segundos
- [ ] JS parse < 1 segundo

### 12.3 Interactividad
1. En `/productos`
2. Escribe en búsqueda

**Esperado:**
- [ ] Filtrado instantáneo (sin lag)
- [ ] Respuesta inmediata al escribir

---

## 🎯 Resumen Final

Después de completar todas las fases:

| Fase | Esperado | ✅ |
|------|----------|-----|
| 1. Servicios | Todos accesibles | |
| 2. Navegación | Todas las páginas cargan | |
| 3. Registro | Usuario creado en BD | |
| 4. Carrito | Items agregados y persistidos | |
| 5. Autenticación | Login/Logout funciona | |
| 6. Búsqueda | Filtros funcionan | |
| 7. Responsividad | Funciona en todos los tamaños | |
| 8. Estilos | Paleta correcta | |
| 9. API | Endpoints responden | |
| 10. Errores | Mensajes claros | |
| 11. Persistencia | Datos se guardan | |
| 12. Performance | Carga rápida | |

---

## 🚨 Troubleshooting Rápido

**Si algo no funciona:**

1. **Verificar logs:**
   ```bash
   docker-compose logs -f frontend
   docker-compose logs -f backend
   docker-compose logs -f mysql
   ```

2. **Restartar servicios:**
   ```bash
   docker-compose restart
   ```

3. **Limpiar y reconstruir:**
   ```bash
   docker-compose down -v
   docker-compose up --build
   ```

4. **Limpiar cache del navegador:**
   - DevTools → Application → Clear site data
   - O usar Ctrl+Shift+Delete

5. **Verificar conectividad:**
   ```bash
   docker-compose exec frontend curl http://backend:8080/api/v1/health
   ```

---

## ✅ Si todo funciona...

¡Felicidades! El frontend está **completamente funcional** y listo para:
- ✅ Desarrollo local
- ✅ Testing
- ✅ Deployment
- ✅ Producción

**Documentación:** Ver [DOCKER_GUIDE.md](./DOCKER_GUIDE.md) y [FRONTEND.md](./front/FRONTEND.md)

---

**¡A usar!** 🚀
