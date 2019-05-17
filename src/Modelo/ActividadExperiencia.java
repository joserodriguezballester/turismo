/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.Duration;
import java.time.LocalDateTime;


/**
 *
 * @author 20857464y
 */
public class ActividadExperiencia {

    private int orden;
    private int idExperiencia;
    private Actividad actividad;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private Duration duracion;
    private double precio;
    private int numPlazas;

    public ActividadExperiencia(int orden, int idExperiencia, Actividad actividad, LocalDateTime fechaInicio, LocalDateTime fechaFinal, double precio, int numPlazas) {
        this.orden = orden;
        this.idExperiencia = idExperiencia;
        this.actividad = actividad;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.precio = precio;
        this.numPlazas = numPlazas;
        this.duracion = Duration.between(fechaInicio, fechaFinal);
    }

    public Duration getDuracion() {
        return duracion;
    }

    public void setDuracion(Duration duracion) {
        this.duracion = duracion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public int getIdExperiencia() {
        return idExperiencia;
    }

    public void setIdExperiencia(int idExperiencia) {
        this.idExperiencia = idExperiencia;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    @Override
    public String toString() {
        return orden + " - " + actividad;
    }

}
