package com.peoyecto.venta.deportiva.deporte.DTO;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductoDTO {
    private Long id_producto;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Double precio;
    private Integer stock;
    private Boolean disponible;
    private String imagenUrl;
}