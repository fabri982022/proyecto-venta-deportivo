import React, { createContext, useEffect, useMemo, useState } from 'react';
import { carritoAPI } from '../services/api';
import { useAuth } from './AuthContext';

export const CartContext = createContext(null);

const extractErrorMessage = (error) => error?.message || 'No fue posible completar la operación';

const getUserId = (user) => user?.id_usuario ?? user?.id ?? null;

export function CartProvider({ children }) {
  const { user } = useAuth();
  const userId = getUserId(user);
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const refreshCart = async () => {
    if (!userId) {
      setCart(null);
      setError('');
      return null;
    }

    setLoading(true);
    setError('');

    try {
      const response = await carritoAPI.obtenerCarrito(userId);
      setCart(response?.data ?? null);
      return response?.data ?? null;
    } catch (requestError) {
      const message = extractErrorMessage(requestError);
      // Si no existe carrito (404), es normal - simplemente mostrar carrito vacío
      if (!/no encontrado|not found|404|no se encontró/i.test(message)) {
        setError(message);
      }
      // Carrito vacío por defecto
      setCart(null);
      return null;
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    refreshCart();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [userId]);

  const agregarAlCarrito = async (producto, cantidad = 1) => {
    if (!userId) {
      throw new Error('Debes iniciar sesión para agregar productos al carrito');
    }

    const idProducto = producto?.id_producto ?? producto?.id;
    await carritoAPI.agregarItem(userId, idProducto, cantidad);
    await refreshCart();
  };

  const actualizarCantidad = async (idCarritoItem, cantidad) => {
    await carritoAPI.actualizarCantidad(idCarritoItem, cantidad);
    await refreshCart();
  };

  const eliminarItem = async (idCarritoItem) => {
    await carritoAPI.eliminarItem(idCarritoItem);
    await refreshCart();
  };

  const vaciarCarrito = async () => {
    if (!userId) {
      return;
    }

    await carritoAPI.vaciarCarrito(userId);
    await refreshCart();
  };

  const eliminarCarrito = async () => {
    if (!userId) {
      return;
    }

    await carritoAPI.eliminarCarrito(userId);
    setCart(null);
  };

  const items = cart?.items ?? [];
  const totalCantidad = cart?.cantidad ?? items.reduce((sum, item) => sum + (item.cantidad ?? 0), 0);
  const totalPrecio = cart?.precio_total ?? 0;

  const value = useMemo(() => ({
    cart,
    items,
    totalCantidad,
    totalPrecio,
    loading,
    error,
    refreshCart,
    agregarAlCarrito,
    actualizarCantidad,
    eliminarItem,
    vaciarCarrito,
    eliminarCarrito,
  }), [cart, items, totalCantidad, totalPrecio, loading, error, refreshCart, agregarAlCarrito, actualizarCantidad, eliminarItem, vaciarCarrito, eliminarCarrito]);

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}

export const useCart = () => React.useContext(CartContext);