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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private static GestionBD gestion;
    private experienciasDAO gestionBDExperiencias;

    @FXML
    private AnchorPane Ventana;
    @FXML
    private JFXListView<Experiencia> listaVisualExperiencias;

    public static void setGestion(GestionBD gestion) {
        ExperienciaController.gestion = gestion;
    }
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botonCerrarInformacion.getStyleClass().add("botonCerrarInformacion");
        paneInformacion.setVisible(false);
        paneInformacion.getStyleClass().add("paneInformacionActividades");
        gestionBDExperiencias = new experienciasDAO(gestion);
        try {
            for (Experiencia exp : gestionBDExperiencias.consultarTodasExperiencias()) {
                listaVisualExperiencias.getItems().add(exp);
            }
        } catch (SQLException e) {
//            ERROR SQL
            e.printStackTrace();
        } catch (Exception e) {
//            ERROR  
            e.printStackTrace();
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
}
