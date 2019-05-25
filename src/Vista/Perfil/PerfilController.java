package Vista.Perfil;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import Vista.CambiarContra.CambiarContraController;
import Vista.Principal.PrincipalController;
import Vista.Registrar.RegistrarController;
import Vista.Usuario.UsuarioController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class PerfilController implements Initializable {

    @FXML
    private AnchorPane Ventana;
    @FXML
    private PasswordField repContraPF;
    @FXML
    private RadioButton clienteRB;
    @FXML
    private ToggleGroup rolUsuRB;
    @FXML
    private TextField nickTF;
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
    private JFXDatePicker fecNacTF;
    private Usuario usuario;
    @FXML
    private Button modificarBT;

    private Notificacion not;
    private GestionBD gestion;
    private usuariosDAO usuarioDAO;
    @FXML
    private Label labelUser;
    @FXML
    private ImageView caraIV;
    @FXML
    private Pane alFrenteAP;
    @FXML
    private Pane barra;
    @FXML
    private Pane paneBienvenido;
    private PrincipalController principalController;
    private File fotoFile;
    private JFXPasswordField contraPF;
    private String foto;
    @FXML
    private Button botonGuardar;
    @FXML
    private JFXButton cancelarBT;
    @FXML
    private Label labelPW;
    private boolean selecionarFoto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
        Image img = new Image("Imagenes/banner2.jpg");
        ImageView imagev = new ImageView(img);
        Ventana.getChildren().add(imagev);
        imagev.setFitHeight(230);
        imagev.setFitWidth(1300);
        Ventana.toBack();
        alFrenteAP.getStyleClass().add("panePerfilPersonal");
        barra.getStyleClass().add("barraPerfil");
        paneBienvenido.getStyleClass().add("paneBienv");
        cancelarBT.getStyleClass().add("botonCerrarInformacion");
        botonGuardar.getStyleClass().add("botonGuardarModificacion");
        labelPW.getStyleClass().add("recordarpassword");
        alFrenteAP.toFront();
        /// lo pasamos a cuando este desabilitado editar
        ///caraIV.setOnMouseClicked(event -> seleccionFoto());

        botonGuardar.setVisible(false);
        cancelarBT.setVisible(false);
        labelPW.setVisible(false);

        botonGuardar.setVisible(false);
        cancelarBT.setVisible(false);

    }

    private void seleccionFoto() {
        selecionarFoto = true;

        //elegir archivo       
        fotoFile = usuario.cargarfoto();

        //muestra archivo seleccionado
        // cargarfoto();
        if (fotoFile != null) {
            Image image = new Image(fotoFile.toURI().toString());
            caraIV.setImage(image);
        }

//      principalController.cargaNickFoto();  
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelUser.setText(usuario.getNick().toUpperCase());
    }

    public void calcularnodos() {
        usuarioDAO = new usuariosDAO(gestion);
        nickTF.setText(usuario.getNick());
        nombreTF.setText(usuario.getNombre());
        apellidosTF.setText(usuario.getApellidos());
        dniTF.setText(usuario.getDni());
        telefonoTF.setText(usuario.getTelefono());
        direccionTF.setText(usuario.getDireccion());
        emailTF.setText(usuario.getEmail());
        fecNacTF.setValue(usuario.getFecNac());
        cargarfoto();
//        try {
//            caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
//        } catch (Exception e) {
//            caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
//        }
//        ContraPF.setText(usuario.desencriptar(usuario.getPassword()));
    }

    @FXML
    private void modificar(ActionEvent event) throws IOException, SQLException {
        boolean modiNick = false;
        boolean modiFoto = false;

        String nick = nickTF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String dni = dniTF.getText();
        LocalDate fecNac = fecNacTF.getValue();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();

        //fotoFile=seleccion de archivo (seleccionarFoto)     
        int id = usuario.getId();

        /////COMPARAMOS SI HAY CAMBIOS, cambiamos en BD y cambiamos en USUARIO
//---NICK
        if (!usuario.getNick().equals(nick)) {
            modiNick = usuarioDAO.modificarNick(nick, id);
            if (modiNick) {
                usuario.setNick(nick);
                not.info("Modificar", "Ha sido modificado con exito");
            }
        }
        //// Otras consecuencias del cambios de Nick
        if (modiNick) {
            //**cambiar nombre foto                
            File oldfile = usuario.getFotoFile();
            File newfile = fotoFile;
            oldfile.renameTo(newfile);

            //**cambios en vista            
            labelUser.setText(nick.toUpperCase());
            principalController.cargaNick();
            principalController.cargaFoto();
        }
        //------------

//---NOMBRE
        if (!usuario.getNombre().equals(nombre)) {
            boolean modiNombre = usuarioDAO.modificarNombre(nombre, id);
            if (modiNombre) {
                usuario.setNombre(nombre);
                not.info("Modificar", "Ha sido modificado con exito");
            }
        }

//---APELLIDOS
        if (!usuario.getApellidos().equals(apellidos)) {
            boolean modificado = usuarioDAO.modificarApellidosTF(apellidos, id);
            if (modificado) {
                usuario.setApellidos(apellidos);
                not.info("Modificar", "Ha sido modificado con exito");
            }
        }

//---DNI
        if (!usuario.getDni().equals(dni)) {
            boolean modificado = usuarioDAO.modificarDni(dni, id);
            if (modificado) {
                usuario.setDni(dni);
                not.info("Modificar", "Ha sido modificado con exito");
            }
        }

//---FECHA NACIMIENTO
        if (!usuario.getFecNac().equals(fecNac)) {
            boolean modificado = usuarioDAO.modificarFecNac(fecNac, id);
            if (modificado) {
                usuario.setFecNac(fecNac);
                not.info("Modificar", "Ha sido modificado con exito");
            }
        }

//---TELEFONO
        if (!usuario.getTelefono().equals(telefono)) {
            boolean modificado = usuarioDAO.modificarTelefono(telefono, id);
            if (modificado) {
                usuario.setTelefono(telefono);
                not.info("Modificar", "Ha sido modificado con exito");
            }
        }

//---DIRECCION
        if (!usuario.getDireccion().equals(direccion)) {
            boolean modificado = usuarioDAO.modificarDireccion(direccion, id);
            if (modificado) {
                usuario.setDireccion(direccion);
                not.info("Modificar", "Ha sido modificado con exito");
            }
        }
//---EMAIL
        if (!usuario.getEmail().equals(email)) {
            boolean modificado = usuarioDAO.modificarEmail(email, id);
            if (modificado) {
                usuario.setEmail(email);
                not.info("Modificar", "Ha sido modificado con exito");
            }
        }

//---FOTO   
        if (selecionarFoto) {
            usuario.setFotoFile(fotoFile);
            foto = usuario.fotoToString();  ///pasar fotofile a string
            usuario.setFoto(foto);
            modiFoto = usuarioDAO.modificarFoto(foto, id);
            if (modiFoto) {
               not.info("Modificar", "Ha sido modificado con exito");
            }
            
        }
//// Otras consecuencias del cambios de Foto
        if (modiFoto) {
            // guardar foto nueva
            usuario.guardarFoto();
            //cambios en vista         
            principalController.cargaFoto();          
        }
    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }
/////corregir

    private void cargarfoto() {
        // usuario.getFotoFile();
//        if (fotoFile != null) {
//            Image image = new Image(fotoFile.toURI().toString());
//            caraIV.setImage(image);
//        }
        try {
            caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
        } catch (Exception e) {
            caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
        }

//        modificarBT.setDisable(false);
    }

    public void setcontroler(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @FXML
    private void editarPerfil(ActionEvent event) {
        botonGuardar.setVisible(true);
        cancelarBT.setVisible(true);
        labelPW.setVisible(true);
        nickTF.setEditable(true);
        nombreTF.setEditable(true);
        apellidosTF.setEditable(true);
        dniTF.setEditable(true);
//        fecNacTF.setEditable(true);
        direccionTF.setEditable(true);
        emailTF.setEditable(true);
        editarFoto();

    }

    private void editarFoto() {
        caraIV.setOnMouseClicked(event -> seleccionFoto());
    }

    @FXML
    private void cancelar(ActionEvent event) {
        botonGuardar.setVisible(false);
        cancelarBT.setVisible(false);
        labelPW.setVisible(false);

        nickTF.setEditable(false);
        nombreTF.setEditable(false);
        apellidosTF.setEditable(false);
        dniTF.setEditable(false);
        fecNacTF.setEditable(false);
        direccionTF.setEditable(false);
        emailTF.setEditable(false);
    }

    @FXML
    private void cambiarPassword(MouseEvent event) {
        Parent root;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/CambiarContra/CambiarContra.fxml"));
            root = loader.load();  // el meotodo initialize() se ejecuta

            //OBTENER EL CONTROLADOR DE LA VENTANA
            CambiarContraController cambiarcontracontroller = loader.getController();
//            cambiarcontracontroller.setParametros(usuarioDAO);

            Stage escena = new Stage();    //En Stage nuevo.                  
            escena.setTitle("Cambiar Contraseña");

            // NO PERMITE ACCESO A LA VENTANA PRINCIPAL
            escena.initModality(Modality.APPLICATION_MODAL);
            escena.getIcons().add(new Image("/Imagenes/iconos/lock.png"));
            escena.setScene(new Scene(root));
            escena.setResizable(false);
            escena.showAndWait();
        } catch (IOException ex) {
            not.error("ERROR IOException",
                    "en Perfil() --- UsuarioController");
        }
    }

}
