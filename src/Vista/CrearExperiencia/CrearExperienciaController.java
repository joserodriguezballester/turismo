/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.CrearExperiencia;

import Datos.Bda.GestionBD;
import Datos.Bda.experienciasDAO;
import Datos.Bda.subtiposDAO;
import Datos.Bda.tiposDAO;
import Modelo.ActividadExperiencia;
import Modelo.Experiencia;
import Modelo.Subtipo;
import Modelo.Tipo;
import Modelo.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author 20857464y
 */
public class CrearExperienciaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXDatePicker datePickerFechaInicio;
    @FXML
    private JFXListView<ActividadExperiencia> listaActividadesCarrito;
    @FXML
    private JFXListView<ActividadExperiencia> listaActividadesElegir;
    @FXML
    private Label etiquetaActividades;
    @FXML
    private Label etiquetaCarrito;
    @FXML
    private JFXButton botonActividades;
    @FXML
    private JFXButton botonEliminar;
    @FXML
    private Label etiquetaFechaInicio;
    @FXML
    private Label etiquetaFechaFinal;
    @FXML
    private JFXDatePicker datePickerFechaFinal;
    @FXML
    private Label etiquetaHoraInicio;
    @FXML
    private Label etiquetaHoraFinal;
    @FXML
    private JFXTimePicker timePickerHoraInicio;
    @FXML
    private JFXTimePicker timePickerHoraFinal;
    @FXML
    private Label etiquetaNumPlazas;
    @FXML
    private JFXTextField textNumPlazas;
    @FXML
    private Label etiquetaNombreExperiencia;
    @FXML
    private JFXTextField textNombreExperiencia;
    @FXML
    private Label etiquetaPrecioTotal;
    @FXML
    private JFXTextField salidaPrecio;
    @FXML
    private JFXTextArea textDescripcion;

    private GestionBD gestion;
    private Usuario usuario;
    private Experiencia experiencia;
    @FXML
    private AnchorPane Ventana;
    @FXML
    private JFXComboBox<Tipo> comboBoxTipos;
    @FXML
    private JFXComboBox<Subtipo> comboBoxSubTipos;

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Experiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (experiencia != null) {
            etiquetaNombreExperiencia.setText(experiencia.getNombre());
            cargarActividades();
        }
    }

    @FXML
    private void AñadirActividad(ActionEvent event) {
    }

    @FXML
    private void EliminarActividad(ActionEvent event) {
    }

    private void cargarActividades() {
        for (ActividadExperiencia act : experiencia.getListaActividades()) {
            listaActividadesCarrito.getItems().add(act);
        }
        calcularPrecio();
    }

    private void calcularPrecio() {
        double precio = 0;
        for (ActividadExperiencia act : listaActividadesCarrito.getItems()) {
            precio += act.getPrecio();
        }
        etiquetaPrecioTotal.setText(String.valueOf(precio));
    }

    @FXML
    private void ActualizarDatosActividad(MouseEvent event) {
        ActividadExperiencia actExp = listaActividadesCarrito.getSelectionModel().getSelectedItem();
        textNumPlazas.setText(String.valueOf(actExp.getNumPlazas()));
        textDescripcion.setText(actExp.getActividad().getDescripcion());
    }

    private void actualizarTipos() {
        tiposDAO tiposDAO = new tiposDAO(gestion);
        try {
            for (Tipo tipo : tiposDAO.consultarTipos()) {
                comboBoxTipos.getItems().add(tipo);
            }
        } catch (SQLException e) {
//            EXCEPCION SQL
        }
    }

    @FXML
    private void actualizarSubtipos(ActionEvent event) {
        subtiposDAO subtiposDAO = new subtiposDAO(gestion);
        try {
            for (Subtipo subtipo : subtiposDAO.consultarSubtiposporTipo(comboBoxTipos.getSelectionModel().getSelectedItem())) {
                comboBoxSubTipos.getItems().add(subtipo);
            }
        } catch (Exception e) {
        }

    }
}
