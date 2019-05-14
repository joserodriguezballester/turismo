/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.CrearExperiencia;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Datos.Bda.experienciasDAO;
import Datos.Bda.subtiposDAO;
import Datos.Bda.tiposDAO;
import Modelo.Actividad;
import Modelo.ActividadExperiencia;
import Modelo.Experiencia;
import Modelo.Notificacion;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
    private JFXListView<Actividad> listaActividadesElegir;
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
    private Notificacion not = new Notificacion();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
        botonActividades.getStyleClass().add("botonAnyadir");
        botonEliminar.getStyleClass().add("botonEliminar");
    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        actualizarTipos();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (experiencia == null) {
            experiencia = new Experiencia(usuario.getId());
        }
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
        textNombreExperiencia.setText(experiencia.getNombre());
        cargarActividadesExperiencia();
        actualizarTipos();
    }

    @FXML
    private void AñadirActividad(ActionEvent event) {
        int orden = 1;
        boolean actividadValida = false;
        double precioActividad = 0;
        int numPlazas = 0;
        LocalDateTime fechaInicio = null;
        LocalDate diaInicio = null;
        LocalTime horaInicio = null;

        LocalDateTime fechaFinal = null;
        LocalDate diaFinal = null;
        LocalTime horaFinal = null;

        Actividad actividad = null;
//        VALIDACIONES
        try {
            //        VALIDA FECHA INICIO
            try {
                diaInicio = datePickerFechaInicio.getValue();
                actividadValida = true;
            } catch (Exception e) {
                actividadValida = false;
                not.error("Fecha Inicio", "Debes incluir una fecha de inicio para la actividad.");
                throw new Exception();
            }

//        VALIDA HORA INICIO
            try {
                horaInicio = timePickerHoraInicio.getValue();
                actividadValida = true;
            } catch (Exception e) {
                actividadValida = false;
                not.error("Hora Inicio", "Debes incluir una hora de inicio para la actividad.");
                throw new Exception();
            }

//        VALIDA FECHA FINAL
            try {
                diaFinal = datePickerFechaFinal.getValue();
                actividadValida = true;
            } catch (Exception e) {
                actividadValida = false;
                not.error("Fecha Final", "Debes incluir una fecha final para la actividad.");
                throw new Exception();
            }

//        VALIDA HORA FINAL
            try {
                horaFinal = timePickerHoraFinal.getValue();
                actividadValida = true;
            } catch (Exception e) {
                actividadValida = false;
                not.error("Hora Final", "Debes incluir una hora final para la actividad.");
                throw new Exception();
            }

//        VALIDA ACTIVIDAD ELEGIDA
            try {
                actividad = listaActividadesElegir.getSelectionModel().getSelectedItem();
                actividadValida = true;
            } catch (Exception e) {
                actividadValida = false;
                not.error("Elige una actividad", "Debes elegir una actividad de la lista.");
                throw new Exception();
            }

//        COMPROBAR LA COMPOSICION DE LA FECHA DE INICIO
            try {
                fechaInicio = LocalDateTime.of(diaInicio, horaInicio);
                actividadValida = true;
            } catch (Exception e) {
                actividadValida = false;
                not.error("Error", "Debes introducir una fecha y hora válida.");
                throw new Exception();
            }
            //        COMPROBAR LA COMPOSICION DE LA FECHA FINAL
            try {
                fechaFinal = LocalDateTime.of(diaFinal, horaFinal);
                actividadValida = true;
            } catch (Exception e) {
                actividadValida = false;
                not.error("Error", "Debes introducir una fecha y hora válida.");
                throw new Exception();
            }
//        COMPROBAR DISPONIBILIDAD
            if (actividadValida) {
                for (ActividadExperiencia actExp : listaActividadesCarrito.getItems()) {
                    if (fechaInicio.isAfter(actExp.getFechaInicio()) && fechaInicio.isBefore(actExp.getFechaFinal())) {
                        actividadValida = false;
                        not.error("Error", "La fecha de inicio se solapa con otra actividad de tu lista.");
                        throw new Exception();
                    }
                    if (fechaFinal.isAfter(actExp.getFechaInicio()) && fechaFinal.isBefore(actExp.getFechaFinal())) {
                        actividadValida = false;
                        not.error("Error", "La fecha de inicio se solapa con otra actividad de tu lista.");
                        throw new Exception();
                    }
                }
            }
//        COMPROBAR PRECIO
            try {
                precioActividad = actividad.getPrecio();
                actividadValida = true;
            } catch (Exception e) {
                actividadValida = false;
                not.error("Error", "No se ha podido conseguir el precio de la actividad.");
                throw new Exception();
            }

//            COMPROBAR NUMERO DE PLAZAS
            try {
                numPlazas = Integer.parseInt(textNumPlazas.getText());
            } catch (Exception e) {
                throw new Exception();
            }
        } catch (Exception e) {
            not.error("Error", "No hemos podido añadir su actividad.\nVuelva a intentarlo.");
        }

        if (actividadValida) {
            ActividadExperiencia nueva = new ActividadExperiencia(
                    orden,
                    experiencia.getId(),
                    actividad,
                    fechaInicio,
                    fechaFinal,
                    precioActividad,
                    numPlazas);
            listaActividadesCarrito.getItems().add(nueva);
            calcularPrecio();
        }

    }

    @FXML
    private void EliminarActividad(ActionEvent event) {
        listaActividadesCarrito.getItems().removeAll(listaActividadesCarrito.getSelectionModel().getSelectedItems());
        calcularPrecio();
    }

    private void cargarActividadesExperiencia() {
        for (ActividadExperiencia act : experiencia.getListaActividades()) {
            listaActividadesCarrito.getItems().add(act);
        }
        calcularPrecio();
    }

    private void cargarTodasActividades() {
        listaActividadesElegir.getItems().clear();
        actividadesDAO actividadesDAO = new actividadesDAO(gestion);
        Tipo tipo = comboBoxTipos.getSelectionModel().getSelectedItem();
        Subtipo subtipo = comboBoxSubTipos.getSelectionModel().getSelectedItem();
        List<Actividad> lista;
        try {
            if (tipo != null && subtipo == null) {
                lista = actividadesDAO.consultarActividadesPorTipo(tipo);
                for (Actividad act : lista) {
                    listaActividadesElegir.getItems().add(act);
                }
            }
            if (subtipo != null) {
                lista = actividadesDAO.consultarActividadesPorTipoYSubTipo(tipo, subtipo);
                for (Actividad act : lista) {
                    listaActividadesElegir.getItems().add(act);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void calcularPrecio() {
        double precio = 0;
        for (ActividadExperiencia act : listaActividadesCarrito.getItems()) {
            precio += act.getPrecio() * act.getNumPlazas();
        }
        salidaPrecio.setText(String.valueOf(precio));
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
            e.printStackTrace();
        }
        cargarTodasActividades();
    }

    @FXML
    private void actualizarSubtipos(ActionEvent event) {
        if (!comboBoxSubTipos.getSelectionModel().isEmpty()) {
            comboBoxSubTipos.getItems().clear();
        }

        subtiposDAO subtiposDAO = new subtiposDAO(gestion);
        try {
            for (Subtipo subtipo : subtiposDAO.consultarSubtiposporTipo(comboBoxTipos.getSelectionModel().getSelectedItem())) {
                comboBoxSubTipos.getItems().add(subtipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cargarTodasActividades();
    }

    @FXML
    private void cargarActividades(ActionEvent event) {
        cargarTodasActividades();
    }
}
