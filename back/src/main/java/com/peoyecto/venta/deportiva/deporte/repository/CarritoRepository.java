package com.peoyecto.venta.deportiva.deporte.repository;

import com.peoyecto.venta.deportiva.deporte.model.Carrito;
import com.peoyecto.venta.deportiva.deporte.model.UsuarioCliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // Buscar carrito activo de un cliente
    @Query("SELECT c FROM Carrito c WHERE c.cliente = :cliente")
    Carrito findByCliente(@Param("cliente") UsuarioCliente cliente);

    // Usar @Query explícita para evitar problemas con snake_case
    @Query("SELECT c FROM Carrito c WHERE c.cliente.id_usuario = :id_usuario")
    Carrito findByClienteId_usuario(@Param("id_usuario") Long id_usuario);

    // Usar @Query explícita para verificar existencia
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Carrito c WHERE c.cliente.id_usuario = :id_usuario")
    boolean existsByClienteId_usuario(@Param("id_usuario") Long id_usuario);
}