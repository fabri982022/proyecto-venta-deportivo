package com.peoyecto.venta.deportiva.deporte.DTO;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import com.peoyecto.venta.deportiva.deporte.DTO.ClienteDTO;
import java.util.List;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDTO {
    private Long id_carrito;
    private ClienteDTO cliente;
    private List<ProductoDTO>productos;
    private Integer cantidad;
    private Double precio_total;
    private LocalDateTime fecha_creacion;
}