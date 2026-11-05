package com.reportevuelos.aerolineas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AvionRequest(
        @NotBlank(message = "La matrícula es obligatoria") String matricula,
        @NotBlank(message = "El modelo es obligatorio") String modelo,
        @NotNull(message = "La capacidad es obligatoria")
        @Positive(message = "La capacidad debe ser mayor a 0") Integer capacidad,
        @NotNull(message = "La aerolínea es obligatoria") Long aerolineaId) {
}
