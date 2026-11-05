package com.reportevuelos.vuelos.repository;

import com.reportevuelos.vuelos.model.Vuelo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    /** Vuelos cuya salida cae en el rango [desde, hasta). */
    List<Vuelo> findByFechaSalidaGreaterThanEqualAndFechaSalidaLessThan(LocalDateTime desde, LocalDateTime hasta);

    boolean existsByOrigenIdOrDestinoId(Long origenId, Long destinoId);
}
