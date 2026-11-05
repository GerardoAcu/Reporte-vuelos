package com.reportevuelos.reportes.dto;

import com.reportevuelos.reportes.model.Reporte;
import com.reportevuelos.reportes.service.EstadisticasVuelos;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReporteResponse(Long id, String tipo, LocalDate fechaDesde, LocalDate fechaHasta,
                              LocalDateTime fechaGeneracion, Integer totalVuelos,
                              Integer vuelosDemorados, Integer vuelosCancelados,
                              double porcentajeDemorados, double porcentajeCancelados, Long aerolineaId) {

    public static ReporteResponse from(Reporte reporte) {
        return new ReporteResponse(reporte.getId(), reporte.getTipo(), reporte.getFechaDesde(),
                reporte.getFechaHasta(), reporte.getFechaGeneracion(), reporte.getTotalVuelos(),
                reporte.getVuelosDemorados(), reporte.getVuelosCancelados(),
                EstadisticasVuelos.porcentaje(reporte.getVuelosDemorados(), reporte.getTotalVuelos()),
                EstadisticasVuelos.porcentaje(reporte.getVuelosCancelados(), reporte.getTotalVuelos()),
                reporte.getAerolineaId());
    }
}
