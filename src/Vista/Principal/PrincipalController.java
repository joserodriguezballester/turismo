package Vista.Principal;

import Datos.Bda.GestionBD;
import Modelo.Notificacion;
import Modelo.Transicion;
import Modelo.Usuario;
import Vista.Actividad.ActividadController;
import Vista.Buscador.BuscadorController;

import Vista.Perfil.PerfilController;

import Vista.Experiencia.ExperienciaController;
import Vista.ListaExperiencias.ListaExperienciasController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Grupo4
 */
public class PrincipalController implements Initializable {

    private Usuario usuario;
    private Notificacion not;

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
    @FXML
    private Button botonLogo;
    @FXML
    private GridPane gridpane;
    @FXML
    private Pane panePerfil;
    @FXML
    private JFXButton botnPerfil;
    @FXML
    private JFXButton botnSalir;
    @FXML
    private ImageView caraIV;
    @FXML
    private ImageView imgLupa;
    private PrincipalController principalController;

    //INICIO--------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
        styleInicio();   
        efectoTransicion();
    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }

    public void setParametroUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargaNickFoto();      //seria mejor llamarlo desde el otro controlador pero...  
    }

    public void cargaNickFoto() {
        if (usuario != null) {
            botonPerfil.setText(usuario.getNick().toUpperCase());           
            try {
                caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
            } catch (Exception e) {
                caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
            }
        }
    }

    private void styleInicio() {

        //Transicion
        FadeTransition ft = new FadeTransition(Duration.millis(1500), gridpane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        //Panes
        panePerfil.setVisible(false);
        GridPane.setHalignment(caraIV, HPos.RIGHT);
        panePerfil.toFront();

        //Estilos
        gridpane.getStyleClass().add("menu");
        botonLogo.getStyleClass().add("botonMenu");
        botonActividades.getStyleClass().add("botonMenu");
        botonExperiencias.getStyleClass().add("botonMenu");
        botonPerfil.getStyleClass().add("botonMenu");
        botonBuscar.getStyleClass().add("botonMenu");
        botonTransportes.getStyleClass().add("botonMenu");
        botnPerfil.getStyleClass().add("botonMenu");
        botnSalir.getStyleClass().add("botonMenu");
    }

    private void efectoTransicion() {
        try {
            Transicion tr = new Transicion();
            tr.setRoot(Ventana);
            Ventana.toBack();
            tr.start();
        } catch (Exception e) {
            System.out.println("ha petao");
            not.error("ERROR Exception",
                    "en initialize --- PrincipalController");
        }
    }

    //ACCIONES------------------------------------------------------------------
    @FXML
    private void irInicio(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        PrincipalController controlador;
        loader.setLocation(getClass().getResource("/Vista/Principal/Principal.fxml"));
        try {
            Parent root = loader.load();
            controlador = loader.getController();
            controlador.setGestion(gestion);
            Ventana.getChildren().add(root);
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

        ActividadController controlador;

        loader.setLocation(getClass().getResource("/Vista/Actividad/Actividad.fxml"));
        try {
            Parent root = loader.load();

            controlador = loader.getController();
            controlador.setGestion(gestion);
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
            not.error("ERROR IOException",
                    "en irActividad --- PrincipalController");
        } catch (Exception es) {
            es.printStackTrace();
            not.error("ERROR AL CARGAR VENTANA ACTIVIDAD",
                    "Revisa el código y vuelve a intentarlo, (irExperiencia PrincipalController)");
        }
    }

    @FXML
    private void irExperiencia(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();

        ListaExperienciasController controlador;

        loader.setLocation(getClass().getResource("/Vista/ListaExperiencias/ListaExperiencias.fxml"));
        try {
            Parent root = loader.load();

            controlador = loader.getController();
            controlador.setUsuario(usuario);
            controlador.setGestion(gestion);
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
            not.error("ERROR IOException",
                    "en irActividad --- PrincipalController");
        } catch (Exception es) {
            es.printStackTrace();
            not.error("ERROR AL CARGAR VENTANA EXPERIENCIA",
                    "Revisa el código y vuelve a intentarlo, (irExperiencia PrincipalController)");
        }
    }

    @FXML
    private void IrPerfil(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();

        PerfilController perfilController;
       

        loader.setLocation(getClass().getResource("/Vista/Perfil/Perfil.fxml"));
        try {
            Parent root = loader.load();
            perfilController = loader.getController();
            perfilController.setUsuario(usuario);
             perfilController.setcontroler(principalController);
          
            perfilController.setGestion(gestion);
            perfilController.calcularnodos();
            Ventana.getChildren().add(root);

        } catch (IOException ex) {
            ex.printStackTrace();
            not.error("ERROR IOException",
                    "en irPerfil --- PrincipalController");
        } catch (Exception es) {
            es.printStackTrace();
            not.error("ERROR AL CARGAR VENTANA PERFIL",
                    "Revisa el código y vuelve a intentarlo, (irPerfil PrincipalController)");
        }
//        ActividadController actividadController=loader.getController(); por si hace falta
    }

    @FXML
    private void irBuscar(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Buscador/Buscador.fxml";
        BuscadorController controlador;
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();
            controlador = loader.getController();
            controlador.setGestion(gestion);
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
            not.error("ERROR IOException",
                    "en irPerfil --- PrincipalController");
        } catch (Exception es) {
            not.error("ERROR AL CARGAR VENTANA PERFIL",
                    "Revisa el código y vuelve a intentarlo, (irPerfil PrincipalController)");
        }
    }

    @FXML
    private void irTransporte(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Transporte/Transporte.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador ; se ejecuta inicialice
            //anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (IOException ex) {

            ex.printStackTrace();
            not.error("ERROR IOException",
                    "en irTransporte --- PrincipalController");
        } catch (Exception es) {
            not.error("ERROR AL CARGAR VENTANA TRANSPORTE",
                    "Revisa el código y vuelve a intentarlo, (irTarnsporte PrincipalController)");
        }
        //ActividadController actividadController=loader.getController(); por si hace falta
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        try {
            usuario = null;
            gestion.desconectar();

            Stage stage = (Stage) this.panePerfil.getParent().getScene().getWindow();

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

    //CODIGOS AL PASAR EL RATON POR ENCIMA--------------------------------------
    @FXML
    private void ocultarPanePerfil(MouseEvent event) {
        panePerfil.setVisible(false);
    }

    @FXML
    private void mostrarPanePerfil(MouseEvent event) {
        panePerfil.toFront();
        panePerfil.setVisible(true);
        if (!Ventana.getChildren().contains(panePerfil)) {

            Ventana.getChildren().add(panePerfil);
            if (Ventana.getHeight() == 800) {
                panePerfil.setLayoutY(74);
            } else {
                panePerfil.setLayoutY(4);
            }

        }

    }

    @FXML
    private void lupaNormal(MouseEvent event) {
        Image imagen = new Image("Imagenes/iconos/loupe1.png");
        imgLupa.setImage(imagen);
    }

    @FXML
    private void cambiarLupa(MouseEvent event) {
        Image imagen = new Image("Imagenes/iconos/loupe.png");
        imgLupa.setImage(imagen);
        panePerfil.setVisible(false);
    }

    public void setcontroller(PrincipalController principalController) {
    this.principalController=principalController;
    }

}
