package Inicio;

/* cambio
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Turismo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
         ////conectar base de datos//////
        
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/Usuario/Usuario.fxml"));

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/Imagenes/log.png"));
        stage.setTitle(" Amsterdam");   // o nombre agencia
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
