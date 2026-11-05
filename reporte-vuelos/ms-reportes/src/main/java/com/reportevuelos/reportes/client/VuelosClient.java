package com.reportevuelos.reportes.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente HTTP (OpenFeign) hacia ms-vuelos.
 * Las fechas viajan como texto ISO (yyyy-MM-dd) para que el formato sea siempre el mismo.
 */
@FeignClient(name = "ms-vuelos", url = "${servicios.vuelos.url}")
public interface VuelosClient {

    @GetMapping("/vuelos")
    List<VueloDto> buscar(@RequestParam("desde") String desde,
                          @RequestParam("hasta") String hasta,
                          @RequestParam(value = "avionIds", required = false) List<Long> avionIds);

    @GetMapping("/vuelos/trafico-aeropuertos")
    List<TraficoAeropuertoDto> trafico(@RequestParam("desde") String desde,
                                       @RequestParam("hasta") String hasta,
                                       @RequestParam("limite") int limite);
}
