package com.reportevuelos.aerolineas.service;

import com.reportevuelos.aerolineas.dto.AerolineaRequest;
import com.reportevuelos.aerolineas.dto.AerolineaResponse;
import com.reportevuelos.aerolineas.exception.ConflictoException;
import com.reportevuelos.aerolineas.exception.RecursoNoEncontradoException;
import com.reportevuelos.aerolineas.model.Aerolinea;
import com.reportevuelos.aerolineas.repository.AerolineaRepository;
import com.reportevuelos.aerolineas.repository.AvionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AerolineaService {

    private final AerolineaRepository aerolineaRepository;
    private final AvionRepository avionRepository;

    public AerolineaService(AerolineaRepository aerolineaRepository, AvionRepository avionRepository) {
        this.aerolineaRepository = aerolineaRepository;
        this.avionRepository = avionRepository;
    }

    @Transactional(readOnly = true)
    public List<AerolineaResponse> listar() {
        return aerolineaRepository.findAll().stream().map(AerolineaResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AerolineaResponse obtener(Long id) {
        return AerolineaResponse.from(buscar(id));
    }

    @Transactional
    public AerolineaResponse crear(AerolineaRequest request) {
        String codigo = request.codigoIata().trim().toUpperCase();
        if (aerolineaRepository.existsByCodigoIata(codigo)) {
            throw new ConflictoException("Ya existe una aerolínea con el código IATA " + codigo);
        }
        Aerolinea aerolinea = new Aerolinea(request.nombre().trim(), codigo, request.pais().trim());
        return AerolineaResponse.from(aerolineaRepository.saveAndFlush(aerolinea));
    }

    @Transactional
    public AerolineaResponse actualizar(Long id, AerolineaRequest request) {
        Aerolinea aerolinea = buscar(id);
        String codigo = request.codigoIata().trim().toUpperCase();
        if (!aerolinea.getCodigoIata().equals(codigo) && aerolineaRepository.existsByCodigoIata(codigo)) {
            throw new ConflictoException("Ya existe una aerolínea con el código IATA " + codigo);
        }
        aerolinea.setNombre(request.nombre().trim());
        aerolinea.setCodigoIata(codigo);
        aerolinea.setPais(request.pais().trim());
        return AerolineaResponse.from(aerolineaRepository.saveAndFlush(aerolinea));
    }

    @Transactional
    public void eliminar(Long id) {
        Aerolinea aerolinea = buscar(id);
        if (avionRepository.existsByAerolineaId(id)) {
            throw new ConflictoException("No se puede eliminar la aerolínea: tiene aviones asociados");
        }
        aerolineaRepository.delete(aerolinea);
    }

    private Aerolinea buscar(Long id) {
        return aerolineaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la aerolínea con id " + id));
    }
}
