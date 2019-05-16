package Vista.Usuario;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Transicion;
import Modelo.Usuario;
import Vista.Administrador.Principal.PrincipalAdminController;
import Vista.Principal.PrincipalController;
import Vista.Registrar.RegistrarController;
import com.sun.security.auth.PrincipalComparator;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.LoginDialog;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;

public class UsuarioController implements Initializable {

    private Label label;
    private Label nombreET;
    private Stage escenario;
    
    @FXML
    private PasswordField contraTF;
    @FXML
    private TextField nickTF;
    private Pane Ventana;
    
    private GestionBD gestion;
    private usuariosDAO usuarioDAO;
    Usuario usuario;
    private Notificacion not;
    @FXML
    private AnchorPane fondoUsuario;
    @FXML
    private Pane paneInicio;
    @FXML
    private Label agencia;
    @FXML
    private Pane paneagencia;
    @FXML
    private Button botonLog;
    @FXML
    private Button botonReg;
    private String tituloAlertSQL="AlertaSQL";
    private String mensajeSQL="error en la base de datos";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestion = new GestionBD();
        gestion.conectar();
        usuarioDAO = new usuariosDAO(gestion);
        usuario = new Usuario();

        Image img = new Image("Imagenes/inicio.jpg");
        ImageView imagev = new ImageView(img);

        fondoUsuario.getChildren().add(imagev);
        paneInicio.getStyleClass().add("paneinicio");
        paneagencia.getStyleClass().add("paneAgencia");
        
        botonLog.getStyleClass().add("botoninicio");
        botonReg.getStyleClass().add("botoninicio");
        
        imagev.setFitHeight(800);
        imagev.setFitWidth(1300);
        
        paneInicio.toFront();
        paneagencia.toFront();
        
        // TODO
    }

    @FXML
    private void logearse(ActionEvent event) {
        // Utilizar uno de estos tres metodos
 //       logearseBueno();
             logearseComoCliente();
 //      logearseComoAdministrador();
    }
    
    @FXML
    private void registrarse(ActionEvent event) throws IOException {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/Registrar/Registrar.fxml"));
            root = loader.load(); // el meotodo initialize() se ejecuta
            //OBTENER EL CONTROLADOR DE LA VENTANA
            RegistrarController registrarController = loader.getController();
            registrarController.setParametros(usuarioDAO);
            Stage escena = new Stage();                      //En Stage nuevo.
            escena.setTitle("Registro");
            escena.initModality(Modality.APPLICATION_MODAL);  // NO PERMITE ACCESO A LA VENTANA PRINCIPAL
            escena.getIcons().add(new Image("/Imagenes/iconos/note.png"));
            escena.setScene(new Scene(root));
            escena.showAndWait();
        } catch (IOException ex) {
            not.error("ERROR IOException",
                    "en Registrarse() --- UsuarioController");

        }

    }

    public void cargarVentanaPrincipal() {

       
        escenario = (Stage) this.nickTF.getParent().getScene().getWindow();

        String nombrefichero = "/Vista/Principal/Principal.fxml";
        PrincipalController principalController;

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(nombrefichero));
            root = loader.load(); // el metodo initialize() se ejecuta
            principalController = loader.getController();
//Pasamos informacion a la clase siguiente
            principalController.setGestion(gestion);
            principalController.setParametroUsuario(usuario);
//                 principalController.setParametros(usuario, bda, cambiador);
//Damos valores a los nodos antes de mostrarlos
            //        principalController.calcularnodos();

            escenario.setScene(new Scene(root));
            escenario.show();

        } catch (IOException ex) {
            not.error("ERROR IOException",
                    "en cargarVentanaPrincipal() --- UsuarioController");
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
            System.err.println("error");  ////mostrar en ventana
        }
    }

    private void cargarVentanaRegistrarse() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/Registrar/Registrar.fxml"));
            root = loader.load(); // el metodo initialize() se ejecuta
            //OBTENER EL CONTROLADOR DE LA VENTANA     UsuarioController usuarioControlador = loader.getController();

            Stage escena = new Stage();                      //En Stage nuevo.
            escena.setTitle("Registrarse");
            escena.initModality(Modality.APPLICATION_MODAL);  // NO PERMITE ACCESO A LA VENTANA PRINCIPAL
            escena.setScene(new Scene(root));
            escena.showAndWait();
            //RECOGEMOS  LA INFORMACION ESCRITA EN LA OTRA VENTANA

        } catch (IOException ex) {
            not.error("ERROR IOException",
                    "en cargarVentanaRegistrarse() --- UsuarioController");
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
//       mostrar error

        }
    }

    private void cargarVentanaPrincipalAdmin() {

        escenario = (Stage) this.nickTF.getParent().getScene().getWindow();
        String nombrefichero = "/Vista/Administrador/Principal/PrincipalAdmin.fxml";
        PrincipalAdminController principalAdminController;

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(nombrefichero));
            root = loader.load(); // el metodo initialize() se ejecuta
            principalAdminController = loader.getController();
//Pasamos informacion a la clase siguiente
            principalAdminController.setGestion(gestion);
//                 principalController.setParametros(usuario, bda, cambiador);
//Damos valores a los nodos antes de mostrarlos
            //        principalController.calcularnodos();

            escenario.setScene(new Scene(root));
            escenario.show();

        } catch (IOException ex) {
            not.error("ERROR IOException",
                    "en cargarVentanaPrincipalAdmin() --- UsuarioController");
//            aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
            System.err.println("error");  ////mostrar en ventana
        }
    }

    public boolean verificaUsuario() {
        boolean existe = false;
        PasswordEncryptor encryptor = new BasicPasswordEncryptor();
        boolean checkPassword;
        //comprobar si existe usuario//
        String nick = nickTF.getText();
        String contrasenaBD = null;
        try {
            contrasenaBD = usuarioDAO.obtenerContra(nick);
        } catch (SQLException ex) {
           not.alert(tituloAlertSQL, mensajeSQL);
        }
        String contrasena = contraTF.getText();
        checkPassword = encryptor.checkPassword(contrasena, contrasenaBD);

        return checkPassword;
    }

    private void logearseBueno() {
        boolean logeado = verificaUsuario();                  //Verifica que existe y contraseña correcta
        //      boolean logeado=true;                   ///// Puesto para saltarse poner nick y contraseña
        if (logeado) {
            try {
                usuario = usuarioDAO.cargarUsuario(nickTF.getText());

/// segun el roll ejecutará uno de los dos metodos
                if ("CLIENTE".equalsIgnoreCase(usuario.getPerfilString())) {
//                    if ("CLIENTE".equalsIgnoreCase(usuario.getRol2())) {
                    cargarVentanaPrincipal();    // usuario cliente
                } else {
//                   if ("ADMINISTRADOR".equalsIgnoreCase(usuario.getRol2())) {
                    if ("ADMINISTRADOR".equalsIgnoreCase(usuario.getPerfilString())) {
                        cargarVentanaPrincipalAdmin();  //usuario administrador
                    } else {
                        not.error("Segun lorenzo soy tonto",
                            "en logearseBueno() --- UsuarioController");
                    }
                }
            } catch (SQLException ex) {
                not.error("ERROR SQL",
                    "en logearseBueno() --- UsuarioController");
            }
        } else {
//             mostrar ventana que no existe o contraseña erronea
            nickTF.setText("");
            contraTF.setText("");
        }
    }

    private void logearseComoCliente() {
        cargarVentanaPrincipal();
    }

    private void logearseComoAdministrador() {
        cargarVentanaPrincipalAdmin();
    }

}
