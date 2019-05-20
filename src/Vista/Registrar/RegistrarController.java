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
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
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
    private String foto = "avatar.png";
    @FXML
    private Label nombreL;
    @FXML
    private Label ContraL;
    @FXML
    private Label apelliL;
    @FXML
    private Label DirecL;
    @FXML
    private Label telefL;
    @FXML
    private Label dniL;
    @FXML
    private Label emailL;
    @FXML
    private Label usuL;
    @FXML
    private Label repContraL;
    @FXML
    private Label fecNacL;
    private String aviso = " -fx-text-fill:grey";
    File fotoFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
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
        nickTF.setOnMouseClicked(event -> usuL.setStyle(aviso));
        contraPF.setOnMouseClicked(event -> ContraL.setStyle(aviso));
        nombreTF.setOnMouseClicked(event -> nombreL.setStyle(aviso));
        apellidosTF.setOnMouseClicked(event -> apelliL.setStyle(aviso));
        telefonoTF.setOnMouseClicked(event -> telefL.setStyle(aviso));
        emailTF.setOnMouseClicked(event -> emailL.setStyle(aviso));
        fecNacTF.setOnMouseClicked(event -> fecNacL.setStyle(aviso));

    }

    @FXML
    private void registrar(ActionEvent event) throws SQLException {
        boolean registrado = false;
        String nick = nickTF.getText();
        String contrasena = contraPF.getText();
//        String contraCopia = repContraPF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String dni = dniTF.getText();
        LocalDate fecNac = fecNacTF.getValue();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();
        if (fotoFile == null) {
            foto = "avatar.png";
        } else {

            foto =nick;
        }
//       avatarIV.getImage().getUrl();

        //Control de entradas nulas//
        boolean noInsertar = camposVacios();
        //crear usuario//
        if (!noInsertar) {
            Usuario usuario = new Usuario(dni, nombre, apellidos, contrasena, direccion, telefono, email, nick, fecNac, foto);
            try {
                //insertar usuario en BD//

                registrado = usuarioDAO.insertarUsuario(usuario);
                if (registrado) {

                usuarioDAO.insertarUsuario(usuario);
                
//                String titulo = "Bienvenido " + usuario.getNick();
//                String mensaje = "Disfruta de tu viaje a Amsterdam";
//                not.ventanaInfo(titulo, mensaje);
//            }
//            }catch (SQLException ex) {
//              not.alert("Error","Hay un error de SQL");
//            }


                    //////// cerrar ventana ////
                    Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();   //Identificamos la ventana (Stage) 
                    stage.close();
                    //////// fin cerrar ventana //// 
                     FileWriter fw=new FileWriter("src/imagenes/usuarios/".concat(nick));
                    //informar esta registrado//
                    not.alert("Registrarse", "Ya te has registrado");
                } else {
                    not.confirm("Registrarse", "No te has registrado");
                }

            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1062) {
                    not.alert("Registrase", "Usuario Repetido");
                    usuL.setStyle(aviso);
                } else {
                    not.alert("SQL", "Error en la BD");
                }
            } catch (IOException ex) {
               not.alert("Imagen","Tu imagen no ha podido ser guardada");
            }
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        //Identificamos la ventana (Stage) 
        Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();
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
                //                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        fotoFile = fileChooser.showOpenDialog(stage);
        if (fotoFile != null) {
            Image image = new Image(fotoFile.toURI().toString());
            avatarIV.setImage(image);
           
               
            
           
        }
        
    }

    private boolean camposVacios() {   //   devuelve false si no hay ningun campo "necesario" nulo 

        boolean vacio = false;
        String estilo = null;
        String aviso = " -fx-text-fill: rgb(238,32,32)"; //rojo
        String nick = nickTF.getText();
        String contrasena = contraPF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String dni = dniTF.getText();
        LocalDate fecNac = fecNacTF.getValue();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();
        String foto = avatarIV.getImage().toString();
/////comprobar si los campos estan vacios y pone el label en rojo
        if ("".equals(nick)) {
            vacio = true;
            usuL.setStyle(aviso);
        }
        if ("".equals(contrasena)) {
            vacio = true;
            ContraL.setStyle(aviso);
        }
        if ("".equals(nombre)) {
            vacio = true;
            nombreL.setStyle(aviso);
        }
        if ("".equals(apellidos)) {
            vacio = true;
            apelliL.setStyle(aviso);
        }
        ////     lo  comentado xq permite null en BD
//        if ("".equals(dni)) {
//            vacio = true;
//            dniL.setStyle(aviso);
//        }

        if (fecNac == null) {
            vacio = true;
            fecNacL.setStyle(aviso);
        }
        if ("".equals(telefono)) {
            vacio = true;
            telefL.setStyle(aviso);
        }
//        if ("".equals(telefono)) {
//            vacio = true;
//            telefL.setStyle(aviso);
//        }       
//        if ("".equals(direccion)) {
//            vacio = true;
//            DirecL.setStyle(aviso);
//        }
        if ("".equals(email)) {
            vacio = true;
            emailL.setStyle(aviso);
        }
        return vacio;
    }

    @FXML
    private void comprobar(MouseEvent event) {
        camposVacios();
    }

}
