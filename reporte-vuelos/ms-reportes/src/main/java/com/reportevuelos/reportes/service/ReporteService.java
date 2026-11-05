package com.reportevuelos.reportes.service;

import com.reportevuelos.reportes.client.AerolineasClient;
import com.reportevuelos.reportes.client.AvionDto;
import com.reportevuelos.reportes.client.TraficoAeropuertoDto;
import com.reportevuelos.reportes.client.VueloDto;
import com.reportevuelos.reportes.client.VuelosClient;
import com.reportevuelos.reportes.dto.ReporteRequest;
import com.reportevuelos.reportes.dto.ReporteResponse;
import com.reportevuelos.reportes.exception.RecursoNoEncontradoException;
import com.reportevuelos.reportes.exception.ServicioNoDisponibleException;
import com.reportevuelos.reportes.exception.SolicitudInvalidaException;
import com.reportevuelos.reportes.model.Reporte;
import com.reportevuelos.reportes.repository.ReporteRepository;
import feign.FeignException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final VuelosClient vuelosClient;
    private final AerolineasClient aerolineasClient;

    public ReporteService(ReporteRepository reporteRepository, VuelosClient vuelosClient,
                          AerolineasClient aerolineasClient) {
        this.reporteRepository = reporteRepository;
        this.vuelosClient = vuelosClient;
        this.aerolineasClient = aerolineasClient;
    }

    /**
     * Genera un reporte consultando por HTTP (OpenFeign) a ms-vuelos y, si el reporte es de una
     * aerolinea, tambien a ms-aerolineas. El resultado se guarda en la base de datos de reportes.
     */
    public ReporteResponse generar(ReporteRequest request) {
        if (request.fechaDesde().isAfter(request.fechaHasta())) {
            throw new SolicitudInvalidaException("La fecha 'desde' no puede ser posterior a 'hasta'");
        }

        String tipo;
        List<VueloDto> vuelos;
        if (request.aerolineaId() == null) {
            tipo = "GENERAL";
            vuelos = consultarVuelos(request.fechaDesde(), request.fechaHasta(), null);
        } else {
            tipo = "POR_AEROLINEA";
            verificarAerolinea(request.aerolineaId());
            List<Long> avionIds = idsDeAviones(request.aerolineaId());
            vuelos = avionIds.isEmpty()
                    ? List.of()
                    : consultarVuelos(request.fechaDesde(), request.fechaHasta(), avionIds);
        }

        EstadisticasVuelos.Resultado resultado = EstadisticasVuelos.calcular(vuelos);
        Reporte reporte = new Reporte(tipo, request.fechaDesde(), request.fechaHasta(), LocalDateTime.now(),
                resultado.total(), resultado.demorados(), resultado.cancelados(), request.aerolineaId());
        return ReporteResponse.from(reporteRepository.save(reporte));
    }

    public List<ReporteResponse> listar() {
        return reporteRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaGeneracion"))
                .stream().map(ReporteResponse::from).toList();
    }

    public ReporteResponse obtener(Long id) {
        return ReporteResponse.from(buscar(id));
    }

    public void eliminar(Long id) {
        reporteRepository.delete(buscar(id));
    }

    public List<TraficoAeropuertoDto> traficoAeropuertos(LocalDate desde, LocalDate hasta, int limite) {
        if (desde.isAfter(hasta)) {
            throw new SolicitudInvalidaException("La fecha 'desde' no puede ser posterior a 'hasta'");
        }
        try {
            return vuelosClient.trafico(desde.toString(), hasta.toString(), limite);
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("ms-vuelos no está disponible");
        }
    }

    private Reporte buscar(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el reporte con id " + id));
    }

    private void verificarAerolinea(Long aerolineaId) {
        try {
            aerolineasClient.obtenerAerolinea(aerolineaId);
        } catch (FeignException.NotFound e) {
            throw new SolicitudInvalidaException("La aerolínea " + aerolineaId + " no existe");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("ms-aerolineas no está disponible");
        }
    }

    private List<Long> idsDeAviones(Long aerolineaId) {
        try {
            return aerolineasClient.avionesPorAerolinea(aerolineaId).stream().map(AvionDto::id).toList();
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("ms-aerolineas no está disponible");
        }
    }

    private List<VueloDto> consultarVuelos(LocalDate desde, LocalDate hasta, List<Long> avionIds) {
        try {
            return vuelosClient.buscar(desde.toString(), hasta.toString(), avionIds);
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("ms-vuelos no está disponible");
        }
    }
}
