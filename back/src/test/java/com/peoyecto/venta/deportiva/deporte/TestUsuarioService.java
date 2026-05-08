package com.peoyecto.venta.deportiva.deporte;

import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioAdminDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioClienteDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioLogisticaDTO;
import com.peoyecto.venta.deportiva.deporte.services.UsuarioService;
import com.peoyecto.venta.deportiva.deporte.util.Rol;

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
class TestUsuarioService {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    private com.peoyecto.venta.deportiva.deporte.repository.UsuarioRepository usuarioRepository;

    static UsuarioAdminDTO adminDTO;
    static UsuarioClienteDTO clienteDTO;
    static UsuarioLogisticaDTO logisticaDTO;

    @BeforeAll

    static void init() {
        log.info("Iniciando tests de UsuarioService");
    }

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada test
        usuarioRepository.deleteAll();

        adminDTO = new UsuarioAdminDTO();

        adminDTO.setEstado(true);
        adminDTO.setNombre("Admin");
        adminDTO.setEmail("gababa@gmail.com");
        adminDTO.setPassword("admin123");
        adminDTO.setRol(Rol.ADMIN);
        adminDTO.setApellido("Apellido");
        adminDTO.setDni("12345678");
        adminDTO.setPassword("1234");
        adminDTO.setNombre_usuario("admin_test");
        adminDTO.setDepartamento("IT");

        clienteDTO = new UsuarioClienteDTO();

        clienteDTO.setEstado(true);
        clienteDTO.setNombre("Cliente");
        clienteDTO.setEmail("cliente@gmail.com");
        clienteDTO.setPassword("cliente123");
        clienteDTO.setRol(Rol.USER);
        clienteDTO.setApellido("Apellido");
        clienteDTO.setDni("87654321");
        clienteDTO.setPassword("5678");
        clienteDTO.setNombre_usuario("cliente_test");
        clienteDTO.setTelefono("123456789");
        clienteDTO.setDireccion("Calle Falsa 123");

        logisticaDTO = new UsuarioLogisticaDTO();

        logisticaDTO.setEstado(true);
        logisticaDTO.setNombre("Logistica");
        logisticaDTO.setEmail("logisitca@gmail.com");
        logisticaDTO.setPassword("logistica123");
        logisticaDTO.setRol(Rol.LOGISTIC);
        logisticaDTO.setApellido("ApellidoL");
        logisticaDTO.setDni("11223344");
        logisticaDTO.setPassword("91011");
        logisticaDTO.setNombre_usuario("logistica_test");
        logisticaDTO.setDepartamento("calle siempreviva");
    }

    @Test
    void crearUsuarioAdminTest() throws IOException {
        log.info("Creando usuario Admin");
        UsuarioAdminDTO creado = usuarioService.guardarUsuarioAdmin(adminDTO);
        assertNotNull(creado);
        assertEquals(adminDTO.getNombre(), creado.getNombre());
        assertEquals(adminDTO.getRol(), creado.getRol());
        assertEquals(adminDTO.getDepartamento(), creado.getDepartamento());
        log.info("Usuario Admin creado con ID: {}", creado.getId_usuario());
    }

    @Test
    void crearUsuarioClienteTest() throws IOException {
        log.info("Creando usuario Cliente");
        UsuarioClienteDTO creado = usuarioService.guardarUsuarioCliente(clienteDTO);
        assertNotNull(creado);
        assertEquals(clienteDTO.getNombre(), creado.getNombre());
        assertEquals(clienteDTO.getRol(), creado.getRol());
        assertEquals(clienteDTO.getTelefono(), creado.getTelefono());
        log.info("Usuario Cliente creado con ID: {}", creado.getId_usuario());
    }

    @Test
    void crearUsuarioLogisticaTest() throws IOException {
        log.info("Creando usuario Logistica");
        UsuarioLogisticaDTO creado = usuarioService.guardarUsuarioLogistica(logisticaDTO);
        assertNotNull(creado);
        assertEquals(logisticaDTO.getNombre(), creado.getNombre());
        assertEquals(logisticaDTO.getRol(), creado.getRol());
        assertEquals(logisticaDTO.getDepartamento(), creado.getDepartamento());
        log.info("Usuario Logistica creado con ID: {}", creado.getId_usuario());
    }

    @Test
    void buscarUsuarioPorIdTest() throws IOException {
        log.info("Buscando usuario por ID");
        UsuarioAdminDTO creado = usuarioService.guardarUsuarioAdmin(adminDTO);
        assertNotNull(creado);
        Long idBuscado = creado.getId_usuario();
        log.info("Buscando usuario con ID: {}", idBuscado);
        assertDoesNotThrow(() -> {
            assertNotNull(usuarioService.obtenerUsuarioPorId(idBuscado));
        });
        log.info("Usuario encontrado: {}", idBuscado);
    }

    @Test
    void buscarUsuarioPorIdNoExistenteTest() throws IOException {
        log.info("Buscando usuario por ID no existente");
        Long idNoExistente = 99999L;
        log.info("Buscando usuario con ID: {}", idNoExistente);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.obtenerUsuarioPorId(idNoExistente);
        });
        String expectedMessage = "Usuario no encontrado con id: " + idNoExistente;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        log.info("Excepción lanzada correctamente para ID no existente: {}", idNoExistente);
    }

    @Test
    void crearYBuscarUsuarioClienteTest() throws IOException {
        log.info("Creando y buscando usuario Cliente");
        UsuarioClienteDTO creado = usuarioService.guardarUsuarioCliente(clienteDTO);
        assertNotNull(creado);
        Long idBuscado = creado.getId_usuario();
        log.info("Buscando usuario con ID: {}", idBuscado);
        assertDoesNotThrow(() -> {
            assertNotNull(usuarioService.obtenerUsuarioPorId(idBuscado));
        });
        log.info("Usuario encontrado: {}", idBuscado);
    }

    @Test
    void eliminarUsuarioTest() throws IOException {
        log.info("Creando y eliminando usuario Admin");
        UsuarioAdminDTO creado = usuarioService.guardarUsuarioAdmin(adminDTO);
        assertNotNull(creado);
        Long idEliminar = creado.getId_usuario();
        log.info("Eliminando usuario con ID: {}", idEliminar);
        assertDoesNotThrow(() -> {
            usuarioService.eliminarUsuario(idEliminar);
        });
        log.info("Usuario eliminado: {}", idEliminar);
        log.info("Verificando que el usuario ya no existe");
        log.info("Verificación completa: Usuario no encontrado tras eliminación");
    }
}