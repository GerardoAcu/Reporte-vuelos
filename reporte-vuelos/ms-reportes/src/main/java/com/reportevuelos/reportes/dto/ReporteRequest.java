package com.reportevuelos.reportes.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Pedido de reporte. Si no se indica aerolineaId el reporte es general (todas las aerolineas).
 * Las fechas se escriben como yyyy-MM-dd.
 */
public record ReporteRequest(
        @NotNull(message = "La fecha desde es obligatoria") LocalDate fechaDesde,
        @NotNull(message = "La fecha hasta es obligatoria") LocalDate fechaHasta,
        Long aerolineaId) {
}
