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
    GW["<b>API GATEWAY</b><br/>────────────<br/>Rol: punto de entrada único<br/>────────────<br/>• Enrutamiento<br/>• Seguridad (JWT)"]

    subgraph MS["ARQUITECTURA DE MICROSERVICIOS"]
        direction TB
        M1["<b>1. MS Aerolíneas</b><br/>────────────<br/><b>Aerolinea</b><br/>id<br/>nombre<br/>codigo_iata<br/>pais<br/>────────────<br/><b>Avion</b><br/>id<br/>matricula<br/>modelo<br/>capacidad<br/>aerolinea_id"]
        M2["<b>2. MS Vuelos</b><br/>────────────<br/><b>Aeropuerto</b><br/>id<br/>nombre<br/>codigo_iata<br/>ciudad<br/>pais<br/>────────────<br/><b>Vuelo</b><br/>id<br/>numero_vuelo<br/>fecha_salida<br/>fecha_llegada<br/>estado<br/>minutos_demora<br/>avion_id<br/>origen_id<br/>destino_id"]
        M3["<b>3. MS Reportes</b><br/>────────────<br/><b>Reporte</b><br/>id<br/>tipo<br/>fecha_desde<br/>fecha_hasta<br/>fecha_generacion<br/>total_vuelos<br/>vuelos_demorados<br/>vuelos_cancelados<br/>aerolinea_id"]
    end

    subgraph BD["BASES DE DATOS (PostgreSQL)"]
        direction TB
        D1[("<b>aerolineas_db</b><br/>Aerolinea<br/>Avion")]
        D2[("<b>vuelos_db</b><br/>Aeropuerto<br/>Vuelo")]
        D3[("<b>reportes_db</b><br/>Reporte")]
    end

    GW -- "sync" --> M1 & M2 & M3
    M2 -. "valida avion_id" .-> M1
    M3 -. "consulta vuelos" .-> M2
    M3 -. "consulta aerolíneas" .-> M1
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

# Estructura del repositorio

```
reporte-vuelos/
├── api-gateway/
├── ms-aerolineas/
├── ms-vuelos/
├── ms-reportes/
├── docs/
│   ├── diagrama-clases.md
│   └── diagrama-er.md
└── README.md
```

---

# Cómo ejecutar

> A completar a medida que avance el proyecto.

1. Clonar el repositorio.
2. Crear las tres bases de datos en PostgreSQL y configurar el `application.properties` de cada servicio.
3. Levantar cada microservicio y, por último, el gateway.

---

# Endpoints

> A completar a medida que se implementen.
