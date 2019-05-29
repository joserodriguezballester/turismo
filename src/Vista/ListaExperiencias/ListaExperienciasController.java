/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.ListaExperiencias;

import Datos.Bda.GestionBD;
import Datos.Bda.experienciasDAO;
import Modelo.Actividad;
import Modelo.ActividadExperiencia;
import Modelo.Experiencia;
import Modelo.Notificacion;
import Modelo.Usuario;
import Vista.Experiencia.ExperienciaController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 20857464y
 */
public class ListaExperienciasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Usuario usuario;
    private GestionBD gestion;
    ObservableList<Pane> listaPaneExperiencias = FXCollections.observableArrayList();
    ObservableList<Experiencia> listaExperiencias = FXCollections.observableArrayList();
    ObservableList<Experiencia> listaExperienciasSeleccionadas = FXCollections.observableArrayList();
    private Notificacion not;
    experienciasDAO expDAO;

    @FXML
    private ScrollPane scrollPaneActividadesBuscador;
    @FXML
    private Pane paneActividadesBuscador;
    @FXML
    private JFXTextField entradaBusqueda;
    @FXML
    private JFXButton ImprimirExperiencias;
    @FXML
    private JFXButton botonConsultarExperiencias;
    @FXML
    private AnchorPane Ventana;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        inicio();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Image img = new Image("Imagenes/fondoexperiencialista1.jpg");
        ImageView imagev = new ImageView(img);

        imagev.setFitHeight(730);
        imagev.setFitWidth(1300);

//        imagev.setOpacity(0.8); 
        this.Ventana.getChildren().add(imagev);
        imagev.toBack();
    }

    private void inicio() {
        expDAO = new experienciasDAO(gestion);
        not = new Notificacion();
        botonConsultarExperiencias.getStyleClass().add("botonConsultarExperiencias");
        ImprimirExperiencias.getStyleClass().add("botonImprimirExperiencia");
        scrollPaneActividadesBuscador.getStyleClass().add("paneBuscador");
        entradaBusqueda.getStyleClass().add("buscador");
        cargarListaExperiencias();
    }

    private void cargarListaExperiencias() {
        paneActividadesBuscador.getChildren().clear();
        int posicionX = 25;
        int posicionY = 10;
        try {
            List<Experiencia> lista = expDAO.consultarTodasExperiencias();
            if (!entradaBusqueda.getText().isEmpty()) {
                lista = buscarActividades(lista);
            }
            for (Experiencia exp : lista) {
                final Pane pane;
                ImageView img;
                Label titulo;
                TextArea descripcion;
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
                String foto = exp.getFoto();
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
                titulo.setText(exp.getNombre());
                titulo.getStyleClass().add("tituloBusc");

//                    DESCRIPCION
                descripcion.setLayoutX(240);
                descripcion.setLayoutY(60);
                descripcion.setPrefSize(630, 120);
                descripcion.setEditable(false);
                descripcion.setWrapText(true);
                descripcion.setText(exp.getDescripcion());
//                descripcion.setStyle("-fx-background-color: rgb(255, 244, 229)");
                descripcion.getStyleClass().add("descripcionBusc");

                pane.getChildren().addAll(img, titulo, descripcion);

//                AÑADE PULSANDO LA DESCRIPCION
                final String estilo;
                descripcion.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    pane.getStyleClass().clear();
                    pane.getStyleClass().add(guardarExperiencia(exp));
                    event.consume();
                });

//                AÑADE PULSANDO EL PANE
                pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    pane.getStyleClass().clear();
                    pane.getStyleClass().add(guardarExperiencia(exp));
                    event.consume();
                });
                listaPaneExperiencias.add(pane);
                paneActividadesBuscador.getChildren().add(pane);
            }
        } catch (SQLException e) {
            not.error("ERROR", "No se ha podido conectar con la base de datos");
        } catch (Exception e) {
            not.error("ERROR", "Error al intentar listar experiencias");
        }
    }

    private String guardarExperiencia(Experiencia exp) {
        String estilo;
        boolean encontrado = false;
        for (Experiencia expBucle : listaExperienciasSeleccionadas) {
            if (expBucle.equals(exp)) {
                encontrado = true;
            }
        }
        if (!encontrado) {
            listaExperienciasSeleccionadas.add(exp);
            estilo = "paneActividadBuscadorSeleccionado";
        } else {
            listaExperienciasSeleccionadas.remove(exp);
            estilo = "paneActividadBuscador";
        }
        return estilo;

    }

    private List<Experiencia> buscarActividades(List<Experiencia> lista) {
        List<Experiencia> encontrados = new ArrayList<>();
        String descripcion;
        String nombre;
        String busqueda = entradaBusqueda.getText().toLowerCase();
        for (Experiencia exp : lista) {
            try {
                descripcion = exp.getDescripcion().toLowerCase();
                nombre = exp.getNombre().toLowerCase();
                if (!busqueda.isEmpty()) {
                    if (descripcion.contains(busqueda) || nombre.contains(busqueda)) {
                        encontrados.add(exp);
                    }
                }
            } catch (Exception e) {
                not.error("ERROR", "La busqueda ha fallado");
            }
        }
        return encontrados;
    }

    @FXML
    private void buscar(KeyEvent event) {
        cargarListaExperiencias();
    }

    @FXML
    private void imprimirExperiencias(ActionEvent event) {
        if (!listaExperienciasSeleccionadas.isEmpty()) {
            elejirArchivoTicket();
        } else {
            not.error("ELIGE UN CONJUNTO DE EXPERIENCIAS", "Debes elegir un conjunto de experiencias a imprimir");
        }
    }

    @FXML
    private void cargarExperiencias(ActionEvent event) {
        if (!listaExperienciasSeleccionadas.isEmpty()) {
            Ventana.getChildren().removeAll(Ventana.getChildren());
            FXMLLoader loader = new FXMLLoader();

            ExperienciaController controlador;

            loader.setLocation(getClass().getResource("/Vista/Experiencia/Experiencia.fxml"));
            try {
                Parent root = loader.load();

                controlador = loader.getController();
                controlador.setUsuario(usuario);
                controlador.setGestion(gestion);
                controlador.setListaExperiencias(listaExperienciasSeleccionadas);
                Ventana.getChildren().add(root);
            } catch (IOException ex) {
                not.error("ERROR", "No se ha podido conectar con la base de datos");
            } catch (Exception es) {
                not.error("ERROR", "Error al intentar cargar experiencias");
            }
        } else {
            not.error("ELIGE UN CONJUNTO DE EXPERIENCIAS", "Debes elegir un conjunto de experiencias a consultar");
        }
    }

    public void elejirArchivoTicket() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Archivo de Actividades");
        File archivo = dirChooser.showDialog(new Stage());
        generarTicket(archivo);
    }

    public void generarTicket(File file) {
        String ruta = file.toString();
        String nombre = usuario.getNick();
        DateTimeFormatter patronArchivo = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        DateTimeFormatter patronFecha = DateTimeFormatter.ofPattern("HH:mm:ss, dd/MM/yyyy");
        Path fichero = Paths.get(ruta + "/" + patronArchivo.format(LocalDateTime.now()) + ".txt");

        try (BufferedWriter bw = Files.newBufferedWriter(fichero, Charset.defaultCharset(), StandardOpenOption.CREATE)) {
            bw.write("Fichero creado por " + nombre);
            bw.newLine();

            int contadorExp = 1;
            for (Experiencia exp : listaExperienciasSeleccionadas) {
                bw.newLine();
                bw.write("Experiencia " + contadorExp + " - " + exp.getNombre());
                bw.newLine();
                bw.write("  " + exp.getDescripcion());
                bw.newLine();

                int contadorAct = 1;
                for (ActividadExperiencia actexp : exp.getListaActividades()) {
                    bw.write("  Actividad " + contadorAct + " - " + actexp.getActividad().getNombre());
                    bw.newLine();
                    bw.write("      " + actexp.getActividad().getDescripcion());
                    bw.newLine();
                    bw.write("      Inicio: " + patronFecha.format(actexp.getFechaInicio()));
                    bw.newLine();
                    bw.write("      Final: " + patronFecha.format(actexp.getFechaFinal()));
                    bw.newLine();
                    contadorAct++;
                }
                contadorExp++;
                bw.newLine();
                bw.write("----------------------------------------------------------------------------");
                bw.newLine();
            }
        } catch (IOException ex) {
            not.error("ERROR AL GENERAR TICKET", "Ha habido un error al exportar las experiencias");
        }
    }
}
