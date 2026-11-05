package com.reportevuelos.aerolineas.controller;

import com.reportevuelos.aerolineas.dto.AerolineaRequest;
import com.reportevuelos.aerolineas.dto.AerolineaResponse;
import com.reportevuelos.aerolineas.service.AerolineaService;
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
@RequestMapping("/aerolineas")
public class AerolineaController {

    private final AerolineaService service;

    public AerolineaController(AerolineaService service) {
        this.service = service;
    }

    @GetMapping
    public List<AerolineaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AerolineaResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AerolineaResponse crear(@Valid @RequestBody AerolineaRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public AerolineaResponse actualizar(@PathVariable Long id, @Valid @RequestBody AerolineaRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
