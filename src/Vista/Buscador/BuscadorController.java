/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Buscador;

import Datos.Bda.GestionBD;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class BuscadorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private GestionBD gestion;
    private Connection conn;
    @FXML
    private AnchorPane Ventana;
    
    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();
        inicio();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicio() {
        Pane pane = new Pane();
        pane.setLayoutX(200);
        pane.setLayoutY(50);
        pane.setMaxHeight(300);
        pane.setMaxWidth(900);
        pane.getStyleClass().add("-fx-background-color: #FF0000;");
        Ventana.getChildren().add(pane);
    }
}
