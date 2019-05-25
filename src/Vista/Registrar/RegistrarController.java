package Vista.Registrar;

import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
 * @author Grupo4
 */
public class RegistrarController implements Initializable {

    private usuariosDAO usuarioDAO;
    private Notificacion not;
    public String foto = "avatar.png";
    private String aviso = " -fx-text-fill:black";
    private String mal = " -fx-text-fill:red";

    String nick, contrasena, nombre, apellidos, dni, telefono, direccion, email;
    LocalDate fecNac;
    File fotoFile;

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
    private Label repContraL;
    @FXML
    private Label fecNacL;
    @FXML
    private Label contraL;
    @FXML
    private Label nickL;
    private Usuario usuario;

    //INICIO--------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
        usuario = new Usuario();

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
        nickTF.setOnMouseClicked(event -> nickL.setStyle(aviso));
        contraPF.setOnMouseClicked(event -> contraL.setStyle(aviso));
        nombreTF.setOnMouseClicked(event -> nombreL.setStyle(aviso));
        apellidosTF.setOnMouseClicked(event -> apelliL.setStyle(aviso));
        telefonoTF.setOnMouseClicked(event -> telefL.setStyle(aviso));
        emailTF.setOnMouseClicked(event -> emailL.setStyle(aviso));
        fecNacTF.setOnMouseClicked(event -> fecNacL.setStyle(aviso));
    }

    private void cargarfoto() {
        Stage stage = (Stage) this.avatarIV.getParent().getScene().getWindow();
        fotoFile = usuario.cargarfoto();

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
    private void registrar(ActionEvent event) throws SQLException, IOException {
        //Boton Registrar
        boolean registrado = false;

        if (comprobacionCampos() == true) {

            Path to;
            Path from;

            LocalDate fecNac = fecNacTF.getValue();

            String foto = usuario.fotoToString();

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
                //avatarIV.getImage().getUrl();

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
    }

    @FXML
    private void comprobar(MouseEvent event) {
        camposVacios();
    }

    @FXML
    private void salir(ActionEvent event) {
        //Boton salir
        //Identificamos la ventana (Stage) 
        Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();
        stage.close();
    }
    //CONTROL DE DATOS----------------------------------------------------------

    private boolean comprobacionCampos() throws SQLException {
        recogerDatos();
        boolean todoCorrecto = true;
        String correcto = " -fx-text-fill: rgb(56, 175, 88)"; //Verde

        if ((usuarioDAO.clienteExiste(nick) == true) || nickTF.getText().equals("")) {
            todoCorrecto = false;
            nickTF.setText("");
            if (nickTF.getText().equals("")) {
                nickTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
            } else {
                nickTF.setPromptText("Ese usuario ya existe");
            }
            nombreL.setStyle(mal);
        } else {
            nickL.setStyle(correcto);
        }

        if (comprobardni() == false) {
            todoCorrecto = false;
            dniTF.setText("");
            dniTF.setPromptText("Intoduce un DNI valido");
            dniL.setStyle(mal);
        } else {
            dniL.setStyle(correcto);
        }
        if (comprobarTelefono() == false) {
            todoCorrecto = false;
            telefonoTF.setText("");
            telefonoTF.setPromptText("Intoduce un Número valido");
            telefL.setStyle(mal);
        } else {
            telefL.setStyle(correcto);
        }
        if (comprobarEmail() == false) {
            todoCorrecto = false;
            emailTF.setText("");
            emailTF.setPromptText("Intoduce un Email valido");
            emailL.setStyle(mal);
        } else {
            emailL.setStyle(correcto);
        }
        return todoCorrecto;
    }

    public boolean comprobardni() {
        boolean dniCorrecto;
        if (dni.matches("^\\d?\\d{7}(-|\\s)?[A-Za-z]$") || dni.equals("")) {
            dniCorrecto = true;
        } else {
            dniCorrecto = false;
        }
        return dniCorrecto;
    }

    public boolean comprobarTelefono() {
        boolean tlfCorrecto;
        if (telefono.matches("^[0-9]{2,3}-? ?[0-9]{6,7}$")) {
            tlfCorrecto = true;
        } else {
            tlfCorrecto = false;
        }
        return tlfCorrecto;

    }

    public boolean comprobarEmail() {
        boolean emailCorrecto;
        if (email.matches("^[A-Za-z0-9\\-\\.]+@[A-Za-z0-9\\-\\.]+\\.[A-Za-z]{2,}$")) {
            emailCorrecto = true;
        } else {
            emailCorrecto = false;
        }
        return emailCorrecto;
    }

    private boolean camposVacios() {   //   devuelve true si hay algun campo "necesario" nulo 

        boolean vacio = false;

        String estilo = null;

        //String foto = avatarIV.getImage().toString();
        recogerDatos();

        //Comprobar si los campos estan vacios y pone el label en rojo
        if (nick.equals("")) {
            vacio = true;
            nickL.setStyle(mal);
            nickTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
        }
        if (contrasena.equals("")) {
            vacio = true;
            contraL.setStyle(mal);
            contraPF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
        }
        if (nombre.equals("")) {
            vacio = true;
            nombreL.setStyle(mal);
            nombreTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
        }
        if (apellidos.equals("")) {
            vacio = true;
            apelliL.setStyle(mal);
            apellidosTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
        }
        if (fecNac == null) {
            vacio = true;
            fecNacL.setStyle(mal);
            fecNacTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
        }
        if (telefono.equals("")) {
            vacio = true;
            telefL.setStyle(mal);
            telefonoTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
        }
        if (email.equals("")) {
            vacio = true;
            emailL.setStyle(mal);
            emailTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
        }
        return vacio;
    }

    //METODOS UTILES------------------------------------------------------------
    private void recogerDatos() {
        nick = nickTF.getText();
        contrasena = contraPF.getText();
        nombre = nombreTF.getText();
        apellidos = apellidosTF.getText();
        dni = dniTF.getText();
        telefono = telefonoTF.getText();
        direccion = direccionTF.getText();
        email = emailTF.getText();

        fecNac = fecNacTF.getValue();
    }

    public void limpiarRegistros() {
        nickTF.setText("");
        contraPF.setText("");
        nombreTF.setText("");
        apellidosTF.setText("");
        dniTF.setText("");
        telefonoTF.setText("");
        direccionTF.setText("");
        emailTF.setText("");
    }

}
