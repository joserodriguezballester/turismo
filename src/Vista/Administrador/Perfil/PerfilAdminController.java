package Vista.Administrador.Perfil;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class PerfilAdminController implements Initializable {

    @FXML
    private GridPane menu21;
    @FXML
    private Button botonAñadir;
    @FXML
    private Button botonListar;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonModificar;
    @FXML
    private TableView<Usuario> tableview;
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
    private ComboBox<?> tipoUsuario;
    @FXML
    private TableColumn<Usuario, String> nickTC;
    @FXML
    private TableColumn<Usuario, String> nombreTC;
    @FXML
    private TableColumn<Usuario, String> apellidosTC;
    @FXML
    private TableColumn<Usuario, String> DniTC;
    @FXML
    private TableColumn<Usuario, String> telefonoTC;
    @FXML
    private TableColumn<Usuario, String> direccionTC;
    @FXML
    private TableColumn<Usuario, String> emailTC;
    @FXML
    private TableColumn<Usuario, LocalDate> fecNacTC;
    @FXML
    private TableColumn<Usuario, String> rolTC;
    private GestionBD gestion;
    ObservableList<Usuario> usuarios;
    private usuariosDAO usuariosDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarios = FXCollections.observableArrayList();
        System.out.println("inicialice");// TODO
    }

    public void setGestion(GestionBD gestion) {
        System.out.println("setgestion");
        this.gestion = gestion;
    }

    public void ejecutaAlPrincipio() {
        usuariosDAO = new usuariosDAO(gestion);
        cargarUsuarios();
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
    }

    private void cargarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try {

            lista = usuariosDAO.listarUsuarios();

            usuarios.addAll(lista);
            tableview.setItems(usuarios);
            nickTC.setCellValueFactory(new PropertyValueFactory<>("nick"));
            nombreTC.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            apellidosTC.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
            DniTC.setCellValueFactory(new PropertyValueFactory<>("dni"));
            telefonoTC.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            direccionTC.setCellValueFactory(new PropertyValueFactory<>("direccion"));
            emailTC.setCellValueFactory(new PropertyValueFactory<>("email"));
            fecNacTC.setCellValueFactory(new PropertyValueFactory<>("fecNac"));
            rolTC.setCellValueFactory(new PropertyValueFactory<>("rol"));

        } catch (SQLException ex) {
            System.out.println("SQL ERROR: " + ex.getMessage());
//            Logger.getLogger(ActividadAdminController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception es) {
            System.out.println("Error: " + es.getMessage());
            Notificacion.error("ERROR EXCEPTION ", "Ha ocurrido un grave problema");
        }
    }
}


