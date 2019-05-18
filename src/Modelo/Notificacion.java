
package Modelo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class Notificacion {
    
    public void info(String titulo, String mensaje){
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
    
    public void confirm(String titulo, String mensaje){
        
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

    public void error(String string) {
    }
    
}
