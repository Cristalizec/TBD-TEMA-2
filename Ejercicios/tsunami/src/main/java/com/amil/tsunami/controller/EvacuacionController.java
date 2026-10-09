
package com.amil.tsunami.controller;

import com.amil.tsunami.entity.Evacuacion;
import com.amil.tsunami.entity.ZonaRiesgo;
import com.amil.tsunami.entity.RutaEvacuacion;

import com.amil.tsunami.repository.EvacuacionRepository;
import com.amil.tsunami.repository.ZonaRiesgoRepository;
import com.amil.tsunami.repository.RutaEvacuacionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/evacuaciones")
public class EvacuacionController {

    private final EvacuacionRepository evacuacionRepository;
    private final ZonaRiesgoRepository zonaRepository;
    private final RutaEvacuacionRepository rutaRepository;

    // Constructor
    public EvacuacionController(
            EvacuacionRepository evacuacionRepository,
            ZonaRiesgoRepository zonaRepository,
            RutaEvacuacionRepository rutaRepository) {

        this.evacuacionRepository = evacuacionRepository;
        this.zonaRepository = zonaRepository;
        this.rutaRepository = rutaRepository;
    }

    // GET - Obtener todas las evacuaciones
    @GetMapping
    public List<Evacuacion> getAll() {
        return evacuacionRepository.findAll();
    }

    // GET - Buscar evacuacion por ID
    @GetMapping("/{id}")
    public Evacuacion getById(@PathVariable Long id) {

        return evacuacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Evacuacion no encontrada"
                ));
    }

    // POST - Registrar nueva evacuacion
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evacuacion create(
            @RequestBody EvacuacionRequest data) {

        Evacuacion evacuacion = new Evacuacion();

        asignarDatos(evacuacion, data);

        return evacuacionRepository.save(evacuacion);
    }

    // PUT - Actualizar evacuacion
    @PutMapping("/{id}")
    public Evacuacion update(
            @PathVariable Long id,
            @RequestBody EvacuacionRequest data) {

        Evacuacion evacuacion = getById(id);

        asignarDatos(evacuacion, data);

        return evacuacionRepository.save(evacuacion);
    }

    // DELETE - Eliminar evacuacion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        Evacuacion evacuacion = getById(id);

        evacuacionRepository.delete(evacuacion);

        return ResponseEntity.noContent().build();
    }

    // Validar y asignar los datos
    private void asignarDatos(
            Evacuacion evacuacion,
            EvacuacionRequest data) {

        if (data.cantidadPersonas() == null ||
                data.cantidadPersonas() <= 0 ||
                data.personasEvacuadas() == null ||
                data.personasEvacuadas() < 0 ||
                data.personasEvacuadas() > data.cantidadPersonas() ||
                data.estado() == null ||
                data.zonaRiesgoId() == null ||
                data.rutaEvacuacionId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Datos de evacuacion invalidos"
            );
        }

        // Verificar el estado de la evacuacion
        Set<String> estados = Set.of(
                "PENDIENTE",
                "EN_PROCESO",
                "COMPLETADA"
        );

        if (!estados.contains(data.estado())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Estado de evacuacion no valido"
            );
        }

        // Buscar la zona de riesgo
        ZonaRiesgo zona = zonaRepository
                .findById(data.zonaRiesgoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Zona de riesgo no encontrada"
                ));

        // Buscar la ruta de evacuacion
        RutaEvacuacion ruta = rutaRepository
                .findById(data.rutaEvacuacionId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ruta de evacuacion no encontrada"
                ));

        // Comprobar que la ruta corresponde a la zona
        if (ruta.getZonaRiesgo() == null ||
                !zona.getId().equals(
                        ruta.getZonaRiesgo().getId())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La ruta no corresponde a la zona de riesgo"
            );
        }

        // Guardar los datos de la evacuacion
        evacuacion.setCantidadPersonas(
                data.cantidadPersonas()
        );

        evacuacion.setPersonasEvacuadas(
                data.personasEvacuadas()
        );

        evacuacion.setEstado(data.estado());
        evacuacion.setZonaRiesgo(zona);
        evacuacion.setRutaEvacuacion(ruta);
    }

    // Datos recibidos desde JSON
    public record EvacuacionRequest(
            Integer cantidadPersonas,
            Integer personasEvacuadas,
            String estado,
            Long zonaRiesgoId,
            Long rutaEvacuacionId
    ) {
    }
}
