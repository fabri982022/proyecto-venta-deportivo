package com.peoyecto.venta.deportiva.deporte.services;

import com.peoyecto.venta.deportiva.deporte.DTO.CarritoDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.CarritoItemDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.ProductoDTO;
import com.peoyecto.venta.deportiva.deporte.model.Carrito;
import com.peoyecto.venta.deportiva.deporte.model.CarritoItem;
import com.peoyecto.venta.deportiva.deporte.model.Producto;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioClienteDTO;
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

        // Mapear datos del cliente
        UsuarioCliente cliente = carrito.getCliente();
        UsuarioClienteDTO clienteDTO = new UsuarioClienteDTO();
        clienteDTO.setId_usuario(cliente.getId_usuario());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setDni(cliente.getDni());
        clienteDTO.setNombre_usuario(cliente.getNombre_usuario());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setDireccion(cliente.getDireccion());
        carritoDTO.setCliente(clienteDTO);

        carritoDTO.setItems(
                carritoItemRepository.findByCarrito(carrito).stream().map(item -> {
                    CarritoItemDTO itemDTO = new CarritoItemDTO();
                    itemDTO.setId_carrito_item(item.getId_carrito_item());
                    itemDTO.setId_carrito(carrito.getId_carrito());
                    itemDTO.setCantidad(item.getCantidad());
                    itemDTO.setPrecioTotal(item.getPrecioTotal());

                    // Mapear datos del cliente en cada item
                    itemDTO.setCliente(clienteDTO);

                    Producto producto = item.getProducto();
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.setId_producto(producto.getId_producto());
                    productoDTO.setNombre(producto.getNombre());
                    productoDTO.setDescripcion(producto.getDescripcion());
                    productoDTO.setCategoria(producto.getCategoria());
                    productoDTO.setPrecio(producto.getPrecio());
                    productoDTO.setStock(producto.getStock());
                    productoDTO.setDisponible(producto.getDisponible());
                    productoDTO.setImagenUrl(producto.getImagenUrl());
                    itemDTO.setProducto(productoDTO);
                    return itemDTO;
                }).collect(Collectors.toList()));

        // Calcular y asignar cantidad total (suma de cantidades de todos los items)
        Integer cantidadTotal = carrito.getItems().stream()
                .mapToInt(CarritoItem::getCantidad)
                .sum();
        carritoDTO.setCantidad(cantidadTotal);

        // Calcular y asignar precio total
        carrito.calcularPrecioTotal();
        carritoDTO.setPrecio_total(carrito.getPrecioTotal());

        // Asignar fecha de creación
        carritoDTO.setFecha_creacion(carrito.getFechaCreacion());

        return carritoDTO;
    }

    public boolean carritoExisteParaClienteId(Long id_usuario) {
        return carritoRepository.existsByClienteId_usuario(id_usuario);
    }

    public CarritoItemDTO agregarItemAlCarrito(Long id_usuario, Long id_producto, Integer cantidad) {
        UsuarioCliente cliente = (UsuarioCliente) usuarioRepository.findById(id_usuario).orElse(null);
        // Buscar producto SOLO por ID
        Producto producto = productoRepository.findById(id_producto).orElse(null);
        if (cliente == null || producto == null) {
            log.error("Cliente o producto no encontrado. Cliente: {}, Producto: {}", cliente, producto);
            return null;
        }

        // Validar stock disponible
        if (producto.getStock() == null || producto.getStock() < cantidad) {
            log.error("Stock insuficiente. Stock disponible: {}, Cantidad solicitada: {}",
                    producto.getStock() != null ? producto.getStock() : 0, cantidad);
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
        // Calcular y establecer el precio total
        carritoItem.setPrecioTotal(cantidad * producto.getPrecio());
        carritoItemRepository.save(carritoItem);

        CarritoItemDTO itemDTO = new CarritoItemDTO();
        itemDTO.setId_carrito_item(carritoItem.getId_carrito_item());
        itemDTO.setId_carrito(carrito.getId_carrito());
        itemDTO.setCantidad(carritoItem.getCantidad());
        itemDTO.setPrecioTotal(carritoItem.getPrecioTotal());

        // Devolver datos del cliente
        UsuarioClienteDTO clienteDTO = new UsuarioClienteDTO();
        clienteDTO.setId_usuario(cliente.getId_usuario());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setDni(cliente.getDni());
        clienteDTO.setNombre_usuario(cliente.getNombre_usuario());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setDireccion(cliente.getDireccion());
        itemDTO.setCliente(clienteDTO);

        // Devolver producto completo
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId_producto(producto.getId_producto());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setCategoria(producto.getCategoria());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(producto.getStock());
        productoDTO.setDisponible(producto.getDisponible());
        productoDTO.setImagenUrl(producto.getImagenUrl());
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

    // Actualizar cantidad de un item específico
    public CarritoItemDTO actualizarCantidadItem(Long id_carrito_item, Integer nuevaCantidad) {
        log.info("Actualizando cantidad del item: id_item={}, nueva_cantidad={}", id_carrito_item, nuevaCantidad);
        try {
            CarritoItem carritoItem = carritoItemRepository.findById(id_carrito_item).orElse(null);
            if (carritoItem == null) {
                log.error("Item del carrito no encontrado: {}", id_carrito_item);
                return null;
            }

            // Validar stock disponible
            Producto producto = carritoItem.getProducto();
            if (producto.getStock() == null || producto.getStock() < nuevaCantidad) {
                log.error("Stock insuficiente. Stock disponible: {}, Cantidad solicitada: {}",
                        producto.getStock() != null ? producto.getStock() : 0, nuevaCantidad);
                return null;
            }

            carritoItem.setCantidad(nuevaCantidad);
            // Recalcular precio total con la nueva cantidad
            carritoItem.setPrecioTotal(nuevaCantidad * producto.getPrecio());
            carritoItemRepository.save(carritoItem);

            CarritoItemDTO itemDTO = new CarritoItemDTO();
            itemDTO.setId_carrito_item(carritoItem.getId_carrito_item());
            itemDTO.setId_carrito(carritoItem.getCarrito().getId_carrito());
            itemDTO.setCantidad(carritoItem.getCantidad());
            itemDTO.setPrecioTotal(carritoItem.getPrecioTotal());

            // Devolver datos del cliente
            UsuarioCliente cliente = carritoItem.getCarrito().getCliente();
            UsuarioClienteDTO clienteDTO = new UsuarioClienteDTO();
            clienteDTO.setId_usuario(cliente.getId_usuario());
            clienteDTO.setNombre(cliente.getNombre());
            clienteDTO.setApellido(cliente.getApellido());
            clienteDTO.setEmail(cliente.getEmail());
            clienteDTO.setDni(cliente.getDni());
            clienteDTO.setNombre_usuario(cliente.getNombre_usuario());
            clienteDTO.setTelefono(cliente.getTelefono());
            clienteDTO.setDireccion(cliente.getDireccion());
            itemDTO.setCliente(clienteDTO);

            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setId_producto(producto.getId_producto());
            productoDTO.setNombre(producto.getNombre());
            productoDTO.setDescripcion(producto.getDescripcion());
            productoDTO.setCategoria(producto.getCategoria());
            productoDTO.setPrecio(producto.getPrecio());
            productoDTO.setStock(producto.getStock());
            productoDTO.setDisponible(producto.getDisponible());
            productoDTO.setImagenUrl(producto.getImagenUrl());
            itemDTO.setProducto(productoDTO);

            return itemDTO;
        } catch (Exception e) {
            log.error("Error al actualizar cantidad del item: {}", e.getMessage());
            return null;
        }
    }

    // Eliminar un item específico del carrito
    public void eliminarItemDelCarrito(Long id_carrito_item) {
        log.info("Eliminando item del carrito: id_item={}", id_carrito_item);
        try {
            CarritoItem carritoItem = carritoItemRepository.findById(id_carrito_item).orElse(null);
            if (carritoItem != null) {
                carritoItemRepository.delete(carritoItem);
                log.info("Item eliminado correctamente");
            } else {
                log.warn("Item del carrito no encontrado: {}", id_carrito_item);
            }
        } catch (Exception e) {
            log.error("Error al eliminar item: {}", e.getMessage());
        }
    }

    // Calcular el total del carrito (suma de precios × cantidades)
    public Double calcularTotalCarrito(Long id_usuario) {
        log.info("Calculando total del carrito para cliente: {}", id_usuario);
        try {
            Carrito carrito = carritoRepository.findByClienteId_usuario(id_usuario);
            if (carrito == null) {
                log.warn("Carrito no encontrado para cliente: {}", id_usuario);
                return 0.0;
            }

            java.util.List<CarritoItem> items = carritoItemRepository.findByCarrito(carrito);
            Double total = items.stream()
                    .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                    .sum();

            log.info("Total del carrito calculado: {}", total);
            return total;
        } catch (Exception e) {
            log.error("Error al calcular total: {}", e.getMessage());
            return 0.0;
        }
    }

    // Vaciar carrito (eliminar todos los items sin eliminar el carrito)
    public void vaciarCarrito(Long id_usuario) {
        log.info("Vaciando carrito para cliente: {}", id_usuario);
        try {
            Carrito carrito = carritoRepository.findByClienteId_usuario(id_usuario);
            if (carrito != null) {
                java.util.List<CarritoItem> items = carritoItemRepository.findByCarrito(carrito);
                carritoItemRepository.deleteAll(items);
                log.info("Carrito vaciado correctamente para cliente: {}", id_usuario);
            } else {
                log.warn("Carrito no encontrado para cliente: {}", id_usuario);
            }
        } catch (Exception e) {
            log.error("Error al vaciar carrito: {}", e.getMessage());
        }
    }

    // Obtener total de items en el carrito
    public Integer obtenerTotalItems(Long id_usuario) {
        log.info("Obteniendo total de items del carrito para cliente: {}", id_usuario);
        try {
            Carrito carrito = carritoRepository.findByClienteId_usuario(id_usuario);
            if (carrito == null) {
                log.warn("Carrito no encontrado para cliente: {}", id_usuario);
                return 0;
            }

            java.util.List<CarritoItem> items = carritoItemRepository.findByCarrito(carrito);
            Integer totalItems = items.stream()
                    .mapToInt(CarritoItem::getCantidad)
                    .sum();

            log.info("Total de items en carrito: {}", totalItems);
            return totalItems;
        } catch (Exception e) {
            log.error("Error al obtener total de items: {}", e.getMessage());
            return 0;
        }
    }
}