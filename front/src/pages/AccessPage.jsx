import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { apiCall } from '../services/api';
import { useAuth } from '../context/AuthContext';

export function AccessPage() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [form, setForm] = useState({
    nombre_usuario: '',
    password: '',
  });
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!form.nombre_usuario.trim()) {
      setMessage('Ingresa tu nombre de usuario.');
      return;
    }

    if (!form.password.trim()) {
      setMessage('Ingresa tu contraseña.');
      return;
    }

    try {
      setLoading(true);
      setMessage('');

      // Llamar al endpoint de login
      const response = await apiCall('/usuarios/login', {
        method: 'POST',
        body: JSON.stringify({
          nombre_usuario: form.nombre_usuario.trim(),
          password: form.password.trim(),
        }),
      });

      if (response?.data) {
        login(response.data);
        navigate('/productos');
      } else {
        setMessage('Usuario o contraseña incorrectos');
      }
    } catch (requestError) {
      setMessage(requestError?.message || 'No se pudo validar el usuario');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page container page-auth">
      <div className="auth-grid">
        <section className="auth-copy card">
          <span className="eyebrow">Acceso local</span>
          <h1>Inicia sesión en tu cuenta</h1>
          <p>
            Ingresa tu nombre de usuario y contraseña. ¿No tienes cuenta? <Link to="/registro">Regístrate aquí</Link>.
          </p>
          <div className="hero-actions">
            <Link to="/productos" className="btn-ghost">Ver productos</Link>
          </div>
        </section>

        <form className="auth-form card" onSubmit={handleSubmit}>
          <h2>Iniciar sesión</h2>
          
          <label>
            Nombre de usuario
            <input
              type="text"
              name="nombre_usuario"
              value={form.nombre_usuario}
              onChange={handleChange}
              placeholder="Tu nombre de usuario"
              autoComplete="username"
            />
          </label>

          <label>
            Contraseña
            <input
              type="password"
              name="password"
              value={form.password}
              onChange={handleChange}
              placeholder="Tu contraseña"
              autoComplete="current-password"
            />
          </label>

          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Iniciando sesión...' : 'Entrar'}
          </button>

          {message ? <p className="form-message">{message}</p> : null}
        </form>
      </div>
    </div>
  );
}