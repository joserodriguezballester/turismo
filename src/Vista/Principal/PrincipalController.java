
package Vista.Principal;

import Datos.Bda.GestionBD;
import Vista.Actividad.ActividadController;
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
public class PrincipalController implements Initializable {

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
//<<<<<<< HEAD
//       
//        bda.conectar();
//=======
//        gestion = new GestionBD();
//        gestion.conectar();
//>>>>>>> origin/master
    }

    @FXML
    private void irActividad(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Actividad/Actividad.fxml";
        ActividadController.setGestion(gestion);
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
            /////////tratar el error////
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
        }
//        ActividadController actividadController=loader.getController(); por si hace falta
    }

    @FXML
    private void irExperiencia(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Experiencia/Experiencia.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            /////////tratar el error////
            ex.printStackTrace();
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
        }
//        ActividadController actividadController=loader.getController(); por si hace falta
    }

    @FXML
    private void IrPerfil(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Perfil/Perfil.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            /////////tratar el error////
            ex.printStackTrace();
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
        }
//        ActividadController actividadController=loader.getController(); por si hace falta
    }

    @FXML
    private void irBuscar(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Buscador/Buscador.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador ; se ejecuta inicialice
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            /////////tratar el error////
            ex.printStackTrace();
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
        }
//        ActividadController actividadController=loader.getController(); por si hace falta
    }

    public void setParametros(Stage escenario) {

    }

    @FXML
    private void irTransporte(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Transporte/Transporte.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador ; se ejecuta inicialice
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            /////////tratar el error////
            ex.printStackTrace();
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
        }
//        ActividadController actividadController=loader.getController(); por si hace falta

    }

}
