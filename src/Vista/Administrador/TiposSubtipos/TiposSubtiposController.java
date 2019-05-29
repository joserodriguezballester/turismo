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
            not.error("Error", "Error al intentar cargar los tipos");
        }
    }
    
    @FXML
    private void cargarSubtipos(MouseEvent event) {
        tipoSeleccionado = tablaTipos.getSelectionModel().getSelectedItem();
        nombreTipo.setText(tipoSeleccionado.getNombre());
        try {
            listaSubtipos = FXCollections.observableList(subDAO.consultarSubtiposporTipo(tipoSeleccionado));
            tablaSubtipos.setItems(listaSubtipos);
            columnaIdSubtipo.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnaTipoSubtipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            columnaNombreSubtipo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        } catch (Exception e) {
            not.error("Error", "Error al intentar cargar los subtipos");
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
            not.alert("Error", "No se ha podido insertar el nuevo tipo");
        }
    }
    
    @FXML
    private void modificarTipo(ActionEvent event) {
        try {
            tipoSeleccionado.setNombre(nombreTipo.getText());
            tipDAO.actualizarTipo(tipoSeleccionado);
            cargarTipos();
        } catch (Exception e) {
            not.alert("Error", "No se ha podido modificar el tipo");
        }
    }
    
    @FXML
    private void borrarTipo(ActionEvent event) {
        try {
            System.out.println(tipoSeleccionado);
            tipDAO.borrarTipo(tipoSeleccionado);
        } catch (Exception e) {
            not.alert("Error", "No se ha podido borrar el tipo");
        }
    }
    
    @FXML
    private void añadirSubtipo(ActionEvent event) {
    }
    
    @FXML
    private void modificarSubtipo(ActionEvent event) {
    }
    
    @FXML
    private void borrarSubtipo(ActionEvent event) {
    }
    
    @FXML
    private void cargarDatosSubtipo(MouseEvent event) {
    }
}
