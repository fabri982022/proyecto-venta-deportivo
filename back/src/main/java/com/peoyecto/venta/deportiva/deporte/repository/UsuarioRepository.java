package com.peoyecto.venta.deportiva.deporte.repository;

import com.peoyecto.venta.deportiva.deporte.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.nombre_usuario = :nombre_usuario")
    Usuario getUserByNombreUsuario(@Param("nombre_usuario") String nombre_usuario);
}