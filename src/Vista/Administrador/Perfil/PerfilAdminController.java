package Vista.Administrador.Perfil;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import Vista.Administrador.Registrar.RegistrarAdminController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class PerfilAdminController implements Initializable {

    @FXML
    private Button botonAñadir;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonModificar;
    @FXML
    private ComboBox<String> tipoUsuario;
    @FXML
    private TableColumn<Usuario, String> nickTC;
    @FXML
    private TableColumn<Usuario, String> nombreTC;
    @FXML
    private TableColumn<Usuario, String> apellidosTC;
    @FXML
    private TableColumn<Usuario, String> dniTC;
    @FXML
    private TableColumn<Usuario, String> telefonoTC;
    @FXML
    private TableColumn<Usuario, String> direccionTC;
    @FXML
    private TableColumn<Usuario, String> emailTC;
    @FXML
    private TableColumn<Usuario, LocalDate> fecNacTC;

    private GestionBD gestion;
    private ObservableList<Usuario> usuarios;
    private usuariosDAO usuarioDAO;
    @FXML
    private TableView<Usuario> usuariosTV;
    @FXML
    private TextField nickTF;
    @FXML
    private TextField nombreTF;
    @FXML
    private TextField apellidosTF;
    @FXML
    private TextField telefonoTF;
    @FXML
    private TextField direccionTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField dniTF;
//    private TextField rolTF;
    @FXML
    private Label id_invisibleTF;
    @FXML
    private JFXComboBox<String> rolCB;
    private ObservableList<String> rolOL;
    private Notificacion not;
    @FXML
    private JFXDatePicker fecNacDP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
        usuarios = FXCollections.observableArrayList();
        nickTC.setCellValueFactory(new PropertyValueFactory<>("nick"));
        nombreTC.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidosTC.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        dniTC.setCellValueFactory(new PropertyValueFactory<>("dni"));

        telefonoTC.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        direccionTC.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        emailTC.setCellValueFactory(new PropertyValueFactory<>("email"));
        fecNacTC.setCellValueFactory(new PropertyValueFactory<>("fecNac"));

        usuariosTV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> cargarEtiquetas(newValue));

    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }

    public void ejecutaAlPrincipio() {
        usuarioDAO = new usuariosDAO(gestion);
        ObservableList<String> roleOL = FXCollections.observableArrayList();
        ObservableList<String> rolOL = FXCollections.observableArrayList();
        roleOL.add("CLIENTE");
        roleOL.add("ADMINISTRADOR");
        roleOL.add("TODOS");
        rolOL.add("CLIENTE");
        rolOL.add("ADMINISTRADOR");
        tipoUsuario.setItems(roleOL);
        tipoUsuario.setValue("Clientes");
        tipoUsuario.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            cargarTabla(newValue);
        });
        rolCB.setItems(rolOL);
        cargarTabla("Clientes");

    }

    @FXML
    private void anadir(ActionEvent event) {
        //carga ventana registrar, y le pasa la pelota
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/Administrador/Registrar/RegistrarAdmin.fxml"));
            root = loader.load();
            RegistrarAdminController registrarAdminController = loader.getController();
            registrarAdminController.setParametros(gestion, usuarioDAO);
            Stage escena = new Stage();
            escena.setTitle("Registrarse");
            escena.initModality(Modality.APPLICATION_MODAL);
            escena.setScene(new Scene(root));
            escena.showAndWait();
        } catch (IOException ex) {
            not.error("ERROR IOException", "" + ex.getMessage()
                    + " en anadir() --- PerfilAdminController");
        }

    }

    @FXML
    private void modificar(ActionEvent event) {   ///falta el rol

        String nick = nickTF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String DNI = dniTF.getText();
        LocalDate fecNac = fecNacDP.getValue();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();
        String rol = rolCB.getValue();
        int id = Integer.parseInt(id_invisibleTF.getText());

        try {
            usuarioDAO.modificarUsuario(DNI, nombre, apellidos, rol, nick, direccion, telefono, email, id, fecNac);
            // si ha modificado algo
            cargarTabla(rolCB.getValue());
            //asi hemos recargado la lista
        } catch (SQLException ex) {
            not.error("ERROR SQL", "" + ex.getMessage()
                    + " en modificar() --- PerfilAdminController");
        }

    }

    @FXML
    private void borrar(ActionEvent event) {
        int id = Integer.valueOf(id_invisibleTF.getText());
        usuarioDAO.borrarUsuario(id);
    }

    private void cargarTabla(String seleccion) {
        try {

            List<Usuario> lista = new ArrayList<>();
//            String seleccion = tipoUsuario.getSelectionModel().getSelectedItem();

            switch (seleccion) {
                case "CLIENTE":

                    lista = usuarioDAO.listarClientes();

                    break;
                case "ADMINISTRADOR":
                    lista = usuarioDAO.listarAdministradores();
                    break;
                case "TODOS":
                    lista = usuarioDAO.listarClientes();
                    break;
                default:
                    lista = usuarioDAO.listarClientes();

            }
            cargarUsuarios(lista);

        } catch (SQLException ex) {
            not.error("ERROR SQL", "" + ex.getMessage()
                    + " en cargarTabla() --- PerfilAdminController");
        } catch (Exception es) {
            not.error("ERROR EXCEPTION", "" + es.getMessage()
                    + " en cargarTabla() --- PerfilAdminController");
        }

    }

    private void cargarUsuarios(List<Usuario> lista) {
        usuarios.clear();
        usuarios.addAll(lista);
        usuariosTV.setItems(usuarios);
    }

//    private void asignarColumnas() {
//        usuariosTV.setItems(usuarios);
//    }
    private void cargarEtiquetas(Usuario usuario) {
        id_invisibleTF.setText(Integer.toString(usuario.getId()));

        nickTF.setText(usuario.getNick());
        nombreTF.setText(usuario.getNombre());
        apellidosTF.setText(usuario.getApellidos());
        dniTF.setText(usuario.getDni());
        telefonoTF.setText(usuario.getTelefono());
        direccionTF.setText(usuario.getDireccion());
        emailTF.setText(usuario.getEmail());
        fecNacDP.setValue(usuario.getFecNac());
        rolCB.setValue(usuario.getPerfilString());

//        fecNacTF.setText(usuario.getFecNac().toString());
//        rolTF.setText(usuario.getPerfilString());
    }

}
