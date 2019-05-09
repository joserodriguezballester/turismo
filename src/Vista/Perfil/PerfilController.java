/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Perfil;

import java.net.URL;
import java.sql.Connection;
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

/**
 * FXML Controller class
 *
 * @author joser
 */
public class PerfilController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Connection conn;
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
    private Button salirBT;
    @FXML
    private Button aceptarBT;
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
    private TextField fecNacTF;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // comprobar  rol usuario para mostrar radioButton
        // TODO
    }    

    @FXML
    private void salir(ActionEvent event) {
    }

    @FXML
    private void aceptar(ActionEvent event) {
    }

    @FXML
    private void mostrarRepContra(MouseEvent event) {
        //al entrar mostrar las etiquetas de repetir contraseña
        
    }
    
}
