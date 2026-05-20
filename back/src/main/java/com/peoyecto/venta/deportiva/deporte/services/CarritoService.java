package com.peoyecto.venta.deportiva.deporte.services;

import com.peoyecto.venta.deportiva.deporte.DTO.CarritoDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.CarritoItemDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.ProductoDTO;
import com.peoyecto.venta.deportiva.deporte.model.Carrito;
import com.peoyecto.venta.deportiva.deporte.model.CarritoItem;
import com.peoyecto.venta.deportiva.deporte.model.Producto;
import com.peoyecto.venta.deportiva.deporte.model.UsuarioCliente;
import com.peoyecto.venta.deportiva.deporte.repository.CarritoRepository;
import com.peoyecto.venta.deportiva.deporte.repository.CarritoItemRepository;
import com.peoyecto.venta.deportiva.deporte.repository.ProductoRepository;
import com.peoyecto.venta.deportiva.deporte.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@Component
public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(CarritoRepository carritoRepository,
            CarritoItemRepository carritoItemRepository,
            UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.carritoItemRepository = carritoItemRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    // Métodos del servicio aquí
    public CarritoDTO obtenerCarritoPorClienteId(Long id_usuario) {
        Carrito carrito = carritoRepository.findByClienteId_usuario(id_usuario);
        if (carrito == null) {
            return null;
        }
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setId_carrito(carrito.getId_carrito());
        carritoDTO.setId_cliente(carrito.getCliente().getId_usuario());
        carritoDTO.setItems(
                carritoItemRepository.findByCarrito(carrito).stream().map(item -> {
                    CarritoItemDTO itemDTO = new CarritoItemDTO();
                    itemDTO.setId_carrito_item(item.getId_carrito_item());
                    itemDTO.setCantidad(item.getCantidad());
                    Producto producto = item.getProducto();
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.setId_producto(producto.getId_producto());
                    productoDTO.setNombre(producto.getNombre());
                    productoDTO.setPrecio(producto.getPrecio());
                    itemDTO.setProducto(productoDTO);
                    return itemDTO;
                }).collect(Collectors.toList()));
        return carritoDTO;
    }

    public boolean carritoExisteParaClienteId(Long id_usuario) {
        return carritoRepository.existsByClienteId_usuario(id_usuario);
    }

    public CarritoItemDTO agregarItemAlCarrito(Long id_usuario, Long id_producto, Integer cantidad) {
        UsuarioCliente cliente = (UsuarioCliente) usuarioRepository.findById(id_usuario).orElse(null);
        Producto producto = productoRepository.findById(id_producto).orElse(null);
        if (cliente == null || producto == null) {
            log.error("Cliente o producto no encontrado");
            return null;
        }
        Carrito carrito = carritoRepository.findByCliente(cliente);
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setCliente(cliente);
            carritoRepository.save(carrito);
        }
        CarritoItem carritoItem = new CarritoItem();
        carritoItem.setCarrito(carrito);
        carritoItem.setProducto(producto);
        carritoItem.setCantidad(cantidad);
        carritoItemRepository.save(carritoItem);

        CarritoItemDTO itemDTO = new CarritoItemDTO();
        itemDTO.setId_carrito_item(carritoItem.getId_carrito_item());
        itemDTO.setCantidad(carritoItem.getCantidad());
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId_producto(producto.getId_producto());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setPrecio(producto.getPrecio());
        itemDTO.setProducto(productoDTO);

        return itemDTO;
    }

    public void eliminarCarritoPorClienteId(Long id_usuario) {
        UsuarioCliente cliente = (UsuarioCliente) usuarioRepository.findById(id_usuario).orElse(null);
        if (cliente != null) {
            Carrito carrito = carritoRepository.findByCliente(cliente);
            if (carrito != null) {
                carritoRepository.delete(carrito);
            }
        }
    }
}