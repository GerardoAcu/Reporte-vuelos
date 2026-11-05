package com.reportevuelos.vuelos.dto;

import com.reportevuelos.vuelos.model.Aeropuerto;

public record AeropuertoResponse(Long id, String nombre, String codigoIata, String ciudad, String pais) {

    public static AeropuertoResponse from(Aeropuerto aeropuerto) {
        return new AeropuertoResponse(aeropuerto.getId(), aeropuerto.getNombre(),
                aeropuerto.getCodigoIata(), aeropuerto.getCiudad(), aeropuerto.getPais());
    }
}
