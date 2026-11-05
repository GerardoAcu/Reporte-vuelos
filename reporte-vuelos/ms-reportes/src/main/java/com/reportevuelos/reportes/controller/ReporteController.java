package com.reportevuelos.reportes.controller;

import com.reportevuelos.reportes.client.TraficoAeropuertoDto;
import com.reportevuelos.reportes.dto.ReporteRequest;
import com.reportevuelos.reportes.dto.ReporteResponse;
import com.reportevuelos.reportes.service.ReporteService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    /** Genera un reporte nuevo y lo guarda. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReporteResponse generar(@Valid @RequestBody ReporteRequest request) {
        return service.generar(request);
    }

    /** Lista los reportes guardados (los mas nuevos primero). */
    @GetMapping
    public List<ReporteResponse> listar() {
        return service.listar();
    }

    /** Aeropuertos con mas trafico en un periodo (consulta a ms-vuelos, no se guarda). */
    @GetMapping("/trafico-aeropuertos")
    public List<TraficoAeropuertoDto> trafico(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(defaultValue = "5") int limite) {
        return service.traficoAeropuertos(desde, hasta, limite);
    }

    @GetMapping("/{id}")
    public ReporteResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
