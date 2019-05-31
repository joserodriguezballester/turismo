package Vista.Principal;

import Datos.Bda.GestionBD;
import Modelo.Notificacion;
import Modelo.Transicion;
import Modelo.Usuario;
import Vista.Actividad.ActividadController;
import Vista.Buscador.BuscadorController;
import Vista.Contactar.ContactarController;
import Vista.Perfil.PerfilController;
import Vista.ListaExperiencias.ListaExperienciasController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXNodesList;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private GestionBD gestion;
    private PrincipalController principalController;

    @FXML
    private AnchorPane Menu;
    @FXML
    private AnchorPane Ventana;
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
    @FXML
    private JFXButton botonContactar;

    //INICIO--------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();

        efectoTransicion();
        styleInicio();

////        NODE LIST
        try {
            JFXNodesList botonesNodesList = new JFXNodesList();
            JFXButton redes = new JFXButton("Redes Sociales");

            Image twitter = new Image("/Imagenes/iconos/twitter.png");
            ImageView imgTwitter = new ImageView(twitter);
            imgTwitter.setFitHeight(40);
            imgTwitter.setFitWidth(40);

            Image facebook = new Image("/Imagenes/iconos/facebook.png");
            ImageView imgFacebook = new ImageView(facebook);
            imgFacebook.setFitHeight(40);
            imgFacebook.setFitWidth(40);

            Image instagram = new Image("/Imagenes/iconos/instagram.png");
            ImageView imgInstagram = new ImageView(instagram);
            imgInstagram.setFitHeight(40);
            imgInstagram.setFitWidth(40);

            JFXButton botonTwitter = new JFXButton();
            botonTwitter.setGraphic(imgTwitter);
            JFXButton botonInstagram = new JFXButton();
            botonInstagram.setGraphic(imgInstagram);
            JFXButton botonFacebook = new JFXButton();
            botonFacebook.setGraphic(imgFacebook);

            redes.getStyleClass().add("botonRedes");
            botonTwitter.getStyleClass().add("botonRedes");
            botonInstagram.getStyleClass().add("botonRedes");
            botonFacebook.getStyleClass().add("botonRedes");

            Label etiquetaTwitter = new Label("@Johansa");
            Label etiquetaInstagram = new Label("@Johansa");
            Label etiquetaFacebook = new Label("facebook.com/Johansa");

            etiquetaTwitter.setVisible(false);
            etiquetaInstagram.setVisible(false);
            etiquetaFacebook.setVisible(false);

            etiquetaTwitter.relocate(1100, 702);
            etiquetaInstagram.relocate(1100, 640);
            etiquetaFacebook.relocate(995, 576);

            etiquetaTwitter.getStyleClass().add("etiquetaRedesSociales");
            etiquetaInstagram.getStyleClass().add("etiquetaRedesSociales");
            etiquetaFacebook.getStyleClass().add("etiquetaRedesSociales");

//            ACCION BOTON TWITTER
            botonTwitter.setOnMouseEntered((event) -> {
                etiquetaTwitter.setVisible(true);
            });
            botonTwitter.setOnMouseExited((event) -> {
                etiquetaTwitter.setVisible(false);
            });

//            ACCION BOTON INSTAGRAM
            botonInstagram.setOnMouseEntered((event) -> {
                etiquetaInstagram.setVisible(true);
            });
            botonInstagram.setOnMouseExited((event) -> {
                etiquetaInstagram.setVisible(false);
            });

//            ACCION BOTON FACEBOOK
            botonFacebook.setOnMouseEntered((event) -> {
                etiquetaFacebook.setVisible(true);
            });
            botonFacebook.setOnMouseExited((event) -> {
                etiquetaFacebook.setVisible(false);
            });

            botonesNodesList.addAnimatedNode(redes);
            botonesNodesList.addAnimatedNode(botonTwitter);
            botonesNodesList.addAnimatedNode(botonInstagram);
            botonesNodesList.addAnimatedNode(botonFacebook);

            botonesNodesList.setSpacing(10);
            botonesNodesList.relocate(1150, 750);
            botonesNodesList.setRotate(-180);
            Ventana.getChildren().addAll(botonesNodesList, etiquetaFacebook, etiquetaInstagram, etiquetaTwitter);
            botonesNodesList.toFront();
        } catch (Exception e) {
            not.error("Error", "Error al intentar cargar las redes sociales.");
        }

    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void calcularNodos() {
        cargaNick();
        MuestraImagen();
    }

    public void cargaNick() {
        if (usuario != null) {
            botonPerfil.setText(usuario.getNick().toUpperCase());
        }
    }

    public void MuestraImagen() {
        if (usuario.getFotoFile() == null) {
            if (usuario.getFoto() != null) {
                try {
                    caraIV.setImage(new Image("C:\\tempTurismo\\" + usuario.getFoto()));
                } catch (Exception e) {
                    try {
                        caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
                    } catch (Exception ex) {
                        caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
                    }
                }
            } else {
                caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
            }

        } else {
            caraIV.setImage(new Image(usuario.getFotoFile().toURI().toString()));
        }

//        if (usuario.getFotoFile() != null) {
//            caraIV.setImage(new Image(usuario.getFotoFile().toURI().toString()));
//        } else {
//            try {
//                caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
//            } catch (Exception e) {
//                caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
//            }
//        }
    }

    private void styleInicio() {

        //Transicion
        FadeTransition ft = new FadeTransition(Duration.millis(500), gridpane);
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
        botonContactar.getStyleClass().add("botonMenu");
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
        } catch (Exception es) {
            not.error("Error", "Error al cargar la ventana Inicio");
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
        } catch (Exception es) {
            not.error("Error", "Error al cargar la ventana Actividades");
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
        } catch (Exception es) {
            not.error("Error", "Error al cargar la ventana Experiencias");
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
        } catch (Exception es) {
            not.error("Error", "Error al cargar la ventana Perfil");
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
        } catch (Exception es) {
            not.error("Error", "Error al cargar la ventana Buscar Actividades");
        }
    }

    @FXML
    private void irTransporte(ActionEvent event) {      //Boton informacion
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Transporte/Transporte.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        try {
            Parent root = loader.load();    //para obtener el controlador ; se ejecuta inicialice
            //anchorPane.getChildren().add(FXMLLoader.load(loader.getLocation()));
            Ventana.getChildren().add(root);
        } catch (Exception es) {
            not.error("Error", "Error al cargar la ventana Información");
        }
        //ActividadController actividadController=loader.getController(); por si hace falta
    }

    @FXML
    private void irContactar(ActionEvent event) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();
        String nombrefichero = "/Vista/Contactar/Contactar.fxml";
        loader.setLocation(getClass().getResource(nombrefichero));
        ContactarController contactarController = new ContactarController();
        try {
            Parent root = loader.load();             //para obtener el controlador ; se ejecuta inicialice
            contactarController = loader.getController();

            contactarController.setGestion(gestion);
            contactarController.setUsuario(usuario);
            contactarController.cargainicial();
            Ventana.getChildren().add(root);
        } catch (Exception es) {
            not.error("Error", "Error al cargar la ventana Contactar");
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
            not.error("Error", "Error al cerrar sesión");
        } catch (IOException ex) {
            not.error("Error", "Error al volver a la pantalla de login");
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

    public void setController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void efectoTransicion() {
        try {
            Transicion tr = new Transicion();
            tr.setRoot(Ventana);
            Ventana.toBack();
            tr.start();
        } catch (Exception e) {
            not.error("Error", "Error al cargar imagenes");
        }
    }

}
