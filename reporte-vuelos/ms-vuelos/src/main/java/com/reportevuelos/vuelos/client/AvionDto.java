package com.reportevuelos.vuelos.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AvionDto(Long id, String matricula, String modelo, Integer capacidad,
                       Long aerolineaId, String aerolineaNombre) {
}
