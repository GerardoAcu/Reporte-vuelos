-- Crea las tres bases de datos del proyecto (una por microservicio).
-- Ejecutar conectado como usuario postgres, por ejemplo:
--   psql -U postgres -f docs/crear-bases.sql
-- Las tablas las crea solas cada microservicio al arrancar (spring.jpa.hibernate.ddl-auto=update).

CREATE DATABASE aerolineas_db;
CREATE DATABASE vuelos_db;
CREATE DATABASE reportes_db;
