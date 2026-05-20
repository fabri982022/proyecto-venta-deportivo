package com.peoyecto.venta.deportiva.deporte.repository;

import com.peoyecto.venta.deportiva.deporte.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    Producto findByNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre AND p.descripcion = :descripcion AND p.categoria = :categoria AND p.precio = :precio AND p.stock = :stock")
    Producto findByPropiedades(@Param("nombre") String nombre, @Param("descripcion") String descripcion,
            @Param("categoria") String categoria, @Param("precio") Double precio, @Param("stock") Integer stock);
}