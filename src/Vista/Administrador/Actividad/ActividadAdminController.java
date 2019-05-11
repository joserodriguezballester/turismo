
package Vista.Administrador.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Tipo;
import Vista.Actividad.ActividadController;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class ActividadAdminController implements Initializable {

    @FXML
    private GridPane menu2;
    @FXML
    private Button botonAñadir;
    @FXML
    private Button botonListar;
    @FXML
    private Button botonBorrar;
    @FXML   
    private Button botonGuardar;
    @FXML
    private Button botonModificar;
    @FXML
    private Button botonListarIdTipo;
    @FXML
    private TableView<Actividad> tableview;
    @FXML
    private TableColumn<Actividad, Integer> tb_id;
    @FXML
    private TableColumn<Actividad, String> tb_nombre;
    @FXML
    private TableColumn<Actividad, String> tb_descripcion;
    @FXML
    private TableColumn<Actividad, String> tb_horario;
    @FXML
    private TableColumn<Actividad, Double> tb_precio;
    @FXML
    private TableColumn<Actividad, String> tb_direccion;
    @FXML
    private TableColumn<Actividad, String> tb_url;
    @FXML
    private TableColumn<Actividad, String> tb_telefono;
    @FXML
    private TableColumn<Actividad, String> tb_foto;
    @FXML
    private TableColumn<Actividad, Integer> tb_idTipo;
    @FXML
    private Pane paneFoto;
    @FXML
    private TextField textId;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textHorario;
    @FXML
    private TextArea textDescripcion;
    @FXML
    private TextField textPrecio;
    @FXML
    private TextField textDireccion;
    @FXML
    private TextField textUrl;
    @FXML
    private TextField textTelefono;
    @FXML
    private TextField textFoto;
    @FXML
    private TextField textIdTipo;
    @FXML
    private TextField textListarIdTipo;
    
    
    private ObservableList<Actividad> actividades;
    
    private static GestionBD gestion;
//    private Connection conn;
    private actividadesDAO activiDAO;
    
    public static void setGestion(GestionBD gestion) {
        ActividadAdminController.gestion = gestion;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actividades = FXCollections.observableArrayList();
//        conn = gestion.getConn();
        activiDAO = new actividadesDAO(gestion);
//         activiDAO = new actividadesDAO(gestion);
         cargarTabla();
//        conn = gestion.getConn();
//        System.out.println(gestion.isConectado());
    }    
    
    private void cargarTabla(){
        List<Actividad> lista = new ArrayList<>();
//        Tipo tipo = new Tipo(2, "Restaurantes");
        
         System.out.println("ESTOY EN CARGARTABLA");
        try {
            
            lista = activiDAO.listarActividad();
            System.out.println("Lista :" + lista);
            System.out.println("ESTOY DESPUES DE CARGARTABLA");
            
            actividades.addAll(lista);
            tableview.setItems(actividades);
            
            tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tb_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tb_horario.setCellValueFactory(new PropertyValueFactory<>("horario"));
            tb_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            tb_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            tb_url.setCellValueFactory(new PropertyValueFactory<>("url"));
            tb_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            tb_foto.setCellValueFactory(new PropertyValueFactory<>("foto"));
            tb_idTipo.setCellValueFactory(new PropertyValueFactory<>("idTipo"));
            
        } catch (SQLException ex) {
            System.out.println("SQL ERROR: " + ex.getMessage());
//            Logger.getLogger(ActividadAdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception es){
            System.out.println("Error: " + es.getMessage());
            Notificacion.error("ERROR EXCEPTION ", "Ha ocurrido un grave problema");
        }
    }
    
    @FXML
    private void anadir(ActionEvent event) {
    }

    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void borrar(ActionEvent event) {
    }

    @FXML
    private void listar(ActionEvent event) {
        cargarTabla();
    }
    
    @FXML
    private void guardar(ActionEvent event) {
    }
}
