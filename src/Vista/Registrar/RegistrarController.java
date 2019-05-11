/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Registrar;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Usuario;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private TextField ContraPF;
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
    private JFXTextField repContraPF;
    @FXML
    private JFXDatePicker fecNacTF;
    @FXML
    private RadioButton clienteRB;
    @FXML
    private ToggleGroup rolUsuRB;
    @FXML
    private RadioButton AdminRB;
    @FXML
    private Button salirBT;

    private GestionBD bda;
    private usuariosDAO usuarioDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aceptarBT.getStyleClass().add("botonAceptarRegistro");
        fecNacTF.getStyleClass().add("calendarioRegistrar");
    }

    @FXML
    private void registrar(ActionEvent event) {

        String nick = nickTF.getText();
        String contrasena = ContraPF.getText();
        String ContrasenaCopia = repContraPF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String DNI = dniTF.getText();

//        LocalDate fecNac = LocalDate.parse(fecNacTF.getText(), DateTimeFormatter.ISO_DATE); VIEJO
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
            System.out.println("contraseña distinta");
        }
        // ---- I SINO QUE?
    }

    @FXML
    private void salir(ActionEvent event) {
    }

    public void setParametros(GestionBD bda, usuariosDAO usuarioDAO) {
        this.bda = bda;
        this.usuarioDAO = usuarioDAO;

    }

}
