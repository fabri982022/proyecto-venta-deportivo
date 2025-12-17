package com.peoyecto.venta.deportiva.deporte.repository;
import com.peoyecto.venta.deportiva.deporte.model.Pedido;;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}