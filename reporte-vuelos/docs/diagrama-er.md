# Reporte de Vuelos - Diagramas entidad-relación por microservicio

Cada microservicio tiene su propia base de datos PostgreSQL.

## ms-aerolineas (aerolineas_db)

```mermaid
erDiagram
    AEROLINEA {
        bigint id PK
        varchar nombre
        varchar codigo_iata UK
        varchar pais
    }
    AVION {
        bigint id PK
        varchar matricula UK
        varchar modelo
        int capacidad
        bigint aerolinea_id FK
    }
    AEROLINEA ||--o{ AVION : opera
```

## ms-vuelos (vuelos_db)

```mermaid
erDiagram
    AEROPUERTO {
        bigint id PK
        varchar nombre
        varchar codigo_iata UK
        varchar ciudad
        varchar pais
    }
    VUELO {
        bigint id PK
        varchar numero_vuelo
        timestamp fecha_salida
        timestamp fecha_llegada
        varchar estado
        int minutos_demora
        bigint avion_id "referencia a ms-aerolineas"
        bigint origen_id FK
        bigint destino_id FK
    }
    AEROPUERTO ||--o{ VUELO : origen
    AEROPUERTO ||--o{ VUELO : destino
```

## ms-reportes (reportes_db)

```mermaid
erDiagram
    REPORTE {
        bigint id PK
        varchar tipo
        date fecha_desde
        date fecha_hasta
        timestamp fecha_generacion
        int total_vuelos
        int vuelos_demorados
        int vuelos_cancelados
        bigint aerolinea_id "referencia a ms-aerolineas"
    }
```

## Referencias entre servicios

| Campo | Servicio origen | Apunta a |
|---|---|---|
| `vuelo.avion_id` | ms-vuelos | `avion.id` en ms-aerolineas |
| `reporte.aerolinea_id` | ms-reportes | `aerolinea.id` en ms-aerolineas |

Estas referencias no son claves foráneas de base de datos: cada servicio valida el dato consultando al otro por HTTP.
