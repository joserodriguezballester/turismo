/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador.TiposSubtipos;

import Datos.Bda.GestionBD;
import Datos.Bda.subtiposDAO;
import Datos.Bda.tiposDAO;
import Modelo.Notificacion;
import Modelo.Subtipo;
import Modelo.Tipo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author 20857464y
 */
public class TiposSubtiposController implements Initializable {

    private GestionBD gestion;
    private tiposDAO tipDAO;
    private subtiposDAO subDAO;
    private Notificacion not;
    private ObservableList<Tipo> listaTipos;
    private ObservableList<Subtipo> listaSubtipos;
    private Tipo tipoSeleccionado;
    private Subtipo subTipoSeleccionado;

    @FXML
    private TableView<Tipo> tablaTipos;
    @FXML
    private TableView<Subtipo> tablaSubtipos;
    @FXML
    private TableColumn<Tipo, Integer> columnaIdTipo;
    @FXML
    private TableColumn<Tipo, String> columnaNombreTipo;
    @FXML
    private TableColumn<Subtipo, Integer> columnaIdSubtipo;
    @FXML
    private TableColumn<Subtipo, String> columnaTipoSubtipo;
    @FXML
    private TableColumn<Subtipo, String> columnaNombreSubtipo;
    @FXML
    private JFXButton botonAñadirTipo;
    @FXML
    private JFXButton botonModificarTipo;
    @FXML
    private JFXButton botonBorrarTipo;
    @FXML
    private JFXButton botonAñadirSubtipo;
    @FXML
    private JFXButton botonModificarSubtipo;
    @FXML
    private JFXButton botonBorrarSubtipo;
    @FXML
    private JFXTextField nombreSubtipo;
    @FXML
    private JFXComboBox<Tipo> comboboxTipo;
    @FXML
    private JFXTextField nombreTipo;
    @FXML
    private AnchorPane anchorP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        inicio();
    }

    private void inicio() {
        not = new Notificacion();
        tipDAO = new tiposDAO(gestion);
        subDAO = new subtiposDAO(gestion);
        botonAñadirTipo.getStyleClass().add("botonAnyadirTiposSubtipos");
        botonAñadirSubtipo.getStyleClass().add("botonAnyadirTiposSubtipos");

        botonModificarTipo.getStyleClass().add("botonModificarTiposSubtipos");
        botonModificarSubtipo.getStyleClass().add("botonModificarTiposSubtipos");

        botonBorrarTipo.getStyleClass().add("botonBorrarTiposSubtipos");
        botonBorrarSubtipo.getStyleClass().add("botonBorrarTiposSubtipos");

        anchorP.getStyleClass().add("fondoAdminTiposSubtipos");

        cargarTipos();
    }

    private void cargarTipos() {

        try {
            listaTipos = FXCollections.observableList(tipDAO.consultarTipos());
            comboboxTipo.setItems(listaTipos);
            tablaTipos.setItems(listaTipos);
            columnaIdTipo.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnaNombreTipo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        } catch (Exception e) {
            not.error("Error", "Error cargando los tipos");
        }
    }

    @FXML
    private void cargarSubtipos(MouseEvent event) {
        cargarSubtipos();
    }

    private void cargarSubtipos() {
        try {
            tipoSeleccionado = tablaTipos.getSelectionModel().getSelectedItem();
        } catch (Exception e) {
            not.error("Error", "Debe seleccionar un tipo");
        }

        nombreTipo.setText
        (tipoSeleccionado.getNombre());
        try {
            listaSubtipos = FXCollections.observableList(subDAO.consultarSubtiposporTipo(tipoSeleccionado));
            tablaSubtipos.setItems(listaSubtipos);
            columnaIdSubtipo.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnaTipoSubtipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            columnaNombreSubtipo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        } catch (Exception e) {
            not.error("Error", "Error cargando los subtipos");
        }
        cargarTipos();
    }

    @FXML
    private void añadirTipo(ActionEvent event) {
        Tipo tipo = new Tipo(nombreTipo.getText());
        try {
            tipDAO.insertarTipo(tipo);
            cargarTipos();
        } catch (Exception e) {
            not.error("Error", "No se ha podido insertar el nuevo tipo");
        }
        cargarSubtipos();
    }

    @FXML
    private void modificarTipo(ActionEvent event) {
        try {
            tipoSeleccionado.setNombre(nombreTipo.getText());
            tipDAO.actualizarTipo(tipoSeleccionado);
            cargarTipos();
        } catch (Exception e) {
            not.error("Error", "No se ha podido modificar el tipo");
        }
        cargarSubtipos();
    }

    @FXML
    private void borrarTipo(ActionEvent event) {
        try {
            tipDAO.borrarTipo(tipoSeleccionado);
        } catch (Exception e) {
            e.printStackTrace();
            not.error("Error", "No se ha podido borrar el tipo");
        }
        cargarSubtipos();
    }

    @FXML
    private void añadirSubtipo(ActionEvent event) {
        Tipo tipo = comboboxTipo.getValue();
        try {
            subDAO.insertarSubtipo(new Subtipo(tipo, nombreSubtipo.getText()), tipo);
        } catch (Exception e) {
            not.error("Error", "No se ha podido insertar el nuevo subtipo");
        }
        cargarSubtipos();
    }

    @FXML
    private void modificarSubtipo(ActionEvent event) {
        Tipo tipo = comboboxTipo.getValue();
        System.out.println(tipo.getId());
        System.out.println(tipo.getNombre());
        try {
            subTipoSeleccionado.setNombre(nombreSubtipo.getText());
            subDAO.actualizarSubtipo(subTipoSeleccionado, tipo);
        } catch (Exception e) {
            e.printStackTrace();
            not.error("Error", "No se ha podido modificar el subtipo");
        }
        cargarSubtipos();
    }

    @FXML
    private void borrarSubtipo(ActionEvent event) {
        try {
            subDAO.borrarSubtipo(subTipoSeleccionado);
        } catch (Exception e) {
            not.error("Error", "No se ha podido borrar el nuevo subtipo");
        }
        cargarSubtipos();
    }

    @FXML
    private void cargarDatosSubtipo(MouseEvent event) {
        subTipoSeleccionado = tablaSubtipos.getSelectionModel().getSelectedItem();
        nombreSubtipo.setText(subTipoSeleccionado.getNombre());
    }
}
