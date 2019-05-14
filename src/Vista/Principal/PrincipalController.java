package Vista.Principal;

import Datos.Bda.GestionBD;
import Modelo.Notificacion;
import Modelo.Transicion;
import Modelo.Usuario;
import Vista.Actividad.ActividadController;
import Vista.CrearExperiencia.CrearExperienciaController;

import Vista.Perfil.PerfilController;

import Vista.Experiencia.ExperienciaController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    @FXML
    private Button botonActividades;
    @FXML
    private Button botonExperiencias;
    @FXML
    private Button botonPerfil;
    @FXML
    private Button botonBuscar;
    @FXML
    private Button botonTransportes;
    private Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botonActividades.getStyleClass().add("botonMenu");
        botonExperiencias.getStyleClass().add("botonMenu");
        botonPerfil.getStyleClass().add("botonMenu");
        botonBuscar.getStyleClass().add("botonMenu");
        botonTransportes.getStyleClass().add("botonMenu");
//        gestion = new GestionBD();
//        gestion.conectar();
        try {
            Transicion tr = new Transicion();
            tr.setRoot(Ventana);
            tr.start();
        } catch (Exception e) {
            System.out.println("ha petao");
        }

    }

    @FXML
    private void irActividad(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();

        ActividadController controlador;

        loader.setLocation(getClass().getResource("/Vista/Actividad/Actividad.fxml"));
        try {
            Parent root = loader.load();

            controlador = loader.getController();
            controlador.setGestion(gestion);
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception es) {
            es.printStackTrace();
            Notificacion.error("ERROR AL CARGAR VENTANA EXPERIENCIA",
                    "Revisa el código y vuelve a intentarlo, (irExperiencia PrincipalController)");
        }
    }

    @FXML
    private void irExperiencia(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();

        ExperienciaController controlador;

        loader.setLocation(getClass().getResource("/Vista/Experiencia/Experiencia.fxml"));
        try {
            Parent root = loader.load();

            controlador = loader.getController();
            controlador.setGestion(gestion);
            controlador.setUsuario(usuario);
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception es) {
            es.printStackTrace();
            Notificacion.error("ERROR AL CARGAR VENTANA EXPERIENCIA",
                    "Revisa el código y vuelve a intentarlo, (irExperiencia PrincipalController)");
        }
    }

    @FXML
    private void IrPerfil(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Perfil/Perfil.fxml";

//        PerfilController perfilController=loader.getController();
//        perfilController.setUsuario(usuario);
//        perfilController.calcularnodos();
//        PerfilController.setGestion(gestion);
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
            PerfilController perfilController = loader.getController();
            perfilController.setUsuario(usuario);
            perfilController.calcularnodos();
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);

        } catch (IOException ex) {
            /////////tratar el error////
            ex.printStackTrace();
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
        } catch (Exception es) {
            Notificacion.error("ERROR AL CARGAR VENTANA PERFIL",
                    "Revisa el código y vuelve a intentarlo, (irPerfil PrincipalController)");
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
        } catch (Exception es) {
            Notificacion.error("ERROR AL CARGAR VENTANA BUSCADOR",
                    "Revisa el código y vuelve a intentarlo, (irBuscar PrincipalController)");
        }
//        ActividadController actividadController=loader.getController(); por si hace falta
    }

    public void setParametros(Stage escenario) {

    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
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
        } catch (Exception es) {
            Notificacion.error("ERROR AL CARGAR VENTANA TRANSPORTE",
                    "Revisa el código y vuelve a intentarlo, (irTarnsporte PrincipalController)");
        }
//        ActividadController actividadController=loader.getController(); por si hace falta

    }

    public void setParametroUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
