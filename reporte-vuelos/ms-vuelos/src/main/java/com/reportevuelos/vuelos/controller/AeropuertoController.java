package com.reportevuelos.vuelos.controller;

import com.reportevuelos.vuelos.dto.AeropuertoRequest;
import com.reportevuelos.vuelos.dto.AeropuertoResponse;
import com.reportevuelos.vuelos.service.AeropuertoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aeropuertos")
public class AeropuertoController {

    private final AeropuertoService service;

    public AeropuertoController(AeropuertoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AeropuertoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AeropuertoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AeropuertoResponse crear(@Valid @RequestBody AeropuertoRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public AeropuertoResponse actualizar(@PathVariable Long id, @Valid @RequestBody AeropuertoRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
