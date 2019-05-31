/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FilesDAO;

import Modelo.Usuario;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 *
 * @author joser
 */
public class UsuarioFiles {

    private Usuario usuario;

    public UsuarioFiles(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioFiles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Guarda la foto (nick.txt) en el Package y c:/tempTurismo/
     *
     * @param fotoFile
     * @param foto
     * @throws IOException
     */
    public void guardarFoto(File fotoFile, String foto) throws IOException {
        if (fotoFile != null) {
            Path Original = Paths.get(fotoFile.toURI());
            Path enPackage = Paths.get("src/imagenes/usuarios/" + foto);
            Path enDisco = Paths.get("C:\\tempTurismo\\" + foto);
            Files.copy(Original.toAbsolutePath(), enPackage.toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Original.toAbsolutePath(), enDisco.toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void cambiarArchivoFoto(String OldArchivo, String NewArchivo) throws IOException {

        Path from = Paths.get(OldArchivo);
        Path to = Paths.get("src/imagenes/usuarios/" + NewArchivo);
        //Files.copy(from.toFile(), to.toFile());
        Files.copy(from.toAbsolutePath(), to.toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);

    }

    /**
     * Asigna el valor del nick al archivo (imagen)
     *
     * @param fotoFile
     * @param nick
     * @return String tipo: nick.ext
     */
    public String darNombreFoto(File fotoFile, String nick) {
        String foto;
        if (fotoFile == null) {
            foto = "avatar.png";
        } else {
            String nombreString = fotoFile.getName();
            String[] extensionStrings = nombreString.split("\\.");
            foto = nick + "." + (extensionStrings[extensionStrings.length - 1]);
        }
        return foto;
    }

    /**
     * Seleccion de una imagen
     *
     * @return File
     */
    public File SelectFoto() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(// Agregar filtros para facilitar la busqueda
                // new FileChooser.ExtensionFilter("All ", "*.*"),
                new FileChooser.ExtensionFilter("Imagen", "*.jpg"),
                new FileChooser.ExtensionFilter("Imagen", "*.png")
        );
        File fotoElegida = fileChooser.showOpenDialog(null);
        return fotoElegida;
    }


    /**
     *Coje la extension de foto y la añade al nick 
     * @param foto
     * @param nick
     * @return tipo nick.ext
     */
    public String cambiarNombreFoto(String foto, String nick) {
        String[] extensionStrings = foto.split("\\.");
        String newNombre = nick + "." + (extensionStrings[extensionStrings.length - 1]);
        return newNombre;
    }

}
