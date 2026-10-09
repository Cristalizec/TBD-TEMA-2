
package com.amil.tsunami.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "zonas_riesgo")
public class ZonaRiesgo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la zona
    @Column(nullable = false)
    private String nombre;

    // Ubicacion de la zona
    private String ubicacion;

    // Nivel de riesgo: ALTO, MEDIO o BAJO
    private String nivelRiesgo;

    // Cantidad estimada de habitantes
    private Integer poblacionEstimada;

    // Constructor vacio
    public ZonaRiesgo() {
    }

    // Constructor con parametros
    public ZonaRiesgo(String nombre, String ubicacion,
                      String nivelRiesgo, Integer poblacionEstimada) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.nivelRiesgo = nivelRiesgo;
        this.poblacionEstimada = poblacionEstimada;
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

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public Integer getPoblacionEstimada() {
        return poblacionEstimada;
    }

    public void setPoblacionEstimada(Integer poblacionEstimada) {
        this.poblacionEstimada = poblacionEstimada;
    }
}
