package com.reportevuelos.aerolineas.controller;

import com.reportevuelos.aerolineas.dto.AvionRequest;
import com.reportevuelos.aerolineas.dto.AvionResponse;
import com.reportevuelos.aerolineas.service.AvionService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aviones")
public class AvionController {

    private final AvionService service;

    public AvionController(AvionService service) {
        this.service = service;
    }

    @GetMapping
    public List<AvionResponse> listar(@RequestParam(required = false) Long aerolineaId) {
        return service.listar(aerolineaId);
    }

    @GetMapping("/{id}")
    public AvionResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvionResponse crear(@Valid @RequestBody AvionRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public AvionResponse actualizar(@PathVariable Long id, @Valid @RequestBody AvionRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
