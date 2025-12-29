package com.peoyecto.venta.deportiva.deporte.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.peoyecto.venta.deportiva.deporte.model.Carrito;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public Cliente extends Usuario {
    @Column(length = 15)
    private String telefono;

    @Column(length = 100)
    private String direccion;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Carrito carrito;

}
