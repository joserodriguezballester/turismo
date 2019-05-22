/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Experiencia;

import Datos.Bda.GestionBD;
import Datos.Bda.experienciasDAO;
import Modelo.ActividadExperiencia;
import Modelo.Experiencia;
import Modelo.Notificacion;
import Modelo.Usuario;
import Vista.CrearExperiencia.CrearExperienciaController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class ExperienciaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private GestionBD gestion;
    private experienciasDAO gestionBDExperiencias;
    private Usuario usuario;
    private Stage escenario;
    private ObservableList<Experiencia> listaExperiencias = FXCollections.observableArrayList();

    @FXML
    private AnchorPane Ventana;
    @FXML
    private JFXListView<Experiencia> listaVisualExperiencias;
    @FXML
    private JFXTextField salidaPrecio;
    @FXML
    private JFXButton botonCerrarInformacion;
    @FXML
    private Pane paneInformacion;
    @FXML
    private JFXTextArea textAreaDescripcion;
    @FXML
    private ImageView imagenExperiencia;
    @FXML
    private JFXTextField textFieldFechaValidez;
    @FXML
    private Label etiquetaListaActividades;
    @FXML
    private JFXListView<ActividadExperiencia> listaActividades;
    @FXML
    private JFXButton botonCrearExperiencia;
    @FXML
    private JFXButton botonModificarExperiencia;
    private Notificacion not;

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setListaExperiencias(ObservableList<Experiencia> listaExperienciasSeleccionadas) {
        listaExperiencias = listaExperienciasSeleccionadas;
        inicio();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        IMAGEN FONDO
        Image img = new Image("Imagenes/fondoExperienc3.jpg");
        ImageView imagev = new ImageView(img);

        imagev.setFitHeight(730);
        imagev.setFitWidth(1300);

        this.Ventana.getChildren().add(imagev);
        botonCrearExperiencia.toFront();
        listaVisualExperiencias.toFront();
        paneInformacion.toFront();

//        NOTIFICACION
        not = new Notificacion();

//        ESTILOS
        botonCrearExperiencia.getStyleClass().add("botonCrearExperiencia");
        botonModificarExperiencia.getStyleClass().add("botonCrearExperiencia");
        botonCerrarInformacion.getStyleClass().add("botonCerrarInformacion");
        paneInformacion.setVisible(false);
        paneInformacion.getStyleClass().add("paneInformacionActividades");

        textAreaDescripcion.getStyleClass().add("textoActividad");
        textFieldFechaValidez.getStyleClass().add("textoActividad");
        listaActividades.getStyleClass().add("textoActividad");
        salidaPrecio.getStyleClass().add("textoActividad");
    }

    private void inicio() {
        gestionBDExperiencias = new experienciasDAO(gestion);
        try {
            for (Experiencia exp : listaExperiencias) {
                listaVisualExperiencias.getItems().add(exp);
            }
        } catch (Exception es) {
            es.printStackTrace();
            not.error("ERROR EXCEPTION (initialize ExperienciaController)",
                    "Revisa el código y vuelve a intentarlo");
        }
    }

    @FXML
    public void cargarExperiencia() {
        listaActividades.getItems().clear();
        paneInformacion.setVisible(true);
        FadeTransition transicion = new FadeTransition(Duration.millis(1000), paneInformacion);
        transicion.setFromValue(0);
        transicion.setToValue(1);
        transicion.play();
        Experiencia experiencia = listaVisualExperiencias.getSelectionModel().getSelectedItem();
        textAreaDescripcion.setText(experiencia.getDescripcion());
        textFieldFechaValidez.setText(experiencia.getFechaTopeValidez().toString());
        for (ActividadExperiencia actExp : experiencia.getListaActividades()) {
            listaActividades.getItems().add(actExp);
        }
        salidaPrecio.setText(String.valueOf(experiencia.calcularPrecio()) + "€");
    }

    @FXML
    private void cerrarInformacion(ActionEvent event) {
        cerrarInfo();
    }

    private void cerrarInfo() {
        FadeTransition transicion = new FadeTransition(Duration.millis(1000), paneInformacion);
        transicion.setFromValue(1.0);
        transicion.setToValue(0);
        transicion.play();
    }

    @FXML
    private void CrearExperiencia(ActionEvent event) {
        CargarVentanaCrear();
    }

    @FXML
    private void ModificarExperiencia(ActionEvent event) {
        CargarVentanaCrear(listaVisualExperiencias.getSelectionModel().getSelectedItem());
    }

    private void CargarVentanaCrear() {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();

        CrearExperienciaController controlador;

        loader.setLocation(getClass().getResource("/Vista/CrearExperiencia/CrearExperiencia.fxml"));
        try {
            Parent root = loader.load();

            controlador = loader.getController();
            controlador.setGestion(gestion);
            controlador.setUsuario(usuario);
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
            not.error("ERROR IOException",
                    "en CargarVentanaCrear() --- ExperienciaController");
        } catch (Exception es) {
            es.printStackTrace();
            not.error("ERROR AL CARGAR VENTANA EXPERIENCIA",
                    "en CargarVentanaCrear --- ExperienciaController");
        }
    }

    private void CargarVentanaCrear(Experiencia experiencia) {
        Ventana.getChildren().removeAll(Ventana.getChildren());
        FXMLLoader loader = new FXMLLoader();

        CrearExperienciaController controlador;

        loader.setLocation(getClass().getResource("/Vista/CrearExperiencia/CrearExperiencia.fxml"));
        try {
            Parent root = loader.load();

            controlador = loader.getController();
            controlador.setGestion(gestion);
            controlador.setUsuario(usuario);
            controlador.setExperiencia(experiencia);
            Ventana.getChildren().add(root);
        } catch (IOException ex) {
            ex.printStackTrace();
            not.error("ERROR IOException",
                    "en CargarVentanaCrear(param) --- ExperienciaController");
        } catch (Exception es) {
            es.printStackTrace();
            not.error("ERROR AL CARGAR VENTANA EXPERIENCIA",
                    "en CargarVentanaCrear(param) --- ExperienciaController");
        }
    }

}
