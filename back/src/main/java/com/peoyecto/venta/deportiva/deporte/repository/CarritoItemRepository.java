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
    @Query("SELECT ci FROM CarritoItem ci WHERE ci.carrito = :carrito AND ci.producto = :producto")
    CarritoItem findByCarritoAndProducto(@Param("carrito") Carrito carrito, @Param("producto") Producto producto);

    // Listar todos los items de un carrito
    @Query("SELECT ci FROM CarritoItem ci WHERE ci.carrito = :carrito")
    List<CarritoItem> findByCarrito(@Param("carrito") Carrito carrito);

    // Listar items por ID de carrito - usando @Query
    @Query("SELECT ci FROM CarritoItem ci WHERE ci.carrito.id_carrito = :id_carrito")
    List<CarritoItem> findByCarritoId_carrito(@Param("id_carrito") Long id_carrito);

    // Eliminar todos los items de un carrito
    @Query("DELETE FROM CarritoItem ci WHERE ci.carrito = :carrito")
    void deleteByCarrito(@Param("carrito") Carrito carrito);

    // Verificar si existe un producto en el carrito
    @Query("SELECT CASE WHEN COUNT(ci) > 0 THEN true ELSE false END FROM CarritoItem ci WHERE ci.carrito = :carrito AND ci.producto = :producto")
    boolean existsByCarritoAndProducto(@Param("carrito") Carrito carrito, @Param("producto") Producto producto);
}