package com.reportevuelos.vuelos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "vuelo")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_vuelo", nullable = false)
    private String numeroVuelo;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida;

    @Column(name = "fecha_llegada", nullable = false)
    private LocalDateTime fechaLlegada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVuelo estado;

    @Column(name = "minutos_demora", nullable = false)
    private Integer minutosDemora;

    /** Referencia a un Avion de ms-aerolineas (no es una clave foranea de base de datos). */
    @Column(name = "avion_id", nullable = false)
    private Long avionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origen_id", nullable = false)
    private Aeropuerto origen;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destino_id", nullable = false)
    private Aeropuerto destino;

    protected Vuelo() {
        // requerido por JPA
    }

    public Vuelo(String numeroVuelo, LocalDateTime fechaSalida, LocalDateTime fechaLlegada,
                 EstadoVuelo estado, Integer minutosDemora, Long avionId,
                 Aeropuerto origen, Aeropuerto destino) {
        this.numeroVuelo = numeroVuelo;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.estado = estado;
        this.minutosDemora = minutosDemora;
        this.avionId = avionId;
        this.origen = origen;
        this.destino = destino;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public EstadoVuelo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVuelo estado) {
        this.estado = estado;
    }

    public Integer getMinutosDemora() {
        return minutosDemora;
    }

    public void setMinutosDemora(Integer minutosDemora) {
        this.minutosDemora = minutosDemora;
    }

    public Long getAvionId() {
        return avionId;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }
}
