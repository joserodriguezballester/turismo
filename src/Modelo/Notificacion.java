package Modelo;

import java.util.Optional;
import javafx.application.Platform;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import eu.hansolo.enzo.notification.Notification;
import eu.hansolo.enzo.notification.Notification.Notifier;
import java.io.File;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;
import org.controlsfx.control.Notifications;

public class Notificacion {

    public void info(String titulo, String mensaje) {
        Image img = new Image("/Imagenes/iconos/info.png");
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(30);
        imgV.setFitWidth(30);

        Notifications notification = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(imgV)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                    }

                });

        notification.darkStyle();
        notification.show();
    }

    public void confirm(String titulo, String mensaje) {

        Image img = new Image("/Imagenes/iconos/tick.png");
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(30);
        imgV.setFitWidth(30);

        Notifications notification = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(imgV)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                    }

                });

        notification.darkStyle();
        notification.show();
    }

    public void error(String titulo, String mensaje) {
        Image img = new Image("/Imagenes/iconos/error.png");
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(30);
        imgV.setFitWidth(30);

        Notifications notification = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(imgV)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                    }

                });

        notification.darkStyle();
        notification.show();
    }

    public void alert(String titulo, String mensaje) {
        Image img = new Image("/Imagenes/iconos/warning.png");
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(30);
        imgV.setFitWidth(30);

        Notifications notification = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(imgV)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                    }

                });

        notification.darkStyle();
        notification.show();
    }

    public boolean alertWarningDelete(String titulo, String cadena) {
        boolean ok = false;
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Delete");
        alert.setHeaderText(titulo);
        alert.setContentText(cadena);


        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ok = true;
        }
        return ok;
    }

    public Pair<String, String> recordar() {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Recordar contraseña");
        dialog.setHeaderText("Te mandamos la contraseña al correo");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
//        ButtonType cancelButtonType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nick = new TextField();
        nick.setPromptText("nick");
        TextField correo = new TextField();
        correo.setPromptText("correo");

//        grid.add(new Label("Username:"), 0, 0);
        grid.add(nick, 1, 0);
//        grid.add(new Label("Password:"), 0, 1);
        grid.add(correo, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);

        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        nick.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!"".equals(correo.getText())) {
                loginButton.setDisable(false);
            }

        });
        correo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!"".equals(nick.getText())) {
                loginButton.setDisable(false);
            }

        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
//        Platform.runLater(() ->  nick.requestFocus());
        Platform.runLater(() -> loginButton.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter((ButtonType dialogButton) -> {
            if (dialogButton == loginButtonType) {
                Pair<String, String> resultado = new Pair<>(nick.getText(), correo.getText());
                return resultado;
            }
            return null;
        });
        dialog.showAndWait();
        return new Pair<>(nick.getText(), correo.getText());
    }

    public void prueba(String titulo, String mensaje) {
//        Notification info = new Notification("Title", "Info-Message");

        Notifier.INSTANCE.notifyError(titulo, mensaje);

    }
}
