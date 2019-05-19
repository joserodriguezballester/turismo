package Modelo;

import java.util.HashMap;
import java.util.Map;

public class Actividad {

    private int id;
    private String nombre;
    private double precio;
    private String horario;
    private String descripcion;
    private String url;
    private String direccion;
    private String telefono;
    private String foto;
    private int idsubTipo;

    private final Map<String, String> mapFotos = new HashMap<>();

    public Actividad(int id, String nombre, double precio, String horario, String descripcion, String url, String direccion, String telefono, String foto, int idsubTipo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.horario = horario;
        this.descripcion = descripcion;
        this.url = url;
        this.direccion = direccion;
        this.telefono = telefono;
        this.foto = foto;
        this.idsubTipo = idsubTipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getIdsubTipo() {
        return idsubTipo;
    }

    public void setIdsubTipo(int idsubTipo) {
        this.idsubTipo = idsubTipo;
    }
    
    
    @Override
    public String toString() {
        return id + "";  
                
    }
//
//    @Override
//    public String toString() {
//        return nombre;  
//                
//    }

//    @Override
//    public String toString() {
//        return "Actividad{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", horario=" + horario + ", descripcion=" + descripcion + ", url=" + url + ", direccion=" + direccion + ", telefono=" + telefono + ", foto=" + foto + ", idsubTipo=" + idsubTipo + ", mapFotos=" + mapFotos + '}';
//    }
//    
    

}
