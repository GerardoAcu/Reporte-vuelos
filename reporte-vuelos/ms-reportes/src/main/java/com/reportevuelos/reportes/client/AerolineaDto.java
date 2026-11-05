package com.reportevuelos.reportes.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AerolineaDto(Long id, String nombre, String codigoIata, String pais) {
}
