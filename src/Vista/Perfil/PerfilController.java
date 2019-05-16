package Vista.Perfil;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private RadioButton AdminRB;
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
    @FXML
    private AnchorPane alFrenteAP;
    private Notificacion not;
    private GestionBD gestion;
    private usuariosDAO usuarioDAO;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
       
        Image img = new Image("Imagenes/fondoPerfil.jpg");
        ImageView imagev = new ImageView(img);
        Ventana.getChildren().add(imagev);
        imagev.setFitHeight(800);
        imagev.setFitWidth(1300);
        Ventana.toBack();
       alFrenteAP.getStyleClass().add("paneinicio");
        alFrenteAP.toFront();

       
        // TODO
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        
//        fecNacTF.setText(usuario.getFecNac().toString());

//        ContraPF.setText(usuario.desencriptar(usuario.getPassword()));
    }

    @FXML
    private void modificar(ActionEvent event) {

        String nick = nickTF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String DNI = dniTF.getText();
        LocalDate fecNac = fecNacTF.getValue();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();
        int id = usuario.getId();

        try {
            usuarioDAO.modificarUsuariodesdeUsuario(DNI, nombre, apellidos,  nick, direccion, telefono, email, id, fecNac);
            // si ha modificado algo
          
            //asi hemos recargado la lista
        } catch (SQLException ex) {
            not.error("ERROR SQL", "" + ex.getMessage()
                    + " en modificar() --- PerfilAdminController");
        }

    }

    @FXML
    private void mostrarBoton(MouseEvent event) {

        modificarBT.setDisable(false);
    }

    public void setGestion(GestionBD gestion) {
        this.gestion=gestion;
    }

}
