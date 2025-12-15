package com.peoyecto.venta.deportiva.deporte.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    Long id_producto;

    @Column(length = 100)
    String nombre;

    @Column(length = 255)
    String descripcion;

    @Column(length = 50)
    String categoria;

    @Column
    Double precio;

    @Column
    Integer stock;

    @Column
    Boolean disponible;

    @Column(length = 255)
    String imagenUrl;
}