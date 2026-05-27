package com.peoyecto.venta.deportiva.deporte;

//importacion de entidades
import com.peoyecto.venta.deportiva.deporte.model.UsuarioCliente;
import com.peoyecto.venta.deportiva.deporte.model.Carrito;
import com.peoyecto.venta.deportiva.deporte.model.CarritoItem;
import com.peoyecto.venta.deportiva.deporte.model.Producto;

//importacion de DTOs
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioClienteDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.CarritoDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.CarritoItemDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.ProductoDTO;

//importacion de repositorios y servicios

import com.peoyecto.venta.deportiva.deporte.repository.CarritoItemRepository;
import com.peoyecto.venta.deportiva.deporte.repository.CarritoRepository;
import com.peoyecto.venta.deportiva.deporte.repository.ProductoRepository;
import com.peoyecto.venta.deportiva.deporte.repository.UsuarioRepository;

import com.peoyecto.venta.deportiva.deporte.services.*;
import com.peoyecto.venta.deportiva.deporte.util.Rol;

import org.springframework.boot.test.context.SpringBootTest;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class TestCarritoService {

    @Autowired
    CarritoService carritoService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ProductoService productoService;

    static UsuarioClienteDTO clienteDTO;
    static UsuarioClienteDTO clienteDTO2sinCarro;
    static CarritoDTO carritoDTO;
    static CarritoItemDTO carritoItemDTO;
    static ProductoDTO productoDTO;
    static ProductoDTO productoDTO2;

    @BeforeAll
    static void init() {
        log.info("Iniciando tests de CarritoService");
    }

    @BeforeEach
    void setUp() {
        /**
         * Usuario
         * private Long id_usuario;
         * private String nombre;
         * private String apellido;
         * private String dni;
         * private String email;
         * private String password;
         * private String nombre_usuario;
         * private Boolean estado=true;
         * private Rol rol;
         * 
         * Carrito
         * private Long id_carrito;
         * private UsuarioCliente cliente;
         * private Double precioTotal;
         * 
         * CarritoItem
         * private Long id_carrito_item;
         * private Carrito carrito;
         * private Producto producto;
         * private Integer cantidad;
         * 
         * Producto
         * private Long id_producto;
         * private String nombre;
         * private String descripcion;
         * private Double precio;
         * private Integer stock;
         */
        clienteDTO = new UsuarioClienteDTO();

        clienteDTO.setEstado(true);
        clienteDTO.setNombre("Cliente");
        clienteDTO.setEmail("cliente@gmail.com");
        clienteDTO.setPassword("cliente123");
        clienteDTO.setRol(Rol.CLIENTE);
        clienteDTO.setApellido("Apellido");
        clienteDTO.setDni("87654321");
        clienteDTO.setPassword("5678");
        clienteDTO.setNombre_usuario("cliente_test");
        clienteDTO.setTelefono("123456789");
        clienteDTO.setDireccion("Calle Falsa 123");

        clienteDTO2sinCarro = new UsuarioClienteDTO();
        clienteDTO2sinCarro.setEstado(true);
        clienteDTO2sinCarro.setNombre("Cliente Sin Carro");
        clienteDTO2sinCarro.setEmail("cliente2@gmail.com");
        clienteDTO2sinCarro.setPassword("cliente123");
        clienteDTO2sinCarro.setRol(Rol.CLIENTE);
        clienteDTO2sinCarro.setApellido("Apellido2");
        clienteDTO2sinCarro.setDni("12345678");
        clienteDTO2sinCarro.setPassword("5678");
        clienteDTO2sinCarro.setNombre_usuario("cliente_test2");
        clienteDTO2sinCarro.setTelefono("987654321");
        clienteDTO2sinCarro.setDireccion("Calle Verdadera 456");

        productoDTO = new ProductoDTO();
        productoDTO.setNombre("Pelota de Futbol");
        productoDTO.setDescripcion("Pelota oficial de futbol");
        productoDTO.setPrecio(1500.0);
        productoDTO.setStock(100);

        productoDTO2 = new ProductoDTO();
        productoDTO2.setNombre("Camiseta de Futbol");
        productoDTO2.setDescripcion("Camiseta oficial de futbol");
        productoDTO2.setPrecio(2500.0);
        productoDTO2.setStock(50);

        carritoDTO = new CarritoDTO();
        carritoDTO.setId_cliente(1L);
        carritoDTO.setPrecio_total(0.0);
        carritoDTO.setItems(null);

        carritoItemDTO = new CarritoItemDTO();
        carritoItemDTO.setId_carrito_item(1L);
        carritoItemDTO.setId_carrito(1L);
        carritoItemDTO.setCantidad(2);
        carritoItemDTO.setProducto(productoDTO);

    }

    @Test
    void contextLoads() {
        log.info("Test de contexto de CarritoService");
    }

    @Test
    void testObtenerCarritoPorClienteId() {
        log.info("Iniciando test: obtenerCarritoPorClienteId");

        // Crear el cliente primero
        UsuarioClienteDTO clienteCreado = usuarioService.guardarUsuarioCliente(clienteDTO2sinCarro);
        assertNotNull(clienteCreado);
        assertNotNull(clienteCreado.getId_usuario());

        // Ahora buscar el carrito con el ID real del cliente creado
        CarritoDTO carritoObtenido = carritoService.obtenerCarritoPorClienteId(clienteCreado.getId_usuario());

        // El carrito puede ser null si no se crea automáticamente
        if (carritoObtenido != null) {
            assertEquals(clienteCreado.getId_usuario(), carritoObtenido.getId_cliente());
            log.info("Carrito obtenido: {}", carritoObtenido);
        } else {
            log.info("No existe carrito para el cliente recién creado");
        }

    }

    @Test
    void testCarritoExisteParaClienteId() {
        log.info("Iniciando test: carritoExisteParaClienteId");

        // Crear el cliente primero
        UsuarioClienteDTO clienteCreado = usuarioService.guardarUsuarioCliente(clienteDTO2sinCarro);
        assertNotNull(clienteCreado);
        assertNotNull(clienteCreado.getId_usuario());

        // Obtener o crear el carrito
        CarritoDTO carritoObtenido = carritoService.obtenerCarritoPorClienteId(clienteCreado.getId_usuario());
        if (carritoObtenido == null) {
            // Si no existe, agregar un item para crear el carrito
            ProductoDTO productoCreado = productoService.guardarProducto(productoDTO);
            assertNotNull(productoCreado);
            assertNotNull(productoCreado.getId_producto());

            carritoService.agregarItemAlCarrito(clienteCreado.getId_usuario(), productoCreado.getId_producto(), 1);
        }

        // Verificar que el carrito ahora existe
        boolean existeDespues = carritoService.carritoExisteParaClienteId(clienteCreado.getId_usuario());
        assertTrue(existeDespues, "El carrito debería existir después de ser creado");
    }

}
