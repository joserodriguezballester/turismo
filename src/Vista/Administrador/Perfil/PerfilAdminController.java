package Vista.Administrador.Perfil;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarCampos = new ValidarCampos();
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

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
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
        int id = usuario.getId();

        /////********************//////       //falta comprobar valores de entrada
        /////COMPARAMOS SI HAY CAMBIOS, cambiamos en BD y cambiamos en USUARIO
//---NICK
        if (!usuario.getNick().equals(nick)) {
            try {
                //Miramos que sea valido
                if ((usuarioDAO.clienteExiste(nick) == true) || nickTF.getText().equals("")) {
                    todoCorrecto = false;
                    nickTF.setText("");
                    if (nickTF.getText().equals("")) {
                        nickTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
                    } else {
                        nickTF.setPromptText("Ese usuario ya existe");
                    }
                    nickL.setStyle(MAL);
                } else {
                    nickL.setStyle(CORRECTO);
                    modiNick = usuarioDAO.modificarNick(nick, id);
                    if (modiNick) {
                        usuario.setNick(nick);
                    } else {
                        correctoSQL = false;
                    }
                }

                //// Otras consecuencias del cambios de Nick
//        if (modiNick) {
//            //**cambiar nombre foto              
//            File oldfile = usuario.getFotoFile();
//            File newfile = fotoFile;
//            oldfile.renameTo(newfile); //creo que no lo hace bien mejor guardar foto
//            //**cambios en vista            
//            labelUser.setText(nick.toUpperCase());
//            if (principalController != null) {
//                principalController.cargaNick();
//                principalController.cargaFoto();
//            }
//              if (controlador != null) {
//                controlador.cargaNick();
//                controlador.cargaFoto();
//            }   
//fin NICK  ------------
//---NOMBRE
                if (!usuario.getNombre().equals(nombre)) {
                    if (nombre.equals("")) {
                        todoCorrecto = false;
                        nombreL.setStyle(MAL);
                        nombreTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
                    } else {

                        boolean modiNombre = usuarioDAO.modificarNombre(nombre, id);
                        if (modiNombre) {
                            usuario.setNombre(nombre);
                        } else {
                            correctoSQL = false;
                        }
                    }
                }

//---APELLIDOS
                if (!usuario.getApellidos().equals(apellidos)) {
                    if (apellidos.equals("")) {
                        todoCorrecto = false;
                        apellidosL.setStyle(MAL);
                        apellidosTF.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
                    } else {
                        boolean modificado = usuarioDAO.modificarApellidosTF(apellidos, id);
                        if (modificado) {
                            usuario.setApellidos(apellidos);
                        } else {
                            correctoSQL = false;
                        }
                    }
                }
//---DNI
                if (!usuario.getDni().equals(dni)) {
                    if (validarCampos.comprobardni(dni) == false || dni.equals("")) {
                        todoCorrecto = false;
                        dniTF.setText("");
                        dniTF.setPromptText("Intoduce un DNI valido");
                        dniL.setStyle(MAL);
                    } else {
                        dniL.setStyle(CORRECTO);
                        boolean modificado = usuarioDAO.modificarDni(dni, id);
                        if (modificado) {
                            usuario.setDni(dni);
                        } else {
                            correctoSQL = false;
                        }
                    }
                }
//---FECHA NACIMIENTO
                if (!usuario.getFecNac().equals(fecNac) || fecNac == null) {
                    todoCorrecto = false;
                    fecNacL.setStyle(MAL);
                    fecNacDP.setPromptText("*  ESTE CAMPO ES OBLIGATORIO");
                } else {
                    boolean modificado = usuarioDAO.modificarFecNac(fecNac, id);
                    if (modificado) {
                        usuario.setFecNac(fecNac);
                    } else {
                        correctoSQL = false;
                    }
                }

//---TELEFONO
                if (!usuario.getTelefono().equals(telefono)) {
                    if (validarCampos.comprobarTelefono(telefono) == false || telefono.equals("")) {
                        todoCorrecto = false;
                        telefonoTF.setText("");
                        telefonoTF.setPromptText("Intoduce un Número valido");
                        telefL.setStyle(MAL);
                    } else {
                        telefL.setStyle(CORRECTO);
                        boolean modificado = usuarioDAO.modificarTelefono(telefono, id);
                        if (modificado) {
                            usuario.setTelefono(telefono);
                        } else {
                            correctoSQL = false;
                        }
                    }
                }
                //---DIRECCION
                if (!usuario.getDireccion().equals(direccion)) {
                    boolean modificado = usuarioDAO.modificarDireccion(direccion, id);
                    if (modificado) {
                        usuario.setDireccion(direccion);
                    } else {
                        correctoSQL = false;
                    }
                }
//---EMAIL
                if (!usuario.getEmail().equals(email)) {
                    if (validarCampos.comprobarEmail(email) == false || email.equals("")) {
                        todoCorrecto = false;
                        emailTF.setText("");
                        emailTF.setPromptText("Intoduce un Email valido");
                        emailL.setStyle(MAL);
                    } else {
                        emailL.setStyle(CORRECTO);
                        boolean modificado = usuarioDAO.modificarEmail(email, id);
                        if (modificado) {
                            usuario.setEmail(email);
                        } else {
                            correctoSQL = false;
                        }
                    }
                }
//---FOTO   
//        if (selecionarFoto) {
//            usuario.setFotoFile(fotoFile);
//            foto = usuario.fotoToString();  ///pasar fotofile a string
//            usuario.setFoto(foto);
//            modiFoto = usuarioDAO.modificarFoto(foto, id);
//            if (modiFoto) {
//                not.confirm("Modificar", "Ha sido modificado con exito");
//            } else {
//                correctoSQL = false;
//            }
//
//        }
////// Otras consecuencias del cambios de Foto
//        if (modiFoto) {
//            // guardar foto nueva
//            usuario.guardarFoto();
//            //cambios en vista        
//            if (principalController != null) {
//                principalController.cargaFoto();
//            } else {
//                controlador.cargaFoto();
//            }

//        }
//////INFORMAR DEL RESULTADO  
                if (todoCorrecto && correctoSQL) {
                    not.confirm("Modificar", "Ha sido modificado con exito");
                } else {
                    not.error("Modificar", "NO ha sido posible modificar");
                }

                ////********************/// fin comprobar valores
//                boolean modificado = usuarioDAO.modificarUsuario(dni, nombre, apellidos, rol, nick, direccion, telefono, email, id, fecNac);
//                // si ha modificado algo
//                if (modificado) {
//                    cargarTabla(rolCB.getValue()); //asi hemos recargado la lista
//                    not.confirm("Modificado", "Se ha modificado con exito");
//                } else {
//                    not.alert("Modificado", "No se ha modificado con exito");
//                }
            } catch (SQLException ex) {
                not.error("ERROR", "Error al intentar conectar con la base de datos");
            }
        }
    }       //////falta controlar campos, comprobar que introduce todos en particular foto y rol

    @FXML
    private void borrar(ActionEvent event) {
//        int id = Integer.valueOf(id_invisibleTF.getText());
        if (usuario != null) {
            not.alertWarningDelete("Borrar", "Seguro que quieres borrar este usuario");         //////*******esta not no vale.
            int id = usuario.getId();
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
        this.usuario = newValue;
        if (usuario != null) {
            cargarEtiquetas();
        } else {
            limpiarEtiquetas();
        }

    }

    private void cargarEtiquetas() {
//        id_invisibleTF.setText(Integer.toString(usuario.getId()));
//        id_invisibleTF.setText(usuario.getId() + "");
        nickTF.setText(usuario.getNick());
        nombreTF.setText(usuario.getNombre());
        apellidosTF.setText(usuario.getApellidos());
        dniTF.setText(usuario.getDni());
        telefonoTF.setText(usuario.getTelefono());
        direccionTF.setText(usuario.getDireccion());
        emailTF.setText(usuario.getEmail());
        fecNacDP.setValue(usuario.getFecNac());
        rolCB.setValue(usuario.getPerfilString());
        archivoTF.setText(usuario.getFoto());
        cargarfoto();
    }

    private void cargarfoto() {
        try {
            caraIV.setImage(new Image("Imagenes/usuarios/" + usuario.getFoto()));
        } catch (Exception e) {
            caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
        }
    }

    private void limpiarEtiquetas() {
//        id_invisibleTF.setText(Integer.toString(usuario.getId()));
//        id_invisibleTF.setText("");
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
    }

    private void renovarLabels() {
        CORRECTO= " -fx-text-fill:black";
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

}
