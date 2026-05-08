package com.peoyecto.venta.deportiva.deporte;

import com.peoyecto.venta.deportiva.deporte.DTO.ProductoDTO;
import com.peoyecto.venta.deportiva.deporte.services.ProductoService;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class TestProductoService {

    @Autowired
    ProductoService productoService;

    static ProductoDTO productoDTO;
    static ProductoDTO productoDTOsinStock;

    @BeforeAll
    static void init() {
        log.info("Iniciando tests de ProductoService");
    }

    @BeforeEach
    public void setUp() throws Exception {
        log.info("Iniciando testeos de ProductoService");
        productoDTO = new ProductoDTO();
        productoDTO.setNombre("Producto Test");
        productoDTO.setDescripcion("Descripcion Test");
        productoDTO.setCategoria("Categoria Test");
        productoDTO.setPrecio(100.0);
        productoDTO.setStock(10);
        productoDTO.setDisponible(true);
        productoDTO.setImagenUrl("http://imagen.test/producto.jpg");
        productoDTOsinStock = new ProductoDTO();
        productoDTOsinStock.setNombre("Producto Sin Stock");
        productoDTOsinStock.setDescripcion("Descripcion Sin Stock");
        productoDTOsinStock.setCategoria("Categoria Sin Stock");
        productoDTOsinStock.setPrecio(50.0);
        productoDTOsinStock.setStock(0);
        productoDTOsinStock.setDisponible(true);
        productoDTOsinStock.setImagenUrl("http://imagen.test/producto_sin_stock.jpg");

    }

    @Test
    public void testGuardarYObtenerProducto() {
        log.info("Iniciando testGuardarYObtenerProducto");

        // Guardar producto
        ProductoDTO productoGuardado = productoService.guardarProducto(productoDTO);
        assertNotNull(productoGuardado);
        assertNotNull(productoGuardado.getId_producto());
        assertEquals("Producto Test", productoGuardado.getNombre());

        // Obtener producto por ID
        ProductoDTO productoObtenido = productoService.obtenerProductoPorId(productoGuardado.getId_producto());
        assertNotNull(productoObtenido);
        assertEquals(productoGuardado.getId_producto(), productoObtenido.getId_producto());
        assertEquals("Producto Test", productoObtenido.getNombre());

        log.info("testGuardarYObtenerProducto finalizado exitosamente");
    }

    @Test
    public void testEliminarProducto() {
        log.info("Iniciando testEliminarProducto");

        // Guardar producto
        ProductoDTO productoGuardado = productoService.guardarProducto(productoDTO);
        Long idProducto = productoGuardado.getId_producto();
        assertNotNull(idProducto);

        // Eliminar producto
        productoService.eliminarProducto(idProducto);

        // Verificar que el producto esté marcado como no disponible
        ProductoDTO productoEliminado = productoService.obtenerProductoPorId(idProducto);
        assertNotNull(productoEliminado);
        assertFalse(productoEliminado.getDisponible());

        log.info("testEliminarProducto finalizado exitosamente");
    }

    @Test
    public void testModificarProducto() {
        log.info("Iniciando testModificarProducto");

        // Guardar producto
        ProductoDTO productoGuardado = productoService.guardarProducto(productoDTO);
        Long idProducto = productoGuardado.getId_producto();
        assertNotNull(idProducto);

        // Modificar datos del producto
        ProductoDTO productoModificadoDTO = new ProductoDTO();
        productoModificadoDTO.setNombre("Producto Modificado");
        productoModificadoDTO.setDescripcion("Descripcion Modificada");
        productoModificadoDTO.setCategoria("Categoria Modificada");
        productoModificadoDTO.setPrecio(150.0);
        productoModificadoDTO.setStock(5);
        productoModificadoDTO.setDisponible(true);
        productoModificadoDTO.setImagenUrl("http://imagen.test/producto_modificado.jpg");

        // Modificar producto
        ProductoDTO productoModificado = productoService.modificarProducto(idProducto, productoModificadoDTO);
        assertNotNull(productoModificado);
        assertEquals("Producto Modificado", productoModificado.getNombre());
        assertEquals(150.0, productoModificado.getPrecio());

        log.info("testModificarProducto finalizado exitosamente");
    }

    @Test
    void ingresarStockInsuficiente() {
        log.info("Iniciando testIngresarStockInsuficiente");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productoService.guardarProducto(productoDTOsinStock);
        });
        String expectedMessage = "Stock debe ser mayor a cero";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        log.info("testIngresarStockInsuficiente finalizado exitosamente");
    }

    @Test
    void modificarStockInsuficiente() {
        log.info("Iniciando testModificarStockInsuficiente");

        // Guardar producto
        ProductoDTO productoGuardado = productoService.guardarProducto(productoDTO);
        Long idProducto = productoGuardado.getId_producto();
        assertNotNull(idProducto);

        // Intentar modificar el producto con stock insuficiente
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productoService.modificarProducto(idProducto, productoDTOsinStock);
        });
        String expectedMessage = "Stock debe ser mayor a cero";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        log.info("testModificarStockInsuficiente finalizado exitosamente");
    }
}
