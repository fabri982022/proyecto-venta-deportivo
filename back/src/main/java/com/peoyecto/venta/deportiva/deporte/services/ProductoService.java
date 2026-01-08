package com.peoyecto.venta.deportiva.deporte.services;

import org.springframework.beans.factory.annotation.Autowired;
import com.peoyecto.venta.deportiva.deporte.repository.ProductoRepository;
import com.peoyecto.venta.deportiva.deporte.model.Producto;
import com.peoyecto.venta.deportiva.deporte.DTO.ProductoDTO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j

public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // crear producto

    public ProductoDTO guardarProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
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
        ProductoDTOaEntity(productoExistente, productoDTO);
        Producto productoActualizado = productoRepository.save(productoExistente);
        ProductoDTO productoDTORetorno = new ProductoDTO();
        ProductoEntityaDTO(productoDTORetorno, productoActualizado);
        log.info("Producto con ID: {} modificado exitosamente", id_producto);
        return productoDTORetorno;
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

}
