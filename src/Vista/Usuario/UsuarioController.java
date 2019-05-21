package Vista.Usuario;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Correo;
import Modelo.Notificacion;
import Modelo.Transicion;
import Modelo.Usuario;
import Vista.Administrador.Principal.PrincipalAdminController;
import Vista.Principal.PrincipalController;
import Vista.Registrar.RegistrarController;
import com.sun.security.auth.PrincipalComparator;
import java.io.IOException;
import static java.lang.Math.random;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javax.mail.MessagingException;
import org.controlsfx.dialog.LoginDialog;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;

public class UsuarioController implements Initializable {

    private Label label;
    private Label nombreET;
    private Stage escenario;
    private TranslateTransition translatePrincipal;
    private TranslateTransition translateAgencia;
    private String tituloAlertSQL = "AlertaSQL";
    private String mensajeSQL = "error en la base de datos";
    private GestionBD gestion;
    private usuariosDAO usuarioDAO;
    private Notificacion not;
    Usuario usuario;

    @FXML
    private PasswordField contraTF;
    @FXML
    private TextField nickTF;
    @FXML
    private Pane Ventana;
    @FXML
    private AnchorPane fondoUsuario;
    @FXML
    private Label agencia;
    @FXML
    private Pane paneagencia;
    @FXML
    private Button botonLog;
    @FXML
    private Button botonReg;
    @FXML
    private Polygon triangle;
    @FXML
    private Pane paneInicio;
    @FXML
    private Pane paneCapaTriangulo;
    @FXML
    private Label olvidar;

    //INICIO--------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestion = new GestionBD();
        gestion.conectar();
        usuarioDAO = new usuariosDAO(gestion);
        usuario = new Usuario();

        styleInicio();
    }

    private void styleInicio() {

        //Imagen fondo
        Image img = new Image("Imagenes/inicioprueba.jpg");
        ImageView imagev = new ImageView(img);
        fondoUsuario.getChildren().add(imagev);
        imagev.setFitHeight(800);
        imagev.setFitWidth(1300);

        //panes
        triangle.toFront();
        triangle.setOpacity(0.85);
        paneInicio.toFront();
        paneagencia.toFront();
        paneCapaTriangulo.toFront();

        //Estilos
        paneagencia.getStyleClass().add("paneAgencia");
        botonLog.getStyleClass().add("botoninicio");
        botonReg.getStyleClass().add("botoninicio");
        olvidar.getStyleClass().add("recordarpassword");
    }

    //ACCIONES------------------------------------------------------------------
    @FXML
    private void logearse(ActionEvent event) throws InterruptedException {
        // Utilizar uno de estos tres metodos

        logearseBueno();
        //logearseComoCliente();
        //logearseComoAdministrador();

    }

    @FXML
    private void registrarse(ActionEvent event) throws IOException {
        Parent root;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Vista/Registrar/Registrar.fxml"));
            root = loader.load();  // el meotodo initialize() se ejecuta

            //OBTENER EL CONTROLADOR DE LA VENTANA
            RegistrarController registrarController = loader.getController();
            registrarController.setParametros(usuarioDAO);

            Stage escena = new Stage();    //En Stage nuevo.                  
            escena.setTitle("Registro");

            // NO PERMITE ACCESO A LA VENTANA PRINCIPAL
            escena.initModality(Modality.APPLICATION_MODAL);
            escena.getIcons().add(new Image("/Imagenes/iconos/note.png"));
            escena.setScene(new Scene(root));
            escena.showAndWait();
        } catch (IOException ex) {
            not.error("ERROR IOException",
                    "en Registrarse() --- UsuarioController");
        }
    }

    //VENTANAS------------------------------------------------------------------
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

            //principalController.setParametros(usuario, bda, cambiador);
            //Damos valores a los nodos antes de mostrarlos
            //principalController.calcularnodos();
            escenario.setScene(new Scene(root));
            escenario.show();

        } catch (IOException ex) {
            not.error("ERROR IOException",
                    "en cargarVentanaPrincipal() --- UsuarioController");
            //aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
            System.err.println("error");  ////mostrar en ventana
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
            principalAdminController.setParametroUsuario(usuario);

            //principalController.setParametros(usuario, bda, cambiador);
            //Damos valores a los nodos antes de mostrarlos
            //principalController.calcularnodos();
            escenario.setScene(new Scene(root));
            escenario.show();

        } catch (IOException ex) {
            not.error("ERROR IOException",
                    "en cargarVentanaPrincipalAdmin() --- UsuarioController");
            //aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
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

            Stage escena = new Stage();     //En Stage nuevo.
            escena.setTitle("Registrarse");

            // NO PERMITE ACCESO A LA VENTANA PRINCIPAL
            escena.initModality(Modality.APPLICATION_MODAL);
            escena.setScene(new Scene(root));
            escena.showAndWait();
            //RECOGEMOS  LA INFORMACION ESCRITA EN LA OTRA VENTANA

        } catch (IOException ex) {
            not.error("ERROR IOException",
                    "en cargarVentanaRegistrarse() --- UsuarioController");
            //aviso.mostrarAlarma("ERROR IOExcepction:  No se encuentra la ventana de login");
            //mostrar error

        }
    }

    //CONTROL-------------------------------------------------------------------
    public boolean verificaUsuario() {

        PasswordEncryptor encryptor = new BasicPasswordEncryptor();
        boolean existe = false;
        boolean checkPassword;

        String nick = nickTF.getText();
        String contrasena = contraTF.getText();

        String contrasenaBD = null;

        try {
            contrasenaBD = usuarioDAO.obtenerContra(nick);
        } catch (SQLException ex) {
            not.alert(tituloAlertSQL, mensajeSQL);
        }

        checkPassword = encryptor.checkPassword(contrasena, contrasenaBD);

        return checkPassword;
    }

    private void logearseBueno() throws InterruptedException {
        boolean logeado = verificaUsuario();        //Verifica que existe y contraseña correcta

        if (logeado) {
            try {
                usuario = usuarioDAO.cargarUsuario(nickTF.getText());

                // segun el roll ejecutará uno de los dos metodos
                if ("CLIENTE".equalsIgnoreCase(usuario.getPerfilString())) {
                    
                    String rol = "cliente";
                    transicionPrincipal(rol);

                } else {
                
                    if ("ADMINISTRADOR".equalsIgnoreCase(usuario.getPerfilString())) {
                        String rol = "admin";
                        transicionPrincipal(rol);  
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
            //mostrar ventana que no existe o contraseña erronea
            nickTF.setText("");
            contraTF.setText("");
        }
    }

    @FXML
    private void habilitarBT(MouseEvent event) {
        //Hasta que pulsas para escribir los campos no se habilita el boton de login
        if (botonLog.isDisable()) {
            FadeTransition ft = new FadeTransition(Duration.millis(500), botonLog);
            ft.setFromValue(0.6);
            ft.setToValue(1);
            ft.play();

            botonLog.setDisable(false);
        }
    }

    private void transicionPrincipal(String rol) {

        translatePrincipal = new TranslateTransition(Duration.seconds(1), paneCapaTriangulo);
        translateAgencia = new TranslateTransition(Duration.seconds(1), paneagencia);

        translateAgencia.setFromX(0);
        translateAgencia.setToX(600);

        translatePrincipal.setFromX(0);
        translatePrincipal.setToX(-1350);
        
        translatePrincipal.setInterpolator(Interpolator.LINEAR);
        translatePrincipal.play();
        translateAgencia.setInterpolator(Interpolator.LINEAR);
        translateAgencia.play();
        
        translatePrincipal.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(rol.equalsIgnoreCase("cliente")){
                    cargarVentanaPrincipal();
                }else if(rol.equalsIgnoreCase("admin")){
                    cargarVentanaPrincipalAdmin();
                }
            }
        });

    }

    @FXML
    private void recordarPass(MouseEvent event) throws SQLException, MessagingException {
        
        //Enviar un correo con una nueva contraseña
        not = new Notificacion();
        Pair<String, String> pareja = not.recordar();
        if ((!"".equals(pareja.getKey()))) {//por si damos a cancelar 
            String email = usuarioDAO.DarCorreo(pareja.getKey());
            if (email != null) {

                if (email.equals(pareja.getValue())) {
                    String numero = (int) (Math.random() * 1000) + "";
                    String contra = usuario.encriptar(numero);
                    usuarioDAO.introducirContra(contra, pareja.getKey());
                    Correo correo = new Correo();
                    correo.setparametros(pareja, numero, correo);
                    correo.mandarcorreo();
                    not.info("Email", "Revisa tu correo, te hemos enviado un mensaje");
                } else {
                    not.alert("Email", "No coincide con el correo que tenemos de ti");
                }
            }
        }
    }

    //ATAJOS DE PROGRAMADOR-----------------------------------------------------
    private void logearseComoCliente() {
        cargarVentanaPrincipal();
    }

    private void logearseComoAdministrador() {
        cargarVentanaPrincipalAdmin();
    }
}
