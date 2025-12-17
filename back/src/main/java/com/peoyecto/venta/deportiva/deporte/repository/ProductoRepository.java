package com.peoyecto.venta.deportiva.deporte.repository;
import com.peoyecto.venta.deportiva.deporte.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {


}