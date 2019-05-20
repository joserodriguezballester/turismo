package Modelo;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;
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

    public void ventanaInfo(String titulo, String mensaje) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(titulo));
        content.setBody(new Text(mensaje));
        
        JFXDialog dialog = new JFXDialog();
        dialog.setContent(content);

        dialog.setAlignment(Pos.CENTER);
        
        dialog.show();
    }
//    public static void errorDB(String titulo, String mensaje) {
//        Image img = new Image("/Imagenes/iconos/botonError1.jpg");
//        Notifications notification = Notifications.create()
//        .title(titulo)
//        .text(mensaje)
//        .graphic(new ImageView(img))
//        .hideAfter(Duration.seconds(25))
//        .position(Pos.BOTTOM_RIGHT)
//        .onAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent arg0) {
//                System.out.println("Not supported yet.");
//            }
//
//        });
//        
//        notification.showError();   
//    }
}
