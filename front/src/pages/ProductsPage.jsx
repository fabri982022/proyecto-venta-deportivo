import React, { useEffect, useMemo, useState } from 'react';
import { ProductCard } from '../components/ProductCard';
import { productosAPI } from '../services/api';

export function ProductsPage() {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [query, setQuery] = useState('');
  const [categoria, setCategoria] = useState('Todas');

  useEffect(() => {
    let isMounted = true;

    const loadProducts = async () => {
      try {
        const response = await productosAPI.obtenerTodos();
        if (isMounted) {
          setProductos(Array.isArray(response?.data) ? response.data : []);
        }
      } catch (requestError) {
        if (isMounted) {
          setError(requestError?.message || 'No se pudieron cargar los productos');
        }
      } finally {
        if (isMounted) {
          setLoading(false);
        }
      }
    };

    loadProducts();

    return () => {
      isMounted = false;
    };
  }, []);

  const categoriasDisponibles = useMemo(() => {
    const uniqueCategories = Array.from(new Set(productos.map((producto) => producto.categoria).filter(Boolean)));
    return ['Todas', ...uniqueCategories.sort()];
  }, [productos]);

  const productosFiltrados = useMemo(() => {
    const normalizedQuery = query.trim().toLowerCase();

    return productos.filter((producto) => {
      const matchesCategory = categoria === 'Todas' || producto.categoria === categoria;
      const searchableText = `${producto.nombre || ''} ${producto.descripcion || ''} ${producto.categoria || ''}`.toLowerCase();
      const matchesQuery = !normalizedQuery || searchableText.includes(normalizedQuery);

      return matchesCategory && matchesQuery;
    });
  }, [productos, query, categoria]);

  return (
    <div className="page container page-products">
      <div className="section-title">
        <span>Inventario</span>
        <h1>Productos</h1>
        <p>Filtra el catálogo, revisa el stock y agrega artículos al carrito conectado al backend.</p>
      </div>

      <div className="filters card">
        <label>
          Buscar
          <input
            type="search"
            value={query}
            onChange={(event) => setQuery(event.target.value)}
            placeholder="Nombre, categoría o descripción"
          />
        </label>

        <label>
          Categoría
          <select value={categoria} onChange={(event) => setCategoria(event.target.value)}>
            {categoriasDisponibles.map((option) => (
              <option key={option} value={option}>{option}</option>
            ))}
          </select>
        </label>
      </div>

      {loading ? <div className="state-message card">Cargando productos...</div> : null}
      {error ? <div className="state-message card error">{error}</div> : null}

      {!loading && !error && productosFiltrados.length === 0 ? (
        <div className="state-message card">No hay productos que coincidan con el filtro actual.</div>
      ) : null}

      <div className="products-grid">
        {productosFiltrados.map((producto) => (
          <ProductCard key={producto.id_producto} producto={producto} />
        ))}
      </div>
    </div>
  );
}