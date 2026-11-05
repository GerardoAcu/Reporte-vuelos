package com.reportevuelos.reportes.repository;

import com.reportevuelos.reportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
}
