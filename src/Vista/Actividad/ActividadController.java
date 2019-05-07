/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Modelo.Actividad;
import Modelo.Tipo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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

    private GestionBD gestion;

    private ObservableList<Button> botones = FXCollections.observableArrayList();
    @FXML
    private Pane paneListaBotones;
    @FXML
    private Pane paneInformacion;

    ObservableList<Actividad> listaDatosActividades = FXCollections.observableArrayList();

    private actividadesDAO gestionActividad = new actividadesDAO(gestion);
    Tipo tipoElegido = null;
    @FXML
    private JFXListView<Actividad> listaElementos = new JFXListView<Actividad>();

    public ActividadController(GestionBD gestion) {
        this.gestion = gestion;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        paneListaBotones.setMinHeight(700);
//        paneListaBotones.setMaxHeight(15000);
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
                    public void handle(MouseEvent me) {
                        cargarActividades(tipo);
                    }
                });
                boton.setStyle("-fx-padding: 0.7em 0.57em;"
                        + "-fx-font-size: 14px;"
                        + "-jfx-button-type: RAISED;"
                        + "-fx-background-color: rgb(77,102,204);"
                        + "-fx-text-fill: WHITE;");
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
}
