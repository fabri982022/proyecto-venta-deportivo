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
    private Long id_pedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column
    private Double total;

    @Column(length = 50)
    private String estado;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fecha_creacion;

    @UpdateTimestamp
    private LocalDateTime fecha_actualizacion;
}