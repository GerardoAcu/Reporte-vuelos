package com.reportevuelos.vuelos.repository;

import com.reportevuelos.vuelos.model.Aeropuerto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Long> {

    boolean existsByCodigoIata(String codigoIata);
}
