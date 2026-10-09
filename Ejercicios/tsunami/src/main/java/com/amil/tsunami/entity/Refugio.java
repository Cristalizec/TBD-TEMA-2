
package com.amil.tsunami.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "refugios")
public class Refugio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del refugio
    @Column(nullable = false)
    private String nombre;

    // Ubicacion del refugio
    private String ubicacion;

    // Capacidad maxima de personas
    private Integer capacidad;

    // Indica si el refugio esta disponible
    private Boolean disponible;

    // Constructor vacio
    public Refugio() {
    }

    // Constructor con parametros
    public Refugio(String nombre, String ubicacion,
                   Integer capacidad, Boolean disponible) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.disponible = disponible;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}
