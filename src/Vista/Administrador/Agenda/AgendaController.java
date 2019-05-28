/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador.Agenda;

import Datos.Bda.GestionBD;
import Datos.Bda.experienciasDAO;
import Modelo.Notificacion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author 20857464y
 */
public class AgendaController implements Initializable {

    private GestionBD gestion;
    private Notificacion not;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
        inicio();
    }

    private void inicio() {
        not = new Notificacion();
    }
}
