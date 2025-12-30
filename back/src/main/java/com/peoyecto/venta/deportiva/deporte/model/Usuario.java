package com.peoyecto.venta.deportiva.deporte.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.peoyecto.venta.deportiva.deporte.util.Rol;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long id_usuario;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellido;

    @Column(length = 8, unique = true)
    private String dni;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 64)
    private String password;

    @Column(length = 30, unique = true)
    private String nombre_usuario;

    @Column
    private Boolean estado=true;

    @Enumerated(EnumType.STRING)
    private Rol rol;


}
