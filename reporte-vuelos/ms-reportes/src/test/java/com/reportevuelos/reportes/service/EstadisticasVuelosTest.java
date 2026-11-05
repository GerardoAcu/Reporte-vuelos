package com.reportevuelos.reportes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.reportevuelos.reportes.client.VueloDto;
import java.util.List;
import org.junit.jupiter.api.Test;

class EstadisticasVuelosTest {

    @Test
    void cuentaVuelosDemoradosYCancelados() {
        List<VueloDto> vuelos = List.of(
                new VueloDto(1L, "AR100", "ATERRIZADO", 0, 1L),
                new VueloDto(2L, "AR101", "DEMORADO", 45, 1L),
                new VueloDto(3L, "AR102", "ATERRIZADO", 20, 2L),
                new VueloDto(4L, "AR103", "CANCELADO", 0, 2L));

        EstadisticasVuelos.Resultado resultado = EstadisticasVuelos.calcular(vuelos);

        assertEquals(4, resultado.total());
        assertEquals(2, resultado.demorados());
        assertEquals(1, resultado.cancelados());
    }

    @Test
    void unVueloCanceladoNoCuentaComoDemorado() {
        List<VueloDto> vuelos = List.of(new VueloDto(1L, "AR200", "CANCELADO", 30, 1L));

        EstadisticasVuelos.Resultado resultado = EstadisticasVuelos.calcular(vuelos);

        assertEquals(0, resultado.demorados());
        assertEquals(1, resultado.cancelados());
    }

    @Test
    void sinVuelosElResultadoEsCero() {
        EstadisticasVuelos.Resultado resultado = EstadisticasVuelos.calcular(List.of());

        assertEquals(0, resultado.total());
        assertEquals(0, resultado.demorados());
        assertEquals(0, resultado.cancelados());
    }

    @Test
    void calculaPorcentajesConDosDecimales() {
        assertEquals(0.0, EstadisticasVuelos.porcentaje(0, 0), 0.0001);
        assertEquals(25.0, EstadisticasVuelos.porcentaje(1, 4), 0.0001);
        assertEquals(33.33, EstadisticasVuelos.porcentaje(1, 3), 0.0001);
    }
}
