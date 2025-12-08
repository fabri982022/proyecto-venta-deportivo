import logo from './logo.svg';
import './App.css';
import { useEffect, useState } from 'react';

function App() {
  const [backendStatus, setBackendStatus] = useState('checking');
  const [backendData, setBackendData] = useState(null);
  const apiUrl = 'http://localhost:8080/api/health';

  useEffect(() => {
    // Intentar conectar al backend
    fetch(apiUrl)
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error('Backend respondió con error');
      })
      .then(data => {
        setBackendData(data);
        setBackendStatus('connected');
      })
      .catch(error => {
        console.error('Backend no disponible:', error);
        setBackendStatus('disconnected');
      });
  }, [apiUrl]);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Proyecto Deportivo</h1>
        
        {backendStatus === 'checking' && (
          <p style={{color: 'yellow'}}>⏳ Verificando conexión con backend...</p>
        )}
        
        {backendStatus === 'connected' && backendData && (
          <div style={{color: 'green'}}>
            <p>✓ Backend conectado</p>
            <p>Estado: {backendData.status}</p>
            <p>{backendData.message}</p>
            <p style={{fontSize: '14px', marginTop: '20px'}}>
              Listo para empezar a trabajar con los modelos
            </p>
          </div>
        )}
        
        {backendStatus === 'disconnected' && (
          <div style={{color: 'red'}}>
            <p>✗ Backend no disponible</p>
            <p style={{fontSize: '14px'}}>
              Asegúrate de que el servidor esté corriendo en http://localhost:8080
            </p>
          </div>
        )}
      </header>
    </div>
  );
}

export default App;