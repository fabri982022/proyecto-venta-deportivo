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


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    Long id_pedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    Cliente cliente;

    @Column
    Double total;

    @Column(length = 50)
    String estado;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime fecha_creacion;

    @UpdateTimestamp
    LocalDateTime fecha_actualizacion;
}