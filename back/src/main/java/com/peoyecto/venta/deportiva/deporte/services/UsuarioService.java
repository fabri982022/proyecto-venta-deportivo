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

import com.peoyecto.venta.deportiva.deporte.repository.CarritoRepository;
import com.peoyecto.venta.deportiva.deporte.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
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

        // Si es un UsuarioCliente, convertir a DTO y mostrar datos adicionales
        if (usuario instanceof UsuarioCliente) {
            UsuarioCliente cliente = (UsuarioCliente) usuario;
            log.info("  - Tipo: CLIENTE");
            log.info("  - Teléfono: {}", cliente.getTelefono());
            log.info("  - Dirección: {}", cliente.getDireccion());

            UsuarioClienteDTO clienteDTO = new UsuarioClienteDTO();
            asignarValoresComunesEntityaDTO(clienteDTO, cliente);
            clienteDTO.setTelefono(cliente.getTelefono());
            clienteDTO.setDireccion(cliente.getDireccion());
            clienteDTO.setRol(cliente.getRol());

            log.info("=================================\n");
            return clienteDTO;
        }

        // Si es un UsuarioAdmin, convertir a DTO y mostrar datos adicionales
        if (usuario instanceof UsuarioAdmin) {
            UsuarioAdmin admin = (UsuarioAdmin) usuario;
            log.info("  - Tipo: ADMIN");
            log.info("  - Departamento: {}", admin.getDepartamento());

            UsuarioAdminDTO adminDTO = new UsuarioAdminDTO();
            asignarValoresComunesEntityaDTO(adminDTO, admin);
            adminDTO.setDepartamento(admin.getDepartamento());
            adminDTO.setRol(admin.getRol());

            log.info("=================================\n");
            return adminDTO;
        }

        // Si es un UsuarioLogistica, convertir a DTO y mostrar datos adicionales
        if (usuario instanceof UsuarioLogistica) {
            UsuarioLogistica logistica = (UsuarioLogistica) usuario;
            log.info("  - Tipo: LOGÍSTICA");
            log.info("  - Departamento: {}", logistica.getDepartamento());

            UsuarioLogisticaDTO logisticaDTO = new UsuarioLogisticaDTO();
            asignarValoresComunesEntityaDTO(logisticaDTO, logistica);
            logisticaDTO.setDepartamento(logistica.getDepartamento());
            logisticaDTO.setRol(logistica.getRol());

            log.info("=================================\n");
            return logisticaDTO;
        }

        // Fallback a UsuarioDTO base (nunca debería llegar aquí)
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        asignarValoresComunesEntityaDTO(usuarioDTO, usuario);
        log.info("=================================\n");
        return usuarioDTO;
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Modificar usuario admin
    public UsuarioAdminDTO modificarUsuarioAdmin(Long id, UsuarioAdminDTO usuarioAdminDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!(usuarioExistente instanceof UsuarioAdmin)) {
            throw new RuntimeException("El usuario no es un admin");
        }
        UsuarioAdmin adminExistente = (UsuarioAdmin) usuarioExistente;
        adminExistente.setNombre(usuarioAdminDTO.getNombre());
        adminExistente.setApellido(usuarioAdminDTO.getApellido());
        adminExistente.setDni(usuarioAdminDTO.getDni());
        adminExistente.setEmail(usuarioAdminDTO.getEmail());
        adminExistente.setPassword(usuarioAdminDTO.getPassword());
        adminExistente.setNombre_usuario(usuarioAdminDTO.getNombre_usuario());
        // Solo actualizar si no viene null
        if (usuarioAdminDTO.getDepartamento() != null) {
            adminExistente.setDepartamento(usuarioAdminDTO.getDepartamento());
        }
        Usuario adminActualizado = usuarioRepository.save(adminExistente);
        UsuarioAdminDTO adminActualizadoDTO = new UsuarioAdminDTO();
        asignarValoresComunesEntityaDTO(adminActualizadoDTO, adminActualizado);
        adminActualizadoDTO.setDepartamento(((UsuarioAdmin) adminActualizado).getDepartamento());
        adminActualizadoDTO.setRol(adminActualizado.getRol());
        return adminActualizadoDTO;
    }

    // Modificar usuario logistica
    public UsuarioLogisticaDTO modificarUsuarioLogistica(Long id, UsuarioLogisticaDTO usuarioLogisticaDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!(usuarioExistente instanceof UsuarioLogistica)) {
            throw new RuntimeException("El usuario no es un logistica");
        }
        UsuarioLogistica logisticaExistente = (UsuarioLogistica) usuarioExistente;
        logisticaExistente.setNombre(usuarioLogisticaDTO.getNombre());
        logisticaExistente.setApellido(usuarioLogisticaDTO.getApellido());
        logisticaExistente.setDni(usuarioLogisticaDTO.getDni());
        logisticaExistente.setEmail(usuarioLogisticaDTO.getEmail());
        logisticaExistente.setPassword(usuarioLogisticaDTO.getPassword());
        logisticaExistente.setNombre_usuario(usuarioLogisticaDTO.getNombre_usuario());
        // Solo actualizar si no viene null
        if (usuarioLogisticaDTO.getDepartamento() != null) {
            logisticaExistente.setDepartamento(usuarioLogisticaDTO.getDepartamento());
        }
        Usuario logisticaActualizado = usuarioRepository.save(logisticaExistente);
        UsuarioLogisticaDTO logisticaActualizadoDTO = new UsuarioLogisticaDTO();
        asignarValoresComunesEntityaDTO(logisticaActualizadoDTO, logisticaActualizado);
        logisticaActualizadoDTO.setDepartamento(((UsuarioLogistica) logisticaActualizado).getDepartamento());
        logisticaActualizadoDTO.setRol(logisticaActualizado.getRol());
        return logisticaActualizadoDTO;
    }

    // Modificar usuario cliente
    public UsuarioClienteDTO modificarUsuarioCliente(Long id, UsuarioClienteDTO usuarioClienteDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!(usuarioExistente instanceof UsuarioCliente)) {
            throw new RuntimeException("El usuario no es un cliente");
        }
        UsuarioCliente clienteExistente = (UsuarioCliente) usuarioExistente;
        clienteExistente.setNombre(usuarioClienteDTO.getNombre());
        clienteExistente.setApellido(usuarioClienteDTO.getApellido());
        clienteExistente.setDni(usuarioClienteDTO.getDni());
        clienteExistente.setEmail(usuarioClienteDTO.getEmail());
        clienteExistente.setPassword(usuarioClienteDTO.getPassword());
        clienteExistente.setNombre_usuario(usuarioClienteDTO.getNombre_usuario());
        // Solo actualizar si no viene null
        if (usuarioClienteDTO.getTelefono() != null) {
            clienteExistente.setTelefono(usuarioClienteDTO.getTelefono());
        }
        if (usuarioClienteDTO.getDireccion() != null) {
            clienteExistente.setDireccion(usuarioClienteDTO.getDireccion());
        }
        Usuario clienteActualizado = usuarioRepository.save(clienteExistente);
        UsuarioClienteDTO clienteActualizadoDTO = new UsuarioClienteDTO();
        asignarValoresComunesEntityaDTO(clienteActualizadoDTO, clienteActualizado);
        clienteActualizadoDTO.setTelefono(((UsuarioCliente) clienteActualizado).getTelefono());
        clienteActualizadoDTO.setDireccion(((UsuarioCliente) clienteActualizado).getDireccion());
        clienteActualizadoDTO.setRol(clienteActualizado.getRol());
        return clienteActualizadoDTO;
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