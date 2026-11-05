package com.reportevuelos.aerolineas.repository;

import com.reportevuelos.aerolineas.model.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AerolineaRepository extends JpaRepository<Aerolinea, Long> {

    boolean existsByCodigoIata(String codigoIata);
}
