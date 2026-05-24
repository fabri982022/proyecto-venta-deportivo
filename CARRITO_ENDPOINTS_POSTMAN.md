# 🛒 ENDPOINTS DEL CARRITO - POSTMAN

**Base URL:** `http://localhost:8080`

---

## 📋 Tabla de Endpoints

| # | Método | Endpoint | Descripción |
|---|--------|----------|-------------|
| 1 | GET | `/api/v1/carrito/cliente/{id_usuario}` | Obtener carrito completo |
| 2 | GET | `/api/v1/carrito/cliente/{id_usuario}/existe` | Verificar si existe carrito |
| 3 | GET | `/api/v1/carrito/cliente/{id_usuario}/total-items` | Total de items en carrito |
| 4 | GET | `/api/v1/carrito/cliente/{id_usuario}/total` | Total de precio del carrito |
| 5 | POST | `/api/v1/carrito/cliente/{id_usuario}/item` | Agregar item al carrito |
| 6 | PUT | `/api/v1/carrito/item/{id_carrito_item}` | Actualizar cantidad de item |
| 7 | DELETE | `/api/v1/carrito/item/{id_carrito_item}` | Eliminar item del carrito |
| 8 | DELETE | `/api/v1/carrito/cliente/{id_usuario}/vaciar` | Vaciar todos los items |
| 9 | DELETE | `/api/v1/carrito/cliente/{id_usuario}` | Eliminar carrito completo |

---

## 🔄 ENDPOINTS DETALLADOS

### 1️⃣ OBTENER CARRITO COMPLETO
```http
GET http://localhost:8080/api/v1/carrito/cliente/1 HTTP/1.1
Host: localhost:8080
```

**Respuesta 200:**
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

---

### 2️⃣ VERIFICAR SI EXISTE CARRITO
```http
GET http://localhost:8080/api/v1/carrito/cliente/1/existe HTTP/1.1
Host: localhost:8080
```

**Respuesta 200:**
```json
{
  "success": true,
  "message": "Verificación completada",
  "data": true
}
```

---

### 3️⃣ OBTENER TOTAL DE ITEMS
```http
GET http://localhost:8080/api/v1/carrito/cliente/1/total-items HTTP/1.1
Host: localhost:8080
```

**Respuesta 200:**
```json
{
  "success": true,
  "message": "Total de items obtenido",
  "data": 5
}
```

---

### 4️⃣ CALCULAR TOTAL DEL CARRITO (PRECIO)
```http
GET http://localhost:8080/api/v1/carrito/cliente/1/total HTTP/1.1
Host: localhost:8080
```

**Respuesta 200:**
```json
{
  "success": true,
  "message": "Total calculado exitosamente",
  "data": 359.96
}
```

---

### 5️⃣ AGREGAR ITEM AL CARRITO
```http
POST http://localhost:8080/api/v1/carrito/cliente/1/item?id_producto=1&cantidad=2 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
```

**Query Parameters:**
- `id_producto` = `1` (ID del producto a agregar)
- `cantidad` = `2` (Cantidad a agregar)

**Respuesta 201:**
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

---

### 6️⃣ ACTUALIZAR CANTIDAD DE ITEM
```http
PUT http://localhost:8080/api/v1/carrito/item/1?cantidad=5 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
```

**Query Parameters:**
- `cantidad` = `5` (Nueva cantidad)

**Respuesta 200:**
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

---

### 7️⃣ ELIMINAR ITEM DEL CARRITO
```http
DELETE http://localhost:8080/api/v1/carrito/item/1 HTTP/1.1
Host: localhost:8080
```

**Respuesta 200:**
```json
{
  "success": true,
  "message": "Item eliminado correctamente"
}
```

---

### 8️⃣ VACIAR CARRITO (Eliminar todos los items)
```http
DELETE http://localhost:8080/api/v1/carrito/cliente/1/vaciar HTTP/1.1
Host: localhost:8080
```

**Respuesta 200:**
```json
{
  "success": true,
  "message": "Carrito vaciado correctamente"
}
```

---

### 9️⃣ ELIMINAR CARRITO COMPLETAMENTE
```http
DELETE http://localhost:8080/api/v1/carrito/cliente/1 HTTP/1.1
Host: localhost:8080
```

**Respuesta 200:**
```json
{
  "success": true,
  "message": "Carrito eliminado correctamente"
}
```

---

## 📝 WORKFLOW RECOMENDADO PARA PROBAR

### Paso 1: Crear un Usuario Cliente
```bash
POST http://localhost:8080/api/v1/usuarios/cliente

{
  "nombre": "Juan",
  "apellido": "García",
  "dni": "12345678",
  "email": "juan@example.com",
  "telefono": "1234567890",
  "nombre_usuario": "juangarcia",
  "contrasena": "password123",
  "direccion": "Calle Principal 123"
}
```

### Paso 2: Crear un Producto
```bash
POST http://localhost:8080/api/v1/productos

{
  "nombre": "Balón de Fútbol",
  "descripcion": "Balón profesional de cuero",
  "categoria": "Fútbol",
  "precio": 89.99,
  "stock": 50,
  "imagenUrl": "https://example.com/balon.jpg"
}
```

### Paso 3: Agregar Item al Carrito
```bash
POST http://localhost:8080/api/v1/carrito/cliente/1/item?id_producto=1&cantidad=2
```

### Paso 4: Obtener Carrito
```bash
GET http://localhost:8080/api/v1/carrito/cliente/1
```

### Paso 5: Actualizar Cantidad
```bash
PUT http://localhost:8080/api/v1/carrito/item/1?cantidad=5
```

### Paso 6: Obtener Total
```bash
GET http://localhost:8080/api/v1/carrito/cliente/1/total
```

### Paso 7: Vaciar Carrito
```bash
DELETE http://localhost:8080/api/v1/carrito/cliente/1/vaciar
```

---

## 🧪 CÓDIGOS DE RESPUESTA

| Código | Significado |
|--------|------------|
| 200 | ✅ OK - Solicitud exitosa |
| 201 | ✅ Created - Recurso creado exitosamente |
| 400 | ❌ Bad Request - Parámetros inválidos |
| 404 | ❌ Not Found - Recurso no encontrado |
| 500 | ❌ Internal Server Error - Error del servidor |

---

## 💡 NOTAS IMPORTANTES

- Reemplaza `{id_usuario}` con el ID del usuario cliente
- Reemplaza `{id_producto}` con el ID del producto
- Reemplaza `{id_carrito_item}` con el ID del item del carrito
- La cantidad debe ser mayor a 0
- El carrito se crea automáticamente cuando se crea un usuario cliente
