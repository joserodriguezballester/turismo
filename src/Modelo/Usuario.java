
package Modelo;

public class Usuario implements Comparable<Usuario> {
    
    private int id;
    private String dni;
    private String nombre;
    private String apellidos;
    private String password;
    private String direccion;
    private String telefono;
    private String email;

    
    private enum roll {USUARIO,EMPLEADO}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + 
                            ", dni=" + dni + 
                            ", nombre=" + nombre +
                            ", apellidos=" + apellidos +
                            ", password=" + password +
                            ", direccion=" + direccion + 
                            ", telefono=" + telefono + 
                            ", email=" + email + '}';
    }
      
    @Override
    public int compareTo(Usuario us) {
        int compara;
        if(this.id < us.getId()){
            compara = -1;
        }
        else if(this.id > us.getId()){
            compara = 1;
        }
        else {
            compara = 0;
        }
        
        return compara;
    }
    
}
