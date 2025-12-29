package com.peoyecto.venta.deportiva.deporte.DTO;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id_pedido;
    private Long id_cliente;
    private LocalDateTime fecha_creacion;
    private LocalDateTime fecha_actualizacion;
    private String estado;
    private Double total;
}