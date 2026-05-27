import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/footer.css';

export function Footer() {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="site-footer">
      <div className="container footer-grid">
        <section>
          <h3>SportShop</h3>
          <p>Una tienda deportiva con navegación simple, compra rápida y base lista para crecer.</p>
        </section>

        <section>
          <h4>Ruta rápida</h4>
          <div className="footer-links">
            <Link to="/">Inicio</Link>
            <Link to="/productos">Productos</Link>
            <Link to="/carrito">Carrito</Link>
            <Link to="/registro">Registro</Link>
          </div>
        </section>
      </div>

      <div className="container footer-bottom">
        <p>&copy; {currentYear} SportShop. Todos los derechos reservados.</p>
      </div>
    </footer>
  );
}