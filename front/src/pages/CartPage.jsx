import React, { useMemo } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useCart } from '../context/CartContext';

const getProducto = (item) => item?.producto || {};

export function CartPage() {
  const navigate = useNavigate();
  const { isAuthenticated, user } = useAuth();
  const { items, totalCantidad, totalPrecio, loading, error, refreshCart, actualizarCantidad, eliminarItem, vaciarCarrito } = useCart();

  const totalFormateado = useMemo(() => Number(totalPrecio || 0).toFixed(2), [totalPrecio]);

  if (!isAuthenticated || user?.rol !== 'CLIENTE') {
    return (
      <div className="page container page-cart">
        <div className="empty-state card">
          <h1>Carrito</h1>
          <p>Para usar el carrito necesitas iniciar sesión como cliente registrado.</p>
          <div className="hero-actions">
            <Link to="/acceso" className="btn-primary">Ir a acceso</Link>
            <Link to="/registro" className="btn-ghost">Registrarse</Link>
          </div>
        </div>
      </div>
    );
  }

  const handleUpdate = async (itemId, currentQuantity, delta) => {
    const nextQuantity = currentQuantity + delta;
    if (nextQuantity < 1) {
      return;
    }

    await actualizarCantidad(itemId, nextQuantity);
  };

  return (
    <div className="page container page-cart">
      <div className="section-title">
        <span>Compra</span>
        <h1>Tu carrito</h1>
        <p>Administra cantidades, elimina productos y vacía el carrito cuando lo necesites.</p>
      </div>

      {loading ? <div className="state-message card">Actualizando carrito...</div> : null}
      {error ? <div className="state-message card error">{error}</div> : null}

      <div className="cart-layout">
        <section className="cart-items">
          {items.length === 0 && !loading ? (
            <div className="empty-state card">
              <h3>Tu carrito está vacío</h3>
              <p>Explora el catálogo y agrega productos para verlos aquí.</p>
              <button type="button" className="btn-primary" onClick={() => navigate('/productos')}>
                Ir a productos
              </button>
            </div>
          ) : null}

          {items.map((item) => {
            const producto = getProducto(item);
            const itemPrice = Number(item?.precioTotal ?? producto?.precio ?? 0);

            return (
              <article key={item.id_carrito_item} className="cart-item card">
                <div>
                  <span className="cart-item-category">{producto.categoria || 'Producto'}</span>
                  <h3>{producto.nombre || 'Producto sin nombre'}</h3>
                  <p>{producto.descripcion || 'Sin descripción disponible'}</p>
                </div>

                <div className="cart-item-controls">
                  <div className="quantity-control">
                    <button type="button" onClick={() => handleUpdate(item.id_carrito_item, item.cantidad, -1)}>-</button>
                    <strong>{item.cantidad}</strong>
                    <button type="button" onClick={() => handleUpdate(item.id_carrito_item, item.cantidad, 1)}>+</button>
                  </div>

                  <strong>${itemPrice.toFixed(2)}</strong>

                  <button type="button" className="btn-ghost btn-small" onClick={() => eliminarItem(item.id_carrito_item)}>
                    Eliminar
                  </button>
                </div>
              </article>
            );
          })}
        </section>

        <aside className="cart-summary card">
          <h2>Resumen</h2>
          <div className="summary-row">
            <span>Artículos</span>
            <strong>{totalCantidad}</strong>
          </div>
          <div className="summary-row">
            <span>Total</span>
            <strong>${totalFormateado}</strong>
          </div>

          <div className="summary-actions">
            <button type="button" className="btn-primary" onClick={refreshCart}>Actualizar carrito</button>
            <button type="button" className="btn-ghost" onClick={vaciarCarrito}>Vaciar carrito</button>
          </div>
        </aside>
      </div>
    </div>
  );
}