/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Contactar;

import Modelo.Correo;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class ContactarController implements Initializable {

    @FXML
    private AnchorPane fondoUsuario;
    @FXML
    private JFXListView<String> guiasLV; ////pasar a usuarios
    @FXML
    private Label nickL;
    @FXML
    private Label telefL;
    @FXML
    private ImageView caraIV;
    @FXML
    private TextField AsuntoTA;
    @FXML
    private Button mandarBT;
    @FXML
    private TextArea mensajeTA;
    private Correo correo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       correo=new Correo();
    }    

    @FXML
    private void mandarCorreo(ActionEvent event) {
        try {
            String saliente="";
            String llegante="";
            String contrasena="";
            correo.sendEmail(llegante, llegante, contrasena);
        } catch (MessagingException ex) {
            System.out.println("error "+ex);
        }
        
    }
    
}
