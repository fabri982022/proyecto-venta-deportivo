package com.peoyecto.venta.deportiva.deporte.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import com.peoyecto.venta.deportiva.deporte.model.Cliente;
import com.peoyecto.venta.deportiva.deporte.model.Producto;
import org.joda.time.DateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    Long id_carrito;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    Producto producto;

    @Column
    Integer cantidad;

    @Column
    Double precioTotal;

    @Column
    @CreationTimestamp
    DateTime fechaCreacion;
}