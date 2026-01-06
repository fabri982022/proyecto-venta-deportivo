package com.peoyecto.venta.deportiva.deporte.repository;

import com.peoyecto.venta.deportiva.deporte.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}