
package Modelo;
////id int(11) AI PK 
////nombre varchar(45) 
////duracion int(5) 
////precio decimal(6,2) 
////Descripcion longtext 
////foto varchar(45) 
////fotoDescripcion varchar(45) 
////Clase varchar(45)
public class Transporte {
    private int id;
    private String nombre;
    private String duracion;
    private double precio;
    private String descripcion;
    private String foto;
    private String fotoDescripcion;
    private String tipo;

    public Transporte() {
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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoDescripcion() {
        return fotoDescripcion;
    }

    public void setFotoDescripcion(String fotoDescripcion) {
        this.fotoDescripcion = fotoDescripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

   
    
    
    
    
    
    
}
