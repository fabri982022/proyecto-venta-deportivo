# 🎯 Pasos Exactos - Paso a Paso

## 1️⃣ Inicia el Sistema (5 minutos)

```powershell
# En PowerShell desde f:\Proyecto Independiente
docker-compose up -d --build

# Espera a ver esto en los logs:
# Backend: "Started DeporteApplication"
```

---

## 2️⃣ Carga 100 Usuarios + 50 Productos (3 minutos)

### Windows
```powershell
# Aún en PowerShell, desde f:\Proyecto Independiente
.\load-test-data.bat
```

**Verás**:
```
🔄 Iniciando carga de datos de prueba...
👥 Creando usuarios...
. . . . . . . . . . 10
. . . . . . . . . . 20
...
✅ Datos de prueba cargados exitosamente!
```

### Linux/Mac
```bash
bash load-test-data.sh
```

---

## 3️⃣ Verifica en el navegador

Abre: **http://localhost:3000**

Deberías ver:
- ✅ Página inicio sin errores
- ✅ 50 productos listados
- ✅ Botón "Registrarse"

---

## 4️⃣ Prueba con Usuario Admin

1. Abre DevTools (F12) → Console
2. Intenta acceder como admin:
   - Usuario: `usuario1`
   - Contraseña: `pass123`

**Verás** en console:
```
📡 Intento 1/3: GET http://localhost:8080/api/v1/usuarios/1
✅ Éxito: 200
```

---

## 5️⃣ Actualiza el Frontend (Estilos Mercado Libre)

### Archivo: `front/src/index.css`

Abre y al FINAL agrega:

```css
@import './styles/mercadolibre.css';
```

Guarda. El navegador se actualiza automáticamente.

---

## 6️⃣ Crea Página de Vendedor (Opcional pero recomendado)

### Crea archivo: `front/src/pages/VendorPage.jsx`

Copia todo el código que está en `CHANGES_SUMMARY.md` en la sección "VendorPage.jsx"

### Luego, abre `front/src/App.js`

Agrega al inicio:
```jsx
import { VendorPage } from './pages/VendorPage';
```

Agrega dentro de `<Routes>`:
```jsx
<Route path="/vender" element={<VendorPage />} />
```

Guarda.

---

## 7️⃣ Actualiza Header para mostrar opción Vendedor

### Archivo: `front/src/components/Header.jsx`

Busca donde dice `<Link to="/registro">` y agrega después:

```jsx
{user?.rol === 'VENDEDOR' && (
  <Link to="/vender" className="nav-link">
    📦 Mis Productos
  </Link>
)}
```

---

## ✅ Verificación Final

En **http://localhost:3000**:

1. **Sin login**:
   - ✅ Ver productos
   - ✅ Ver header sin opciones

2. **Como usuario4 (Cliente)**:
   - ✅ Ver carrito
   - ✅ Poder agregar al carrito
   - ✅ No ver opción "Mis Productos"

3. **Como usuario52 (Vendedor)**:
   - ✅ Ver opción "Mis Productos"
   - ✅ Poder subir productos

4. **Como usuario1 (Admin)**:
   - ✅ Ver acceso especial (ajustar según necesites)

---

## 🎨 Usuarios de Prueba

Copia cualquiera de estos en el login:

**Admins:**
- `usuario1` / `pass123`
- `usuario2` / `pass123`
- `usuario3` / `pass123`

**Clientes:**
- `usuario4` / `pass123`
- `usuario10` / `pass123`
- `usuario20` / `pass123`

**Vendedores:**
- `usuario52` / `pass123`
- `usuario75` / `pass123`
- `usuario100` / `pass123`

---

## 🐛 Si hay error: "ERR_NAME_NOT_RESOLVED"

```bash
# Reconstruye todo
docker-compose down -v
docker-compose up -d --build

# Espera 2 minutos
# Luego ejecuta
.\load-test-data.bat
```

---

## 📊 Resultado esperado

```
✅ Docker corriendo
✅ 100 usuarios creados
✅ 50 productos creados
✅ Frontend con estilos Mercado Libre
✅ Roles funcionando
✅ Carrito operativo
```

---

**¡Listo! Tu sistema está funcionando con datos de prueba y estilos modernos.**
