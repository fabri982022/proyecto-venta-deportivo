package com.peoyecto.venta.deportiva.deporte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioVendedor extends Usuario {
    @Column(length = 100)
    private String empresa;

    @Column(length = 20)
    private String telefono_empresa;

    @Column(length = 100)
    private String direccion_empresa;

    @Column(length = 20)
    private String ruc;
}
