package Vista.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Datos.Bda.subtiposDAO;
import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Subtipo;
import Modelo.Tipo;
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
    private Notificacion not;
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

        not = new Notificacion();

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
            double posicionY = 25;
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
            not.error("ERROR", "Error al conectar con la DB Turismo");

        } catch (Exception es) {
            not.error("ERROR", "Error al conectar con la DB Turismo");
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
        informacionDireccion.getStyleClass().add("textoActividad");
        informacionPrecio.getStyleClass().add("textoActividad");
        informacionHorario.getStyleClass().add("textoActividad");
        descripcionActividad.getStyleClass().add("textoActividad");
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
            not.error("ERROR", "Error al conectar con la DB Turismo");
        } catch (Exception es) {
            not.error("ERROR", "Error al cargar información");

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
            subtipo = subDAO.consultarSubtipoActividad(actividad).getNombre() + " - ";
        } catch (SQLException e) {
            not.error("ERROR", "Error al conectar con la DB Turismo");
        }
        etiquetaSubtipoTitulo.setText(subtipo + actividad.getNombre());
        etiquetaSubtipoTitulo.getStyleClass().add("tituloActividades");

        try {
            informacionDireccion.setText(actividad.getDireccion());
            if (informacionDireccion.getText().isEmpty()) {
                informacionDireccion.setText("Sin dirección");
            }
        } catch (Exception e) {
            informacionDireccion.setText("Sin dirección");
        }

        try {
            informacionPrecio.setText(String.valueOf(actividad.getPrecio()));
            if (informacionPrecio.getText().equals("0.0")) {
                informacionPrecio.setText("Gratis");
            } else {
                informacionPrecio.appendText("€ por persona");
            }
        } catch (Exception e) {
            informacionPrecio.setText("Gratis");
        }

        try {
            informacionHorario.setText(actividad.getHorario());
            if (informacionHorario.getText().isEmpty()) {
                informacionHorario.setText("Horario libre");
            }
        } catch (Exception e) {
            informacionHorario.setText("Horario libre");
        }

        try {
            descripcionActividad.setText(actividad.getDescripcion());
            if (descripcionActividad.getText().isEmpty()) {
                descripcionActividad.setText("Sin descripcion");
            }
        } catch (Exception e) {
            descripcionActividad.setText("Sin descripcion");
        }

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
            not.error("ERROR", "Error al conectar con la DB Turismo");
        }

    }

}
