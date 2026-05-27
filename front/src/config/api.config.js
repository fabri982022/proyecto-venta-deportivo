/**
 * Configuración centralizada de la API
 * Este archivo define todas las constantes y configuraciones necesarias
 * para conectarse correctamente al backend
 */

// Obtener la URL base del backend desde variables de entorno
const API_BASE_URL = (() => {
  const envUrl = process.env.REACT_APP_API_URL;
  
  if (envUrl) {
    return envUrl.replace(/\/$/, ''); // Eliminar trailing slash
  }
  
  // Fallback para desarrollo local
  return 'http://localhost:8080';
})();

const API_PREFIX = '/api/v1';
const API_TIMEOUT = parseInt(process.env.REACT_APP_API_TIMEOUT || '10000', 10);
const RETRY_ATTEMPTS = parseInt(process.env.REACT_APP_RETRY_ATTEMPTS || '3', 10);

// Validar que la configuración sea válida
const validateConfig = () => {
  console.log('🔧 API Configuration:');
  console.log(`   Base URL: ${API_BASE_URL}`);
  console.log(`   Prefix: ${API_PREFIX}`);
  console.log(`   Timeout: ${API_TIMEOUT}ms`);
  console.log(`   Retry Attempts: ${RETRY_ATTEMPTS}`);
};

// Ejecutar validación
if (process.env.NODE_ENV === 'development') {
  validateConfig();
}

export const apiConfig = {
  baseUrl: API_BASE_URL,
  prefix: API_PREFIX,
  timeout: API_TIMEOUT,
  retryAttempts: RETRY_ATTEMPTS,
  fullUrl: `${API_BASE_URL}${API_PREFIX}`,
};

export default apiConfig;
