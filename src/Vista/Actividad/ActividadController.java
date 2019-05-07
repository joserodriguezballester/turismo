/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Actividad;

import Datos.Bda.actividadesDAO;
import Modelo.Tipo;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
    private ScrollPane scrollTipoActividades;

    static private Connection conn;

    public static void setConn(Connection conexion) {
        conn = conexion;
    }

    /**
     * Initializes the controller class.
     */
    private ObservableList<Button> botones = FXCollections.observableArrayList();
    @FXML
    private Pane paneListaBotones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        paneListaBotones.setMinHeight(700);
//        paneListaBotones.setMaxHeight(15000);
        scrollTipoActividades.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollTipoActividades.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        actividadesDAO gestionActividad = new actividadesDAO(conn);

        try {
            List<Tipo> lista = gestionActividad.consultarTipoActividades();
            double posicionX = 13;
            double posicionY = 10;
            Button boton;
            
            for (int i = 0; i < 10; i++) {
                for (Tipo tipo : lista) {
                    boton = new Button(tipo.getNombre());
                    boton.setLayoutX(posicionX);
                    boton.setLayoutY(posicionY);
                    posicionY += 60;
                    boton.setMinSize(180, 50);
                    botones.add(boton);
                }
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
}
