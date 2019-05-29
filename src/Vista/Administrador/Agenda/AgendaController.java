/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador.Agenda;

import Datos.Bda.GestionBD;
import Datos.Bda.experienciasActividadesDAO;
import Datos.Bda.experienciasDAO;
import Modelo.ActividadExperiencia;
import Modelo.Notificacion;
import Modelo.Subtipo;
import Modelo.Tipo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author 20857464y
 */
public class AgendaController implements Initializable {

    private GestionBD gestion;
    private Notificacion not;
    experienciasActividadesDAO actexpDAO;
    @FXML
    private AnchorPane anchorP;
    @FXML
    private JFXDatePicker datePickerInicio;
    @FXML
    private JFXDatePicker datePickerFinal;
    @FXML
    private JFXButton botonComprobarAgenda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        inicio();
    }

    private void inicio() {
        not = new Notificacion();
        anchorP.getStyleClass().add("fondoAdminAgenda");

        actexpDAO = new experienciasActividadesDAO(gestion);
        LocalDate minDate = LocalDate.now();
        datePickerInicio.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(minDate));
            }
        });
        datePickerFinal.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(minDate));
            }
        });
    }

    @FXML
    private void cargarActividades(ActionEvent event) {
        LocalDate fechaInicio = datePickerInicio.getValue();
        LocalDate fechaFinal = datePickerFinal.getValue();
        Tipo tipo = null;
        Subtipo subtipo = null;
        try {
            for (ActividadExperiencia act : actexpDAO.consultarAgenda(fechaInicio, fechaFinal, tipo, subtipo)) {
                System.out.println(act);
            }
        } catch (Exception e) {
            e.printStackTrace();
            not.error("Error", "No se han podido cargar la actividades de la agenda");
        }

    }
}
