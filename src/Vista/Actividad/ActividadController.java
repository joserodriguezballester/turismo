/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Actividad;

import Modelo.Tipo;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class ActividadController implements Initializable {

    @FXML
    private AnchorPane Ventana;
    @FXML
    private ScrollPane scrollTipoActividades;

    static private Connection conn;

    public ActividadController(Connection conn) {
        this.conn = conn;
    }

    public static void setConn(Connection conexion) {
        conn = conexion;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Tipo> lista = new ArrayList<>();
        for (Tipo tipo : lista) {
            System.out.println(tipo);
        }
    }
}
