package com.peoyecto.venta.deportiva.deporte.services;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioAdminDTO;;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioClienteDTO;;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioDTO;;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioLogisticaDTO;;
import com.peoyecto.venta.deportiva.deporte.model.Usuario;;
import com.peoyecto.venta.deportiva.deporte.model.UsuarioAdmin;;
import com.peoyecto.venta.deportiva.deporte.model.UsuarioCliente;;
import com.peoyecto.venta.deportiva.deporte.model.UsuarioLogistica;;
import com.peoyecto.venta.deportiva.deporte.repository.UsuarioRepository;;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Service;;
import java.util.Optional;;
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método para guardar un usuario genérico
    public UsuarioClienteDTO guardarUsuarioCliente(UsuarioCliente usuarioCliente) {
        UsuarioCliente usuarioGuardado = usuarioRepository.save(usuarioCliente);
        UsuarioClienteDTO usuarioClienteDTO = new UsuarioClienteDTO();
        asignarValoresComunes(usuarioClienteDTO, usuarioGuardado);
        usuarioClienteDTO.setTelefono(usuarioGuardado.getTelefono());
        usuarioClienteDTO.setDireccion(usuarioGuardado.getDireccion());
        usuarioClienteDTO.setCarrito(null); // Asignar el carrito si es necesario
        return usuarioClienteDTO;
    }

    public UsuarioAdminDTO guardarUsuarioAdmin(UsuarioAdmin usuarioAdmin) {
        UsuarioAdmin usuarioGuardado = usuarioRepository.save(usuarioAdmin);
        UsuarioAdminDTO usuarioAdminDTO = new UsuarioAdminDTO();
        asignarValoresComunes(usuarioAdminDTO, usuarioGuardado);
        usuarioAdminDTO.setDepartamento(usuarioGuardado.getDepartamento());
        return usuarioAdminDTO;
    }

    public UsuarioLogisticaDTO guardarUsuarioLogistica(UsuarioLogistica usuarioLogistica) {
        UsuarioLogistica usuarioGuardado = usuarioRepository.save(usuarioLogistica);
        UsuarioLogisticaDTO usuarioLogisticaDTO = new UsuarioLogisticaDTO();
        asignarValoresComunes(usuarioLogisticaDTO, usuarioGuardado);
        usuarioLogisticaDTO.setDepartamento(usuarioGuardado.getDepartamento());;
        return usuarioLogisticaDTO;
    }

    private void asignarValoresComunes(UsuarioDTO usuarioDTO, Usuario usuario) {
        usuarioDTO.setId_usuario(usuario.getId_usuario());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setDni(usuario.getDni());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setNombre_usuario(usuario.getNombre_usuario());
        usuarioDTO.setRol(usuario.getRol());
    }

    //Metodo para buscar usuario por id
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    //Metodo para modificar usuario
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
        asignarValoresComunes(usuarioActualizadoDTO, usuarioActualizado);
        return usuarioActualizadoDTO;
    }
    //Metodo para eliminar usuario
    public void eliminarUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
    //Metodo para listar usuarios
    public java.util.List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

}