# Endpoints API - Proyecto Independiente Deportivo

## 🚀 Inicio Rápido

```bash
cd f:\proyecto-indepndiente-deportivo
docker-compose up --build
```

**Servicios disponibles después del startup:**
- **Backend API**: http://localhost:8080
- **Frontend**: http://localhost:3000
- **Adminer (BD)**: http://localhost:8081
- **MySQL**: localhost:3307 (usuario: root, contraseña: password)

---

## 📋 USUARIOS API

### 1. Crear Usuario Cliente
```bash
POST http://localhost:8080/api/v1/usuarios/cliente

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "dni": "12345678",
  "email": "juan@example.com",
  "password": "password123",
  "nombre_usuario": "juanperez",
  "telefono": "1234567890",
  "direccion": "Calle Principal 123"
}
```

### 2. Crear Usuario Admin
```bash
POST http://localhost:8080/api/v1/usuarios/admin

{
  "nombre": "Admin",
  "apellido": "Principal",
  "dni": "87654321",
  "email": "admin@example.com",
  "password": "adminpass",
  "nombre_usuario": "admin",
  "departamento": "Gerencia"
}
```

### 3. Crear Usuario Logística
```bash
POST http://localhost:8080/api/v1/usuarios/logistica

{
  "nombre": "Logistica",
  "apellido": "Manager",
  "dni": "11111111",
  "email": "logistica@example.com",
  "password": "logpass",
  "nombre_usuario": "logisticamgr",
  "departamento": "Almacén"
}
```

### 4. Obtener Todos los Usuarios
```bash
GET http://localhost:8080/api/v1/usuarios
```

### 5. Obtener Usuario por ID
```bash
GET http://localhost:8080/api/v1/usuarios/{id}
```

### 6. Modificar Usuario Cliente
```bash
PUT http://localhost:8080/api/v1/usuarios/cliente/{id}

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "dni": "12345678",
  "email": "juan@example.com",
  "nombre_usuario": "juanperez",
  "telefono": "9876543210",
  "direccion": "Avenida Principal 456"
}
```

### 7. Modificar Usuario Admin
```bash
PUT http://localhost:8080/api/v1/usuarios/admin/{id}

{
  "nombre": "Admin",
  "apellido": "Principal",
  "dni": "87654321",
  "email": "admin@example.com",
  "nombre_usuario": "admin",
  "departamento": "Dirección General"
}
```

### 8. Modificar Usuario Logística
```bash
PUT http://localhost:8080/api/v1/usuarios/logistica/{id}

{
  "nombre": "Logistica",
  "apellido": "Manager",
  "dni": "11111111",
  "email": "logistica@example.com",
  "nombre_usuario": "logisticamgr",
  "departamento": "Distribución"
}
```

### 9. Eliminar Usuario
```bash
DELETE http://localhost:8080/api/v1/usuarios/{id}
```

---

## 🏀 PRODUCTOS API

### 1. Crear Producto
```bash
POST http://localhost:8080/api/v1/productos

{
  "nombre": "Balón de Fútbol",
  "descripcion": "Balón oficial de fútbol 5",
  "categoria": "Fútbol",
  "precio": 89.99,
  "stock": 50,
  "disponible": true,
  "imagenUrl": "https://example.com/balon.jpg"
}
```

### 2. Listar Todos los Productos
```bash
GET http://localhost:8080/api/v1/productos
```

### 3. Obtener Producto por ID
```bash
GET http://localhost:8080/api/v1/productos/{id}
```

### 4. Modificar Producto
```bash
PUT http://localhost:8080/api/v1/productos/{id}

{
  "nombre": "Balón de Fútbol Premium",
  "descripcion": "Balón oficial de fútbol 5 - Edición Premium",
  "categoria": "Fútbol",
  "precio": 119.99,
  "stock": 45,
  "disponible": true,
  "imagenUrl": "https://example.com/balon-premium.jpg"
}
```

### 5. Eliminar Producto (marcar como no disponible)
```bash
DELETE http://localhost:8080/api/v1/productos/{id}
```

---

## 🛒 CARRITO API

### 1. Obtener Carrito de Cliente
```bash
GET http://localhost:8080/api/v1/carrito/cliente/{id_usuario}
```

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Carrito obtenido exitosamente",
  "data": {
    "id_carrito": 1,
    "id_cliente": 1,
    "items": [
      {
        "id_carrito_item": 1,
        "cantidad": 2,
        "producto": {
          "id_producto": 1,
          "nombre": "Balón de Fútbol",
          "precio": 89.99
        }
      }
    ]
  }
}
```

### 2. Verificar si Existe Carrito
```bash
GET http://localhost:8080/api/v1/carrito/cliente/{id_usuario}/existe
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Verificación completada",
  "data": true
}
```

### 3. Obtener Total de Items del Carrito
```bash
GET http://localhost:8080/api/v1/carrito/cliente/{id_usuario}/total-items
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Total de items obtenido",
  "data": 5
}
```

### 4. Calcular Total del Carrito (Precio)
```bash
GET http://localhost:8080/api/v1/carrito/cliente/{id_usuario}/total
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Total calculado exitosamente",
  "data": 359.96
}
```

### 5. Agregar Item al Carrito
```bash
POST http://localhost:8080/api/v1/carrito/cliente/{id_usuario}/item?id_producto=1&cantidad=2
```

**Parámetros:**
- `id_producto` (required) - ID del producto a agregar
- `cantidad` (required) - Cantidad (debe ser > 0)

**Respuesta exitosa (201):**
```json
{
  "success": true,
  "message": "Item agregado exitosamente",
  "data": {
    "id_carrito_item": 1,
    "cantidad": 2,
    "producto": {
      "id_producto": 1,
      "nombre": "Balón de Fútbol",
      "precio": 89.99
    }
  }
}
```

**Ejemplo cURL:**
```bash
curl -X POST "http://localhost:8080/api/v1/carrito/cliente/1/item?id_producto=1&cantidad=2" \
  -H "Content-Type: application/json"
```

### 6. Actualizar Cantidad de Item
```bash
PUT http://localhost:8080/api/v1/carrito/item/{id_carrito_item}?cantidad=5
```

**Parámetros:**
- `cantidad` (required) - Nueva cantidad (debe ser > 0)

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Cantidad actualizada exitosamente",
  "data": {
    "id_carrito_item": 1,
    "cantidad": 5,
    "producto": {
      "id_producto": 1,
      "nombre": "Balón de Fútbol",
      "precio": 89.99
    }
  }
}
```

**Ejemplo cURL:**
```bash
curl -X PUT "http://localhost:8080/api/v1/carrito/item/1?cantidad=5" \
  -H "Content-Type: application/json"
```

### 7. Eliminar Item del Carrito
```bash
DELETE http://localhost:8080/api/v1/carrito/item/{id_carrito_item}
```

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Item eliminado correctamente"
}
```

**Ejemplo cURL:**
```bash
curl -X DELETE http://localhost:8080/api/v1/carrito/item/1
```

### 8. Vaciar Carrito (Eliminar todos los items)
```bash
DELETE http://localhost:8080/api/v1/carrito/cliente/{id_usuario}/vaciar
```

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Carrito vaciado correctamente"
}
```

**Ejemplo cURL:**
```bash
curl -X DELETE http://localhost:8080/api/v1/carrito/cliente/1/vaciar
```

### 9. Eliminar Completamente el Carrito
```bash
DELETE http://localhost:8080/api/v1/carrito/cliente/{id_usuario}
```

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Carrito eliminado correctamente"
}
```

**Ejemplo cURL:**
```bash
curl -X DELETE http://localhost:8080/api/v1/carrito/cliente/1
```

---

## 🏥 HEALTH CHECK

### Verificar que el Backend esté corriendo
```bash
GET http://localhost:8080/api/v1/health
```

**Respuesta esperada:**
```json
{
  "success": true,
  "message": "Server is running",
  "data": null
}
```

---

## 🧪 Pruebas con cURL

### Ejemplo: Listar todos los usuarios
```bash
curl -X GET http://localhost:8080/api/v1/usuarios
```

### Ejemplo: Crear un producto
```bash
curl -X POST http://localhost:8080/api/v1/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Zapatillas Deportivas",
    "descripcion": "Zapatillas para correr",
    "categoria": "Calzado",
    "precio": 129.99,
    "stock": 30,
    "disponible": true,
    "imagenUrl": "https://example.com/zapatillas.jpg"
  }'
```

### Ejemplo: Modificar un usuario
```bash
curl -X PUT http://localhost:8080/api/v1/usuarios/cliente/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellido": "García",
    "dni": "12345678",
    "email": "juan@example.com",
    "nombre_usuario": "juangarcia",
    "telefono": "9999999999",
    "direccion": "Nueva Dirección 789"
  }'
```

---

## 📊 Base de Datos

### Acceso a Adminer
- **URL**: http://localhost:8081
- **Sistema**: MySQL
- **Servidor**: db
- **Usuario**: root
- **Contraseña**: password
- **Base de datos**: deporte

### Tablas principales:
- `usuario` - Tabla base de usuarios
- `usuario_cliente` - Datos específicos de clientes
- `usuario_admin` - Datos específicos de admins
- `usuario_logistica` - Datos específicos de logística
- `producto` - Tabla de productos
- `carrito` - Carrito de compras
- `carrito_item` - Items en carrito

---

## ⚠️ Notas Importantes

1. **Null Fields**: Al modificar usuarios, si un campo no se incluye en el JSON, se mantiene el valor anterior (no se sobrescribe con null)

2. **Stock**: En productos, el stock debe ser mayor a 0

3. **Disponibilidad**: Eliminar un producto lo marca como no disponible (soft delete)

4. **Autenticación**: Por ahora no hay autenticación JWT (próxima fase)

5. **Roles**: 
   - `CLIENTE` - Usuario normal que compra
   - `ADMIN` - Administrador del sistema
   - `LOGISTICA` - Encargado de logística

### 📦 Notas del Carrito

1. **Creación Automática**: El carrito se crea automáticamente cuando:
   - Un cliente se registra
   - Se intenta agregar un item a un carrito inexistente

2. **Items**: 
   - Cada item del carrito vincula un producto con una cantidad
   - La cantidad debe ser siempre mayor a 0
   - No se puede duplicar el mismo producto en un carrito (validación futura)

3. **Total del Carrito**:
   - Se calcula en tiempo real: `suma(precio_producto × cantidad)`
   - No se almacena en BD, se calcula cada vez que se solicita

4. **Validaciones**:
   - No se puede agregar un producto que no existe
   - No se puede agregar un cliente que no existe
   - La cantidad debe ser mayor a 0
   - El item a actualizar/eliminar debe existir

5. **Endpoints por Flujo**:
   - **Consultar**: GET (obtener carrito, verificar existencia, totales)
   - **Agregar**: POST (nuevo item)
   - **Modificar**: PUT (cambiar cantidad)
   - **Eliminar**: DELETE (item, vaciar, carrito completo)

---

## 🔍 Verificación de Estado

### Ver logs del backend
```bash
docker-compose logs -f backend
```

### Ver logs de MySQL
```bash
docker-compose logs -f db
```

### Ver todos los logs
```bash
docker-compose logs -f
```

### Verificar estado de contenedores
```bash
docker-compose ps
```

---

## 🛑 Detener los Servicios

```bash
# Detener sin eliminar datos
docker-compose stop

# Detener y eliminar contenedores
docker-compose down

# Detener y eliminar contenedores + volúmenes
docker-compose down -v
```

