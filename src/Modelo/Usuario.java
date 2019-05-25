package Modelo;

import java.io.File;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static java.nio.file.attribute.FileTime.from;
import static java.nio.file.attribute.FileTime.from;
import java.time.LocalDate;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private File fotoFile;

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

    public File getFotoFile() {
        return fotoFile;
    }

    public void setFotoFile(File fotoFile) {
        this.fotoFile = fotoFile;
    }

    public File cargarfoto() {
        FileChooser fileChooser = new FileChooser();
        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                // new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File fotoElegida = fileChooser.showOpenDialog(null);
//        if (fotoFile != null) {
//            Image image = new Image(fotoFile.toURI().toString());
//            caraIV.setImage(image);
//        }
        return fotoElegida;
    }

    public void guardarFoto() throws IOException {
        if (fotoFile != null) {
            Path from = Paths.get(fotoFile.toURI());
            Path to = Paths.get("src/imagenes/usuarios/" + foto);
            //Files.copy(from.toFile(), to.toFile());
            Files.copy(from.toAbsolutePath(), to.toAbsolutePath(),StandardCopyOption.REPLACE_EXISTING);            
        }
    }

    public String fotoToString() {
        if (fotoFile == null) {
            foto = "avatar.png";
        } else {
            String nombreString = fotoFile.getName();
            String[] extensionStrings = nombreString.split("\\.");
            foto = nick + "." + (extensionStrings[extensionStrings.length - 1]);
        }
        return foto;
    }

    private enum rol {
        CLIENTE, ADMINISTRADOR
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

        this.password = encriptar(password);
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
