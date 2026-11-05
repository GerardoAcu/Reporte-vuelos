package com.reportevuelos.vuelos.dto;

import com.reportevuelos.vuelos.model.EstadoVuelo;
import com.reportevuelos.vuelos.model.Vuelo;
import java.time.LocalDateTime;

public record VueloResponse(Long id, String numeroVuelo, LocalDateTime fechaSalida, LocalDateTime fechaLlegada,
                            EstadoVuelo estado, Integer minutosDemora, Long avionId,
                            Long origenId, String origenCodigo, Long destinoId, String destinoCodigo) {

    public static VueloResponse from(Vuelo vuelo) {
        return new VueloResponse(vuelo.getId(), vuelo.getNumeroVuelo(), vuelo.getFechaSalida(),
                vuelo.getFechaLlegada(), vuelo.getEstado(), vuelo.getMinutosDemora(), vuelo.getAvionId(),
                vuelo.getOrigen().getId(), vuelo.getOrigen().getCodigoIata(),
                vuelo.getDestino().getId(), vuelo.getDestino().getCodigoIata());
    }
}
