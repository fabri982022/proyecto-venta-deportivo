// Importar el cliente mejorado de API
import apiCallWithRetry from '../utils/apiClient';

// Usar el cliente mejorado como función principal
export const apiCall = apiCallWithRetry;

export const healthAPI = {
  obtenerEstado: () => apiCall('/health', { method: 'GET' }),
};

// ============= USUARIOS =============
export const usuariosAPI = {
  crearCliente: (datos) => apiCall('/usuarios/cliente', {
    method: 'POST',
    body: JSON.stringify(datos),
  }),
  
  obtenerTodos: () => apiCall('/usuarios', { method: 'GET' }),
  
  obtenerPorId: (id) => apiCall(`/usuarios/${id}`, { method: 'GET' }),
  
  modificarCliente: (id, datos) => apiCall(`/usuarios/cliente/${id}`, {
    method: 'PUT',
    body: JSON.stringify(datos),
  }),
  
  eliminar: (id) => apiCall(`/usuarios/${id}`, { method: 'DELETE' }),
};

// ============= PRODUCTOS =============
export const productosAPI = {
  crear: (datos) => apiCall('/productos', {
    method: 'POST',
    body: JSON.stringify(datos),
  }),
  
  obtenerTodos: () => apiCall('/productos', { method: 'GET' }),
  
  obtenerPorId: (id) => apiCall(`/productos/${id}`, { method: 'GET' }),
  
  modificar: (id, datos) => apiCall(`/productos/${id}`, {
    method: 'PUT',
    body: JSON.stringify(datos),
  }),
  
  eliminar: (id) => apiCall(`/productos/${id}`, { method: 'DELETE' }),
};

// ============= CARRITO =============
export const carritoAPI = {
  obtenerCarrito: (idUsuario) => apiCall(`/carrito/cliente/${idUsuario}`, { method: 'GET' }),
  
  existeCarrito: (idUsuario) => apiCall(`/carrito/cliente/${idUsuario}/existe`, { method: 'GET' }),
  
  obtenerTotalItems: (idUsuario) => apiCall(`/carrito/cliente/${idUsuario}/total-items`, { method: 'GET' }),
  
  calcularTotal: (idUsuario) => apiCall(`/carrito/cliente/${idUsuario}/total`, { method: 'GET' }),
  
  agregarItem: (idUsuario, idProducto, cantidad) => 
    apiCall(`/carrito/cliente/${idUsuario}/item?id_producto=${idProducto}&cantidad=${cantidad}`, {
      method: 'POST',
    }),
  
  actualizarCantidad: (idCarritoItem, cantidad) => 
    apiCall(`/carrito/item/${idCarritoItem}?cantidad=${cantidad}`, {
      method: 'PUT',
    }),
  
  eliminarItem: (idCarritoItem) => apiCall(`/carrito/item/${idCarritoItem}`, { method: 'DELETE' }),
  
  vaciarCarrito: (idUsuario) => apiCall(`/carrito/cliente/${idUsuario}/vaciar`, { method: 'DELETE' }),
  
  eliminarCarrito: (idUsuario) => apiCall(`/carrito/cliente/${idUsuario}`, { method: 'DELETE' }),
};
