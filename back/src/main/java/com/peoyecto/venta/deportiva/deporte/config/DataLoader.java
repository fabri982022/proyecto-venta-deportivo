package com.peoyecto.venta.deportiva.deporte.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioAdminDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioClienteDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.UsuarioVendedorDTO;
import com.peoyecto.venta.deportiva.deporte.DTO.ProductoDTO;
import com.peoyecto.venta.deportiva.deporte.services.UsuarioService;
import com.peoyecto.venta.deportiva.deporte.services.ProductoService;
import com.peoyecto.venta.deportiva.deporte.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final UsuarioRepository usuarioRepository;

    public DataLoader(UsuarioService usuarioService, ProductoService productoService,
            UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Iniciando carga de datos de prueba...");

        // Verificar si ya existen datos cargados
        if (usuarioRepository.count() > 0) {
            log.info("Datos ya existen en la base de datos. Omitiendo carga inicial.");
            return;
        }

        try {
            // Cargar usuarios
            loadUsers();

            // Cargar productos
            loadProducts();

            log.info("✅ Datos de prueba cargados exitosamente!");
        } catch (Exception e) {
            log.error("❌ Error al cargar datos de prueba: " + e.getMessage(), e);
        }
    }

    private void loadUsers() {
        log.info("Cargando 100 usuarios...");

        String[] nombres = {
                "Juan", "María", "Carlos", "Ana", "Roberto", "Sofia", "Miguel", "Elena",
                "Diego", "Laura", "Pedro", "Carmen", "Luis", "Isabel", "Fernando", "Rosa",
                "Manuel", "Patricia", "Javier", "Francisca", "Antonio", "Catalina", "Sergio", "Mariana",
                "Ricardo", "Beatriz", "Álvaro", "Lidia", "Gonzalo", "Montserrat"
        };

        String[] apellidos = {
                "García", "Rodríguez", "Martínez", "López", "Pérez", "González", "Sánchez", "Torres",
                "Ramirez", "Flores", "Moreno", "Medina", "Gutierrez", "Ortiz", "Jimenez", "Vargas",
                "Castro", "Rivas", "Vega", "Silva", "Rojas", "Salazar", "Mendez", "Cortez",
                "Rivera", "Herrera", "Navarro", "Dominguez", "Fuentes", "Campos"
        };

        String[] dominios = { "gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "correo.com" };

        // 33 Administradores
        for (int i = 1; i <= 33; i++) {
            try {
                UsuarioAdminDTO adminDTO = new UsuarioAdminDTO();
                adminDTO.setNombre(nombres[(i - 1) % nombres.length]);
                adminDTO.setApellido(apellidos[(i - 1) % apellidos.length]);
                adminDTO.setDni(String.format("%08d", i));
                adminDTO.setEmail("admin" + i + "@" + dominios[(i - 1) % dominios.length]);
                adminDTO.setPassword("password123");
                adminDTO.setNombre_usuario("admin_" + i);
                adminDTO.setDepartamento("Gerencia");

                usuarioService.guardarUsuarioAdmin(adminDTO);
            } catch (Exception e) {
                log.warn("Error creando admin " + i + ": " + e.getMessage());
            }
        }
        log.info("✓ 33 administradores creados");

        // 33 Vendedores
        for (int i = 34; i <= 66; i++) {
            try {
                UsuarioVendedorDTO vendedorDTO = new UsuarioVendedorDTO();
                vendedorDTO.setNombre(nombres[(i - 1) % nombres.length]);
                vendedorDTO.setApellido(apellidos[(i - 1) % apellidos.length]);
                vendedorDTO.setDni(String.format("%08d", i));
                vendedorDTO.setEmail("vendedor" + i + "@" + dominios[(i - 1) % dominios.length]);
                vendedorDTO.setPassword("password123");
                vendedorDTO.setNombre_usuario("vendor_" + i);
                vendedorDTO.setEmpresa("Empresa Deportiva " + i);
                vendedorDTO.setTelefono("+34 91 " + String.format("%06d", i));
                vendedorDTO.setDireccion("Calle Principal " + i);
                vendedorDTO.setTelefono_empresa("+34 91 " + String.format("%06d", i + 1000));
                vendedorDTO.setDireccion_empresa("Avenida Comercial " + i);
                vendedorDTO.setRuc("RUC-" + String.format("%08d", i));

                usuarioService.guardarUsuarioVendedor(vendedorDTO);
            } catch (Exception e) {
                log.warn("Error creando vendedor " + i + ": " + e.getMessage());
            }
        }
        log.info("✓ 33 vendedores creados");

        // 34 Clientes
        for (int i = 67; i <= 100; i++) {
            try {
                UsuarioClienteDTO clienteDTO = new UsuarioClienteDTO();
                clienteDTO.setNombre(nombres[(i - 1) % nombres.length]);
                clienteDTO.setApellido(apellidos[(i - 1) % apellidos.length]);
                clienteDTO.setDni(String.format("%08d", i));
                clienteDTO.setEmail("cliente" + i + "@" + dominios[(i - 1) % dominios.length]);
                clienteDTO.setPassword("password123");
                clienteDTO.setNombre_usuario("client_" + i);
                clienteDTO.setTelefono("+34 91 " + String.format("%06d", i));
                clienteDTO.setDireccion("Calle " + (i - 66) + " " + apellidos[(i - 1) % apellidos.length]);

                usuarioService.guardarUsuarioCliente(clienteDTO);
            } catch (Exception e) {
                log.warn("Error creando cliente " + i + ": " + e.getMessage());
            }
        }
        log.info("✓ 34 clientes creados");
    }

    private void loadProducts() {
        log.info("Cargando 100 productos...");

        String[] categorias = {
                "Fútbol", "Tenis", "Atletismo", "Natación", "Ciclismo",
                "Baloncesto", "Voleibol", "Bádminton", "Rugby", "Hockey",
                "Alpinismo", "Surf", "Esquí", "Patinaje", "Yoga"
        };

        String[] productos = {
                "Zapatillas", "Balón", "Raqueta", "Pesas", "Cuerda",
                "Botella", "Mochila", "Guantes", "Cinturón", "Banda elástica",
                "Tapete", "Casco", "Gafas", "Traje", "Calcetines",
                "Camiseta", "Pantalón", "Chaqueta", "Protector", "Rodillera"
        };

        int contador = 0;
        for (String categoria : categorias) {
            for (int i = 0; i < 7; i++) { // ~105 productos
                if (contador >= 100)
                    break;

                try {
                    ProductoDTO productoDTO = new ProductoDTO();
                    String producto = productos[i % productos.length];
                    productoDTO.setNombre(producto + " " + categoria + " " + (i + 1));
                    productoDTO.setDescripcion("Producto deportivo de " + categoria + ". Artículo: " + producto);
                    productoDTO.setCategoria(categoria);
                    productoDTO.setPrecio(Math.round((10 + Math.random() * 490) * 100.0) / 100.0); // Entre 10 y 500
                    productoDTO.setStock(Math.max(5, (int) (Math.random() * 100)));
                    productoDTO.setDisponible(true);

                    productoService.guardarProducto(productoDTO);
                    contador++;
                } catch (Exception e) {
                    log.warn("Error creando producto " + contador + ": " + e.getMessage());
                }
            }
            if (contador >= 100)
                break;
        }
        log.info("✓ " + contador + " productos creados");
    }
}
