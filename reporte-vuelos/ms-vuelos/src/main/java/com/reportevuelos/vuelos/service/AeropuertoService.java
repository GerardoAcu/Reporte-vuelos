package com.reportevuelos.vuelos.service;

import com.reportevuelos.vuelos.dto.AeropuertoRequest;
import com.reportevuelos.vuelos.dto.AeropuertoResponse;
import com.reportevuelos.vuelos.exception.ConflictoException;
import com.reportevuelos.vuelos.exception.RecursoNoEncontradoException;
import com.reportevuelos.vuelos.model.Aeropuerto;
import com.reportevuelos.vuelos.repository.AeropuertoRepository;
import com.reportevuelos.vuelos.repository.VueloRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AeropuertoService {

    private final AeropuertoRepository aeropuertoRepository;
    private final VueloRepository vueloRepository;

    public AeropuertoService(AeropuertoRepository aeropuertoRepository, VueloRepository vueloRepository) {
        this.aeropuertoRepository = aeropuertoRepository;
        this.vueloRepository = vueloRepository;
    }

    @Transactional(readOnly = true)
    public List<AeropuertoResponse> listar() {
        return aeropuertoRepository.findAll().stream().map(AeropuertoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AeropuertoResponse obtener(Long id) {
        return AeropuertoResponse.from(buscar(id));
    }

    @Transactional
    public AeropuertoResponse crear(AeropuertoRequest request) {
        String codigo = request.codigoIata().trim().toUpperCase();
        if (aeropuertoRepository.existsByCodigoIata(codigo)) {
            throw new ConflictoException("Ya existe un aeropuerto con el código IATA " + codigo);
        }
        Aeropuerto aeropuerto = new Aeropuerto(request.nombre().trim(), codigo,
                request.ciudad().trim(), request.pais().trim());
        return AeropuertoResponse.from(aeropuertoRepository.saveAndFlush(aeropuerto));
    }

    @Transactional
    public AeropuertoResponse actualizar(Long id, AeropuertoRequest request) {
        Aeropuerto aeropuerto = buscar(id);
        String codigo = request.codigoIata().trim().toUpperCase();
        if (!aeropuerto.getCodigoIata().equals(codigo) && aeropuertoRepository.existsByCodigoIata(codigo)) {
            throw new ConflictoException("Ya existe un aeropuerto con el código IATA " + codigo);
        }
        aeropuerto.setNombre(request.nombre().trim());
        aeropuerto.setCodigoIata(codigo);
        aeropuerto.setCiudad(request.ciudad().trim());
        aeropuerto.setPais(request.pais().trim());
        return AeropuertoResponse.from(aeropuertoRepository.saveAndFlush(aeropuerto));
    }

    @Transactional
    public void eliminar(Long id) {
        Aeropuerto aeropuerto = buscar(id);
        if (vueloRepository.existsByOrigenIdOrDestinoId(id, id)) {
            throw new ConflictoException("No se puede eliminar el aeropuerto: tiene vuelos asociados");
        }
        aeropuertoRepository.delete(aeropuerto);
    }

    private Aeropuerto buscar(Long id) {
        return aeropuertoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el aeropuerto con id " + id));
    }
}
