package com.peoyecto.venta.deportiva.deporte.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioClienteDTO;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDTO {
    private Long id_carrito;
    private Long id_cliente;
    private List<CarritoItemDTO> items;
    private Integer cantidad;
    private Double precio_total;
    private LocalDateTime fecha_creacion;
}