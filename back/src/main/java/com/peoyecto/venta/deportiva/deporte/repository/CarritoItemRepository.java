package com.peoyecto.venta.deportiva.deporte.repository;

import org.springframework.stereotype.Repository;
import com.peoyecto.venta.deportiva.deporte.model.CarritoItem;
import com.peoyecto.venta.deportiva.deporte.model.Carrito;
import com.peoyecto.venta.deportiva.deporte.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    // Buscar item específico en un carrito
    CarritoItem findByCarritoAndProducto(Carrito carrito, Producto producto);

    // Listar todos los items de un carrito
    List<CarritoItem> findByCarrito(Carrito carrito);

    // Listar items por ID de carrito - usando @Query
    @Query("SELECT ci FROM CarritoItem ci WHERE ci.carrito.id_carrito = :id_carrito")
    List<CarritoItem> findByCarritoId_carrito(@Param("id_carrito") Long id_carrito);

    // Eliminar todos los items de un carrito
    void deleteByCarrito(Carrito carrito);

    // Verificar si existe un producto en el carrito
    boolean existsByCarritoAndProducto(Carrito carrito, Producto producto);
}