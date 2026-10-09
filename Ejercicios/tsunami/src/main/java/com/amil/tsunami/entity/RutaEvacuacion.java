
package com.amil.tsunami.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rutas_evacuacion")
public class RutaEvacuacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la ruta
    @Column(nullable = false)
    private String nombre;

    // Distancia de la ruta en kilometros
    private Double distanciaKm;

    // Tiempo estimado de evacuacion en minutos
    private Integer tiempoEstimadoMinutos;

    // Indica si la ruta esta disponible
    private Boolean disponible;

    // Zona de riesgo donde comienza la ruta
    @ManyToOne
    @JoinColumn(name = "zona_riesgo_id")
    private ZonaRiesgo zonaRiesgo;

    // Refugio al que conduce la ruta
    @ManyToOne
    @JoinColumn(name = "refugio_id")
    private Refugio refugio;

    // Constructor vacio
    public RutaEvacuacion() {
    }

    // Constructor con parametros
    public RutaEvacuacion(String nombre, Double distanciaKm,
                          Integer tiempoEstimadoMinutos,
                          Boolean disponible,
                          ZonaRiesgo zonaRiesgo,
                          Refugio refugio) {
        this.nombre = nombre;
        this.distanciaKm = distanciaKm;
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
        this.disponible = disponible;
        this.zonaRiesgo = zonaRiesgo;
        this.refugio = refugio;
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

    public Double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(Double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public Integer getTiempoEstimadoMinutos() {
        return tiempoEstimadoMinutos;
    }

    public void setTiempoEstimadoMinutos(Integer tiempoEstimadoMinutos) {
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public ZonaRiesgo getZonaRiesgo() {
        return zonaRiesgo;
    }

    public void setZonaRiesgo(ZonaRiesgo zonaRiesgo) {
        this.zonaRiesgo = zonaRiesgo;
    }

    public Refugio getRefugio() {
        return refugio;
    }

    public void setRefugio(Refugio refugio) {
        this.refugio = refugio;
    }
}
