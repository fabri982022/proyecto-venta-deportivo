import React, { useState } from 'react';
import { Link, NavLink } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useCart } from '../context/CartContext';
import '../styles/header.css';

export function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const { user, isAuthenticated, logout } = useAuth();
  const { totalCantidad } = useCart();

  const handleLogout = () => {
    logout();
    setIsMenuOpen(false);
  };

  const handleLinkClick = () => {
    setIsMenuOpen(false);
  };

  return (
    <header className="site-header">
      <div className="container header-inner">
        <Link to="/" className="brand" onClick={handleLinkClick}>
          <span className="brand-mark">S</span>
          <span className="brand-text">
            <strong>SportShop</strong>
            <small>Venta deportiva</small>
          </span>
        </Link>

        <button
          type="button"
          className="menu-toggle"
          onClick={() => setIsMenuOpen((current) => !current)}
          aria-label="Abrir menú"
          aria-expanded={isMenuOpen}
        >
          <span />
          <span />
          <span />
        </button>

        <nav className={`site-nav ${isMenuOpen ? 'is-open' : ''}`}>
          <NavLink to="/" onClick={handleLinkClick}>Inicio</NavLink>
          <NavLink to="/productos" onClick={handleLinkClick}>Productos</NavLink>
          
          {/* Carrito solo para clientes autenticados */}
          {isAuthenticated && user?.rol === 'CLIENTE' && (
            <NavLink to="/carrito" onClick={handleLinkClick}>Carrito ({totalCantidad})</NavLink>
          )}

          {!isAuthenticated && (
            <>
              <NavLink to="/acceso" onClick={handleLinkClick}>Acceso</NavLink>
              <NavLink to="/registro" onClick={handleLinkClick}>Registro</NavLink>
            </>
          )}

          {isAuthenticated ? (
            <div className="header-user-actions">
              <span className="header-user-pill">
                {user?.nombre || user?.nombre_usuario || 'Usuario'}
              </span>
              <button type="button" className="btn-ghost btn-small" onClick={handleLogout}>
                Salir
              </button>
            </div>
          ) : null}
        </nav>
      </div>
    </header>
  );
}