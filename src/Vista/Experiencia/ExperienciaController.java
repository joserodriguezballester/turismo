/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Experiencia;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class ExperienciaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Connection conn;

    public ExperienciaController(Connection conn) {
        this.conn = conn;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
