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
    //Metodo para modificar usuario
    //Metodo para eliminar usuario
    //Metodo para listar usuarios
    



}