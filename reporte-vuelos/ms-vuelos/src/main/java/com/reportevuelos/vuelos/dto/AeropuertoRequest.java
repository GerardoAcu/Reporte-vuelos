package com.reportevuelos.vuelos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AeropuertoRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El código IATA es obligatorio")
        @Size(min = 3, max = 3, message = "El código IATA del aeropuerto debe tener 3 caracteres") String codigoIata,
        @NotBlank(message = "La ciudad es obligatoria") String ciudad,
        @NotBlank(message = "El país es obligatorio") String pais) {
}
