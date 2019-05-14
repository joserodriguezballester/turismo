package Vista.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Tipo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
 * @author joser
 */
public class ActividadController implements Initializable {

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

    private GestionBD gestion;
    private ObservableList<Button> botones = FXCollections.observableArrayList();
    private ObservableList<Actividad> listaDatosActividades = FXCollections.observableArrayList();
    private actividadesDAO gestionBDActividad;

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        inicio();
    }
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Ventana.setVisible(true);
        botonCerrarInformacion.getStyleClass().add("botonCerrarInformacion");
        paneInformacion.setVisible(false);
        paneInformacion.getStyleClass().add("paneInformacionActividades");

        scrollTipoActividades.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTipoActividades.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private void inicio() {
        gestionBDActividad = new actividadesDAO(gestion);
        try {
            List<Tipo> lista = gestionBDActividad.consultarTipoActividades();
            System.out.println(lista.isEmpty());
            for (Tipo tipo : lista) {
                System.out.println(tipo);
            }
            double posicionX = 5;
            double posicionY = 15;
            JFXButton boton;
            for (Tipo tipo : lista) {
                boton = new JFXButton(tipo.getNombre());
                boton.setLayoutX(posicionX);
                boton.setLayoutY(posicionY);
                posicionY += 130;
                boton.setMinSize(186, 100);
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
        } catch (SQLException e) {
            Notificacion.error("ERROR SQL EXCEPTION", "Problemas con la DB"
                    + "(initialize ActividadController");
        } catch (Exception e) {
            e.printStackTrace();
            Notificacion.error("ERROR EXCEPTION", "Comprueba tu código"
                    + "(initialize ActividadController");
        }
    }

    public void cargarActividades(Tipo tipo) {
        paneInformacion.setVisible(false);
        listaDatosActividades.clear();
        try {
            for (Actividad actividad : gestionBDActividad.consultarActividadesPorTipo(tipo)) {
                listaDatosActividades.add(actividad);
            }
            listaElementos.setItems(listaDatosActividades);
        } catch (SQLException e) {
            Notificacion.error("ERROR SQL EXCEPTION", "Problemas con la DB"
                    + "(cargarActividades ActividadController");
        } catch (Exception e) {
            Notificacion.error("ERROR EXCEPTION", "Comprueba tu código"
                    + "(cargarActividades ActividadController");

        }
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
        etiquetaSubtipoTitulo.setText(actividad.getNombre());
        etiquetaSubtipoTitulo.getStyleClass().add("tituloActividades");

        informacionDireccion.setText(actividad.getDireccion());
        informacionDireccion.getStyleClass().add("textoActividad");

        informacionPrecio.setText(String.valueOf(actividad.getPrecio()) + "€ por persona");
        informacionPrecio.getStyleClass().add("textoActividad");

        informacionHorario.setText(actividad.getHorario());
        informacionHorario.getStyleClass().add("textoActividad");

        descripcionActividad.setText(actividad.getDescripcion());
        descripcionActividad.getStyleClass().add("textoActividad");
        try {
//            if (actividad.getUrl() == null) {
            if (actividad.getFoto() == null) {
                fotoActividad.setVisible(false);
            } else {
                fotoActividad.setVisible(true);
                fotoActividad.setImage(new Image("Imagenes/" + actividad.getFoto()));
                fotoActividad.setFitHeight(330);
                fotoActividad.setFitWidth(330);
                fotoActividad.setPreserveRatio(false);
            }
        } catch (Exception e) {
            fotoActividad.setVisible(false);
            Notificacion.error("ERROR EXCEPTION", "la foto no ha podido cargarse"
                    + "(cargarInformacionActividad ActividadController");
        }

        if (actividad.getUrl() == null) {
            paneWebView.setVisible(false);
            webViewActividad.setVisible(false);
            System.out.println("no tiene");
        } else {
            paneWebView.setVisible(true);
            webViewActividad.setVisible(true);
            webViewActividad.setMaxSize(426, 240);
            webViewActividad.setMinSize(426, 240);
            webViewActividad.resize(426, 240);
            webViewActividad.getEngine().load(actividad.getUrl());
        }
    }
}
