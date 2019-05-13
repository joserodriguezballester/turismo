/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Transporte;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class TransporteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Connection conn;
    @FXML
    private TreeView<?> tarjetasTV;
    @FXML
    private ImageView imagTarj;
    @FXML
    private ImageView imagDescrip;
    @FXML
    private JFXTextArea descripTA;
    @FXML
    private JFXTextField DuracionTF;
    @FXML
    private JFXTextField precioTF;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
