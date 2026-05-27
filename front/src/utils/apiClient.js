/**
 * Cliente mejorado para llamadas a la API con:
 * - Retry automático
 * - Timeout personalizado
 * - Manejo robusto de errores
 * - Logging detallado
 */

import apiConfig from '../config/api.config';

const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));

/**
 * Realiza una llamada a la API con reintentos automáticos
 * @param {string} endpoint - El endpoint de la API (ej: '/usuarios')
 * @param {object} options - Opciones de fetch estándar
 * @returns {Promise<object>} - La respuesta JSON del servidor
 */
export const apiCallWithRetry = async (endpoint, options = {}) => {
  const fullUrl = `${apiConfig.fullUrl}${endpoint}`;
  
  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
  };

  let lastError = null;

  for (let attempt = 1; attempt <= apiConfig.retryAttempts; attempt++) {
    try {
      console.log(`Attempt ${attempt}/${apiConfig.retryAttempts}: ${options.method || 'GET'} ${fullUrl}`);

      const controller = new AbortController();
      const timeoutId = setTimeout(() => controller.abort(), apiConfig.timeout);

      try {
        const response = await fetch(fullUrl, {
          ...defaultOptions,
          ...options,
          signal: controller.signal,
        });

        clearTimeout(timeoutId);

        // Intentar parsear la respuesta JSON
        let data;
        try {
          data = await response.json();
        } catch {
          // Si no es JSON, crear un objeto con el status
          data = { message: `HTTP ${response.status}` };
        }

        // Si no es OK, lanzar error
        if (!response.ok) {
          throw new Error(data.message || `Error ${response.status}: ${response.statusText}`);
        }

        console.log(`Success: ${response.status}`);
        return data;
      } finally {
        clearTimeout(timeoutId);
      }
    } catch (error) {
      lastError = error;
      console.warn(`Attempt ${attempt} failed:`, error.message);

      // Si es el último intento, no reintentar
      if (attempt === apiConfig.retryAttempts) {
        break;
      }

      // Esperar antes del siguiente intento (backoff exponencial)
      const waitTime = Math.min(1000 * Math.pow(2, attempt - 1), 5000);
      console.log(`Retrying in ${waitTime}ms...`);
      await sleep(waitTime);
    }
  }

  // Si llegamos aquí, todos los intentos fallaron
  const errorMessage = lastError?.message || 'Failed to connect to server';
  console.error(`Error after ${apiConfig.retryAttempts} attempts:`, errorMessage);
  throw new Error(errorMessage);
};

export default apiCallWithRetry;
