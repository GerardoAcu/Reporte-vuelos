package com.reportevuelos.vuelos.dto;

import com.reportevuelos.vuelos.model.EstadoVuelo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record EstadoRequest(
        @NotNull(message = "El estado es obligatorio") EstadoVuelo estado,
        @PositiveOrZero(message = "Los minutos de demora no pueden ser negativos") Integer minutosDemora) {
}
