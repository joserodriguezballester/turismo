/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Contactar;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Correo;
import Modelo.Notificacion;
import Modelo.Usuario;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.mail.MessagingException;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class ContactarController implements Initializable {

    private Correo correo;
    private ObservableList guiasOL = FXCollections.observableArrayList();
    private List<Usuario> guiasL;
    private GestionBD gestion;
    private usuariosDAO usuarioDAO;
    private Usuario usuario;
    private Usuario guiaTuristico;
    private Notificacion not;
    @FXML
    private AnchorPane fondoUsuario;
    @FXML
    private JFXListView<Usuario> guiasLV = new JFXListView<Usuario>(); ////pasar a usuarios
    @FXML
    private Label nickL;
    @FXML
    private Label telefL;
    @FXML
    private ImageView caraIV;
    @FXML
    private Button mandarBT;
    @FXML
    private TextArea mensajeTA;
    @FXML
    private TextField passgmail;
    @FXML
    private TextField asuntoTA;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
        correo = new Correo();
        guiasLV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> modifSelecListView(newValue));
         caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
         
         //Imagen fondo
        Image img = new Image("Imagenes/fondoContactar.jpg");
        ImageView imagev = new ImageView(img);

         imagev.setFitHeight(230);
        imagev.setFitWidth(1300);

        imagev.setOpacity(0.7); 
        this.fondoUsuario.getChildren().add(imagev);
        imagev.toBack();
    }

    @FXML
    private void mandarCorreo(ActionEvent event) {
        try {
            String emisor = usuario.getEmail();
            String receptor = guiaTuristico.getEmail();
            String contrasena = passgmail.getText();
            String asunto=asuntoTA.getText();
            String mensaje=mensajeTA.getText();
            correo.sendEmail(emisor, receptor, contrasena,asunto,mensaje);
        } catch (MessagingException ex) {
            not.error("ERROR", "No se ha podido mandar el correo");
        }
    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }
    

    public void cargainicial() {
        usuarioDAO = new usuariosDAO(gestion);

        try {
            guiasL = usuarioDAO.listarAdministradores();
        } catch (SQLException ex) {
            not.error("ERROR", "No se ha podido con la base de datos");
        }
        guiasOL.addAll(guiasL);
        guiasLV.setItems(guiasOL);
        nickL.setText("");
        telefL.setText("");
        caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));         
    }

    private void modifSelecListView(Usuario newValue) {
      this.guiaTuristico=newValue;
        if (guiaTuristico != null) {
            mandarBT.setDisable(false);
            asuntoTA.setDisable(false);
            mensajeTA.setDisable(false);
            nickL.setText(guiaTuristico.getNick());
            telefL.setText(guiaTuristico.getTelefono());
            System.out.println("newValue.getFoto"+guiaTuristico.getFoto());
            caraIV.setImage(new Image("Imagenes/usuarios/" + guiaTuristico.getFoto()));
        } else {
            nickL.setText("");
            telefL.setText("");
            caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
        }
    }
    private void cargarfoto(Usuario newValue) {
        try {
            caraIV.setImage(new Image("Imagenes/usuarios/" + newValue.getFoto()));
        } catch (Exception e) {
            caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuario=usuario;
    }
    
    
}
