
package com.amil.tsunami.controller;

import com.amil.tsunami.entity.ZonaRiesgo;
import com.amil.tsunami.repository.ZonaRiesgoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/zonas-riesgo")
public class ZonaRiesgoController {

    private final ZonaRiesgoRepository repository;

    public ZonaRiesgoController(ZonaRiesgoRepository repository) {
        this.repository = repository;
    }

    // GET - Obtener todas las zonas
    @GetMapping
    public List<ZonaRiesgo> getAll() {
        return repository.findAll();
    }

    // GET - Buscar una zona por ID
    @GetMapping("/{id}")
    public ZonaRiesgo getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Zona de riesgo no encontrada"
                ));
    }

    // POST - Registrar una nueva zona
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZonaRiesgo create(@RequestBody ZonaRiesgo zona) {
        zona.setId(null);
        return repository.save(zona);
    }

    // PUT - Actualizar una zona existente
    @PutMapping("/{id}")
    public ZonaRiesgo update(
            @PathVariable Long id,
            @RequestBody ZonaRiesgo data) {

        ZonaRiesgo zona = getById(id);

        zona.setNombre(data.getNombre());
        zona.setUbicacion(data.getUbicacion());
        zona.setNivelRiesgo(data.getNivelRiesgo());
        zona.setPoblacionEstimada(data.getPoblacionEstimada());

        return repository.save(zona);
    }

    // DELETE - Eliminar una zona
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        ZonaRiesgo zona = getById(id);
        repository.delete(zona);

        return ResponseEntity.noContent().build();
    }
}
