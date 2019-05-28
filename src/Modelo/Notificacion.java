package Modelo;

import java.util.Optional;
import javafx.application.Platform;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javafx.util.Duration;
import javafx.util.Pair;
import org.controlsfx.control.Notifications;

public class Notificacion {

    public void info(String titulo, String mensaje) {
        Notifications notification = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(null)
                .hideAfter(Duration.seconds(20))
                .position(Pos.TOP_CENTER)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
//                System.out.println("Not supported yet.");
                    }

                });

        notification.showInformation();
        notification.show();
    }

    public void confirm(String titulo, String mensaje) {

        Notifications notification = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(null)
                .hideAfter(Duration.seconds(25))
                .position(Pos.TOP_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
//                System.out.println("Not supported yet.");
                    }

                });

        notification.showConfirm();
    }

    public void error(String titulo, String mensaje) {
        Notifications notification = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(null)
                .hideAfter(Duration.seconds(25))
                .position(Pos.CENTER)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
//                System.out.println("Not supported yet.");
                    }

                });

        notification.showError();
    }

    public void alert(String titulo, String mensaje) {
        Notifications notification = Notifications.create()
                .title(titulo)
                .text(mensaje)
                .graphic(null)
                .hideAfter(Duration.seconds(25))
                .position(Pos.TOP_LEFT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
//                System.out.println("Not supported yet.");
                    }

                });

        notification.showWarning();
    }

    public boolean alertWarningDelete(String titulo, String cadena) {
        boolean ok = false;
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning Delete");
        alert.setHeaderText(titulo);
        alert.setContentText(cadena);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ok =  true;
        } 
        return ok;
    }

    public Pair<String, String> recordar() {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Alzheimer");
        dialog.setHeaderText("Te mandamos la contraseña al correo");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
//        ButtonType cancelButtonType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType,ButtonType.CANCEL);

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
        Platform.runLater(() ->  loginButton.requestFocus());
        

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

    public void ventanaInfo(String titulo, String mensaje) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(titulo));
        content.setBody(new Text(mensaje));

        JFXDialog dialog = new JFXDialog();
        dialog.setContent(content);

        dialog.setAlignment(Pos.CENTER);

        dialog.show();
    }
}
