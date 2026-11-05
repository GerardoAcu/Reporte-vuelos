package com.reportevuelos.aerolineas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AerolineaRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El código IATA es obligatorio")
        @Size(min = 2, max = 3, message = "El código IATA debe tener 2 o 3 caracteres") String codigoIata,
        @NotBlank(message = "El país es obligatorio") String pais) {
}
