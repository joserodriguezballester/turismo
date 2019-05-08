package Vista.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Tipo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class ActividadController implements Initializable {

    @FXML
    private AnchorPane Ventana;
    @FXML
    private ScrollPane scrollTipoActividades = new ScrollPane();
    @FXML
    private Pane paneListaBotones;
    @FXML
    private Pane paneInformacion;
    @FXML
    private JFXListView<Actividad> listaElementos = new JFXListView<Actividad>();
    @FXML
    private JFXButton botonCerrarInformacion;
    @FXML
    private Label tituloActividad;

    private static GestionBD gestion;
    private ObservableList<Button> botones = FXCollections.observableArrayList();
    private ObservableList<Actividad> listaDatosActividades = FXCollections.observableArrayList();
    private actividadesDAO gestionActividad;
    private Tipo tipoElegido = null;

    public static void setGestion(GestionBD gestion) {
        ActividadController.gestion = gestion;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tituloActividad.getStyleClass().add("tituloActividades");
        botonCerrarInformacion.getStyleClass().add("botonCerrarInformacion");
        paneInformacion.setVisible(false);
        paneInformacion.getStyleClass().add("paneInformacionActividades");
        gestionActividad = new actividadesDAO(gestion);
        scrollTipoActividades.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTipoActividades.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        try {
            List<Tipo> lista = gestionActividad.consultarTipoActividades();
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
//            MENSAJE DE ERROR
        } catch (Exception e) {
//            MENSAJE DE ERROR
        }

    }

    public void cargarActividades(Tipo tipo) {
        listaDatosActividades.clear();
        try {
            for (Actividad actividad : gestionActividad.consultarActividadesPorTipo(tipo)) {
                listaDatosActividades.add(actividad);
            }
            listaElementos.setItems(listaDatosActividades);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarInformacion(ActionEvent event) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), paneInformacion);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
    }

    @FXML
    private void cargarInformacionActividad(MouseEvent event) {
//        try {
//            Notificacion notificacion = new Notificacion();
//            notificacion.error("ERROR", "Esto es un error");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        paneInformacion.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(500), paneInformacion);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        Actividad actividad = listaElementos.getSelectionModel().getSelectedItem();
        tituloActividad.setText(actividad.getNombre());
    }

    private void notificacion() {

    }
}
