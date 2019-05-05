package Inicio;

/* cambio
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Vista.Usuario.UsuarioController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Turismo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Vista/Usuario/Usuario.fxml"));
        Parent root = loader.load(); // el metodo initialize() se ejecuta

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/Imagenes/logotipo.jpg"));
        stage.setTitle(" Amsterdam");   // o nombre agencia
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
