/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Perfil;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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

    public PerfilController(Connection conn) {
        this.conn = conn;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
