/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Registrar;

import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class RegistrarController implements Initializable {

    private usuariosDAO usuarioDAO;
    private Notificacion not;
    public String foto = "avatar.png";
    private String aviso = " -fx-text-fill:grey";
    private File fotoFile;

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
    @FXML
    private Pane panePerfil;
    @FXML
    private GridPane gridpane;
    @FXML
    private ImageView avatarIV;
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
    private Usuario usuario;

    //INICIO--------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
        usuario=new Usuario();

        styleInicio();

        styleRellenarCamposVacios();

    }

    private void styleInicio() {

        //Imagen fondo
        Image img = new Image("Imagenes/fondoRegistrarse.jpg");
        ImageView imagev = new ImageView(img);
        Ventana1.getChildren().add(imagev);
        imagev.setFitHeight(730);
        imagev.setFitWidth(1300);

        //Panes
        panePerfil.toFront();
        gridpane.setOpacity(1);

        //Estilos
        aceptarBT.getStyleClass().add("botonAceptarRegistro");
        salirBT.getStyleClass().add("botonEliminar");
        fecNacTF.getStyleClass().add("calendarioRegistrar");
        panePerfil.getStyleClass().add("panePerfil");
    }

    private void styleRellenarCamposVacios() {

        avatarIV.setOnMouseClicked(event -> cargarfoto());
        nickTF.setOnMouseClicked(event -> usuL.setStyle(aviso));
        contraPF.setOnMouseClicked(event -> ContraL.setStyle(aviso));
        repContraPF.setOnMouseClicked(event -> repContraL.setStyle(aviso));
        nombreTF.setOnMouseClicked(event -> nombreL.setStyle(aviso));
        apellidosTF.setOnMouseClicked(event -> apelliL.setStyle(aviso));
        telefonoTF.setOnMouseClicked(event -> telefL.setStyle(aviso));
        emailTF.setOnMouseClicked(event -> emailL.setStyle(aviso));
        fecNacTF.setOnMouseClicked(event -> fecNacL.setStyle(aviso));
    }

    private void cargarfoto() {
        Stage stage = (Stage) this.avatarIV.getParent().getScene().getWindow();
        fotoFile=usuario.cargarfoto();
  
        if (fotoFile != null) {
            Image image = new Image(fotoFile.toURI().toString());
            avatarIV.setImage(image);
        }
        
    }

    public void setParametros(usuariosDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    //ACCIONES------------------------------------------------------------------
    @FXML
    private void registrar(ActionEvent event) {
        //Boton Registrar
        boolean registrado = false;  
        String nick = nickTF.getText();
        String contrasena =usuario.encriptar(contraPF.getText());////cambiado
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String dni = dniTF.getText();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();

        LocalDate fecNac = fecNacTF.getValue();
 
        String foto=usuario.fotoToString();
      
        //avatarIV.getImage().getUrl();

        //Control de entradas nulas//
        boolean noInsertar = camposVacios();
        //crear usuario//
        if (!noInsertar) {
//            usuario = new Usuario(dni, nombre, apellidos, contrasena, direccion, telefono, email, nick, fecNac, foto);
            usuario.setNick(nick);
            usuario.setPassword(contrasena);
            
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setDni(dni);
            usuario.setTelefono(telefono);
            usuario.getDireccion();
            usuario.getEmail();
            usuario.setFecNac(fecNac);
            usuario.setFoto(foto);
            usuario.setFotoFile(fotoFile);
            try {
                //insertar usuario en BD//
                registrado = usuarioDAO.insertarUsuario(usuario);
            } catch (SQLException ex) {
                not.alert("Error", "Hay un error de SQL");
            }

            if (registrado) {
                usuario.guardarFoto();
                //Cerrar ventana 
                Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();//Identificamos la ventana (Stage) 
                stage.close();
                //Fin cerrar ventana

                //FileWriter fw=new FileWriter("src/imagenes/usuarios/".concat(nick));
                //informar esta registrado//
                not.alert("Registrarse", "Ya te has registrado");
            } else {
                not.confirm("Registrarse", "No te has registrado");
            }
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        //Boton salir
        //Identificamos la ventana (Stage) 
        Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();
        stage.close();
    }

    @FXML
    private void comprobar(MouseEvent event) {
        camposVacios();
    }

    private boolean camposVacios() {   //   devuelve true si hay algun campo "necesario" nulo 

        boolean vacio = false;

        String estilo = null;
        String aviso = " -fx-text-fill: rgb(238,32,32)"; //rojo
        String nick = nickTF.getText();
        String contrasena = contraPF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String dni = dniTF.getText();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();
        String foto = avatarIV.getImage().toString();

        LocalDate fecNac = fecNacTF.getValue();

        //Comprobar si los campos estan vacios y pone el label en rojo
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
        if (fecNac == null) {
            vacio = true;
            fecNacL.setStyle(aviso);
        }
        if ("".equals(telefono)) {
            vacio = true;
            telefL.setStyle(aviso);
        }
        if ("".equals(email)) {
            vacio = true;
            emailL.setStyle(aviso);
        }
        return vacio;
        //Lo  comentado xq permite null en BD
        //if ("".equals(dni)) {
        //  vacio = true;
        //  dniL.setStyle(aviso);
        //}
        //if ("".equals(telefono)) {
        //  vacio = true;
        //  telefL.setStyle(aviso);
        //}       
        //if ("".equals(direccion)) {
        //  vacio = true;
        //  DirecL.setStyle(aviso);
        //}
    }

//    public void guardarFoto() {
//
//    }
}
