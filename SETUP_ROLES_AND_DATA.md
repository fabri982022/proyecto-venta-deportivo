# 🚀 Guía de Actualización - Roles y Datos de Prueba

## ¿Qué cambió?

### ✅ Cambios Backend
- **Rol.java**: Ahora soporta 3 roles: `ADMIN`, `CLIENTE`, `VENDEDOR`
- **LoadTestData.java**: Script para cargar 100 usuarios + 50 productos

### ✅ Cambios Frontend
- **CartContext.jsx**: Mejora para manejar carrito vacío (404)
- **mercadolibre.css**: Estilos nuevos tipo Mercado Libre

---

## 📋 Paso 1: Ejecutar el Backend

```bash
# Desde raíz del proyecto
docker-compose up -d

# Espera a que esté listo (aprox 2 minutos)
docker-compose logs backend | grep "Started"
```

---

## 🔄 Paso 2: Cargar Datos de Prueba

### Opción A: Windows (PowerShell)

```powershell
# Desde f:\Proyecto Independiente
.\load-test-data.bat
```

### Opción B: Linux/Mac

```bash
# Desde raíz del proyecto
bash load-test-data.sh
```

### Opción C: Manual (Java)

```bash
cd back
javac LoadTestData.java
java LoadTestData
```

---

## 👥 Usuarios de Prueba Creados

### Admins (3 usuarios)
- **usuario1** / pass123 (Admin)
- **usuario2** / pass123 (Admin)
- **usuario3** / pass123 (Admin)

### Clientes (48 usuarios)
- **usuario4** / pass123 (Cliente)
- **usuario5** / pass123 (Cliente)
- ...
- **usuario51** / pass123 (Cliente)

### Vendedores (49 usuarios)
- **usuario52** / pass123 (Vendedor)
- **usuario53** / pass123 (Vendedor)
- ...
- **usuario100** / pass123 (Vendedor)

---

## 🛒 Productos Cargados

- **50 productos** distribuidos entre vendedores
- Categorías: Deportes, Calzado, Accesorios, Ropa, etc.
- Precios: $29.99 a $399.99
- Stock: 5 a 100 unidades

---

## 🎨 Estilos Mercado Libre

El nuevo archivo `mercadolibre.css` incluye:
- Paleta de colores Mercado Libre (Amarillo #ffd500, Azul #3483fa)
- Componentes modernos: botones, tarjetas, alertas
- Sistema de espaciado consistente
- Responsive design

### Para usar los estilos:

Actualiza `index.css` (dentro de `front/src`):

```css
/* Al final, agrega: */
@import './styles/mercadolibre.css';
```

---

## 🔑 Próximos Cambios Necesarios (Manual)

### 1. Proteger Rutas por Rol

**En `AccessPage.jsx`**: Solo admins pueden crear usuarios

```jsx
const { user } = useAuth();
if (!user || user.rol !== 'ADMIN') {
  return <Navigate to="/" replace />;
}
```

### 2. Página de Vendedor

Crear `front/src/pages/VendorPage.jsx` para que vendedores suban productos:

```jsx
export function VendorPage() {
  const { user } = useAuth();
  
  if (user?.rol !== 'VENDEDOR') {
    return <div>Acceso denegado</div>;
  }
  
  return (
    <div className="page container">
      <h1>Mis Productos</h1>
      {/* Formulario para crear producto */}
    </div>
  );
}
```

### 3. Restricción de Compra

**En `ProductCard.jsx`**: Solo clientes pueden comprar

```jsx
if (!isAuthenticated || user?.rol !== 'CLIENTE') {
  return <button disabled>Inicia sesión para comprar</button>;
}
```

---

## 📊 Flujo de Roles

```
┌─────────────────────────────────────────┐
│          USUARIO NO AUTENTICADO         │
├─────────────────────────────────────────┤
│ • Ver productos                         │
│ • Ver tienda                            │
│ • Registrarse como CLIENTE o VENDEDOR  │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│              ADMIN                      │
├─────────────────────────────────────────┤
│ • Ver panel de administración           │
│ • Crear usuarios (Cliente/Vendedor)    │
│ • Gestionar productos                  │
│ • Ver reportes                         │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│            CLIENTE                      │
├─────────────────────────────────────────┤
│ • Ver productos                         │
│ • Agregar al carrito                   │
│ • Comprar                              │
│ • Ver historial de compras             │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│           VENDEDOR                      │
├─────────────────────────────────────────┤
│ • Ver productos propios                 │
│ • Crear nuevos productos               │
│ • Editar precios/stock                 │
│ • Ver ventas                           │
└─────────────────────────────────────────┘
```

---

## ✅ Verificación

Después de ejecutar todo:

1. Abre http://localhost:3000
2. Registra un usuario (será CLIENTE por defecto)
3. Navega a productos (deberías ver 50)
4. Intenta agregar al carrito
5. Accede como `usuario1` (admin) para ver funciones extra

---

## 🔍 Troubleshooting

### "Error: No se pudo conectar"
```bash
# Verifica que Docker está corriendo
docker ps
# Deberías ver: mysql-db, spring-backend, react-frontend
```

### "El script de datos no funciona"
```bash
# Verifica que el backend está listo
curl http://localhost:8080/api/v1/health
# Deberías ver: {"status":"UP"}
```

### "No veo los productos"
```bash
# Recarga la página (Ctrl+Shift+R)
# O ejecuta nuevamente:
java LoadTestData
```

---

**Última actualización:** Mayo 2026
