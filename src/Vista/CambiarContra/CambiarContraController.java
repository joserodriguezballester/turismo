/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.CambiarContra;

import Datos.Bda.usuariosDAO;
import Modelo.Notificacion;
import Modelo.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class CambiarContraController implements Initializable {

    private String nick;
    private usuariosDAO usuarioDAO;
    private Notificacion not;
    private Usuario usuario;
    private String contrasenyaNueva;
    private boolean exito = false;
    
    @FXML
    private JFXPasswordField antiguaPW;
    @FXML
    private JFXPasswordField nuevaPW;
    @FXML
    private JFXPasswordField repetirNuevaPW;
    @FXML
    private JFXButton guardarBT;
    @FXML
    private JFXButton cancelarBT;
    @FXML
    private AnchorPane anchorFondo;
    @FXML
    private Pane paneContra;
    @FXML
    private Label antiguaL;
    @FXML
    private Label repetirContraL;
    @FXML
    private Label nuevaContraL;

    //INICIO--------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        not = new Notificacion();

        styleInicio();
    }

    public void setParametros(usuariosDAO usuarioDAO, Usuario usuario) {
        this.usuarioDAO = usuarioDAO;
        this.usuario = usuario;
        this.nick = usuario.getNick();
    }

    private void styleInicio() {
        //Imagen fondo
        Image img = new Image("Imagenes/fondoBuscar.jpg");
        ImageView imagev = new ImageView(img);
        anchorFondo.getChildren().add(imagev);
        imagev.setFitHeight(329);
        imagev.setFitWidth(517);
        imagev.toBack();

        //panes
        paneContra.toFront();

        //Estilos
        paneContra.getStyleClass().add("paneNuevaContra");
        guardarBT.getStyleClass().add("botonAceptarRegistro");
        cancelarBT.getStyleClass().add("botonEliminar");
    }

    //ACCIONES------------------------------------------------------------------
    @FXML
    private void actualizarPassword(ActionEvent event) throws InterruptedException {
        if ((!antiguaPW.getText().equals("")) && (!nuevaPW.getText().equals("")) && (!repetirNuevaPW.getText().equals(""))) {
            if (comprobarcontraAntigua() == true) {
                if (contrasenasIguales() == true) {

                    try {
                        PasswordEncryptor encryptor = new BasicPasswordEncryptor();
                        String contrasenaEncriptada = encryptor.encryptPassword(contrasenyaNueva);
                        if ((usuarioDAO.introducirContra(contrasenaEncriptada, nick)) == true) {
                            exito = true;
                            
                            cerrarVentana();
                        }

                    } catch (SQLException ex) {
                        not.alert("ERROR","No se ha podido cambiar la Contraseña");
                        nuevaPW.setText("");
                        repetirNuevaPW.setText("");
                    }

                } else {

                    nuevaContraL.setStyle("-fx-text-fill: red");
                    repetirContraL.setStyle("-fx-text-fill: red");

                    nuevaPW.setText("");
                    repetirNuevaPW.setText("");

                }
            } else {
                not.alert("ERROR", "Contraseña incorrecta");
                antiguaL.setStyle("-fx-text-fill: red");
                antiguaPW.setText("");
            }

        }else{
            not.alert("ERROR", "Campos Vacios");
        }
    }


    @FXML
    private void cancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void devolverStyle(MouseEvent event) {
        nuevaContraL.setStyle("-fx-text-fill: black");
        repetirContraL.setStyle("-fx-text-fill: black");
        antiguaL.setStyle("-fx-text-fill: black");
    }

    //MÉTODOS-------------------------------------------------------------------
    private void cerrarVentana() {
        Stage stage = (Stage) this.guardarBT.getParent().getScene().getWindow();
        stage.close();
    }

    private boolean comprobarcontraAntigua() {
        PasswordEncryptor encryptor = new BasicPasswordEncryptor();
        boolean checkPassword;

        String contrasena = antiguaPW.getText();
        String contrasenaBD = null;

        try {
            contrasenaBD = usuarioDAO.obtenerContra(nick);

        } catch (SQLException ex) {
            not.error("ERROR", "Error al conectar con la DB Turismo");
        }
        checkPassword = encryptor.checkPassword(contrasena, contrasenaBD);
        return checkPassword;
    }

    private boolean contrasenasIguales() {
        boolean iguales = false;
        contrasenyaNueva = nuevaPW.getText();
        if (contrasenyaNueva.equals(repetirNuevaPW.getText())) {
            if (contrasenyaNueva.equals("")) {
                not.alert("ERROR", "Los campos estan vacios");
                iguales = false;
            } else {

                iguales = true;
            }
        } else {
            not.error("ERROR", "Las contraseñas no coinciden");
            nuevaContraL.setStyle("-fx-text-fill: red");
            repetirContraL.setStyle("-fx-text-fill: red");
        }

        return iguales;
    }
    
    public boolean conseguido(){
        return exito;
    }

}
