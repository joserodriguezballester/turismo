/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Registrar;

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
    private Connection conn;
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
    private PasswordField repContraPF;
    @FXML
    private TextField fecNacTF;
    @FXML
    private RadioButton clienteRB;
    @FXML
    private ToggleGroup rolUsuRB;
    @FXML
    private RadioButton AdminRB;
    @FXML
    private Button salirBT;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void registrar(ActionEvent event) {
        // aparte de lo que haga con los datos tiene que cerrarse la ventana

        //////// cerrar ventana ////
        Stage stage = (Stage) this.aceptarBT.getParent().getScene().getWindow();   //Identificamos la ventana (Stage) 
        stage.close();
        //////// fin cerrar ventana ////
    }

    @FXML
    private void mostrarRepContra(MouseEvent event) {
    }

    @FXML
    private void salir(ActionEvent event) {
    }

}
