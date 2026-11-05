package com.reportevuelos.vuelos.service;

import com.reportevuelos.vuelos.client.AvionClient;
import com.reportevuelos.vuelos.dto.EstadoRequest;
import com.reportevuelos.vuelos.dto.TraficoAeropuertoResponse;
import com.reportevuelos.vuelos.dto.VueloRequest;
import com.reportevuelos.vuelos.dto.VueloResponse;
import com.reportevuelos.vuelos.exception.RecursoNoEncontradoException;
import com.reportevuelos.vuelos.exception.ServicioNoDisponibleException;
import com.reportevuelos.vuelos.exception.SolicitudInvalidaException;
import com.reportevuelos.vuelos.model.Aeropuerto;
import com.reportevuelos.vuelos.model.EstadoVuelo;
import com.reportevuelos.vuelos.model.Vuelo;
import com.reportevuelos.vuelos.repository.AeropuertoRepository;
import com.reportevuelos.vuelos.repository.VueloRepository;
import feign.FeignException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VueloService {

    private static final LocalDateTime INICIO_POR_DEFECTO = LocalDateTime.of(1900, 1, 1, 0, 0);
    private static final LocalDateTime FIN_POR_DEFECTO = LocalDateTime.of(2999, 12, 31, 0, 0);

    private final VueloRepository vueloRepository;
    private final AeropuertoRepository aeropuertoRepository;
    private final AvionClient avionClient;

    public VueloService(VueloRepository vueloRepository, AeropuertoRepository aeropuertoRepository,
                        AvionClient avionClient) {
        this.vueloRepository = vueloRepository;
        this.aeropuertoRepository = aeropuertoRepository;
        this.avionClient = avionClient;
    }

    @Transactional(readOnly = true)
    public List<VueloResponse> buscar(LocalDate desde, LocalDate hasta, EstadoVuelo estado, List<Long> avionIds) {
        return filtrarPorFecha(desde, hasta).stream()
                .filter(v -> estado == null || v.getEstado() == estado)
                .filter(v -> avionIds == null || avionIds.contains(v.getAvionId()))
                .map(VueloResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public VueloResponse obtener(Long id) {
        return VueloResponse.from(buscarVuelo(id));
    }

    /**
     * Crea un vuelo. Antes de guardarlo valida, consultando a ms-aerolineas por HTTP (OpenFeign),
     * que el avion indicado exista. No lleva @Transactional para no mantener una conexion a la base
     * abierta mientras se espera la respuesta del otro microservicio.
     */
    public VueloResponse crear(VueloRequest request) {
        if (request.origenId().equals(request.destinoId())) {
            throw new SolicitudInvalidaException("El origen y el destino no pueden ser el mismo aeropuerto");
        }
        if (!request.fechaLlegada().isAfter(request.fechaSalida())) {
            throw new SolicitudInvalidaException("La fecha de llegada debe ser posterior a la de salida");
        }
        Aeropuerto origen = buscarAeropuerto(request.origenId(), "de origen");
        Aeropuerto destino = buscarAeropuerto(request.destinoId(), "de destino");
        verificarAvion(request.avionId());

        Vuelo vuelo = new Vuelo(request.numeroVuelo().trim().toUpperCase(), request.fechaSalida(),
                request.fechaLlegada(), EstadoVuelo.PROGRAMADO, 0, request.avionId(), origen, destino);
        return VueloResponse.from(vueloRepository.saveAndFlush(vuelo));
    }

    @Transactional
    public VueloResponse actualizarEstado(Long id, EstadoRequest request) {
        Vuelo vuelo = buscarVuelo(id);
        if (request.estado() == EstadoVuelo.DEMORADO
                && (request.minutosDemora() == null || request.minutosDemora() <= 0)) {
            throw new SolicitudInvalidaException("Un vuelo DEMORADO necesita minutosDemora mayor a 0");
        }
        vuelo.setEstado(request.estado());
        if (request.minutosDemora() != null) {
            vuelo.setMinutosDemora(request.minutosDemora());
        }
        return VueloResponse.from(vueloRepository.saveAndFlush(vuelo));
    }

    @Transactional
    public void eliminar(Long id) {
        vueloRepository.delete(buscarVuelo(id));
    }

    /** Aeropuertos con mas trafico (vuelos que salen o llegan, sin contar cancelados). */
    @Transactional(readOnly = true)
    public List<TraficoAeropuertoResponse> trafico(LocalDate desde, LocalDate hasta, int limite) {
        Map<Long, Long> conteo = new HashMap<>();
        Map<Long, Aeropuerto> aeropuertos = new HashMap<>();
        for (Vuelo vuelo : filtrarPorFecha(desde, hasta)) {
            if (vuelo.getEstado() == EstadoVuelo.CANCELADO) {
                continue;
            }
            for (Aeropuerto aeropuerto : List.of(vuelo.getOrigen(), vuelo.getDestino())) {
                conteo.merge(aeropuerto.getId(), 1L, Long::sum);
                aeropuertos.putIfAbsent(aeropuerto.getId(), aeropuerto);
            }
        }
        return conteo.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(Math.max(limite, 1))
                .map(entrada -> {
                    Aeropuerto a = aeropuertos.get(entrada.getKey());
                    return new TraficoAeropuertoResponse(a.getId(), a.getCodigoIata(), a.getNombre(),
                            entrada.getValue());
                })
                .toList();
    }

    private List<Vuelo> filtrarPorFecha(LocalDate desde, LocalDate hasta) {
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            throw new SolicitudInvalidaException("La fecha 'desde' no puede ser posterior a 'hasta'");
        }
        LocalDateTime inicio = desde != null ? desde.atStartOfDay() : INICIO_POR_DEFECTO;
        LocalDateTime fin = hasta != null ? hasta.plusDays(1).atStartOfDay() : FIN_POR_DEFECTO;
        return vueloRepository.findByFechaSalidaGreaterThanEqualAndFechaSalidaLessThan(inicio, fin);
    }

    private void verificarAvion(Long avionId) {
        try {
            avionClient.obtenerAvion(avionId);
        } catch (FeignException.NotFound e) {
            throw new SolicitudInvalidaException("El avión " + avionId + " no existe en ms-aerolineas");
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException(
                    "No se pudo validar el avión: ms-aerolineas no está disponible");
        }
    }

    private Aeropuerto buscarAeropuerto(Long id, String rol) {
        return aeropuertoRepository.findById(id)
                .orElseThrow(() -> new SolicitudInvalidaException("El aeropuerto " + rol + " (" + id + ") no existe"));
    }

    private Vuelo buscarVuelo(Long id) {
        return vueloRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el vuelo con id " + id));
    }
}
