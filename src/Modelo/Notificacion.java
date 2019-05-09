
package Modelo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class Notificacion {
    
    public static void info(String titulo, String mensaje){
        Notifications notification = Notifications.create()
        .title(titulo)
        .text(mensaje)
        .graphic(null)
        .hideAfter(Duration.seconds(10))
        .position(Pos.TOP_CENTER)
        .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Not supported yet.");
            }

        });
        
        notification.showInformation();
    }
    
    public static void confirm(String titulo, String mensaje){
        Notifications notification = Notifications.create()
        .title(titulo)
        .text(mensaje)
        .graphic(null)
        .hideAfter(Duration.seconds(5))
        .position(Pos.TOP_RIGHT)
        .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Not supported yet.");
            }

        });
        
        notification.showConfirm();
    }
    
    public static void error(String titulo, String mensaje) {
        Notifications notification = Notifications.create()
        .title(titulo)
        .text(mensaje)
        .graphic(null)
        .hideAfter(Duration.seconds(5))
        .position(Pos.CENTER_RIGHT)
        .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Not supported yet.");
            }

        });
        
        notification.showError();
    }
    
    public static void alert(String titulo, String mensaje) {
        Notifications notification = Notifications.create()
        .title(titulo)
        .text(mensaje)
        .graphic(null)
        .hideAfter(Duration.seconds(5))
        .position(Pos.BOTTOM_RIGHT)
        .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Not supported yet.");
            }

        });
        
        notification.showWarning();   
    }
       
    
}
