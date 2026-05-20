package com.peoyecto.venta.deportiva.deporte.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.peoyecto.venta.deportiva.deporte.DTO.ProductoDTO;
import com.peoyecto.venta.deportiva.deporte.services.ProductoService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarProductos() {
        log.info("GET /api/v1/productos - Listando todos los productos");
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProductoDTO> productos = productoService.obtenerTodosLosProductos();
            response.put("success", true);
            response.put("message", "Productos listados exitosamente");
            response.put("data", productos);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al listar productos: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al listar productos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerProducto(@PathVariable Long id) {
        log.info("GET /api/v1/productos/{} - Obteniendo producto", id);
        Map<String, Object> response = new HashMap<>();
        try {
            ProductoDTO producto = productoService.obtenerProductoPorId(id);
            if (producto != null) {
                response.put("success", true);
                response.put("message", "Producto obtenido exitosamente");
                response.put("data", producto);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Producto no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error("Error al obtener producto: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al obtener producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearProducto(@RequestBody ProductoDTO productoDTO) {
        log.info("POST /api/v1/productos - Creando producto");
        Map<String, Object> response = new HashMap<>();
        try {
            ProductoDTO productoCreado = productoService.guardarProducto(productoDTO);
            response.put("success", true);
            response.put("message", "Producto creado exitosamente");
            response.put("data", productoCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error al crear producto: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al crear producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> modificarProducto(@PathVariable Long id,
            @RequestBody ProductoDTO productoDTO) {
        log.info("PUT /api/v1/productos/{} - Modificando producto", id);
        Map<String, Object> response = new HashMap<>();
        try {
            ProductoDTO productoActualizado = productoService.modificarProducto(id, productoDTO);
            response.put("success", true);
            response.put("message", "Producto modificado exitosamente");
            response.put("data", productoActualizado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al modificar producto: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al modificar producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable Long id) {
        log.info("DELETE /api/v1/productos/{} - Eliminando producto", id);
        Map<String, Object> response = new HashMap<>();
        try {
            productoService.eliminarProducto(id);
            response.put("success", true);
            response.put("message", "Producto eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al eliminar producto: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al eliminar producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
