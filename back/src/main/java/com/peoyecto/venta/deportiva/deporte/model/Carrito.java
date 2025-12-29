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
import java.util.List;

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
    private Long id_carrito;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "carrito_productos",
        joinColumns = @JoinColumn(name = "id_carrito"),
        inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private java.util.List<Producto> productos;


    @Column
    private Integer cantidad;

    @Column
    private Double precioTotal;

    @Column
    @CreationTimestamp
    private DateTime fechaCreacion;
}