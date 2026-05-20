-- Permitir conexiones remotas del usuario root con caching_sha2_password
DROP USER IF EXISTS 'root'@'%';
CREATE USER 'root'@'%' IDENTIFIED WITH caching_sha2_password BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

-- Crear usuario adicional para la aplicación
DROP USER IF EXISTS 'appuser'@'%';
CREATE USER 'appuser'@'%' IDENTIFIED WITH caching_sha2_password BY 'apppassword';
GRANT ALL PRIVILEGES ON deporte.* TO 'appuser'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
