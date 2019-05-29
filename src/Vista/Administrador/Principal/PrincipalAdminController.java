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
import Vista.Administrador.Agenda.AgendaController;
import Vista.Administrador.Experiencia.ExperienciaAdminController;
import Vista.Administrador.Perfil.PerfilAdminController;
import Vista.Administrador.TiposSubtipos.TiposSubtiposController;
import Vista.Administrador.Transportes.TransportesAdminController;
import Vista.Perfil.PerfilController;
import Vista.Principal.PrincipalController;
import com.jfoenix.controls.JFXButton;
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
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class PrincipalAdminController implements Initializable {

    private Notificacion not = new Notificacion();
    private GestionBD gestion;
    private Usuario usuario;

    @FXML
    private AnchorPane Menu;
    @FXML
    private AnchorPane Ventana;
    private ImageView imagev;

//    private ImageView caraIV;
    private ImageView cara1;
    @FXML
    private Button usuarioBT;
    @FXML
    private ImageView caraIV;
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
    PrincipalAdminController controlador;
    @FXML
    private JFXButton botonPerfil;
    @FXML
    private Pane taparP;
    @FXML
    private JFXButton botonTiposSubtipos;
    @FXML
    private JFXButton botonAgenda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();

        Image img = new Image("Imagenes/imagenesPrincipal/0.jpg");
        imagev = new ImageView(img);

        imagev.setFitHeight(800);
        imagev.setFitWidth(1300);

        this.anchorP.getChildren().add(imagev);

        imagev.toBack();

        taparP.toBack();
        styleInicio();
//        gestion = new GestionBD();
//        gestion.conectar();
    }

    private void styleInicio() {

        FadeTransition ft = new FadeTransition(Duration.millis(500), gridpane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        //Estilos
        gridpane.getStyleClass().add("menuP");
        botonActividades.getStyleClass().add("botonMenuP");
        botonExperiencias.getStyleClass().add("botonMenuP");
        botonTransportes.getStyleClass().add("botonMenuP");
        botonPerfil.getStyleClass().add("botonMenuP");
        botnSalir.getStyleClass().add("botonMenuP");
        botonTiposSubtipos.getStyleClass().add("botonMenuP");
        botonTiposSubtipos.setWrapText(true);
        botonAgenda.getStyleClass().add("botonMenuP");
        botonInicio.getStyleClass().add("botonMenuInicioP");
    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }

    @FXML
    private void irInicio(ActionEvent event) throws IOException {

        anchorP.getChildren().removeAll(anchorP.getChildren());
        FXMLLoader loader = new FXMLLoader();
//        PrincipalAdminController controlador;
        loader.setLocation(getClass().getResource("/Vista/Administrador/Principal/PrincipalAdmin.fxml"));
        try {
            Parent root = loader.load();
            controlador = loader.getController();
            controlador.setGestion(gestion);
            anchorP.getChildren().add(root);
        } catch (IOException ex) {
            not.error("ERROR", "Error al intentar cargar la ventana Principal");
        } catch (Exception es) {
            not.error("ERROR", "Error al intentar cargar la ventana Principal");
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
            not.error("ERROR", "Error al intentar cargar la ventana de Actividades");
        } catch (Exception es) {
            not.error("ERROR", "Error al intentar cargar la ventana de Actividades");
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
            not.error("ERROR", " No se encuentra la ventana de Experiencia");
        } catch (Exception es) {
            not.error("ERROR", " No se encuentra la ventana de Experiencia");
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
            not.error("ERROR", " No se encuentra la ventana de Perfil");
        } catch (Exception es) {
            not.error("ERROR", " No se encuentra la ventana de Perfil");
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
            not.error("ERROR", " No se ha podido salir");
//            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            not.error("ERROR", " No se ha podido salir");
//            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void irTransportesAdmin(ActionEvent event) {
//        Ventana.getChildren().removeAll(Ventana.getChildren());
//        FXMLLoader loader = new FXMLLoader();
//        String nombrefichero = "/Vista/Administrador/Transportes/TransportesAdmin.fxml";
//        loader.setLocation(getClass().getResource(nombrefichero));
//        try {
//            Parent root = loader.load();    //para obtener el controlador se ejecuta inicialice
//            TransportesAdminController transportesAdminController = loader.getController();
//            transportesAdminController.setGestion(gestion);
//            //        transportesAdminController.ejecutaAlPrincipio();
////           anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
//            Ventana.getChildren().add(root);
//        } catch (IOException ex) {
//            not.error("ERROR IOExcepction", " No se encuentra la ventana de login");
//        } catch (Exception es) {
//            not.error("ERROR AL CARGAR TRNSPORTE ADMIN", "Verifica tu código,"
//                    + "(irTransporteAdmin PrincipalAdminController)");
//
//        }
//    }
    public void setParametroUsuario(Usuario usuario) {
        this.usuario = usuario;
//        cargaNickFoto();  

        //seria mejor llamarlo desde el otro controlador pero...  
    }

//   public void cargaNickFoto() {
//        if (usuario != null) {
//            botonPerfil.setText(usuario.getNick().toUpperCase());
//            System.out.println("Imagenes/usuarios/" + usuario.getFoto());
////            cara1.setImage(new Image("Imagenes/usuarios/raquel.jpg"));
//            try {
//                caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
//            } catch (Exception e) {
//                caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
//            }
//        }
//    }
    @FXML
    private void IrPerfilAdmin(ActionEvent event) {
        imagev.setImage(null);
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();

        PerfilController perfilController;

        loader.setLocation(getClass().getResource("/Vista/Perfil/Perfil.fxml"));
        try {
            Parent root = loader.load();
            perfilController = loader.getController();
            perfilController.setUsuario(usuario);
            perfilController.setcontrolador(controlador);
            perfilController.setGestion(gestion);
            perfilController.calcularnodos();

            taparP.setStyle(" -fx-background-color: rgb(255, 255, 255)");

            taparP.toFront();
            Ventana.getChildren().add(root);
        } catch (Exception es) {
            not.error("Error", "Error al cargar la ventana Perfil");
        }
//        ActividadController actividadController=loader.getController(); por si hace falta
    }

    public void cargaNick() {
        if (usuario != null) {
            botonPerfil.setText(usuario.getNick().toUpperCase());
        }
    }

    public void cargaFoto() {
        if (usuario.getFotoFile() != null) {
            caraIV.setImage(new Image(usuario.getFotoFile().toURI().toString()));
        } else {
            try {
                caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
            } catch (Exception e) {
                caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
            }
        }
    }

    public void calcularnodos() {
        cargaNick();
        cargaFoto();
    }

    @FXML
    private void irTiposSubtiposAdmin(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Administrador/TiposSubtipos/TiposSubtipos.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();

            TiposSubtiposController controlador = loader.getController();
            controlador.setGestion(gestion);

            Ventana.getChildren().add(root);
        } catch (Exception es) {
            not.error("Error", "No se ha podido cargar la ventana de tipos y subtipos");
        }
    }

    @FXML
    private void irAgenda(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Administrador/Agenda/Agenda.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();

            AgendaController controlador = loader.getController();
            controlador.setGestion(gestion);

            Ventana.getChildren().add(root);
        } catch (Exception es) {
            not.error("Error", "No se ha podido cargar la agenda");
        }
    }
}
