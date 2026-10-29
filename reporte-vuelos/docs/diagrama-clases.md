# Reporte de Vuelos - Diagramas de clases por microservicio

Proyecto final de Desarrollo de Back End. Arquitectura de 3 microservicios detrás de un API Gateway, con base de datos PostgreSQL.

## ms-aerolineas

```mermaid
classDiagram
    class Aerolinea {
        -Long id
        -String nombre
        -String codigoIata
        -String pais
    }
    class Avion {
        -Long id
        -String matricula
        -String modelo
        -Integer capacidad
        -Aerolinea aerolinea
    }
    Aerolinea "1" --> "0..*" Avion : opera
```

## ms-vuelos

```mermaid
classDiagram
    class Aeropuerto {
        -Long id
        -String nombre
        -String codigoIata
        -String ciudad
        -String pais
    }
    class Vuelo {
        -Long id
        -String numeroVuelo
        -LocalDateTime fechaSalida
        -LocalDateTime fechaLlegada
        -EstadoVuelo estado
        -Integer minutosDemora
        -Long avionId
        -Aeropuerto origen
        -Aeropuerto destino
    }
    class EstadoVuelo {
        <<enumeration>>
        PROGRAMADO
        EN_VUELO
        ATERRIZADO
        DEMORADO
        CANCELADO
    }
    Aeropuerto "1" --> "0..*" Vuelo : origen
    Aeropuerto "1" --> "0..*" Vuelo : destino
    Vuelo --> EstadoVuelo
```

`avionId` referencia a un Avión de ms-aerolineas (no es una clave foránea real).

## ms-reportes

```mermaid
classDiagram
    class Reporte {
        -Long id
        -String tipo
        -LocalDate fechaDesde
        -LocalDate fechaHasta
        -LocalDateTime fechaGeneracion
        -Integer totalVuelos
        -Integer vuelosDemorados
        -Integer vuelosCancelados
        -Long aerolineaId
        +generar()
    }
```

`aerolineaId` referencia a una Aerolínea de ms-aerolineas. Para calcular las estadísticas, `generar()` consulta los datos de ms-vuelos.

## Comunicación entre servicios

```mermaid
flowchart LR
    GW[API Gateway] --> A[ms-aerolineas]
    GW --> V[ms-vuelos]
    GW --> R[ms-reportes]
    R -->|consulta vuelos| V
    R -->|consulta aerolíneas| A
    V -->|valida avionId| A
```

## Notas de diseño

- Entre microservicios no hay claves foráneas reales: se guarda solo el `id` y se consulta al otro servicio por HTTP.
- Dentro de un mismo servicio sí hay relaciones JPA (`@ManyToOne` / `@OneToMany`).
- Base de datos PostgreSQL, una por servicio.
