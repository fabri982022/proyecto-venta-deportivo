import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Script para cargar datos de prueba en la API
 * Crea:
 * - 100 usuarios (3 admins, 48 clientes, 49 vendedores)
 * - 50 productos distribuidos entre vendedores
 * 
 * Ejecutar: java LoadTestData
 */
public class LoadTestData {
    private static final String BASE_URL = "http://localhost:8080/api/v1";
    private static final String[] NOMBRES = {
            "Juan", "María", "Carlos", "Ana", "Roberto", "Sofia", "Miguel", "Elena",
            "Diego", "Laura", "Pedro", "Carmen", "Luis", "Isabel", "Fernando", "Rosa",
            "Manuel", "Patricia", "Javier", "Francisca", "Antonio", "Catalina", "Sergio", "Mariana"
    };
    private static final String[] APELLIDOS = {
            "García", "Rodríguez", "Martínez", "López", "Pérez", "González", "Sánchez", "Torres",
            "Ramirez", "Flores", "Moreno", "Medina", "Gutierrez", "Ortiz", "Jimenez", "Vargas",
            "Castro", "Rivas", "Vega", "Silva", "Rojas", "Salazar", "Mendez", "Cortez"
    };
    private static final String[] CATEGORIAS = {
            "Deportes", "Calzado", "Accesorios", "Ropa", "Equipamiento",
            "Bicicletas", "Natación", "Fútbol", "Tenis", "Yoga"
    };
    private static final String[] MARCAS = {
            "Nike", "Adidas", "Puma", "Reebok", "Saucony", "New Balance",
            "Mizuno", "Asics", "Under Armour", "Columbia"
    };

    public static void main(String[] args) throws Exception {
        System.out.println("Starting test data loading...\n");

        // Cargar usuarios
        System.out.println("Creating users...");
        int[] usuarioIds = cargarUsuarios();

        System.out.println("\nUsers created: " + usuarioIds.length);

        // Esperar a que los servicios estén listos
        Thread.sleep(2000);

        // Cargar productos
        System.out.println("\nCreating products...");
        cargarProductos(usuarioIds);

        System.out.println("\nTest data loaded successfully!");
        System.out.println("\nTest users:");
        System.out.println("  - ADMIN: usuario#1, usuario#2, usuario#3");
        System.out.println("  - CLIENTS: usuario#4 to usuario#51");
        System.out.println("  - VENDORS: usuario#52 to usuario#100");
        System.out.println("\nAccess at: http://localhost:3000");
    }

    private static int[] cargarUsuarios() throws Exception {
        int[] ids = new int[100];
        Random rand = new Random();

        for (int i = 1; i <= 100; i++) {
            String nombre = NOMBRES[rand.nextInt(NOMBRES.length)];
            String apellido = APELLIDOS[rand.nextInt(APELLIDOS.length)];
            String rol;

            // Primeros 3 son admins
            if (i <= 3) {
                rol = "ADMIN";
            } else if (i <= 51) {
                rol = "CLIENTE";
            } else {
                rol = "VENDEDOR";
            }

            String usuario = String.format("usuario%d", i);
            String json = String.format(
                    "{\"nombre\":\"%s\",\"apellido\":\"%s\",\"nombre_usuario\":\"%s\",\"contrasena\":\"pass123\",\"email\":\"%s@test.com\",\"dni\":\"%08d\",\"telefono\":\"555000%d\",\"rol\":\"%s\",\"direccion\":\"Calle Test %d\"}",
                    nombre, apellido, usuario, usuario, i * 1000, i, rol, i);

            try {
                int statusCode = postRequest("/usuarios/" + rol.toLowerCase(), json);
                if (statusCode == 201 || statusCode == 200) {
                    ids[i - 1] = i;
                    System.out.print(".");
                } else {
                    System.out.print("x");
                }
            } catch (Exception e) {
                System.out.print("!");
            }

            if (i % 10 == 0) {
                System.out.print(" " + i + "\n");
                Thread.sleep(500);
            }
        }

        System.out.println();
        return ids;
    }

    private static void cargarProductos(int[] usuarioIds) throws Exception {
        Random rand = new Random();
        int productoId = 1;

        // Crear 50 productos
        for (int i = 0; i < 50; i++) {
            // Seleccionar un vendedor aleatorio (ids 52-100)
            int vendedorId = 52 + rand.nextInt(49);

            String categoria = CATEGORIAS[rand.nextInt(CATEGORIAS.length)];
            String marca = MARCAS[rand.nextInt(MARCAS.length)];
            int stock = 5 + rand.nextInt(95);
            double precio = 29.99 + (rand.nextDouble() * 370.01);

            String json = String.format(
                    "{\"nombre\":\"%s %s %d\",\"descripcion\":\"Producto de calidad premium - %s\",\"categoria\":\"%s\",\"precio\":%.2f,\"stock\":%d,\"disponible\":true}",
                    marca, categoria, productoId, categoria, categoria, precio, stock);

            try {
                int statusCode = postRequest("/productos", json);
                if (statusCode == 201 || statusCode == 200) {
                    System.out.print(".");
                } else {
                    System.out.print("x");
                }
            } catch (Exception e) {
                System.out.print("!");
            }

            productoId++;
            if ((i + 1) % 10 == 0) {
                System.out.print(" " + (i + 1) + "\n");
                Thread.sleep(300);
            }
        }

        System.out.println();
    }

    private static int postRequest(String endpoint, String jsonData) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        conn.getOutputStream().write(jsonData.getBytes());

        int statusCode = conn.getResponseCode();
        conn.disconnect();

        return statusCode;
    }
}
