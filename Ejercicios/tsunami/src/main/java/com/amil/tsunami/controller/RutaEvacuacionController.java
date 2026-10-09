
package com.amil.tsunami.controller;

import com.amil.tsunami.entity.RutaEvacuacion;
import com.amil.tsunami.entity.ZonaRiesgo;
import com.amil.tsunami.entity.Refugio;

import com.amil.tsunami.repository.RutaEvacuacionRepository;
import com.amil.tsunami.repository.ZonaRiesgoRepository;
import com.amil.tsunami.repository.RefugioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaEvacuacionController {

    private final RutaEvacuacionRepository rutaRepository;
    private final ZonaRiesgoRepository zonaRepository;
    private final RefugioRepository refugioRepository;

    public RutaEvacuacionController(
            RutaEvacuacionRepository rutaRepository,
            ZonaRiesgoRepository zonaRepository,
            RefugioRepository refugioRepository) {

        this.rutaRepository = rutaRepository;
        this.zonaRepository = zonaRepository;
        this.refugioRepository = refugioRepository;
    }

    // GET - Obtener todas las rutas
    @GetMapping
    public List<RutaEvacuacion> getAll() {
        return rutaRepository.findAll();
    }

    // GET - Buscar una ruta por ID
    @GetMapping("/{id}")
    public RutaEvacuacion getById(@PathVariable Long id) {
        return rutaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ruta de evacuacion no encontrada"
                ));
    }

    // POST - Registrar una nueva ruta
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RutaEvacuacion create(@RequestBody RutaRequest data) {

        RutaEvacuacion ruta = new RutaEvacuacion();

        asignarDatos(ruta, data);

        return rutaRepository.save(ruta);
    }

    // PUT - Actualizar una ruta
    @PutMapping("/{id}")
    public RutaEvacuacion update(
            @PathVariable Long id,
            @RequestBody RutaRequest data) {

        RutaEvacuacion ruta = getById(id);

        asignarDatos(ruta, data);

        return rutaRepository.save(ruta);
    }

    // DELETE - Eliminar una ruta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        RutaEvacuacion ruta = getById(id);

        rutaRepository.delete(ruta);

        return ResponseEntity.noContent().build();
    }

    // Asignar los datos y sus relaciones
    private void asignarDatos(
            RutaEvacuacion ruta,
            RutaRequest data) {

        if (data.nombre() == null ||
                data.nombre().isBlank() ||
                data.distanciaKm() == null ||
                data.distanciaKm() <= 0 ||
                data.tiempoEstimadoMinutos() == null ||
                data.tiempoEstimadoMinutos() <= 0 ||
                data.disponible() == null ||
                data.zonaRiesgoId() == null ||
                data.refugioId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los datos de la ruta son invalidos"
            );
        }

        ZonaRiesgo zona = zonaRepository
                .findById(data.zonaRiesgoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Zona de riesgo no encontrada"
                ));

        Refugio refugio = refugioRepository
                .findById(data.refugioId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Refugio no encontrado"
                ));

        ruta.setNombre(data.nombre());
        ruta.setDistanciaKm(data.distanciaKm());
        ruta.setTiempoEstimadoMinutos(
                data.tiempoEstimadoMinutos()
        );
        ruta.setDisponible(data.disponible());
        ruta.setZonaRiesgo(zona);
        ruta.setRefugio(refugio);
    }

    // Datos que recibiremos en formato JSON
    public record RutaRequest(
            String nombre,
            Double distanciaKm,
            Integer tiempoEstimadoMinutos,
            Boolean disponible,
            Long zonaRiesgoId,
            Long refugioId
    ) {
    }
}
