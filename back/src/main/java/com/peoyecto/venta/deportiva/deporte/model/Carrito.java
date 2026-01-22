package com.peoyecto.venta.deportiva.deporte.model;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import com.peoyecto.venta.deportiva.deporte.model.UsuarioCliente;
import com.peoyecto.venta.deportiva.deporte.model.Producto;
import java.util.List;
import java.util.ArrayList;

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

    @OneToOne
    @JoinColumn(name = "id_cliente", nullable = false, unique = true)
    private UsuarioCliente cliente;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarritoItem> items = new ArrayList<>();

    @Column
    private Double precioTotal;

    @Column
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    public void calcularPrecioTotal() {
        this.precioTotal = items.stream()
                .mapToDouble(CarritoItem::getPrecioTotal)
                .sum();
    }

    public Integer getCantidad() {
        return items.stream()
                .mapToInt(CarritoItem::getCantidad)
                .sum();
    }
}