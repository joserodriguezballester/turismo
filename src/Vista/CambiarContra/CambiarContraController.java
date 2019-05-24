/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.CambiarContra;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class CambiarContraController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        guardarBT.setStyle(" -fx-font-weight: bold;\n" +
"    -fx-text-fill: WHITE;\n" +
"    -fx-background-radius: 20;\n" +
"    -fx-font-size: 16px;\n" +
"    -fx-font-weight: bold;\n" +
"    -fx-background-color: rgb(2,144,46);\n" +
"    -fx-cursor:hand;");
        cancelarBT.getStyleClass().add("botonEliminar");
        paneContra.getStyleClass().add("paneNuevaContra");
        
        Image img = new Image("Imagenes/fondoBuscar.jpg");
        ImageView imagev = new ImageView(img);
        anchorFondo.getChildren().add(imagev);
        imagev.setFitHeight(329);
        imagev.setFitWidth(517);
        imagev.toBack();
        paneContra.toFront();
    }    

    @FXML
    private void actualizarPassword(ActionEvent event) {
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) this.guardarBT.getParent().getScene().getWindow();
        stage.close();
    }
    
}
