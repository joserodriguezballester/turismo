package Vista.Administrador.Perfil;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import Vista.Administrador.Registrar.RegistrarAdminController;
import com.jfoenix.controls.JFXComboBox;
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
//    private TableColumn<Usuario, String> rolTC;

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
    private TextField fecNacTF;
    @FXML
    private TextField dniTF;
//    private TextField rolTF;
    @FXML
    private Label id_invisibleTF;
    @FXML
    private JFXComboBox<String> rolCB;
    private ObservableList<String> rolOL;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarios = FXCollections.observableArrayList();
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
        roleOL.add("Clientes");
        roleOL.add("Administradores");
        roleOL.add("Todos");
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
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/Administrador/Registrar/RegistrarAdmin.fxml"));
            root = loader.load(); // el meotodo initialize() se ejecuta
            //OBTENER EL CONTROLADOR DE LA VENTANA
            RegistrarAdminController registrarAdminController = loader.getController();
            registrarAdminController.setParametros(gestion, usuarioDAO);
            Stage escena = new Stage();                      //En Stage nuevo.
            escena.setTitle("Registrarse");

            escena.initModality(Modality.APPLICATION_MODAL);  // NO PERMITE ACCESO A LA VENTANA PRINCIPAL
            escena.setScene(new Scene(root));
            escena.showAndWait();
        } catch (IOException ex) {
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");

        }

    }

    @FXML
    private void modificar(ActionEvent event) {   ///falta la fecha
        String nick = nickTF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String DNI = dniTF.getText();

//        LocalDate fecNac = fecNacTF.getValue(); //NUEVO CALENDARIO JFOENIX
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();
//       String rol = rolTF.getText();
         String rol = "CLIENTES"; // temporal
        int id = Integer.parseInt(id_invisibleTF.getText());

        try {
            usuarioDAO.modificarUsuario(DNI, nombre, apellidos, rol, nick, direccion, telefono, email, id);
        } catch (SQLException ex) {
            Notificacion.error("ERROR SQL ", "Ha ocurrido un grave problema");
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
                case "Clientes":
                  
                    lista = usuarioDAO.listarClientes();
                  
                    break;
                case "Administradores":
                    lista = usuarioDAO.listarAdministradores();
                    break;
                case "Todos":
                    lista = usuarioDAO.listarClientes();
                    break;
            }
            cargarUsuarios(lista);

        } catch (SQLException ex) {
            Notificacion.error("ERROR SQL ", "Ha ocurrido un grave problema");
        } catch (Exception es) {
            Notificacion.error("ERROR EXCEPTION ", "Ha ocurrido un grave problema");
        }

    }

    private void cargarUsuarios(List<Usuario> lista) {
        usuarios.clear();
        usuarios.addAll(lista);
        usuariosTV.setItems(usuarios);
        nickTC.setCellValueFactory(new PropertyValueFactory<>("nick"));
        nombreTC.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidosTC.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        dniTC.setCellValueFactory(new PropertyValueFactory<Usuario, String>("dni"));

        telefonoTC.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        direccionTC.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        emailTC.setCellValueFactory(new PropertyValueFactory<>("email"));
        fecNacTC.setCellValueFactory(new PropertyValueFactory<>("fecNac"));
//        rolTC.setCellValueFactory(new PropertyValueFactory<>("rol"));
        
        
    }

    private void cargarEtiquetas(Usuario usuario) {
        id_invisibleTF.setText(Integer.toString(usuario.getId()));

        nickTF.setText(usuario.getNick());
        nombreTF.setText(usuario.getNombre());
        apellidosTF.setText(usuario.getApellidos());
        dniTF.setText(usuario.getDNI());
        telefonoTF.setText(usuario.getTelefono());
        direccionTF.setText(usuario.getDireccion());
        emailTF.setText(usuario.getEmail());
        fecNacTF.setText(usuario.getFecNac().toString());
//        rolTF.setText(usuario.getPerfilString());

    }

}
