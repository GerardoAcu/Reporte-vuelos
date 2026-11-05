package com.reportevuelos.reportes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reporte")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "fecha_desde", nullable = false)
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta", nullable = false)
    private LocalDate fechaHasta;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;

    @Column(name = "total_vuelos", nullable = false)
    private Integer totalVuelos;

    @Column(name = "vuelos_demorados", nullable = false)
    private Integer vuelosDemorados;

    @Column(name = "vuelos_cancelados", nullable = false)
    private Integer vuelosCancelados;

    /** Referencia a una Aerolinea de ms-aerolineas (null en los reportes generales). */
    @Column(name = "aerolinea_id")
    private Long aerolineaId;

    protected Reporte() {
        // requerido por JPA
    }

    public Reporte(String tipo, LocalDate fechaDesde, LocalDate fechaHasta, LocalDateTime fechaGeneracion,
                   Integer totalVuelos, Integer vuelosDemorados, Integer vuelosCancelados, Long aerolineaId) {
        this.tipo = tipo;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.fechaGeneracion = fechaGeneracion;
        this.totalVuelos = totalVuelos;
        this.vuelosDemorados = vuelosDemorados;
        this.vuelosCancelados = vuelosCancelados;
        this.aerolineaId = aerolineaId;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public Integer getTotalVuelos() {
        return totalVuelos;
    }

    public Integer getVuelosDemorados() {
        return vuelosDemorados;
    }

    public Integer getVuelosCancelados() {
        return vuelosCancelados;
    }

    public Long getAerolineaId() {
        return aerolineaId;
    }
}
