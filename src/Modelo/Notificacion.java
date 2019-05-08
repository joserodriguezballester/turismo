/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author 20857464y
 */
public class Notificacion {

    Notifications notificacion;

    public void error(String titulo, String mensaje) {
        Notifications.create()
            .text(mensaje)
            .title(titulo)
            .showInformation();
    }

}
