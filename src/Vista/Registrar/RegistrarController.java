/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Registrar;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import Vista.Administrador.Registrar.RegistrarAdminController;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class RegistrarController implements Initializable {

    @FXML
    private AnchorPane Ventana;
    @FXML
    private Button aceptarBT;
    @FXML
    private AnchorPane Ventana1;
    @FXML
    private TextField nickTF;
    @FXML
    private TextField contraPF;
    @FXML
    private TextField nombreTF;
    @FXML
    private TextField apellidosTF;
    @FXML
    private TextField dniTF;
    @FXML
    private TextField telefonoTF;
    @FXML
    private TextField direccionTF;
    @FXML
    private TextField emailTF;
    @FXML
    private JFXPasswordField repContraPF;
    @FXML
    private JFXDatePicker fecNacTF;
    @FXML
    private RadioButton clienteRB;
    @FXML
    private ToggleGroup rolUsuRB;
    @FXML
    private Button salirBT;
    @FXML
    private JFXTextField rolTF;

    private usuariosDAO usuarioDAO;
    private Notificacion not;
    @FXML
    private Pane panePerfil;
    @FXML
    private GridPane gridpane;
    @FXML
    private ImageView avatarIV;
    private String foto="avatar.png";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aceptarBT.getStyleClass().add("botonAceptarRegistro");
        salirBT.getStyleClass().add("botonEliminar");
        fecNacTF.getStyleClass().add("calendarioRegistrar");

        Image img = new Image("Imagenes/fondoRegistrarse.jpg");
        ImageView imagev = new ImageView(img);

        Ventana1.getChildren().add(imagev);
        imagev.setFitHeight(730);
        imagev.setFitWidth(1300);
        panePerfil.getStyleClass().add("panePerfil");
        panePerfil.toFront();
        gridpane.setOpacity(1);
        avatarIV.setOnMouseClicked(event -> cargarfoto());

    }

    @FXML
    private void registrar(ActionEvent event) {
        String nick = nickTF.getText();
        String contrasena = contraPF.getText();
        String ContrasenaCopia = repContraPF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String DNI = dniTF.getText();
        LocalDate fecNac = fecNacTF.getValue();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();  
        String foto = avatarIV.getImage().toString();
        //Control de nulos//
        if (nick==null) {
          nickTF.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");
        } else {
            if (contrasena==null) {
               contraPF.setStyle("-fx-text-fill: white;"); 
            }else if (repContraPF==null){
                repContraPF.setStyle("-fx-background-color: #cf1020");
            }
            
        }
        
        if (contrasena.equals(ContrasenaCopia)) {
            //crear usuario//
            Usuario usuario = new Usuario(DNI, nombre, apellidos, contrasena, direccion, telefono, email, nick, fecNac, foto);

            try {
                //insertar usuario en BD//
                boolean registrado = usuarioDAO.insertarUsuario(usuario);
            } catch (SQLException ex) {
                not.alert("Error", "Hay un error de SQL");
            }

            // aparte de lo que haga con los datos tiene que cerrarse la ventana
            //////// cerrar ventana ////
            Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();   //Identificamos la ventana (Stage) 
            stage.close();
            //////// fin cerrar ventana ////
        } else {
            System.out.println("contraseña distinta");
            not.error("ERROR contraseña distinta",
                    "en registrar --- RegistrarController");
        }

    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();   //Identificamos la ventana (Stage) 
        stage.close();
    }

    public void setParametros(usuariosDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;

    }

    private void cargarfoto() {
        Stage stage = (Stage) this.avatarIV.getParent().getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File fotoFile = fileChooser.showOpenDialog(stage);

        if (fotoFile != null) {
            Image image = new Image(fotoFile.toURI().toString());
            avatarIV.setImage(image);
        } else {
            ///////////seleccionar archivo
        }
    }
}
