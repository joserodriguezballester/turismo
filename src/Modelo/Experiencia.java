 package Modelo;

import java.time.LocalDate;
import java.util.List;

public class Experiencia {

    private int id, idUsuario;
    private String nombre;
    private String descripcion;
    private LocalDate fechaTopeValidez;
    private String foto;
    private List<ActividadExperiencia> listaActividades;

    public Experiencia(int id,int idUsuario, String nombre, String descripcion, LocalDate fechaTopeValidez, String foto, List<ActividadExperiencia> listaActividades) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaTopeValidez = fechaTopeValidez;
        this.foto = foto;
        this.listaActividades = listaActividades;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaTopeValidez() {
        return fechaTopeValidez;
    }

    public void setFechaTopeValidez(LocalDate fechaTopeValidez) {
        this.fechaTopeValidez = fechaTopeValidez;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<ActividadExperiencia> getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(List<ActividadExperiencia> listaActividades) {
        this.listaActividades = listaActividades;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
