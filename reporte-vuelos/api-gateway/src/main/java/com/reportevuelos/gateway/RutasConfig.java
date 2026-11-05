package com.reportevuelos.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Define a que microservicio se envia cada ruta que llega al gateway. */
@Configuration
public class RutasConfig {

    @Bean
    public RouteLocator rutas(RouteLocatorBuilder builder,
                              @Value("${servicios.aerolineas.url}") String aerolineasUrl,
                              @Value("${servicios.vuelos.url}") String vuelosUrl,
                              @Value("${servicios.reportes.url}") String reportesUrl) {
        return builder.routes()
                .route("ms-aerolineas", r -> r.path("/aerolineas/**", "/aviones/**").uri(aerolineasUrl))
                .route("ms-vuelos", r -> r.path("/aeropuertos/**", "/vuelos/**").uri(vuelosUrl))
                .route("ms-reportes", r -> r.path("/reportes/**").uri(reportesUrl))
                .build();
    }
}
