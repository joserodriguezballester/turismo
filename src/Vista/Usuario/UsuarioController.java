package Vista.Usuario;

/*    
 *  To change this license header, choose License Headers in Project Properties.
 *   To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Vista.Principal.PrincipalController;
import com.sun.security.auth.PrincipalComparator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 *
 * @author joser
 */
public class UsuarioController implements Initializable {
    
    private Label label;
    @FXML
    private PasswordField contrasena;
    @FXML
    private Label nombreET;
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logearse(ActionEvent event) {
        cargarVentana();
    }

    @FXML
    private void registrarse(ActionEvent event) {
        cargarVentana();
    }
    public void cargarVentana(){
    Stage escenario = (Stage) this.nombreET.getParent().getScene().getWindow();
        PrincipalController principalController;
        String nombrefichero = "/Vista/Principal/Principal.fxml";
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(nombrefichero));
            root = loader.load(); // el metodo initialize() se ejecuta
            principalController = loader.getController();
//Pasamos informacion a la clase ConexionController
       //     principalController.setParametros(usuario, bda, cambiador);
//Damos valores a los nodos antes de mostrarlos
    //        principalController.calcularnodos();

            escenario.setScene(new Scene(root));
            escenario.show();

        } catch (IOException ex) {
            
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
                System.err.println("error");  ////mostrar en ventana
        }
    }

}
