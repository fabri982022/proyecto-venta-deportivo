package com.peoyecto.venta.deportiva.deporte.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.peoyecto.venta.deportiva.deporte.model.Usuario;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioAdminDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioClienteDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioLogisticaDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioVendedorDTO;
import com.peoyecto.venta.deportiva.deporte.services.UsuarioService;

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
@RequestMapping("/api/v1/usuarios")

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/cliente")
    public ResponseEntity<Map<String, Object>> crearUsuarioCliente(@RequestBody UsuarioClienteDTO usuarioClienteDTO) {
        log.info("POST /api/v1/usuarios/cliente - Creando usuario cliente");
        Map<String, Object> response = new HashMap<>();
        try {
            UsuarioClienteDTO usuarioCreado = usuarioService.guardarUsuarioCliente(usuarioClienteDTO);
            response.put("success", true);
            response.put("message", "Usuario cliente creado exitosamente");
            response.put("data", usuarioCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error al crear usuario cliente: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al crear usuario cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping(value = "/admin")
    public ResponseEntity<Map<String, Object>> crearUsuarioAdmin(@RequestBody UsuarioAdminDTO usuarioAdminDTO) {
        log.info("POST /api/v1/usuarios/admin - Creando usuario admin");
        Map<String, Object> response = new HashMap<>();
        try {
            UsuarioAdminDTO usuarioCreado = usuarioService.guardarUsuarioAdmin(usuarioAdminDTO);
            response.put("success", true);
            response.put("message", "Usuario admin creado exitosamente");
            response.put("data", usuarioCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error al crear usuario admin: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al crear usuario admin: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping(value = "/logistica")
    public ResponseEntity<Map<String, Object>> crearUsuarioLogistica(
            @RequestBody UsuarioLogisticaDTO usuarioLogisticaDTO) {
        log.info("POST /api/v1/usuarios/logistica - Creando usuario logistica");
        Map<String, Object> response = new HashMap<>();
        try {
            UsuarioLogisticaDTO usuarioCreado = usuarioService.guardarUsuarioLogistica(usuarioLogisticaDTO);
            response.put("success", true);
            response.put("message", "Usuario logistica creado exitosamente");
            response.put("data", usuarioCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error al crear usuario logistica: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al crear usuario logistica: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping(value = "/vendedor")
    public ResponseEntity<Map<String, Object>> crearUsuarioVendedor(
            @RequestBody UsuarioVendedorDTO usuarioVendedorDTO) {
        log.info("POST /api/v1/usuarios/vendedor - Creating vendor user");
        Map<String, Object> response = new HashMap<>();
        try {
            UsuarioVendedorDTO usuarioCreado = usuarioService.guardarUsuarioVendedor(usuarioVendedorDTO);
            response.put("success", true);
            response.put("message", "Vendor user created successfully");
            response.put("data", usuarioCreado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error creating vendor user: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error creating vendor user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id_usuario}")
    public ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable Long id_usuario) {
        log.info("GET /api/v1/usuarios/{} - Obteniendo usuario", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(id_usuario);
            response.put("success", true);
            response.put("message", "Usuario obtenido exitosamente");
            response.put("data", usuario);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener usuario: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al obtener usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarUsuarios() {
        log.info("GET /api/v1/usuarios - Listando todos los usuarios");
        Map<String, Object> response = new HashMap<>();
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            response.put("success", true);
            response.put("message", "Usuarios listados exitosamente");
            response.put("data", usuarios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al listar usuarios: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al listar usuarios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        log.info("POST /api/v1/usuarios/login - Iniciando sesión");
        Map<String, Object> response = new HashMap<>();
        try {
            String nombre_usuario = credentials.get("nombre_usuario");
            String password = credentials.get("password");

            if (nombre_usuario == null || nombre_usuario.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "nombre_usuario requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (password == null || password.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "password requerido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            UsuarioDTO usuarioAutenticado = usuarioService.login(nombre_usuario.trim(), password.trim());
            response.put("success", true);
            response.put("message", "Sesión iniciada exitosamente");
            response.put("data", usuarioAutenticado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al iniciar sesión: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PutMapping("/cliente/{id_usuario}")
    public ResponseEntity<Map<String, Object>> modificarUsuarioCliente(
            @PathVariable Long id_usuario,
            @RequestBody UsuarioClienteDTO usuarioClienteDTO) {
        log.info("PUT /api/v1/usuarios/cliente/{} - Modificando usuario cliente", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            UsuarioClienteDTO usuarioActualizado = usuarioService.modificarUsuarioCliente(id_usuario,
                    usuarioClienteDTO);
            response.put("success", true);
            response.put("message", "Usuario cliente modificado exitosamente");
            response.put("data", usuarioActualizado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al modificar usuario cliente: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al modificar usuario cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/admin/{id_usuario}")
    public ResponseEntity<Map<String, Object>> modificarUsuarioAdmin(
            @PathVariable Long id_usuario,
            @RequestBody UsuarioAdminDTO usuarioAdminDTO) {
        log.info("PUT /api/v1/usuarios/admin/{} - Modificando usuario admin", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            UsuarioAdminDTO usuarioActualizado = usuarioService.modificarUsuarioAdmin(id_usuario, usuarioAdminDTO);
            response.put("success", true);
            response.put("message", "Usuario admin modificado exitosamente");
            response.put("data", usuarioActualizado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al modificar usuario admin: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al modificar usuario admin: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/logistica/{id_usuario}")
    public ResponseEntity<Map<String, Object>> modificarUsuarioLogistica(
            @PathVariable Long id_usuario,
            @RequestBody UsuarioLogisticaDTO usuarioLogisticaDTO) {
        log.info("PUT /api/v1/usuarios/logistica/{} - Modificando usuario logistica", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            UsuarioLogisticaDTO usuarioActualizado = usuarioService.modificarUsuarioLogistica(id_usuario,
                    usuarioLogisticaDTO);
            response.put("success", true);
            response.put("message", "Usuario logistica modificado exitosamente");
            response.put("data", usuarioActualizado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al modificar usuario logistica: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al modificar usuario logistica: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id_usuario}")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable Long id_usuario) {
        log.info("DELETE /api/v1/usuarios/{} - Eliminando usuario", id_usuario);
        Map<String, Object> response = new HashMap<>();
        try {
            usuarioService.eliminarUsuario(id_usuario);
            response.put("success", true);
            response.put("message", "Usuario eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al eliminar usuario: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error al eliminar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}