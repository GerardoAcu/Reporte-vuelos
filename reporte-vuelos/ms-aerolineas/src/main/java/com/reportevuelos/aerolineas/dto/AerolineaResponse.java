package com.reportevuelos.aerolineas.dto;

import com.reportevuelos.aerolineas.model.Aerolinea;

public record AerolineaResponse(Long id, String nombre, String codigoIata, String pais) {

    public static AerolineaResponse from(Aerolinea aerolinea) {
        return new AerolineaResponse(aerolinea.getId(), aerolinea.getNombre(),
                aerolinea.getCodigoIata(), aerolinea.getPais());
    }
}
