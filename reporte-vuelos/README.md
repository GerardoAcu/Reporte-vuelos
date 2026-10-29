# Reporte de Vuelos

> **Alumnos:** *Acuña, Gerardo y Olea, Exequiel*

Proyecto final de **Desarrollo de Back End** (Tecnicatura en Desarrollo y Calidad de Software, UNSTA).

# 📌 Descripción del Proyecto

**Reporte de Vuelos** es un sistema distribuido basado en una arquitectura de **microservicios**, desarrollado con **Java 25** y **Spring Boot**, que usa **PostgreSQL** para almacenar los datos de cada servicio.

Su objetivo es gestionar de forma centralizada la información de aerolíneas, aviones, aeropuertos y vuelos, y generar reportes estadísticos: vuelos por aerolínea y por período, porcentaje de demoras y cancelaciones, y aeropuertos con más tráfico.

El sistema implementa el patrón **Database per Service** (una base de datos independiente por microservicio) y expone todo a través de un **API Gateway** como punto de entrada único.

---

# 📌 Sprints review

| Fecha | Entrega | Estado |
|---|---|---|
| 29/10 | README, diagrama de clases y diagrama entidad-relación por microservicio | Entregado |
| 05/11 | Aplicación implementada (microservicios, gateway y base de datos) | Pendiente |
| 12/11 | Documentación del proyecto (estructura, necesidades, etc.) | Pendiente |

---

# Arquitectura del Sistema

El siguiente diagrama muestra el flujo desde el API Gateway hasta las bases de datos, con las entidades y los atributos principales de cada microservicio:

```mermaid
flowchart LR
    GW["API GATEWAY<br/>Punto de entrada único<br/>- Enrutamiento<br/>- Seguridad JWT"]

    subgraph MS["Microservicios"]
        M1["MS Aerolíneas<br/><br/>Aerolinea: id, nombre, codigo_iata, pais<br/><br/>Avion: id, matricula, modelo, capacidad, aerolinea_id"]
        M2["MS Vuelos<br/><br/>Aeropuerto: id, nombre, codigo_iata, ciudad, pais<br/><br/>Vuelo: id, numero_vuelo, fecha_salida, fecha_llegada, estado, minutos_demora, avion_id, origen_id, destino_id"]
        M3["MS Reportes<br/><br/>Reporte: id, tipo, fecha_desde, fecha_hasta, fecha_generacion, total_vuelos, vuelos_demorados, vuelos_cancelados, aerolinea_id"]
    end

    subgraph BD["Bases de datos PostgreSQL"]
        D1[("aerolineas_db<br/>Aerolinea, Avion")]
        D2[("vuelos_db<br/>Aeropuerto, Vuelo")]
        D3[("reportes_db<br/>Reporte")]
    end

    GW -->|sync| M1
    GW -->|sync| M2
    GW -->|sync| M3
    M2 -.->|valida avion_id| M1
    M3 -.->|consulta vuelos| M2
    M3 -.->|consulta aerolíneas| M1
    M1 --> D1
    M2 --> D2
    M3 --> D3
```

| Servicio | Entidades | Responsabilidad |
|---|---|---|
| api-gateway | - | Entrada única, enruta a los servicios |
| ms-aerolineas | Aerolinea, Avion | Flota y datos de cada compañía |
| ms-vuelos | Aeropuerto, Vuelo | Rutas, horarios y estados |
| ms-reportes | Reporte | Consolida datos y genera estadísticas |

Entre microservicios no hay claves foráneas reales: se guarda solo el `id` (por ejemplo `avion_id` en Vuelo) y se consulta al otro servicio por HTTP.

---

# Diagramas

- [Diagramas de clases por microservicio](docs/diagrama-clases.md)
- [Diagramas entidad-relación por microservicio](docs/diagrama-er.md)

---

# Tecnologías

- Java 25 y Spring Boot
- Spring Data JPA
- Spring Cloud Gateway
- OpenFeign
- PostgreSQL
- Maven

---

# Endpoints

> A completar a medida que se implementen.
