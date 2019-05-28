
package Modelo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class VentanaEmergente {

    
    public void info(String titulo, String mensaje, AnchorPane root) {
        StackPane spane = new StackPane();
        
        JFXDialogLayout content = new JFXDialogLayout();
        Text header = new Text(titulo);
        Text body = new Text(mensaje);
        
        
        header.setStyle("-fx-background-color: white");
        content.setHeading(header);
        content.setBody(body);
        content.setBorder(Border.EMPTY);
        
        JFXDialog dialog = new JFXDialog(spane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Press");
        
        button.setStyle("-fx-background-color:white");
        button.setOnAction((ActionEvent event1)-> {
            dialog.close();
        });
        
        button.setLayoutY(200); 
        content.setActions(button);
        content.setStyle("-fx-background-color: red");
        
        spane.setPrefSize(300, 150);
        root.getChildren().add(spane);
        
        dialog.show();
    }
    
    
}
