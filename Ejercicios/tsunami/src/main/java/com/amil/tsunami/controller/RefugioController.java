
package com.amil.tsunami.controller;

import com.amil.tsunami.entity.Refugio;
import com.amil.tsunami.repository.RefugioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/refugios")
public class RefugioController {

    private final RefugioRepository repository;

    public RefugioController(RefugioRepository repository) {
        this.repository = repository;
    }

    // GET - Obtener todos los refugios
    @GetMapping
    public List<Refugio> getAll() {
        return repository.findAll();
    }

    // GET - Buscar un refugio por ID
    @GetMapping("/{id}")
    public Refugio getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Refugio no encontrado"
                ));
    }

    // POST - Registrar un nuevo refugio
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Refugio create(@RequestBody Refugio refugio) {
        refugio.setId(null);
        return repository.save(refugio);
    }

    // PUT - Actualizar un refugio
    @PutMapping("/{id}")
    public Refugio update(
            @PathVariable Long id,
            @RequestBody Refugio data) {

        Refugio refugio = getById(id);

        refugio.setNombre(data.getNombre());
        refugio.setUbicacion(data.getUbicacion());
        refugio.setCapacidad(data.getCapacidad());
        refugio.setDisponible(data.getDisponible());

        return repository.save(refugio);
    }

    // DELETE - Eliminar un refugio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        Refugio refugio = getById(id);

        repository.delete(refugio);

        return ResponseEntity.noContent().build();
    }
}
