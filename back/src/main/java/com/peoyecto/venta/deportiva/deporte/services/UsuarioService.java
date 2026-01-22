package com.peoyecto.venta.deportiva.deporte.services;

import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioAdminDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioClienteDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioLogisticaDTO;
import com.peoyecto.venta.deportiva.deporte.model.Usuario;
import com.peoyecto.venta.deportiva.deporte.model.UsuarioAdmin;
import com.peoyecto.venta.deportiva.deporte.model.UsuarioCliente;
import com.peoyecto.venta.deportiva.deporte.model.UsuarioLogistica;
import com.peoyecto.venta.deportiva.deporte.util.Rol;
import com.peoyecto.venta.deportiva.deporte.model.Carrito;
import com.peoyecto.venta.deportiva.deporte.repository.CarritoRepository;
import com.peoyecto.venta.deportiva.deporte.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private CarritoRepository carritoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, CarritoRepository carritoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
    }

    // Método para guardar un usuario genérico
    public UsuarioClienteDTO guardarUsuarioCliente(UsuarioClienteDTO usuarioClienteDTO) {
        UsuarioCliente usuarioCliente = new UsuarioCliente();
        log.info("Creando usuario Cliente");
        log.info("Datos ingresados: Nombre:{}, Apellido:{}, DNI:{}, email:{}, telefono:{}, nombre_usuario:{}",
                usuarioClienteDTO.getNombre(), usuarioClienteDTO.getApellido(), usuarioClienteDTO.getDni(),
                usuarioClienteDTO.getEmail(), usuarioClienteDTO.getTelefono(), usuarioClienteDTO.getNombre_usuario());
        asignarValoresComunesDTOaEntity(usuarioCliente, usuarioClienteDTO);
        usuarioCliente.setRol(Rol.USER);
        usuarioCliente.setTelefono(usuarioClienteDTO.getTelefono());
        usuarioCliente.setDireccion(usuarioClienteDTO.getDireccion());

        UsuarioCliente usuarioGuardado = usuarioRepository.save(usuarioCliente);

        // creacion de carrito automatica al crear usuario cliente
        Carrito carrito = new Carrito();
        carrito.setCliente(usuarioGuardado);
        carrito.setPrecioTotal(0.0);
        carritoRepository.save(carrito);

        log.info("Carrito creado para el usuario cliente con ID: {}", usuarioGuardado.getId_usuario());

        UsuarioClienteDTO usuarioClienteDTORetorno = new UsuarioClienteDTO();
        asignarValoresComunesEntityaDTO(usuarioClienteDTORetorno, usuarioGuardado);
        usuarioClienteDTORetorno.setTelefono(usuarioGuardado.getTelefono());
        usuarioClienteDTORetorno.setDireccion(usuarioGuardado.getDireccion());
        usuarioClienteDTORetorno.setRol(usuarioGuardado.getRol());
        // Asignar el carrito si es necesario
        log.info("Datos almacenados exitosamente!!");
        return usuarioClienteDTORetorno;
    }

    public UsuarioAdminDTO guardarUsuarioAdmin(UsuarioAdminDTO usuarioAdminDTO) {
        UsuarioAdmin usuarioAdmin = new UsuarioAdmin();
        log.info("Creando usuario Admin");
        log.info("Datos ingresados: Nombre:{}, Apellido:{}, DNI:{}, email:{}, telefono:{}, nombre_usuario:{}",
                usuarioAdminDTO.getNombre(), usuarioAdminDTO.getApellido(), usuarioAdminDTO.getDni(),
                usuarioAdminDTO.getEmail(), usuarioAdminDTO.getNombre_usuario());
        asignarValoresComunesDTOaEntity(usuarioAdmin, usuarioAdminDTO);
        usuarioAdmin.setRol(Rol.ADMIN);
        usuarioAdmin.setDepartamento(usuarioAdminDTO.getDepartamento());

        UsuarioAdmin usuarioGuardado = usuarioRepository.save(usuarioAdmin);
        UsuarioAdminDTO usuarioAdminDTORetorno = new UsuarioAdminDTO();
        asignarValoresComunesEntityaDTO(usuarioAdminDTORetorno, usuarioGuardado);
        usuarioAdminDTORetorno.setRol(usuarioGuardado.getRol());
        usuarioAdminDTORetorno.setDepartamento(usuarioGuardado.getDepartamento());

        return usuarioAdminDTORetorno;
    }

    public UsuarioLogisticaDTO guardarUsuarioLogistica(UsuarioLogisticaDTO usuarioLogisticaDTO) {
        UsuarioLogistica usuarioLogistica = new UsuarioLogistica();
        log.info("Creando usuario Logistica");
        log.info("Datos ingresados: Nombre:{}, Apellido:{}, DNI:{}, email:{}, telefono:{}, nombre_usuario:{}",
                usuarioLogisticaDTO.getNombre(), usuarioLogisticaDTO.getApellido(), usuarioLogisticaDTO.getDni(),
                usuarioLogisticaDTO.getEmail(), usuarioLogisticaDTO.getNombre_usuario());
        asignarValoresComunesDTOaEntity(usuarioLogistica, usuarioLogisticaDTO);
        usuarioLogistica.setDepartamento(usuarioLogisticaDTO.getDepartamento());
        usuarioLogistica.setRol(Rol.LOGISTIC);

        UsuarioLogistica usuarioGuardado = usuarioRepository.save(usuarioLogistica);
        UsuarioLogisticaDTO usuarioLogisticaDTORetorno = new UsuarioLogisticaDTO();
        asignarValoresComunesEntityaDTO(usuarioLogisticaDTORetorno, usuarioGuardado);
        usuarioLogisticaDTORetorno.setRol(usuarioGuardado.getRol());
        usuarioLogisticaDTORetorno.setDepartamento(usuarioGuardado.getDepartamento());
        return usuarioLogisticaDTORetorno;
    }

    private void asignarValoresComunesEntityaDTO(UsuarioDTO usuarioDTO, Usuario usuario) {
        usuarioDTO.setId_usuario(usuario.getId_usuario());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setDni(usuario.getDni());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setNombre_usuario(usuario.getNombre_usuario());
        usuarioDTO.setRol(usuario.getRol());
    }

    private void asignarValoresComunesDTOaEntity(Usuario usuario, UsuarioDTO usuarioDTO) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setDni(usuarioDTO.getDni());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setNombre_usuario(usuarioDTO.getNombre_usuario());
        usuario.setRol(usuarioDTO.getRol());
    }

    // Método para buscar usuario por id CON LOGS DETALLADOS
    public Usuario buscarUsuarioPorId(Long id) {
        log.info("=== Buscando Usuario por ID: {} ===", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(" Usuario NO encontrado con ID: {}", id);
                    return new RuntimeException("Usuario no encontrado con id: " + id);
                });

        log.info("✓ Usuario encontrado exitosamente:");
        log.info("  - ID: {}", usuario.getId_usuario());
        log.info("  - Nombre completo: {} {}", usuario.getNombre(), usuario.getApellido());
        log.info("  - DNI: {}", usuario.getDni());
        log.info("  - Email: {}", usuario.getEmail());
        log.info("  - Usuario: {}", usuario.getNombre_usuario());
        log.info("  - Rol: {}", usuario.getRol());
        log.info("  - Estado: {}", usuario.getEstado() ? "Activo" : "Inactivo");

        // Si es un UsuarioCliente, mostrar datos adicionales
        if (usuario instanceof UsuarioCliente) {
            UsuarioCliente cliente = (UsuarioCliente) usuario;
            log.info("  - Tipo: CLIENTE");
            log.info("  - Teléfono: {}", cliente.getTelefono());
            log.info("  - Dirección: {}", cliente.getDireccion());
        }

        // Si es un UsuarioAdmin, mostrar datos adicionales
        if (usuario instanceof UsuarioAdmin) {
            UsuarioAdmin admin = (UsuarioAdmin) usuario;
            log.info("  - Tipo: ADMIN");
            log.info("  - Departamento: {}", admin.getDepartamento());
        }

        // Si es un UsuarioLogistica, mostrar datos adicionales
        if (usuario instanceof UsuarioLogistica) {
            UsuarioLogistica logistica = (UsuarioLogistica) usuario;
            log.info("  - Tipo: LOGÍSTICA");
            log.info("  - Departamento: {}", logistica.getDepartamento());
        }

        log.info("=================================\n");
        return usuario;
    }

    // Metodo para modificar usuario
    public UsuarioDTO modificarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setDni(usuarioDTO.getDni());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setPassword(usuarioDTO.getPassword());
        usuarioExistente.setNombre_usuario(usuarioDTO.getNombre_usuario());
        usuarioExistente.setRol(usuarioDTO.getRol());
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        UsuarioDTO usuarioActualizadoDTO = new UsuarioDTO();
        asignarValoresComunesEntityaDTO(usuarioActualizadoDTO, usuarioActualizado);
        return usuarioActualizadoDTO;
    }

    // Metodo para eliminar usuario
    public void eliminarUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado(false);
        usuarioRepository.save(usuario);
    }

    // Metodo para listar usuarios
    public java.util.List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

}