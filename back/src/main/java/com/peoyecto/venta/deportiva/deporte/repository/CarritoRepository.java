package com.peoyecto.venta.deportiva.deporte.repository;
import com.peoyecto.venta.deportiva.deporte.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}