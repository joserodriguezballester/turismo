
package Vista.Administrador.Registrar;

import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class RegistrarAdminController implements Initializable {

    @FXML
    private AnchorPane Ventana1;
    @FXML
    private JFXDatePicker fecNacTF;
    @FXML
    private JFXTextField nickTF;
    @FXML
    private JFXPasswordField repContraPF;
    @FXML
    private JFXTextField nombreTF;
    @FXML
    private JFXTextField apellidosTF;
    @FXML
    private JFXTextField dniTF;
    @FXML
    private JFXTextField telefonoTF;
    @FXML
    private JFXTextField direccionTF;
    @FXML
    private JFXTextField emailTF;
    @FXML
    private JFXRadioButton clienteRB;
    @FXML
    private JFXButton aceptarBT;
    @FXML
    private JFXButton salirBT;
    @FXML
    private ToggleGroup rolUsuRB;
    @FXML
    private JFXPasswordField contraPF;
    private usuariosDAO usuarioDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aceptarBT.getStyleClass().add("botonAceptarRegistro");
        salirBT.getStyleClass().add("botonSalirRegistro");
        fecNacTF.getStyleClass().add("calendarioRegistrar");
    }    

    @FXML
    private void registrar(ActionEvent event) {
        
        String nick = nickTF.getText();
        String contrasena = contraPF.getText();
        String ContrasenaCopia = repContraPF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String DNI = dniTF.getText();
        LocalDate fecNac = fecNacTF.getValue(); //NUEVO CALENDARIO JFOENIX
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();
        //Encriptar contraseña //////
        if (contrasena.equals(ContrasenaCopia)) {
            //crear usuario//
            Usuario usuario = new Usuario(DNI, nombre, apellidos, contrasena, direccion, telefono, email, nick, fecNac);       
            //insertar usuario en BD//      
            usuarioDAO.insertarUsuario(usuario);

            // aparte de lo que haga con los datos tiene que cerrarse la ventana
            //////// cerrar ventana ////
            Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();   //Identificamos la ventana (Stage) 
            stage.close();
            //////// fin cerrar ventana ////
        }else{
            Notificacion.alert("alerta","contraseña distinta"); // solo imprime
        }
        // ---- I SINO QUE? nada
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();   //Identificamos la ventana (Stage) 
        stage.close(); 
    }
    
}
