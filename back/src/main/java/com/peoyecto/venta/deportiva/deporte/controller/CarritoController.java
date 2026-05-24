package com.peoyecto.venta.deportiva.deporte.controller;

import java.util.HashMap;
import java.util.Map;

import com.peoyecto.venta.deportiva.deporte.DTO.CarritoDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.CarritoItemDTO;
import com.peoyecto.venta.deportiva.deporte.services.CarritoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carrito")
@Slf4j
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // ============= OBTENER INFORMACIÓN DEL CARRITO =============

    /**
     * Obtiene el carrito completo de un cliente con todos sus items
     * GET /api/v1/carrito/cliente/{id_usuario}
     */
    @GetMapping("/cliente/{id_usuario}")
    public ResponseEntity<Map<String, Object>> obtenerCarrito(@PathVariable Long id_usuario) {
        log.info("GET /api/v1/carrito/cliente/{} - Obteniendo carrito", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            CarritoDTO carrito = carritoService.obtenerCarritoPorClienteId(id_usuario);
            if (carrito != null) {
                response.put("success", true);
                response.put("message", "Carrito obtenido exitosamente");
                response.put("data", carrito);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se encontró carrito para el cliente");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error("Error al obtener carrito: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al obtener el carrito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Verifica si existe un carrito para el cliente
     * GET /api/v1/carrito/cliente/{id_usuario}/existe
     */
    @GetMapping("/cliente/{id_usuario}/existe")
    public ResponseEntity<Map<String, Object>> existeCarrito(@PathVariable Long id_usuario) {
        log.info("GET /api/v1/carrito/cliente/{}/existe - Verificando existencia", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            boolean existe = carritoService.carritoExisteParaClienteId(id_usuario);
            response.put("success", true);
            response.put("message", "Verificación completada");
            response.put("data", existe);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al verificar carrito: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al verificar carrito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Obtiene el total de items en el carrito
     * GET /api/v1/carrito/cliente/{id_usuario}/total-items
     */
    @GetMapping("/cliente/{id_usuario}/total-items")
    public ResponseEntity<Map<String, Object>> obtenerTotalItems(@PathVariable Long id_usuario) {
        log.info("GET /api/v1/carrito/cliente/{}/total-items - Obteniendo total de items", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            Integer totalItems = carritoService.obtenerTotalItems(id_usuario);
            response.put("success", true);
            response.put("message", "Total de items obtenido");
            response.put("data", totalItems);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener total de items: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al obtener total de items: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Calcula el total del carrito (suma de precios × cantidades)
     * GET /api/v1/carrito/cliente/{id_usuario}/total
     */
    @GetMapping("/cliente/{id_usuario}/total")
    public ResponseEntity<Map<String, Object>> calcularTotal(@PathVariable Long id_usuario) {
        log.info("GET /api/v1/carrito/cliente/{}/total - Calculando total", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            Double total = carritoService.calcularTotalCarrito(id_usuario);
            response.put("success", true);
            response.put("message", "Total calculado exitosamente");
            response.put("data", total);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al calcular total: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al calcular total: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ============= AGREGAR ITEMS =============

    /**
     * Agrega un nuevo item al carrito
     * POST /api/v1/carrito/cliente/{id_usuario}/item
     * Parámetros: id_producto, cantidad
     */
    @PostMapping("/cliente/{id_usuario}/item")
    public ResponseEntity<Map<String, Object>> agregarItem(
            @PathVariable Long id_usuario,
            @RequestParam Long id_producto,
            @RequestParam Integer cantidad) {
        log.info("POST /api/v1/carrito/cliente/{}/item - Agregando item", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            if (cantidad <= 0) {
                response.put("success", false);
                response.put("message", "La cantidad debe ser mayor a 0");
                return ResponseEntity.badRequest().body(response);
            }
            CarritoItemDTO item = carritoService.agregarItemAlCarrito(id_usuario, id_producto, cantidad);
            if (item != null) {
                response.put("success", true);
                response.put("message", "Item agregado exitosamente");
                response.put("data", item);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("success", false);
                response.put("message",
                        "No se pudo agregar el item. Verifique que el cliente y producto existan, y que hay stock disponible");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            log.error("Error al agregar item: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al agregar item al carrito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ============= ACTUALIZAR ITEMS =============

    /**
     * Actualiza la cantidad de un item específico
     * PUT /api/v1/carrito/item/{id_carrito_item}
     * Parámetro: cantidad
     */
    @PutMapping("/item/{id_carrito_item}")
    public ResponseEntity<Map<String, Object>> actualizarCantidadItem(
            @PathVariable Long id_carrito_item,
            @RequestParam Integer cantidad) {
        log.info("PUT /api/v1/carrito/item/{} - Actualizando cantidad", id_carrito_item);
        Map<String, Object> response = new HashMap<>();
        try {
            if (cantidad <= 0) {
                response.put("success", false);
                response.put("message", "La cantidad debe ser mayor a 0");
                return ResponseEntity.badRequest().body(response);
            }
            CarritoItemDTO item = carritoService.actualizarCantidadItem(id_carrito_item, cantidad);
            if (item != null) {
                response.put("success", true);
                response.put("message", "Cantidad actualizada exitosamente");
                response.put("data", item);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message",
                        "No se pudo actualizar la cantidad. Verifique que el item exista y que hay stock disponible");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error("Error al actualizar cantidad: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al actualizar cantidad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ============= ELIMINAR ITEMS =============

    /**
     * Elimina un item específico del carrito
     * DELETE /api/v1/carrito/item/{id_carrito_item}
     */
    @DeleteMapping("/item/{id_carrito_item}")
    public ResponseEntity<Map<String, Object>> eliminarItem(@PathVariable Long id_carrito_item) {
        log.info("DELETE /api/v1/carrito/item/{} - Eliminando item", id_carrito_item);
        Map<String, Object> response = new HashMap<>();
        try {
            carritoService.eliminarItemDelCarrito(id_carrito_item);
            response.put("success", true);
            response.put("message", "Item eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al eliminar item: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al eliminar item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Vacía todos los items del carrito sin eliminar el carrito
     * DELETE /api/v1/carrito/cliente/{id_usuario}/vaciar
     */
    @DeleteMapping("/cliente/{id_usuario}/vaciar")
    public ResponseEntity<Map<String, Object>> vaciarCarrito(@PathVariable Long id_usuario) {
        log.info("DELETE /api/v1/carrito/cliente/{}/vaciar - Vaciando carrito", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            carritoService.vaciarCarrito(id_usuario);
            response.put("success", true);
            response.put("message", "Carrito vaciado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al vaciar carrito: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al vaciar carrito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Elimina completamente el carrito del cliente
     * DELETE /api/v1/carrito/cliente/{id_usuario}
     */
    @DeleteMapping("/cliente/{id_usuario}")
    public ResponseEntity<Map<String, Object>> eliminarCarrito(@PathVariable Long id_usuario) {
        log.info("DELETE /api/v1/carrito/cliente/{} - Eliminando carrito", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            carritoService.eliminarCarritoPorClienteId(id_usuario);
            response.put("success", true);
            response.put("message", "Carrito eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al eliminar carrito: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al eliminar carrito: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
