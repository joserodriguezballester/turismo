/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class UsuarioExperiencia {

    private Usuario usuario;
    private Experiencia experiencia;
    private LocalDate fechaContratacion;

    public UsuarioExperiencia(Usuario usuario, Experiencia experiencia, LocalDate fechaContratacion) {
        this.usuario = usuario;
        this.experiencia = experiencia;
        this.fechaContratacion = fechaContratacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Experiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    @Override
    public String toString() {
        return "usuario=" + usuario + ", experiencia=" + experiencia + ", fechaContratacion=" + fechaContratacion;
    }

}
