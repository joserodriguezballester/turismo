/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Buscador;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Modelo.Actividad;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

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
    ObservableList<Pane> listaPaneActividades = FXCollections.observableArrayList();
    ObservableList<Actividad> listaActividades = FXCollections.observableArrayList();

    actividadesDAO actDAO;
    @FXML
    private AnchorPane Ventana;
    @FXML
    private ScrollPane scrollPaneActividadesBuscador;
    @FXML
    private Pane paneActividadesBuscador;
    @FXML
    private JFXTextField entradaBusqueda;
    @FXML
    private JFXTextField entradaPrecioMinimo;
    @FXML
    private JFXTextField entradaPrecioMaximo;

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();
        inicio();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image img = new Image("Imagenes/fondoBuscar3.jpg");
        ImageView imagev = new ImageView(img);

        imagev.setFitHeight(730);
        imagev.setFitWidth(1300);

        this.Ventana.getChildren().add(imagev);
        imagev.toBack();
    }

    public void inicio() {
        actDAO = new actividadesDAO(gestion);
        cargarActividades();

        scrollPaneActividadesBuscador.getStyleClass().add("paneBuscador");
        entradaBusqueda.getStyleClass().add("buscador");

//        scrollPaneActividadesBuscador.setOpacity(0.4);
    }

    private void cargarActividades() {
        paneActividadesBuscador.getChildren().clear();
        Pane pane;
        ImageView img;
        Label titulo;
        TextArea descripcion;
        int posicionX = 25;
        int posicionY = 10;
        try {
            List<Actividad> lista = actDAO.listarActividad();
            if (!entradaBusqueda.getText().isEmpty()) {
                lista = buscarActividades(lista);
            }
            String stringPrecioMinimo = entradaPrecioMinimo.getText();
            String stringPrecioMaximo = entradaPrecioMaximo.getText();
            if (stringPrecioMinimo.equals("") || !stringPrecioMaximo.equals("")) {
                lista = filtrarPorPrecio(stringPrecioMinimo, stringPrecioMaximo, lista);
            }

            for (Actividad act : lista) {
                img = new ImageView();
                titulo = new Label();
                descripcion = new TextArea();
                pane = new Pane();
                pane.getStyleClass().add("paneActividadBuscador");
                pane.setLayoutX(posicionX);
                pane.setLayoutY(posicionY);
                posicionY += 210;
                pane.setPrefSize(900, 200);

//                IMAGEN
                String foto = act.getFoto();
                if (foto == null) {
                    foto = "iconos/sinFoto.png";
                }
                try {
                    img.setImage(new Image("Imagenes/" + foto));
                } catch (Exception e) {
                    img.setImage(new Image("Imagenes/iconos/sinFoto.png"));
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
                titulo.getStyleClass().add("tituloBusc");

//                    DESCRIPCION
                descripcion.setLayoutX(240);
                descripcion.setLayoutY(60);
                descripcion.setPrefSize(630, 120);
                descripcion.setEditable(false);
                descripcion.setWrapText(true);
                descripcion.setText(act.getDescripcion());
//                descripcion.setStyle("-fx-background-color: rgb(255, 244, 229)");
                descripcion.getStyleClass().add("descripcionBusc");

                pane.getChildren().addAll(img, titulo, descripcion);
                listaPaneActividades.add(pane);
                paneActividadesBuscador.getChildren().add(pane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error sql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Actividad> buscarActividades(List<Actividad> lista) {
        List<Actividad> encontrados = new ArrayList<>();
        String descripcion;
        String nombre;
        String busqueda = entradaBusqueda.getText().toLowerCase();
        for (Actividad act : lista) {
            try {
                descripcion = act.getDescripcion().toLowerCase();
                nombre = act.getNombre().toLowerCase();
                if (!busqueda.isEmpty()) {
                    if (descripcion.contains(busqueda) || nombre.contains(busqueda)) {
                        encontrados.add(act);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return encontrados;
    }

    private List<Actividad> filtrarPorPrecio(String stringPrecioMinimo, String stringPrecioMaximo, List<Actividad> lista) {
        List<Actividad> encontrados = new ArrayList<>();
        double precioMinimo = 0;
        double precioMaximo = 99999;

        if (!stringPrecioMinimo.equals("")) {
            precioMinimo = Double.parseDouble(stringPrecioMinimo);
        }
        if (!stringPrecioMaximo.equals("")) {
            precioMaximo = Double.parseDouble(stringPrecioMaximo);
        }

        for (Actividad act : lista) {
            if (act.getPrecio() > precioMinimo && act.getPrecio() < precioMaximo) {
                encontrados.add(act);
            }
        }
        return encontrados;
    }

    @FXML
    private void buscar(KeyEvent event) {
        cargarActividades();
    }

    @FXML
    private void filtrarPorPrecio(KeyEvent event) {
        cargarActividades();
    }
}
