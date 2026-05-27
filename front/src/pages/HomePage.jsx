import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { productosAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';
import { useCart } from '../context/CartContext';

export function HomePage() {
  const { isAuthenticated, user } = useAuth();
  const { totalCantidad } = useCart();
  const [featuredProducts, setFeaturedProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let isMounted = true;

    const loadHomeData = async () => {
      try {
        const productsResponse = await productosAPI.obtenerTodos();
        const products = productsResponse?.data || [];

        if (isMounted) {
          setFeaturedProducts(Array.isArray(products) ? products.slice(0, 3) : []);
        }
      } finally {
        if (isMounted) {
          setLoading(false);
        }
      }
    };

    loadHomeData();

    return () => {
      isMounted = false;
    };
  }, []);

  return (
    <div className="page page-home">
      <section className="hero container">
        <div className="hero-copy">
          <span className="eyebrow">Tienda deportiva conectada al backend</span>
          <h1>Compra, administra y sigue tu carrito desde una sola interfaz.</h1>
          <p>
            SportShop combina un catálogo real, registro de clientes y carrito persistente con una
            interfaz responsive basada en React y Spring Boot.
          </p>

          <div className="hero-actions">
            <Link to="/productos" className="btn-primary btn-lg">Explorar Productos</Link>
          </div>
        </div>
      </section>

      <section className="container section-block">
        <div className="section-title">
          <span>Catálogo</span>
          <h2>Productos destacados</h2>
          <p>Una vista rápida de los artículos que ya expone tu API.</p>
        </div>

        <div className="featured-strip">
          {featuredProducts.length > 0 ? featuredProducts.map((producto) => (
            <article key={producto.id_producto} className="featured-card card">
              <span>{producto.categoria || 'General'}</span>
              <h3>{producto.nombre}</h3>
              <p>{producto.descripcion || 'Sin descripción'}</p>
              <strong>${Number(producto.precio || 0).toFixed(2)}</strong>
            </article>
          )) : (
            <div className="empty-state card">
              <h3>No hay productos cargados todavía</h3>
              <p>Usa la página de productos para revisar el catálogo cuando la API tenga registros.</p>
            </div>
          )}
        </div>
      </section>
    </div>
  );
}