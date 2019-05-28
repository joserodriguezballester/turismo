package Vista.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Datos.Bda.subtiposDAO;
import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Subtipo;
import Modelo.Tipo;
import Modelo.VentanaEmergente;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Grupo4
 */
public class ActividadController implements Initializable {

    private GestionBD gestion;
    private ObservableList<Button> botones = FXCollections.observableArrayList();
    private ObservableList<Actividad> listaDatosActividades = FXCollections.observableArrayList();
    private actividadesDAO gestionBDActividad;
    private subtiposDAO gestionSubtipos;
    private TranslateTransition translate;
    private TranslateTransition transl;
    private VentanaEmergente vent;
    private JFXButton boton;

    @FXML
    private AnchorPane Ventana;
    @FXML
    private ScrollPane scrollTipoActividades;
    @FXML
    private Pane paneListaBotones;
    @FXML
    private Pane paneInformacion;
    @FXML
    private JFXListView<Actividad> listaElementos = new JFXListView<Actividad>();
    @FXML
    private JFXButton botonCerrarInformacion;
    @FXML
    private Label etiquetaPrecio;
    @FXML
    private JFXComboBox<Subtipo> selectorSubtipos;
    @FXML
    private Label etiquetaSubtipoTitulo;
    @FXML
    private Label etiquetaHorario;
    @FXML
    private Label etiquetaDireccion;
    @FXML
    private JFXTextArea descripcionActividad;
    @FXML
    private ImageView fotoActividad;
    @FXML
    private WebView webViewActividad;
    @FXML
    private JFXTextField informacionDireccion;
    @FXML
    private JFXTextArea informacionHorario;
    @FXML
    private JFXTextField informacionPrecio;
    @FXML
    private Pane paneWebView;

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        inicio();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        vent = new VentanaEmergente();

        styleInicio();
    }

    private void inicio() {

        gestionBDActividad = new actividadesDAO(gestion);
        gestionSubtipos = new subtiposDAO(gestion);
        selectorSubtipos.toFront();
        selectorSubtipos.setVisible(false);
        try {
            List<Tipo> lista = gestionBDActividad.consultarTipoActividades();
            double posicionX = 5;
            double posicionY = 5;
            JFXButton boton;
            for (Tipo tipo : lista) {
                boton = new JFXButton(tipo.getNombre());
                boton.setLayoutX(posicionX);
                boton.setLayoutY(posicionY);
                posicionY += 75;
                boton.setMinSize(145, 70);
                boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent me) {
                        cargarActividades(tipo);
                    }
                });
                boton.getStyleClass().add("botonActividad");
                botones.add(boton);
            }

            for (Button botonLista : botones) {
                paneListaBotones.getChildren().add(botonLista);
            }
        } catch (SQLException ex) {
            vent.info("ERROR", "Error al conectar con la DB turismo", Ventana);
        } catch (Exception es) {
            vent.info("ERROR", "Error al conectar con la DB turismo", Ventana);
        }

    }

    private void styleInicio() {

        //Imagen fondo
        Image img = new Image("Imagenes/fondoActividad.jpg");
        ImageView imagev = new ImageView(img);

        imagev.setFitHeight(730);
        imagev.setFitWidth(1300);

        this.Ventana.getChildren().add(imagev);

        //Panes
        listaElementos.setVisible(false);
        Ventana.setVisible(true);
        paneInformacion.setVisible(false);

        scrollTipoActividades.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTipoActividades.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        listaElementos.toFront();
        scrollTipoActividades.toFront();
        paneInformacion.toFront();

        //Estilos
        botonCerrarInformacion.getStyleClass().add("botonCerrarInformacion");
        paneInformacion.getStyleClass().add("paneInformacionActividades");
        scrollTipoActividades.getStyleClass().add("scrollPaneActividades");
        paneListaBotones.getStyleClass().add("scrollPaneActividades");
        listaElementos.getStyleClass().add("listaActividades");
        selectorSubtipos.getStyleClass().add("combo");

    }

    public void cargarActividades(Tipo tipo) {
        transicionCargarActividades();
        selectorSubtipos.setVisible(true);
        try {
            selectorSubtipos.getItems().clear();
            for (Subtipo subtipo : gestionSubtipos.consultarSubtiposporTipo(tipo)) {
                selectorSubtipos.getItems().add(subtipo);
            }
            for (Actividad actividad : gestionBDActividad.consultarActividadesPorTipo(tipo)) {
                listaDatosActividades.add(actividad);
            }
            listaElementos.setItems(listaDatosActividades);
        } catch (SQLException ex) {
            vent.info("ERROR", "Error al conectar con la DB turismo", Ventana);
        } catch (Exception es) {
            vent.info("ERROR", "Error al cargar la información", Ventana);

        }

        if (paneInformacion.isVisible()) {
            paneInformacion.setVisible(false);
        }

    }

    private void transicionCargarActividades() {
        translate = new TranslateTransition(Duration.seconds(1), listaElementos);
        translate.setFromX(0);
        translate.setToX(170);
        translate.setInterpolator(Interpolator.LINEAR);
//        listaElementos.setLayoutX(listaElementos.getLayoutX() + 50);
        translate.play();

        transl = new TranslateTransition(Duration.seconds(1), boton);
        translate.setFromX(0);
        translate.setToX(10);
        translate.setInterpolator(Interpolator.LINEAR);
//        listaElementos.setLayoutX(listaElementos.getLayoutX() + 50);
        translate.play();

        FadeTransition ft = new FadeTransition(Duration.millis(1500), listaElementos);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        listaElementos.setVisible(true);
        listaDatosActividades.clear();

    }

    private void cerrarInformacionEvent(ActionEvent event) {
        cerrarInformacion();
    }

    @FXML
    private void cerrarInformacion() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), paneInformacion);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
    }

    @FXML
    private void cargarInformacionActividad(MouseEvent event) {
        paneInformacion.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), paneInformacion);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        Actividad actividad = listaElementos.getSelectionModel().getSelectedItem();

        subtiposDAO subDAO = new subtiposDAO(gestion);
        String subtipo = "";
        try {
            subtipo = subDAO.consultarSubtipoActividad(actividad).getNombre();
        } catch (SQLException e) {
            vent.info("ERROR", "No se ha podido encontrar el subtipo", Ventana);
        }
        etiquetaSubtipoTitulo.setText(subtipo + " - " + actividad.getNombre());
        etiquetaSubtipoTitulo.getStyleClass().add("tituloActividades");

        if (actividad.getDireccion().equals("")) {
            informacionDireccion.setText("Sin dirección");
        } else {
            informacionDireccion.setText(actividad.getDireccion());
        }
        informacionDireccion.getStyleClass().add("textoActividad");

        if (actividad.getPrecio() == 0) {
            informacionPrecio.setText("Gratis");
        } else {
            informacionPrecio.setText(String.valueOf(actividad.getPrecio()) + "€ por persona");
        }
        informacionPrecio.getStyleClass().add("textoActividad");
        if (actividad.getHorario().equals("")) {
            informacionHorario.setText("Horario libre");
        } else {
            informacionHorario.setText(actividad.getHorario());
        }
        informacionHorario.getStyleClass().add("textoActividad");

        if (actividad.getDescripcion().equals("")) {
            descripcionActividad.setText("Sin descripcion");
        } else {
            descripcionActividad.setText(actividad.getDescripcion());
        }
        descripcionActividad.getStyleClass().add("textoActividad");

        try {
            fotoActividad.setImage(new Image("Imagenes/" + actividad.getFoto()));
        } catch (Exception e) {
            fotoActividad.setImage(new Image("Imagenes/iconos/sinFoto.png"));
        }
        fotoActividad.setFitHeight(337);
        fotoActividad.setFitWidth(330);
        fotoActividad.setPreserveRatio(false);

        if (actividad.getUrl() == null) {
            paneWebView.setVisible(false);
            webViewActividad.setVisible(false);
        } else {
            paneWebView.setVisible(true);
            webViewActividad.setVisible(true);
            webViewActividad.setMaxSize(426, 240);
            webViewActividad.setMinSize(426, 240);
            webViewActividad.resize(426, 240);
            webViewActividad.getEngine().load(actividad.getUrl());
        }
    }

    @FXML
    private void cargarActividades(ActionEvent event) {
        cargarActividades(selectorSubtipos.getSelectionModel().getSelectedItem());
    }

    public void cargarActividades(Subtipo subtipo) {
        try {
            listaDatosActividades.clear();
            for (Actividad actividad : gestionBDActividad.consultarActividadesPorTipoYSubTipo(subtipo)) {
                listaDatosActividades.add(actividad);
            }
            listaElementos.setItems(listaDatosActividades);
        } catch (SQLException e) {
            vent.info("ERROR", "No se han podido cargar las actividades", Ventana);
        }

    }

}
