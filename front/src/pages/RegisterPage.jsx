import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { usuariosAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

const initialForm = {
  nombre: '',
  apellido: '',
  dni: '',
  email: '',
  telefono: '',
  nombre_usuario: '',
  contrasena: '',
  direccion: '',
};

export function RegisterPage() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [form, setForm] = useState(initialForm);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setLoading(true);
      setMessage('');
      const response = await usuariosAPI.crearCliente(form);
      login(response?.data);
      setForm(initialForm);
      navigate('/productos');
    } catch (requestError) {
      setMessage(requestError?.message || 'No se pudo registrar el cliente');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page container page-auth">
      <div className="auth-grid">
        <section className="auth-copy card">
          <span className="eyebrow">Registro</span>
          <h1>Crear cliente nuevo</h1>
          <p>
            Este formulario consume el endpoint de alta de clientes y deja la sesión lista para usar el carrito.
          </p>
        </section>

        <form className="auth-form card" onSubmit={handleSubmit}>
          <h2>Datos del cliente</h2>

          <div className="form-grid">
            <label>
              Nombre
              <input name="nombre" value={form.nombre} onChange={handleChange} required />
            </label>
            <label>
              Apellido
              <input name="apellido" value={form.apellido} onChange={handleChange} required />
            </label>
            <label>
              DNI
              <input name="dni" value={form.dni} onChange={handleChange} required />
            </label>
            <label>
              Email
              <input type="email" name="email" value={form.email} onChange={handleChange} required />
            </label>
            <label>
              Teléfono
              <input name="telefono" value={form.telefono} onChange={handleChange} />
            </label>
            <label>
              Usuario
              <input name="nombre_usuario" value={form.nombre_usuario} onChange={handleChange} required />
            </label>
            <label>
              Contraseña
              <input type="password" name="contrasena" value={form.contrasena} onChange={handleChange} required />
            </label>
            <label className="full-width">
              Dirección
              <input name="direccion" value={form.direccion} onChange={handleChange} required />
            </label>
          </div>

          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Registrando...' : 'Crear cliente'}
          </button>

          {message ? <p className="form-message">{message}</p> : null}
        </form>
      </div>
    </div>
  );
}