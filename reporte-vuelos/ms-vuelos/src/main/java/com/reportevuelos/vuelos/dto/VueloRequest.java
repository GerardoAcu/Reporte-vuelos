package com.reportevuelos.vuelos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record VueloRequest(
        @NotBlank(message = "El número de vuelo es obligatorio") String numeroVuelo,
        @NotNull(message = "La fecha de salida es obligatoria") LocalDateTime fechaSalida,
        @NotNull(message = "La fecha de llegada es obligatoria") LocalDateTime fechaLlegada,
        @NotNull(message = "El avión es obligatorio") Long avionId,
        @NotNull(message = "El aeropuerto de origen es obligatorio") Long origenId,
        @NotNull(message = "El aeropuerto de destino es obligatorio") Long destinoId) {
}
