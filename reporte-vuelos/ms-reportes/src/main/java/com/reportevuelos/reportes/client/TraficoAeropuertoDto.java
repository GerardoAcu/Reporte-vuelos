package com.reportevuelos.reportes.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TraficoAeropuertoDto(Long aeropuertoId, String codigoIata, String nombre, long cantidadVuelos) {
}
