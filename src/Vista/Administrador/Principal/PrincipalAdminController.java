/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador.Principal;

import Datos.Bda.GestionBD;
import Modelo.Notificacion;
import Vista.Administrador.Actividad.ActividadAdminController;
import Vista.Administrador.Perfil.PerfilAdminController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class PrincipalAdminController implements Initializable {

    @FXML
    private AnchorPane Menu;
    @FXML
    private AnchorPane Ventana;
    
    
    private GestionBD gestion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        gestion = new GestionBD();
        gestion = new GestionBD();
        gestion.conectar();
        
//        System.out.println("¿Hay conexion?" + gestion.conectar());
    }

    @FXML
    private void irActividad(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Administrador/Actividad/ActividadAdmin.fxml";      
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
            
             ActividadAdminController actividadAdminController=loader.getController();
             actividadAdminController.setGestion(gestion);
             System.out.println("pricicipal gestion: "+ gestion.isConectado());
             actividadAdminController.ejecutaAlPrincipio();
        
             
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            /////////tratar el error////
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
        } catch (Exception es) {
            Notificacion.error("ERROR AL CARGAR ACTIVIDAD ADMIN", "Verifica tu código,"
          + " No se encuentra la ventana de login (irActividad PrincipalAdminController)");
        
        }
//        ActividadController actividadController=loader.getController(); por si hace falta
    }

    @FXML
    private void irExperiencia(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Administrador/Experiencia/ExperienciaAdmin.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            /////////tratar el error////
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
        } catch (Exception es){
            Notificacion.error("ERROR AL CARGAR EXPERIENCIA ADMIN", "Verifica tu código,"
         + "  No se encuentra la ventana de login (irExperiencia PrincipalAdminController)");
        
        }
    }

    @FXML
    private void IrPerfil(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Administrador/Perfil/PerfilAdmin.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
             PerfilAdminController perfilAdminController=loader.getController();
             perfilAdminController.setGestion(gestion);
             perfilAdminController.ejecutaAlPrincipio();
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            /////////tratar el error////
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
        } catch (Exception es){
            Notificacion.error("ERROR AL CARGAR PERFIL ADMIN", "Verifica tu código,"
        + "  No se encuentra la ventana de login (irPerfil PrincipalAdminController)");
        
        }
    }

    @FXML
    private void irSalir(ActionEvent event) {
        Stage escenario = (Stage) this.Menu.getParent().getScene().getWindow();
        escenario.close();
    }

}
