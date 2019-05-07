package Vista.Usuario;

/*    
 *  To change this license header, choose License Headers in Project Properties.
 *   To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Vista.Administrador.Principal.PrincipalAdminController;
import Vista.Principal.PrincipalController;
import Vista.Registrar.RegistrarController;
import com.sun.security.auth.PrincipalComparator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UsuarioController implements Initializable {

    private Label label;
    @FXML
    private PasswordField contrasena;
    @FXML
    private Label nombreET;
    private Stage escenario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void logearse(ActionEvent event) {
        /// Cuando se verifique que es usuario y segun el roll ejecutará uno de los dos metodos
        cargarVentanaPrincipal();    // usuario cliente
       // cargarVentanaPrincipalAdmin();  //usuario administrador
   
     
    }

    @FXML
    private void registrarse(ActionEvent event) throws IOException {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/Registrar/Registrar.fxml"));
            root = loader.load(); // el meotodo initialize() se ejecuta
            //OBTENER EL CONTROLADOR DE LA VENTANA
//            UsuarioController usuarioControlador = loader.getController();
            Stage escena = new Stage();                      //En Stage nuevo.
            escena.setTitle("Nuevo Stage Usuario");
            escena.initModality(Modality.APPLICATION_MODAL);  // NO PERMITE ACCESO A LA VENTANA PRINCIPAL
            escena.setScene(new Scene(root));
            escena.showAndWait();
            //RECOGEMOS  LA INFORMACION ESCRITA EN LA OTRA VENTANA
//            if (usuarioControlador.isValido()) {
//                usuario = usuarioControlador.getUsuario();
//            }

        } catch (IOException ex) {
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");

        }
        
        
        
        
        
            
       
        

    }

    public void cargarVentanaPrincipal() {
        Stage escenario = (Stage) this.nombreET.getParent().getScene().getWindow();
        String nombrefichero = "/Vista/Principal/Principal.fxml";
        PrincipalController principalController;

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(nombrefichero));
            root = loader.load(); // el metodo initialize() se ejecuta
            principalController = loader.getController();
//Pasamos informacion a la clase siguiente
    principalController.setParametros(escenario);
//                 principalController.setParametros(usuario, bda, cambiador);
//Damos valores a los nodos antes de mostrarlos
            //        principalController.calcularnodos();

            escenario.setScene(new Scene(root));
            escenario.show();

        } catch (IOException ex) {

//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
            System.err.println("error");  ////mostrar en ventana
        }
    }

  

    private void cargarVentanaRegistrarse() {
       Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/Registrar/Registrar.fxml"));
            root = loader.load(); // el metodo initialize() se ejecuta
            //OBTENER EL CONTROLADOR DE LA VENTANA     UsuarioController usuarioControlador = loader.getController();
            
            Stage escena = new Stage();                      //En Stage nuevo.
            escena.setTitle("Registrarse");
            escena.initModality(Modality.APPLICATION_MODAL);  // NO PERMITE ACCESO A LA VENTANA PRINCIPAL
            escena.setScene(new Scene(root));
            escena.showAndWait();
            //RECOGEMOS  LA INFORMACION ESCRITA EN LA OTRA VENTANA
          

        } catch (IOException ex) {
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
//       mostrar error

        }
    }
        
     
    private void cargarVentanaPrincipalAdmin() {
        
         Stage escenario = (Stage) this.nombreET.getParent().getScene().getWindow();
        String nombrefichero = "/Vista/Administrador/Principal/PrincipalAdmin.fxml";
         PrincipalAdminController principalAdminController;

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(nombrefichero));
            root = loader.load(); // el metodo initialize() se ejecuta
            principalAdminController = loader.getController();
//Pasamos informacion a la clase siguiente
//    principalAdminController.setParametros(escenario);
//                 principalController.setParametros(usuario, bda, cambiador);
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
