package com.peoyecto.venta.deportiva.deporte.services;

import com.peoyecto.venta.deportiva.deporte.repository.ProductoRepository;
import com.peoyecto.venta.deportiva.deporte.model.Producto;
import com.peoyecto.venta.deportiva.deporte.DTO.ProductoDTO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // crear producto

    public ProductoDTO guardarProducto(ProductoDTO productoDTO) {
        // Validar que no exista un producto con el mismo nombre
        Producto productoExistente = productoRepository.findByNombre(productoDTO.getNombre());
        if (productoExistente != null) {
            log.warn("Intento de crear producto duplicado. Nombre: {}", productoDTO.getNombre());
            throw new RuntimeException("Ya existe un producto con el nombre: " + productoDTO.getNombre());
        }

        // Validar que no exista un producto con las mismas propiedades
        Producto productoDuplicado = productoRepository.findByPropiedades(
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getCategoria(),
                productoDTO.getPrecio(),
                productoDTO.getStock());
        if (productoDuplicado != null) {
            log.warn("Intento de crear producto con propiedades duplicadas. Nombre: {}", productoDTO.getNombre());
            throw new RuntimeException(
                    "Ya existe un producto con exactamente las mismas propiedades (nombre, descripción, categoría, precio y stock)");
        }

        Producto producto = new Producto();
        if (!stockDisponible(productoDTO.getStock())) {

            throw new RuntimeException("Stock debe ser mayor a cero");
        } else {
            log.info("Creando producto");
            log.info(
                    "Datos ingresados: Nombre:{}, Descripcion:{}, Categoria:{}, Precio:{}, Stock:{}, Disponible:{}, ImagenUrl:{}",
                    productoDTO.getNombre(), productoDTO.getDescripcion(), productoDTO.getCategoria(),
                    productoDTO.getPrecio(), productoDTO.getStock(), productoDTO.getDisponible(),
                    productoDTO.getImagenUrl());
            ProductoDTOaEntity(producto, productoDTO);
            Producto productoGuardado = productoRepository.save(producto);
            ProductoDTO productoDTORetorno = new ProductoDTO();
            ProductoEntityaDTO(productoDTORetorno, productoGuardado);
            log.info("Datos almacenados exitosamente!!");
            return productoDTORetorno;
        }
    }

    // obtener todos los productos

    public java.util.List<ProductoDTO> obtenerTodosLosProductos() {
        log.info("Obteniendo todos los productos");
        java.util.List<Producto> productos = productoRepository.findAll();
        java.util.List<ProductoDTO> productosDTO = new java.util.ArrayList<>();
        for (Producto producto : productos) {
            ProductoDTO productoDTO = new ProductoDTO();
            ProductoEntityaDTO(productoDTO, producto);
            productosDTO.add(productoDTO);
        }
        log.info("Total de productos encontrados: {}", productosDTO.size());
        return productosDTO;
    }

    // obtener producto por id

    public ProductoDTO obtenerProductoPorId(Long id_producto) {
        log.info("Buscando producto con ID: {}", id_producto);
        Producto producto = productoRepository.findById(id_producto).orElse(null);
        if (producto == null) {
            log.warn("Producto con ID: {} no encontrado", id_producto);
            return null;
        }
        ProductoDTO productoDTO = new ProductoDTO();
        ProductoEntityaDTO(productoDTO, producto);
        return productoDTO;
    }

    // eliminar Producto (poner disponible en false)
    public void eliminarProducto(Long id_producto) {
        log.info("Busucando producto con id: {}", id_producto);
        Producto producto = productoRepository.findById(id_producto).orElseThrow(
                () -> new RuntimeException("Producto con ID: " + id_producto + " no encontrado"));

        mostrarDatosProducto(producto);
        producto.setDisponible(false);
        productoRepository.save(producto);
        log.info("Producto con ID: {} marcado como no disponible", id_producto);
    }

    // modificar producto
    public ProductoDTO modificarProducto(Long id_producto, ProductoDTO productoDTO) {
        log.info("Modificando producto con ID: {}", id_producto);
        Producto productoExistente = productoRepository.findById(id_producto).orElseThrow(
                () -> new RuntimeException("Producto con ID: " + id_producto + " no encontrado"));
        mostrarDatosProducto(productoExistente);

        // Validar que no exista otro producto con el mismo nombre (excluyendo el
        // actual)
        if (!productoExistente.getNombre().equals(productoDTO.getNombre())) {
            Producto productoConMismoNombre = productoRepository.findByNombre(productoDTO.getNombre());
            if (productoConMismoNombre != null) {
                log.warn("Intento de modificar producto a nombre duplicado. Nombre: {}", productoDTO.getNombre());
                throw new RuntimeException("Ya existe otro producto con el nombre: " + productoDTO.getNombre());
            }
        }

        // Validar que no exista un producto con las mismas propiedades
        Producto productoDuplicado = productoRepository.findByPropiedades(
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getCategoria(),
                productoDTO.getPrecio(),
                productoDTO.getStock());
        if (productoDuplicado != null) {
            log.warn("Intento de crear producto con propiedades duplicadas. Nombre: {}", productoDTO.getNombre());
            throw new RuntimeException(
                    "Ya existe un producto con exactamente las mismas propiedades (nombre, descripción, categoría, precio y stock)");
        }

        ProductoDTOaEntity(productoExistente, productoDTO);
        if (!stockDisponible(productoDTO.getStock())) {
            throw new RuntimeException("Stock debe ser mayor a cero");
        } else {
            Producto productoActualizado = productoRepository.save(productoExistente);
            ProductoDTO productoDTORetorno = new ProductoDTO();
            ProductoEntityaDTO(productoDTORetorno, productoActualizado);
            log.info("Producto con ID: {} modificado exitosamente", id_producto);
            log.info(
                    "Datos cambiados Nombre:{}, Descripcion:{}, Categoria:{}, Precio:{}, Stock:{}, Disponible:{}, ImagenUrl:{}",
                    productoDTORetorno.getNombre(), productoDTORetorno.getDescripcion(),
                    productoDTORetorno.getCategoria(),
                    productoDTORetorno.getPrecio(), productoDTORetorno.getStock(), productoDTORetorno.getDisponible(),
                    productoDTORetorno.getImagenUrl());
            return productoDTORetorno;
        }
    }

    private void mostrarDatosProducto(Producto producto) {
        log.info(
                "Datos del Producto - ID: {}, Nombre: {}, Descripcion: {}, Categoria: {}, Precio: {}, Stock: {}, Disponible: {}, ImagenUrl: {}",
                producto.getId_producto(), producto.getNombre(), producto.getDescripcion(),
                producto.getCategoria(), producto.getPrecio(), producto.getStock(),
                producto.getDisponible(), producto.getImagenUrl());
    }

    private void ProductoEntityaDTO(ProductoDTO productoDTO, Producto producto) {
        productoDTO.setId_producto(producto.getId_producto());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(producto.getStock());
        productoDTO.setCategoria(producto.getCategoria());
        productoDTO.setDisponible(producto.getDisponible());
        productoDTO.setImagenUrl(producto.getImagenUrl());

    }

    private void ProductoDTOaEntity(Producto producto, ProductoDTO productoDTO) {
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setDisponible(productoDTO.getDisponible());
        producto.setImagenUrl(productoDTO.getImagenUrl());
    }

    private boolean stockDisponible(Integer stock) {
        return stock != null && stock > 0;
    }

}
