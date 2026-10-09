
package com.amil.tsunami.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evacuaciones")
public class Evacuacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha y hora de la evacuacion
    private LocalDateTime fechaHora;

    // Cantidad total de personas que deben evacuar
    private Integer cantidadPersonas;

    // Cantidad de personas evacuadas
    private Integer personasEvacuadas;

    // Estado: PENDIENTE, EN_PROCESO o COMPLETADA
    private String estado;

    // Zona de riesgo donde ocurre la evacuacion
    @ManyToOne
    @JoinColumn(name = "zona_riesgo_id")
    private ZonaRiesgo zonaRiesgo;

    // Ruta utilizada durante la evacuacion
    @ManyToOne
    @JoinColumn(name = "ruta_evacuacion_id")
    private RutaEvacuacion rutaEvacuacion;

    // Constructor vacio
    public Evacuacion() {
    }

    // Constructor con parametros
    public Evacuacion(Integer cantidadPersonas,
                      Integer personasEvacuadas,
                      String estado,
                      ZonaRiesgo zonaRiesgo,
                      RutaEvacuacion rutaEvacuacion) {
        this.cantidadPersonas = cantidadPersonas;
        this.personasEvacuadas = personasEvacuadas;
        this.estado = estado;
        this.zonaRiesgo = zonaRiesgo;
        this.rutaEvacuacion = rutaEvacuacion;
    }

    // Asignar fecha automaticamente al registrar
    @PrePersist
    public void asignarFecha() {
        if (fechaHora == null) {
            fechaHora = LocalDateTime.now();
        }
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Integer getPersonasEvacuadas() {
        return personasEvacuadas;
    }

    public void setPersonasEvacuadas(Integer personasEvacuadas) {
        this.personasEvacuadas = personasEvacuadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ZonaRiesgo getZonaRiesgo() {
        return zonaRiesgo;
    }

    public void setZonaRiesgo(ZonaRiesgo zonaRiesgo) {
        this.zonaRiesgo = zonaRiesgo;
    }

    public RutaEvacuacion getRutaEvacuacion() {
        return rutaEvacuacion;
    }

    public void setRutaEvacuacion(RutaEvacuacion rutaEvacuacion) {
        this.rutaEvacuacion = rutaEvacuacion;
    }
}
