
package Modelo;

import java.util.HashMap;
import java.util.Map;

public class Actividad implements Comparable<Actividad> {
    
    private int id;
    private String nombre;
    private double precio;
    private String horario;
    private String descripcion;
    private String url;
    private String direccion;
    private String telefono;
    private String foto;
    private int idSubtipo;
    
    private final Map<String, String> mapFotos = new HashMap<>();
    
    
    
    public Actividad(){
        
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

    public int getIdSubtipo() {
        return idSubtipo;
    }

    public void setIdSubtipo(int idSubtipo) {
        this.idSubtipo = idSubtipo;
    }
    
// ----------------------- CARGAR MAPFOTOS ----------------------------------
    
    private void cargarMapFotos(){
        
        mapFotos.put("logotipo", "/Imagenes/logotipo.jpg");
        mapFotos.put("rest1", "/Imagenes/rest1.png");
        mapFotos.put("rest2", "/Imagenes/rest2.png");
        mapFotos.put("rest3", "/Imagenes/rest3.png");
        mapFotos.put("rest4", "/Imagenes/rest4.png");
        mapFotos.put("rest5", "/Imagenes/rest5.png");
        mapFotos.put("rest6", "/Imagenes/rest6.png");
        mapFotos.put("rest7", "/Imagenes/rest7.png");
        mapFotos.put("rest8", "/Imagenes/rest8.png");
        mapFotos.put("rest9", "/Imagenes/rest9.png");
        mapFotos.put("rest10", "/Imagenes/rest10.png");
        mapFotos.put("rest11", "/Imagenes/rest11.png");
        mapFotos.put("rest12", "/Imagenes/rest12.png");
        mapFotos.put("rest13", "/Imagenes/rest13.png");
        mapFotos.put("rest14", "/Imagenes/rest14.png");
        mapFotos.put("sitio3", "/Imagenes/sitio3.png");
        mapFotos.put("sitio4", "/Imagenes/sitio4.png");
        mapFotos.put("sitio5", "/Imagenes/sitio5.png");
        mapFotos.put("sitio6", "/Imagenes/sitio6.png");
        mapFotos.put("sitio7", "/Imagenes/sitio7.png");
        mapFotos.put("sitio8", "/Imagenes/sitio8.png");
        mapFotos.put("sitio9", "/Imagenes/sitio9.png");
        mapFotos.put("sitio10", "/Imagenes/sitio10.png");
        mapFotos.put("trans1", "/Imagenes/trans1.png");
        mapFotos.put("trans2", "/Imagenes/trans2.png");
        mapFotos.put("trans3", "/Imagenes/trans4.png");
        mapFotos.put("trans4", "/Imagenes/trans4.png");
        mapFotos.put("trans5", "/Imagenes/trans5.png");
    }
    
    public String dameRutaFoto(String foto){
        cargarMapFotos();
        String ruta;
        
        if(mapFotos != null){
            ruta = mapFotos.get(foto);
            if(ruta.equals("")){
                ruta = "La foto seleccionada no existe en la lista";
            }
        }
        else{
            ruta = "La lista de fotos esta vacia";
        }
        
        return ruta;
    }

// -----------------------------               -----------------------------
    
    
    @Override
    public String toString() {
        return "Actividad{"+", id=" + id + 
                            ", nombre=" + nombre + 
                            ", precio=" + precio + 
                            ", horario=" + horario + 
                            ", descripcion=" + descripcion + 
                            ", url=" + url + 
                            ", direccion=" + direccion + 
                            ", telefono=" + telefono + 
                            ", foto=" + foto + 
                            ", idSubtipo=" + idSubtipo + '}';
    }

    @Override
    public int compareTo(Actividad o) {
        int compara;
        if(this.id < o.getId()){
            compara = -1;
        }
        else if(this.id > o.getId()){
            compara = 1;
        }
        else{
            compara = 0;
        }
        return compara;
    }
  
}
