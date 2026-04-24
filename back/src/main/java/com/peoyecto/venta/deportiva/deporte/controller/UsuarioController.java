package com.peoyecto.venta.deportiva.deporte.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioAdminDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioClienteDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioLogisticaDTO;
import com.peoyecto.venta.deportiva.deporte.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
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

}
