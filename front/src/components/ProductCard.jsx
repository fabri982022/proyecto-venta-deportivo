import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useCart } from '../context/CartContext';
import '../styles/product-card.css';

export function ProductCard({ producto }) {
  const navigate = useNavigate();
  const { isAuthenticated, user } = useAuth();
  const { agregarAlCarrito } = useCart();
  const [cantidad, setCantidad] = useState(1);
  const [saving, setSaving] = useState(false);
  const [feedback, setFeedback] = useState('');

  const stockDisponible = producto?.stock ?? 0;
  const agotado = !producto?.disponible || stockDisponible <= 0;

  const handleAgregar = async () => {
    if (!isAuthenticated) {
      navigate('/acceso');
      return;
    }

    if (user?.rol !== 'CLIENTE') {
      setFeedback('Solo los clientes pueden realizar compras');
      return;
    }

    try {
      setSaving(true);
      setFeedback('');
      await agregarAlCarrito(producto, cantidad);
      setFeedback('Agregado al carrito');
      setCantidad(1);
    } catch (error) {
      setFeedback(error?.message || 'No se pudo agregar el producto');
    } finally {
      setSaving(false);
    }
  };

  return (
    <article className="product-card card">
      <div className="product-image-wrap">
        <img
          src={producto?.imagenUrl || 'https://via.placeholder.com/600x420?text=SportShop'}
          alt={producto?.nombre || 'Producto'}
          onError={(event) => {
            event.currentTarget.src = 'https://via.placeholder.com/600x420?text=SportShop';
          }}
        />
        <span className={`stock-badge ${agotado ? 'danger' : ''}`}>
          {agotado ? 'Sin stock' : `Stock ${stockDisponible}`}
        </span>
      </div>

      <div className="product-content">
        <div className="product-meta">
          <span>{producto?.categoria || 'Sin categoría'}</span>
          <strong>${Number(producto?.precio || 0).toFixed(2)}</strong>
        </div>

        <h3>{producto?.nombre || 'Producto sin nombre'}</h3>
        <p>{producto?.descripcion || 'Sin descripción disponible'}</p>

        <div className="product-actions">
          <label>
            Cantidad
            <select value={cantidad} onChange={(event) => setCantidad(Number(event.target.value))} disabled={agotado}>
              {Array.from({ length: Math.min(stockDisponible || 1, 10) }, (_, index) => index + 1).map((value) => (
                <option key={value} value={value}>{value}</option>
              ))}
            </select>
          </label>

          <button type="button" className="btn-primary" onClick={handleAgregar} disabled={agotado || saving}>
            {saving ? 'Agregando...' : 'Agregar al carrito'}
          </button>
        </div>

        {feedback ? <span className="product-feedback">{feedback}</span> : null}
      </div>
    </article>
  );
}
