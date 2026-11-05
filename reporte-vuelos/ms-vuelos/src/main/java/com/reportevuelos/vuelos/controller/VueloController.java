package com.reportevuelos.vuelos.controller;

import com.reportevuelos.vuelos.dto.EstadoRequest;
import com.reportevuelos.vuelos.dto.TraficoAeropuertoResponse;
import com.reportevuelos.vuelos.dto.VueloRequest;
import com.reportevuelos.vuelos.dto.VueloResponse;
import com.reportevuelos.vuelos.model.EstadoVuelo;
import com.reportevuelos.vuelos.service.VueloService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vuelos")
public class VueloController {

    private final VueloService service;

    public VueloController(VueloService service) {
        this.service = service;
    }

    @GetMapping
    public List<VueloResponse> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) EstadoVuelo estado,
            @RequestParam(required = false) List<Long> avionIds) {
        return service.buscar(desde, hasta, estado, avionIds);
    }

    @GetMapping("/trafico-aeropuertos")
    public List<TraficoAeropuertoResponse> trafico(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(defaultValue = "5") int limite) {
        return service.trafico(desde, hasta, limite);
    }

    @GetMapping("/{id}")
    public VueloResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VueloResponse crear(@Valid @RequestBody VueloRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}/estado")
    public VueloResponse actualizarEstado(@PathVariable Long id, @Valid @RequestBody EstadoRequest request) {
        return service.actualizarEstado(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
