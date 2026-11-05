package com.reportevuelos.vuelos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** Cliente HTTP (OpenFeign) hacia ms-aerolineas. */
@FeignClient(name = "ms-aerolineas", url = "${servicios.aerolineas.url}")
public interface AvionClient {

    @GetMapping("/aviones/{id}")
    AvionDto obtenerAvion(@PathVariable("id") Long id);
}
