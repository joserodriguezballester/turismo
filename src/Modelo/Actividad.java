
package Modelo;

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
