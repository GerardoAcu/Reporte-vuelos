package com.reportevuelos.reportes.service;

import com.reportevuelos.reportes.client.VueloDto;
import java.util.List;

/** Calculos de un reporte. Es logica pura (sin Spring), por eso es facil de probar. */
public final class EstadisticasVuelos {

    private EstadisticasVuelos() {
    }

    /** Los demorados y los cancelados son categorias excluyentes: un vuelo cancelado no cuenta como demorado. */
    public record Resultado(int total, int demorados, int cancelados) {
    }

    public static Resultado calcular(List<VueloDto> vuelos) {
        int demorados = 0;
        int cancelados = 0;
        for (VueloDto vuelo : vuelos) {
            if ("CANCELADO".equals(vuelo.estado())) {
                cancelados++;
            } else if ("DEMORADO".equals(vuelo.estado())
                    || (vuelo.minutosDemora() != null && vuelo.minutosDemora() > 0)) {
                demorados++;
            }
        }
        return new Resultado(vuelos.size(), demorados, cancelados);
    }

    /** Porcentaje con dos decimales; devuelve 0 si no hay vuelos. */
    public static double porcentaje(int parte, int total) {
        if (total == 0) {
            return 0.0;
        }
        return Math.round(parte * 10000.0 / total) / 100.0;
    }
}
