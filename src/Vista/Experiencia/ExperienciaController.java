/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Experiencia;

import Datos.Bda.GestionBD;
import Datos.Bda.experienciasDAO;
import Modelo.Experiencia;
import Modelo.Notificacion;
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
    private JFXDatePicker entradaFechaInicio;
    @FXML
    private JFXDatePicker entradaFechaFinal;
    @FXML
    private JFXTextField textFieldDuracion;

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
            Notificacion.error("ERROR SQL EXCEPTION", "(initialize ExperienciaController");
        } catch (Exception es){
            Notificacion.error("ERROR EXCEPTION (initialize ExperienciaController)",
                                    "Revisa el código y vuelve a intentarlo");
        }
    }

    @FXML
    public void cargarExperiencia() {
        paneInformacion.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), paneInformacion);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        Experiencia experiencia = listaVisualExperiencias.getSelectionModel().getSelectedItem();
        textAreaDescripcion.setText(experiencia.getDescripcion());
        textFieldFechaValidez.setText(experiencia.getFechaTopeValidez().toString());
    }

    @FXML
    private void cerrarInformacion(ActionEvent event) {
        cerrarInfo();
    }

    private void cerrarInfo() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), paneInformacion);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
    }
}
