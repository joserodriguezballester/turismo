/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author 20857464y
 */
public class ExperienciaActividad {

    private int id;
    private List<Actividad> listaActividades;
    private int numPlazas;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private LocalTime duracion;
    private double precio;

    public ExperienciaActividad(int id, List<Actividad> listaActividades, int numPlazas, LocalDateTime fechaInicio, LocalDateTime fechaFinal, LocalTime duracion, double precio) {
        this.id = id;
        this.listaActividades = listaActividades;
        this.numPlazas = numPlazas;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.duracion = duracion;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Actividad> getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(List<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
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

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
