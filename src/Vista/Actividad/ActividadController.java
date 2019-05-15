package Vista.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Tipo;
import Vista.Principal.PrincipalController;
//import static Vista.Principal.PrincipalController.panePerfil;
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
    private Notificacion not;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Image img = new Image("Imagenes/fondoActividad.jpg");
        ImageView imagev = new ImageView(img);

        imagev.setFitHeight(730);
        imagev.setFitWidth(1300);

        this.Ventana.getChildren().add(imagev);

        listaElementos.setVisible(false);

        not = new Notificacion();
        Ventana.setVisible(true);
        botonCerrarInformacion.getStyleClass().add("botonCerrarInformacion");
        paneInformacion.setVisible(false);
        paneInformacion.getStyleClass().add("paneInformacionActividades");

        scrollTipoActividades.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTipoActividades.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollTipoActividades.getStyleClass().add("scrollPaneActividades");
        paneListaBotones.getStyleClass().add("scrollPaneActividades");

        listaElementos.getStyleClass().add("listaActividades");
        scrollTipoActividades.toFront();
        listaElementos.toFront();
        paneInformacion.toFront();
//        PrincipalController.panePerfilMenu.toFront();
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
        } catch (SQLException ex) {
            not.error("ERROR SQL", "" + ex.getMessage()
                    + " en inicio --- ActividadController");
        } catch (Exception es) {
            es.printStackTrace();
            not.error("ERROR EXCEPTION", "" + es.getMessage()
                    + " en cargarActividades --- ActividadController");
        }

    }

    public void cargarActividades(Tipo tipo) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), listaElementos);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        listaElementos.setVisible(true);
        listaDatosActividades.clear();
        try {
            for (Actividad actividad : gestionBDActividad.consultarActividadesPorTipo(tipo)) {
                listaDatosActividades.add(actividad);
            }
            listaElementos.setItems(listaDatosActividades);
        } catch (SQLException ex) {
            not.error("ERROR SQL", "" + ex.getMessage()
                    + " en cargarActividades --- ActividadController");
        } catch (Exception es) {
            not.error("ERROR EXCEPTION", "" + es.getMessage()
                    + " en cargarActividades --- ActividadController");

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
        } catch (Exception es) {
            fotoActividad.setVisible(false);
            not.error("ERROR EXCEPTION", "" + es.getMessage()
                    + " en cargarInformacionActividad --- ActividadController");

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
