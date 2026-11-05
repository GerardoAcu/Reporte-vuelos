package com.reportevuelos.aerolineas.repository;

import com.reportevuelos.aerolineas.model.Avion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvionRepository extends JpaRepository<Avion, Long> {

    List<Avion> findByAerolineaId(Long aerolineaId);

    boolean existsByMatricula(String matricula);

    boolean existsByAerolineaId(Long aerolineaId);
}
