package com.reportevuelos.reportes.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AvionDto(Long id, String matricula, Long aerolineaId) {
}
