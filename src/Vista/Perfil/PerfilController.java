package Vista.Perfil;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import Vista.Principal.PrincipalController;
import Vista.Usuario.UsuarioController;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @FXML
    private JFXPasswordField contraPF;
    private String foto;
    @FXML
    private Button botonGuardar;
    @FXML
    private JFXButton cancelarBT;

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
        alFrenteAP.toFront();
        caraIV.setOnMouseClicked(event -> mostrarFoto());
        botonGuardar.setVisible(false);
        cancelarBT.setVisible(false);

    }

    private void mostrarFoto() {
        fotoFile = usuario.cargarfoto();

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
        try {
            caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
        } catch (Exception e) {
            caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
        }

//        ContraPF.setText(usuario.desencriptar(usuario.getPassword()));
    }

    @FXML
    private void modificar(ActionEvent event) throws IOException {
        String nick = nickTF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String DNI = dniTF.getText();
        LocalDate fecNac = fecNacTF.getValue();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();
        String contrasena = usuario.encriptar(contraPF.getText()); ////encriptada
        usuario.setNick(nick);
        foto = usuario.fotoToString();   ///pasar fotofile a string
        int id = usuario.getId();
        if (!usuario.getNick().equals(nickTF.getText())) {
            boolean modificado = usuarioDAO.modificarNick(nick);
            if (modificado) {
                usuario.setNick(nick);
<<<<<<< HEAD
                usuario.setNombre(nombre);
                usuario.setApellidos(apellidos);
                usuario.setDni(DNI);
                usuario.setFecNac(fecNac);
                usuario.setTelefono(telefono);
                usuario.setDireccion(direccion);
                usuario.setEmail(email);

                usuario.setFotoFile(fotoFile);



                labelUser.setText(nick);
//                usuario.setFoto(DNI);
//                UsuarioController usuarioController=new UsuarioController();        
//                Stage escenario = (Stage) this.Ventana.getParent().getScene().getWindow();  
//              Ventana.getChildren().removeAll(Ventana.getChildren());
//                usuarioController.cargarVentanaPrincipal();
//                PrincipalController principalController=new PrincipalController();
                principalController.cargaNickFoto();

                labelUser.setText(nick.toUpperCase());

=======
                 not.info("Modificar", "Ha sido modificado con exito");
>>>>>>> origin/master
            }
        }
        if (!usuario.getNombre().equals(nombreTF.getText())) {
            boolean modificado = usuarioDAO.modificarNombre(nombre);
            if (modificado) {
                usuario.setNombre(nombre);
            }
        }
         if (!usuario.getApellidos().equals(apellidosTF.getText())) {
            boolean modificado = usuarioDAO.modificarApellidosTF(apellidos);
            if (modificado) {
                usuario.setApellidos(apellidos);             
            }
        }
//        not.alert("Error", "Hay un error de fichero");
            usuario.guardarFoto();
           
            usuario.setNombre(nombre);
            usuario.setApellidos(apellidos);
            usuario.setDni(DNI);
            usuario.setFecNac(fecNac);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);
            usuario.setEmail(email);

            usuario.setFotoFile(fotoFile);

            labelUser.setText(nick);

            principalController.cargaNickFoto();

            labelUser.setText(nick.toUpperCase());

//            }
            //asi hemos recargado la lista
//        } catch (SQLException ex) {
//            not.error("ERROR SQL", "" + ex.getMessage()
//                    + " en modificar() --- PerfilAdminController");
//        }
        }

    

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }
/////corregir

    private void cargarfoto() {
        Stage stage = (Stage) this.caraIV.getParent().getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                // new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File fotoFile = fileChooser.showOpenDialog(stage);
        if (fotoFile != null) {
            Image image = new Image(fotoFile.toURI().toString());
            caraIV.setImage(image);
        }
        modificarBT.setDisable(false);
    }

    public void setcontroler(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @FXML
    private void editarPerfil(ActionEvent event) {
        botonGuardar.setVisible(true);
        cancelarBT.setVisible(true);

        boolean siEditable = true;
        editables(siEditable);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        botonGuardar.setVisible(false);
        cancelarBT.setVisible(false);

        boolean noEditable = false;
        editables(noEditable);
    }

    private void editables(boolean edit) {
        nickTF.setEditable(edit);
        nombreTF.setEditable(edit);
        apellidosTF.setEditable(edit);
        dniTF.setEditable(edit);
        fecNacTF.setEditable(edit);
        direccionTF.setEditable(edit);
        emailTF.setEditable(edit);
    }
}
