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
import Vista.Principal.PrincipalController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Usuario usuario;
    @FXML
    private Button usuarioBT;
//    private ImageView caraIV;
    @FXML
    private ImageView cara1;
    @FXML
    private GridPane gridpane;
    @FXML
    private Button botonActividades;
    @FXML
    private Button botonExperiencias;
    @FXML
    private Button botonTransportes;
    private Button botnPerfil;
    @FXML
    private Button botnSalir;
    @FXML
    private Button botonInicio;
    @FXML
    private AnchorPane anchorP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
        

        Image img = new Image("Imagenes/imagenesPrincipal/0.jpg");
        ImageView imagev = new ImageView(img);

        imagev.setFitHeight(800);
        imagev.setFitWidth(1300);

     
        this.anchorP.getChildren().add(imagev);
        imagev.toBack();
        
        styleInicio();
//        gestion = new GestionBD();
//        gestion.conectar();
    }

    private void styleInicio() {

        FadeTransition ft = new FadeTransition(Duration.millis(1500), gridpane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        
        //Estilos
        gridpane.getStyleClass().add("menuP");
        botonActividades.getStyleClass().add("botonMenuP");
        botonExperiencias.getStyleClass().add("botonMenuP");
        botonTransportes.getStyleClass().add("botonMenuP");
        usuarioBT.getStyleClass().add("botonMenuP");
        botnSalir.getStyleClass().add("botonMenuP");
        botonInicio.getStyleClass().add("botonMenuInicioP");
    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }

    @FXML
    private void irInicio(ActionEvent event) throws IOException {

        anchorP.getChildren().removeAll(anchorP.getChildren());
        FXMLLoader loader = new FXMLLoader();
        PrincipalAdminController controlador;
        loader.setLocation(getClass().getResource("/Vista/Administrador/Principal/PrincipalAdmin.fxml"));
        try {
            Parent root = loader.load();
            controlador = loader.getController();
            controlador.setGestion(gestion);
            anchorP.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
            not.error("ERROR IOException",
                    "en irInicio --- PrincipalController");
        } catch (Exception es) {
            es.printStackTrace();
            not.error("ERROR AL CARGAR VENTANA INICIO",
                    "Revisa el código y vuelve a intentarlo, (irInicio PrincipalController)");
        }
    }

    @FXML
    private void irActividad(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Administrador/Actividad/ActividadAdmin.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice

            ActividadAdminController actividadAdminController = loader.getController();
            actividadAdminController.setGestion(gestion);
            actividadAdminController.ejecutaAlPrincipio();

//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();

            not.error("ERROR IOExcepction", " No se encuentra la ventana de login");
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

            ExperienciaAdminController experienciaAdminController = loader.getController();
            experienciaAdminController.setGestion(gestion);
            experienciaAdminController.ejecutaAlPrincipio();

            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            not.error("ERROR IOExcepction", " No se encuentra la ventana de login");
        } catch (Exception es) {
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
            PerfilAdminController perfilAdminController = loader.getController();
            perfilAdminController.setGestion(gestion);
            perfilAdminController.ejecutaAlPrincipio();
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            not.error("ERROR IOExcepction", " No se encuentra la ventana de login");
        } catch (Exception es) {
            not.error("ERROR AL CARGAR PERFIL ADMIN", "Verifica tu código,"
                    + "(irPerfil PrincipalAdminController)");

        }
    }

    @FXML
    private void irSalir(ActionEvent event) {
        try {
            usuario = null;
            gestion.desconectar();

            Stage stage = (Stage) this.Menu.getParent().getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/Vista/Usuario/Usuario.fxml"));

            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("/Imagenes/log.png"));
            stage.setTitle(" Amsterdam");   // o nombre agencia
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (SQLException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irTransportesAdmin(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Administrador/Transportes/TransportesAdmin.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
            TransportesAdminController transportesAdminController = loader.getController();
            transportesAdminController.setGestion(gestion);
            //        transportesAdminController.ejecutaAlPrincipio();
//           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            not.error("ERROR IOExcepction", " No se encuentra la ventana de login");
        } catch (Exception es) {
            not.error("ERROR AL CARGAR TRNSPORTE ADMIN", "Verifica tu código,"
                    + "(irTransporteAdmin PrincipalAdminController)");

        }
    }

    public void setParametroUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargaNickFoto();      //seria mejor llamarlo desde el otro controlador pero...  
    }

    public void cargaNickFoto() {
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
