package com.peoyecto.venta.deportiva.deporte.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItemDTO {
    private Long id_carrito_item;
    private ProductoDTO producto;
    private Integer cantidad;
    private Double precioTotal;
}
