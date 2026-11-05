package com.reportevuelos.aerolineas.service;

import com.reportevuelos.aerolineas.dto.AvionRequest;
import com.reportevuelos.aerolineas.dto.AvionResponse;
import com.reportevuelos.aerolineas.exception.ConflictoException;
import com.reportevuelos.aerolineas.exception.RecursoNoEncontradoException;
import com.reportevuelos.aerolineas.exception.SolicitudInvalidaException;
import com.reportevuelos.aerolineas.model.Aerolinea;
import com.reportevuelos.aerolineas.model.Avion;
import com.reportevuelos.aerolineas.repository.AerolineaRepository;
import com.reportevuelos.aerolineas.repository.AvionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvionService {

    private final AvionRepository avionRepository;
    private final AerolineaRepository aerolineaRepository;

    public AvionService(AvionRepository avionRepository, AerolineaRepository aerolineaRepository) {
        this.avionRepository = avionRepository;
        this.aerolineaRepository = aerolineaRepository;
    }

    @Transactional(readOnly = true)
    public List<AvionResponse> listar(Long aerolineaId) {
        List<Avion> aviones = aerolineaId == null
                ? avionRepository.findAll()
                : avionRepository.findByAerolineaId(aerolineaId);
        return aviones.stream().map(AvionResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AvionResponse obtener(Long id) {
        return AvionResponse.from(buscar(id));
    }

    @Transactional
    public AvionResponse crear(AvionRequest request) {
        String matricula = request.matricula().trim().toUpperCase();
        if (avionRepository.existsByMatricula(matricula)) {
            throw new ConflictoException("Ya existe un avión con la matrícula " + matricula);
        }
        Aerolinea aerolinea = buscarAerolinea(request.aerolineaId());
        Avion avion = new Avion(matricula, request.modelo().trim(), request.capacidad(), aerolinea);
        return AvionResponse.from(avionRepository.saveAndFlush(avion));
    }

    @Transactional
    public AvionResponse actualizar(Long id, AvionRequest request) {
        Avion avion = buscar(id);
        String matricula = request.matricula().trim().toUpperCase();
        if (!avion.getMatricula().equals(matricula) && avionRepository.existsByMatricula(matricula)) {
            throw new ConflictoException("Ya existe un avión con la matrícula " + matricula);
        }
        avion.setMatricula(matricula);
        avion.setModelo(request.modelo().trim());
        avion.setCapacidad(request.capacidad());
        avion.setAerolinea(buscarAerolinea(request.aerolineaId()));
        return AvionResponse.from(avionRepository.saveAndFlush(avion));
    }

    @Transactional
    public void eliminar(Long id) {
        avionRepository.delete(buscar(id));
    }

    private Avion buscar(Long id) {
        return avionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el avión con id " + id));
    }

    private Aerolinea buscarAerolinea(Long aerolineaId) {
        return aerolineaRepository.findById(aerolineaId)
                .orElseThrow(() -> new SolicitudInvalidaException("La aerolínea " + aerolineaId + " no existe"));
    }
}
