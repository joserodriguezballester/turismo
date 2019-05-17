package Modelo;

import java.time.LocalDate;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;

public class Usuario implements Comparable<Usuario> {

    private int id;
    private String dni;
    private String nombre;
    private String apellidos;
    private String password;
    private String direccion;
    private String telefono;
    private String email;
    private String nick;
    private LocalDate fecNac;
    private rol perfil;
    private String foto;

    public Usuario(String DNI, String nombre, String apellidos, String contrasena, String direccion, String telefono, String email, String nick, LocalDate fecNac, String foto) {
        this.dni = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        String passwordEncriptado = encriptar(contrasena);
        this.password = passwordEncriptado;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.nick = nick;
        this.fecNac = fecNac;
        this.foto = foto;
    }

    private enum rol {
        CLIENTE, ADMINISTRADOR, EMPRESA
    }

    public rol getPerfil() {
        return perfil;

    }

    public String getPerfilString() {
        return perfil.toString();

    }

    public void setPerfil(String perfil) {

        this.perfil = rol.valueOf(perfil);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public LocalDate getFecNac() {
        return fecNac;
    }

    public void setFecNac(LocalDate fecNac) {
        this.fecNac = fecNac;
    }

    public Usuario() {
    }

//    public Usuario(String DNI, String nombre, String apellidos, String contrasena, String direccion, String telefono, String email, String nick, LocalDate fecNac) {
//        this.dni = DNI;
//        this.nombre = nombre;
//        this.apellidos = apellidos;
//        String passwordEncriptado = encriptar(contrasena);
//        this.password = passwordEncriptado;
//        this.direccion = direccion;
//        this.telefono = telefono;
//        this.email = email;
//        this.nick = nick;
//        this.fecNac = fecNac;
//    }

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id
                + ", dni=" + dni
                + ", nombre=" + nombre
                + ", apellidos=" + apellidos
                + ", password=" + password
                + ", direccion=" + direccion
                + ", telefono=" + telefono
                + ", email=" + email + '}';
    }

    @Override
    public int compareTo(Usuario us) {
        int compara;
        if (this.id < us.getId()) {
            compara = -1;
        } else if (this.id > us.getId()) {
            compara = 1;
        } else {
            compara = 0;
        }

        return compara;
    }

    public String encriptar(String contrasena) {
        PasswordEncryptor encryptor = new BasicPasswordEncryptor();
        String contrasenaEncriptada = encryptor.encryptPassword(contrasena);
        return contrasenaEncriptada;
    }
}
