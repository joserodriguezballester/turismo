/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Buscador;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Modelo.Actividad;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class BuscadorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private GestionBD gestion;
    private Connection conn;
    actividadesDAO actDAO;
    @FXML
    private AnchorPane Ventana;
    @FXML
    private ScrollPane scrollPaneActividadesBuscador;
    @FXML
    private Pane paneActividadesBuscador;

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();
        inicio();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void inicio() {
        actDAO = new actividadesDAO(gestion);
        Pane pane;
        ImageView img;
        Label titulo;
        TextArea descripcion;
        int posicionX = 25;
        int posicionY = 10;
        try {
            for (Actividad act : actDAO.listarActividad()) {
                img = new ImageView();
                titulo = new Label();
                descripcion = new TextArea();
                pane = new Pane();
                pane.getStyleClass().add("paneActividadBuscador");
                pane.setLayoutX(posicionX);
                pane.setLayoutY(posicionY);
                posicionY += 250;
                pane.setPrefSize(900, 200);

//                FOTOS
                String foto = act.getFoto();
                if (foto == null) {
                    System.out.println("sin foto");
                } else {
                    try {
                        img.setImage(new Image("Imagenes/" + act.getFoto()));
                    } catch (Exception e) {
                        System.out.println("sin foto");
                    }

                    img.setLayoutX(20);
                    img.setLayoutY(20);
                    img.setFitHeight(160);
                    img.setFitWidth(180);
                    img.setPreserveRatio(false);

//                    TITULO
                    titulo.setLayoutX(240);
                    titulo.setLayoutY(20);
                    titulo.setText(act.getNombre());

//                    DESCRIPCION
                    descripcion.setLayoutX(240);
                    descripcion.setLayoutY(60);
                    descripcion.setPrefSize(630, 120);
                    descripcion.setEditable(false);
                    descripcion.setWrapText(true);
                    descripcion.setText(act.getDescripcion());
                    pane.getChildren().addAll(img, titulo, descripcion);
                    paneActividadesBuscador.getChildren().add(pane);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error sql");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
