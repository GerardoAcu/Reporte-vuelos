package com.reportevuelos.reportes.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Vista reducida de un vuelo de ms-vuelos: solo los campos que necesita el reporte. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VueloDto(Long id, String numeroVuelo, String estado, Integer minutosDemora, Long avionId) {
}
