/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador.Principal;

import Datos.Bda.GestionBD;
import Modelo.Notificacion;
import Modelo.Transicion;
import Modelo.Usuario;
import Vista.Administrador.Actividad.ActividadAdminController;
import Vista.Administrador.Experiencia.ExperienciaAdminController;
import Vista.Administrador.Perfil.PerfilAdminController;
import Vista.Administrador.Transportes.TransportesAdminController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    private Notificacion not = new Notificacion();
    private GestionBD gestion;
    @FXML
    private Button transportesBT;
    private Usuario usuario;
    @FXML
    private Button usuarioBT;
//    private ImageView caraIV;
    @FXML
    private ImageView cara1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Transicion tr = new Transicion();
            tr.setRoot(Ventana);
            tr.start();
        } catch (Exception e) {
            System.out.println("ha petao");
            not.error("ERROR EXCEPTION" ," Error en el carrusel");
        }
//        gestion = new GestionBD();
//        gestion.conectar();
    }
    public void setGestion(GestionBD gestion) {
        this.gestion=gestion;
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
             actividadAdminController.ejecutaAlPrincipio();
        
             
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
             not.error("ERROR IOExcepction" ," No se encuentra la ventana de login");
        } catch (Exception es) {
            not.error("ERROR AL CARGAR ACTIVIDAD ADMIN", "Verifica tu código,"
          + "(irActividad PrincipalAdminController)");
        
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
            
            ExperienciaAdminController experienciaAdminController=loader.getController();
            experienciaAdminController.setGestion(gestion);
            experienciaAdminController.ejecutaAlPrincipio();
            
            
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
             not.error("ERROR IOExcepction" ," No se encuentra la ventana de login");
        } catch (Exception es){
            not.error("ERROR AL CARGAR EXPERIENCIA ADMIN", "Verifica tu código,"
         + "(irExperiencia PrincipalAdminController)");
        
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
            not.error("ERROR IOExcepction" ," No se encuentra la ventana de login");
        } catch (Exception es){
            not.error("ERROR AL CARGAR PERFIL ADMIN", "Verifica tu código,"
        + "(irPerfil PrincipalAdminController)");
        
        }
    }

    @FXML
    private void irSalir(ActionEvent event) {
        Stage escenario = (Stage) this.Menu.getParent().getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void irTransportesAdmin(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Administrador/Transportes/TransportesAdmin.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
             TransportesAdminController transportesAdminController=loader.getController();
             transportesAdminController.setGestion(gestion);
     //        transportesAdminController.ejecutaAlPrincipio();
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
           not.error("ERROR IOExcepction" ," No se encuentra la ventana de login");
        } catch (Exception es){
            not.error("ERROR AL CARGAR TRNSPORTE ADMIN", "Verifica tu código,"
        + "(irTransporteAdmin PrincipalAdminController)");
        
        }       
    }

      public void setParametroUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargaNickFoto();      //seria mejor llamarlo desde el otro controlador pero...  
    }
    public void cargaNickFoto(){
        if (usuario != null) {
            usuarioBT.setText(usuario.getNick().toUpperCase());
            System.out.println("Imagenes/usuarios/" + usuario.getFoto());
//            cara1.setImage(new Image("Imagenes/usuarios/raquel.jpg"));
            try {
                cara1.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));               
            } catch (Exception e) {
                cara1.setImage(new Image("Imagenes/usuarios/avatar.png"));
            }
    }
    }
    
    
    
}
