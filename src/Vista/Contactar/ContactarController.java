/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Contactar;

import Datos.Bda.GestionBD;
import Datos.Bda.usuariosDAO;
import Modelo.Correo;
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

/**
 * FXML Controller class
 *
 * @author joser
 */
public class ContactarController implements Initializable {

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
    private Correo correo;
    private ObservableList guiasOL = FXCollections.observableArrayList();
    private List<Usuario> guiasL;
    private GestionBD gestion;
    private usuariosDAO usuarioDAO;
    private Usuario usuario;
    @FXML
    private TextField passgmail;
    private Usuario guiaTuristico;
    @FXML
    private TextField asuntoTA;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        correo = new Correo();
        guiasLV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> modifSelecListView(newValue));
         caraIV.setImage(new Image("Imagenes/usuarios/avatar.png"));
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
            System.out.println("error " + ex);
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
            ex.printStackTrace();
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
