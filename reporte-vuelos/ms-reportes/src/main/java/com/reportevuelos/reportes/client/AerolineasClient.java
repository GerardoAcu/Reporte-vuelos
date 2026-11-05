package com.reportevuelos.reportes.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/** Cliente HTTP (OpenFeign) hacia ms-aerolineas. */
@FeignClient(name = "ms-aerolineas", url = "${servicios.aerolineas.url}")
public interface AerolineasClient {

    @GetMapping("/aerolineas/{id}")
    AerolineaDto obtenerAerolinea(@PathVariable("id") Long id);

    @GetMapping("/aviones")
    List<AvionDto> avionesPorAerolinea(@RequestParam("aerolineaId") Long aerolineaId);
}
