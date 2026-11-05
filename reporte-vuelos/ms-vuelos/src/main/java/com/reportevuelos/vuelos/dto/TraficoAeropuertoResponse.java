package com.reportevuelos.vuelos.dto;

public record TraficoAeropuertoResponse(Long aeropuertoId, String codigoIata, String nombre, long cantidadVuelos) {
}
