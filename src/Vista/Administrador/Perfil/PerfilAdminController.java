package Vista.Administrador.Perfil;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import FilesDAO.UsuarioFiles;
import Modelo.Notificacion;
import Modelo.Usuario;
import Modelo.ValidarCampos;
import Vista.Registrar.RegistrarController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class PerfilAdminController implements Initializable {

    private GestionBD gestion;
    private ObservableList<Usuario> usuarios;
    private usuariosDAO usuarioDAO;
    private ObservableList<String> rolOL;
    private Notificacion not;
    private Usuario usuario;
    //    private TextField rolTF;
    @FXML
    private Button botonAñadir;
    @FXML
    private Button botonBorrar;
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
    @FXML
    private TableColumn<Usuario, String> archivoTC;
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
    @FXML
    private Label id_invisibleTF;
    @FXML
    private JFXComboBox<String> rolCB;
    @FXML
    private JFXDatePicker fecNacDP;
    @FXML
    private Button editarBT;
    @FXML
    private ImageView caraIV;
    @FXML
    private JFXTextField archivoTF;
    @FXML
    private AnchorPane Ventana;
    @FXML
    private AnchorPane panePerfilA;
    @FXML
    private Label nickL;
    @FXML
    private Label nombreL;
    @FXML
    private Label dniL;
    @FXML
    private Label apellidosL;
    @FXML
    private Label telefL;
    @FXML
    private Label direccL;
    @FXML
    private Label emailL;
    @FXML
    private Label fecNacL;
    @FXML
    private Button modificarBT;
    private final String MAL = " -fx-text-fill:red";
    private String CORRECTO = " -fx-text-fill: rgb(56, 175, 88)"; //Verde
    private ValidarCampos validarCampos;
    private Usuario usuarioSeleccionado;
    private boolean cambioFoto;
    private UsuarioFiles usuarioFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarCampos = new ValidarCampos();
        usuarioFile = new UsuarioFiles();
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
        archivoTC.setCellValueFactory(new PropertyValueFactory<>("foto"));

        Ventana.getStyleClass().add("fondoLiso");
        panePerfilA.toFront();
        usuariosTV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> modifSelecListView(newValue));

    }

    public void ejecutaAlPrincipio() {
        usuarioDAO = new usuariosDAO(gestion);
        ObservableList<String> roleOL = FXCollections.observableArrayList();
        ObservableList<String> rolOL = FXCollections.observableArrayList();
        roleOL.add("CLIENTES");
        roleOL.add("ADMINISTRADOR");
        roleOL.add("TODOS");
        rolOL.add("CLIENTE");
        rolOL.add("ADMINISTRADOR");
        tipoUsuario.setItems(roleOL);
        tipoUsuario.setValue("CLIENTES");
        tipoUsuario.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            cargarTabla(newValue);
        });
        rolCB.setItems(rolOL);
        cargarTabla("CLIENTES");
    }

    private void recargarlista() {
        cargarTabla(tipoUsuario.getValue());
    }

    @FXML
    private void anadir(ActionEvent event) {
        //carga ventana registrar, y le pasa la pelota
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/Registrar/Registrar.fxml"));
            root = loader.load();
            RegistrarController registrarController = loader.getController();
            registrarController.ejecutaPrimero();
            registrarController.setUsuarioDAO(usuarioDAO);
            Stage escena = new Stage();
            escena.setTitle("Registrarse");
            escena.initModality(Modality.APPLICATION_MODAL);
            escena.setScene(new Scene(root));
            escena.showAndWait();
        } catch (IOException ex) {
            not.error("ERROR", " No se encuentra la ventana de Registrar");
        }
        recargarlista();
    }

    @FXML
    private void modificar(ActionEvent event) throws SQLException {   ///falta el rol

        boolean modiNick = false;
        boolean modiFoto = false;
        boolean todoCorrecto = true;
        boolean correctoSQL = true;

        String nick = nickTF.getText();
        String nombre = nombreTF.getText();
        String apellidos = apellidosTF.getText();
        String dni = dniTF.getText();
        LocalDate fecNac = fecNacDP.getValue();
        String telefono = telefonoTF.getText();
        String direccion = direccionTF.getText();
        String email = emailTF.getText();
        String rol = rolCB.getValue();
//        int id = Integer.parseInt(id_invisibleTF.getText());
        int id = usuarioSeleccionado.getId();
        try {

            /////COMPARAMOS SI HAY CAMBIOS, cambiamos en BD y cambiamos en USUARIO
////---NICK
            if (!usuarioSeleccionado.getNick().equals(nick)) {
                //Miramos que no este repetido
                if (usuarioDAO.clienteExiste(nick)) {
                    todoCorrecto = false;
                    nickTF.setText("");
                    nickTF.setPromptText("Ese usuario ya existe");
                    nickL.setStyle(MAL);
                } else { //miramos que no este en blanco
                    if (nickTF.getText().equals("")) {
                        nickTF.setPromptText("*OBLIGATORIO");
                        todoCorrecto = false;
                        nickL.setStyle(MAL);
                    } else {   //modificamos en BD
                        nickL.setStyle(CORRECTO);
                        modiNick = usuarioDAO.modificarNick(nick, id);  // modificacion en BD
                        if (modiNick) {
                            //**cambiar nombre foto al cambiar el nick 
                            archivoTF.setText(usuarioFile.cambiarNombreFoto(usuario.getFoto(), nick));                          
                            usuarioFile.cambiarArchivoFoto(usuarioSeleccionado.getFoto(), archivoTF.getText());                     
                            usuarioDAO.modificarFoto(archivoTF.getText(), id);
                        } else {
                            correctoSQL = false;
                        }
                    }
                }
            }

//fin NICK  ------------
////---NOMBRE
            if (!usuarioSeleccionado.getNombre().equals(nombre)) {
                if (nombre.equals("")) {
                    todoCorrecto = false;
                    nombreL.setStyle(MAL);
                    nombreTF.setPromptText("* OBLIGATORIO");
                } else {
                    boolean modiNombre = usuarioDAO.modificarNombre(nombre, id);
                    if (!modiNombre) {
                        correctoSQL = false;
                    }
                }
            }

//---APELLIDOS
            if (!usuarioSeleccionado.getApellidos().equals(apellidos)) {
                if (apellidos.equals("")) {
                    todoCorrecto = false;
                    apellidosL.setStyle(MAL);
                    apellidosTF.setPromptText("* OBLIGATORIO");
                } else {
                    boolean modificado = usuarioDAO.modificarApellidosTF(apellidos, id);
                    if (!modificado) {
                        correctoSQL = false;
                    }
                }
            }

//---DNI
            if (!usuarioSeleccionado.getDni().equals(dni)) {
                if (validarCampos.comprobardni(dni) == false || dni.equals("")) {
                    todoCorrecto = false;
                    dniTF.setText("");
                    dniTF.setPromptText("Intoduce un DNI valido");
                    dniL.setStyle(MAL);
                } else {
                    dniL.setStyle(CORRECTO);
                    boolean modificado = usuarioDAO.modificarDni(dni, id);
                    if (!modificado) {
                        correctoSQL = false;
                    }
                }
            }
//---FECHA NACIMIENTO
            if (!usuarioSeleccionado.getFecNac().equals(fecNac) || fecNac == null) {
                todoCorrecto = false;
                fecNacL.setStyle(MAL);
                fecNacDP.setPromptText("* OBLIGATORIO");
            } else {
                boolean modificado = usuarioDAO.modificarFecNac(fecNac, id);
                if (!modificado) {
                    correctoSQL = false;
                }
            }

//---TELEFONO
            if (!usuarioSeleccionado.getTelefono().equals(telefono)) {
                if (validarCampos.comprobarTelefono(telefono) == false || telefono.equals("")) {
                    todoCorrecto = false;
                    telefonoTF.setText("");
                    telefonoTF.setPromptText("Intoduce un Número valido");
                    telefL.setStyle(MAL);
                } else {
                    telefL.setStyle(CORRECTO);
                    boolean modificado = usuarioDAO.modificarTelefono(telefono, id);
                    if (!modificado) {
                        correctoSQL = false;
                    }
                }
            }
            //---DIRECCION
            if (!usuarioSeleccionado.getDireccion().equals(direccion)) {
                boolean modificado = usuarioDAO.modificarDireccion(direccion, id);
                if (!modificado) {
                    correctoSQL = false;
                }
            }
//---EMAIL
            if (!usuarioSeleccionado.getEmail().equals(email)) {
                if (validarCampos.comprobarEmail(email) == false || email.equals("")) {
                    todoCorrecto = false;
                    emailTF.setText("");
                    emailTF.setPromptText("Intoduce un Email valido");
                    emailL.setStyle(MAL);
                } else {
                    emailL.setStyle(CORRECTO);
                    boolean modificado = usuarioDAO.modificarEmail(email, id);
                    if (!modificado) {
                        correctoSQL = false;
                    }
                }
            }
//---FOTO

        if (cambioFoto) {
    
            modiFoto = usuarioDAO.modificarFoto(usuarioSeleccionado.getFoto(), id);
            if (modiFoto) {
//                not.confirm("Modificar", "Ha sido modificado con exito");
            } else {
                correctoSQL = false;
            }
        }

////ROL
            if (!usuarioSeleccionado.getPerfilString().equals(rolCB.getValue())) {
                boolean modificado = usuarioDAO.modificarRol(rolCB.getValue(), id);
                if (!modificado) {
                    correctoSQL = false;
                }
            }

////********************/// fin comprobar valores
//////INFORMAR DEL RESULTADO  
            if (todoCorrecto && correctoSQL) {
                not.confirm("Modificar", "Ha sido modificado con exito");
            } else {
                not.error("Modificar", "NO ha sido posible modificar");
            }

        } catch (SQLException ex) {
            not.error("ERROR", "Error al intentar conectar con la base de datos");
        } catch (IOException ex) {
            not.error("ERROR", "Error al modificar la foto");
        }
    }
    /////RECARGAR LA LISTA

    //////falta controlar campos, comprobar que introduce todos en particular foto y rol
    @FXML
    private void borrar(ActionEvent event) {
        if (usuarioSeleccionado != null) {
            not.alertWarningDelete("Borrar", "Seguro que quieres borrar este usuario");         //////*******esta not no vale.
            int id = usuarioSeleccionado.getId();
            try {
                usuarioDAO.borrarUsuario(id);
            } catch (SQLException ex) {
                not.alert("ERROR", " No se ha podido borrar");
            }
            recargarlista();
        } else {
            not.alert("Borrar", "no has seleccionado ningun usuario");
        }
    }       //// Pracicamente acabado falta que la ventana de confirmacion     

    private void cargarTabla(String seleccion) {
        List<Usuario> lista = new ArrayList<>();
        try {
            switch (seleccion) {
                case "CLIENTES":
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
            not.error("ERROR", "Error al intentar conectar con la base de datos");
        }
        renovarLabels();
    }

    private void cargarUsuarios(List<Usuario> lista) {
        usuarios.clear();
        usuarios.addAll(lista);
        usuariosTV.setItems(usuarios);
    }

    private void modifSelecListView(Usuario newValue) {
        this.usuarioSeleccionado = newValue;
        if (usuarioSeleccionado != null) {
            cargarEtiquetas();
        } else {
            limpiarEtiquetas();
        }
        renovarLabels();
    }

    private void cargarEtiquetas() {
        nickTF.setText(usuarioSeleccionado.getNick());
        nombreTF.setText(usuarioSeleccionado.getNombre());
        apellidosTF.setText(usuarioSeleccionado.getApellidos());
        dniTF.setText(usuarioSeleccionado.getDni());
        telefonoTF.setText(usuarioSeleccionado.getTelefono());
        direccionTF.setText(usuarioSeleccionado.getDireccion());
        emailTF.setText(usuarioSeleccionado.getEmail());
        fecNacDP.setValue(usuarioSeleccionado.getFecNac());
        rolCB.setValue(usuarioSeleccionado.getPerfilString());
        archivoTF.setText(usuarioSeleccionado.getFoto());
        cargarfoto();
    }

    private void cargarfoto() {
        try {
            caraIV.setImage(new Image("Imagenes/usuarios/" + usuarioSeleccionado.getFoto()));
        } catch (Exception e) {
            caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
        }
    }

    private void limpiarEtiquetas() {

        nickTF.setText("");
        nombreTF.setText("");
        apellidosTF.setText("");
        dniTF.setText("");
        telefonoTF.setText("");
        direccionTF.setText("");
        emailTF.setText("");
        fecNacDP.setValue(null);
        rolCB.setValue(null);
        archivoTF.setText("");
        limpiarfoto();
    }

    private void limpiarfoto() {
        try {
            caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
        } catch (Exception e) {
            caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        modificarBT.setVisible(true);
        nickTF.setEditable(true);
        nombreTF.setEditable(true);
        apellidosTF.setEditable(true);
        dniTF.setEditable(true);
        telefonoTF.setEditable(true);
        direccionTF.setEditable(true);
        emailTF.setEditable(true);
//        fecNacDP.setEditable(true);
        archivoTF.setEditable(true);
        fotoEditable();
    }

 
 
    private void fotoEditable() {
        caraIV.setOnMouseClicked(event -> SelecionarFoto());
    }

    private void SelecionarFoto() {
        cambioFoto=true;
        try {
            File fotoElegida = usuarioFile.SelectFoto();
            caraIV.setImage(new Image(fotoElegida.toURI().toString()));
            String nombreFoto = usuarioFile.darNombreFoto(fotoElegida, usuarioSeleccionado.getNick());
            usuarioFile.guardarFoto(fotoElegida, nombreFoto);
            
//            usuarioSeleccionado.cambiarArchivoFoto(fotoElegida.toURI().toString(),"Imagenes/usuarios/"+ foto);
            usuarioSeleccionado.setFoto(nombreFoto);

        } catch (IOException ex) {
            not.error("Error", "Al guardar el archivo");
        }
    }

    private void renovarLabels() {
        CORRECTO = " -fx-text-fill:black";
        nickL.setStyle(CORRECTO);
        nombreL.setStyle(CORRECTO);
        apellidosL.setStyle(CORRECTO);
        dniL.setStyle(CORRECTO);
        telefL.setStyle(CORRECTO);
        direccL.setStyle(CORRECTO);
        emailL.setStyle(CORRECTO);
//        fecNacDP.setEditable(true);
        archivoTF.setEditable(true);

    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }

}
