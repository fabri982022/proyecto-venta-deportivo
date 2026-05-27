package com.peoyecto.venta.deportiva.deporte.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UsuarioVendedorDTO {
    private Long id_usuario;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String password;
    private String nombre_usuario;
    private String rol;
    private String direccion;
    private String telefono;
    private String empresa;
    private String telefono_empresa;
    private String direccion_empresa;
    private String ruc;
}
