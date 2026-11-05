package com.reportevuelos.aerolineas.dto;

import com.reportevuelos.aerolineas.model.Avion;

public record AvionResponse(Long id, String matricula, String modelo, Integer capacidad,
                            Long aerolineaId, String aerolineaNombre) {

    public static AvionResponse from(Avion avion) {
        return new AvionResponse(avion.getId(), avion.getMatricula(), avion.getModelo(),
                avion.getCapacidad(), avion.getAerolinea().getId(), avion.getAerolinea().getNombre());
    }
}
