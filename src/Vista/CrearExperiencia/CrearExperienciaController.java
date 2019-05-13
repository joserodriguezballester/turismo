/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.CrearExperiencia;

import Datos.Bda.GestionBD;
import Datos.Bda.experienciasDAO;
import Modelo.ActividadExperiencia;
import Modelo.Experiencia;
import Modelo.Usuario;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author 20857464y
 */
public class CrearExperienciaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private GestionBD gestion;
    private Usuario usuario;
    private Experiencia experiencia;
    @FXML
    private JFXListView<ActividadExperiencia> listaActividades;

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Experiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Experiencia experiencia) {
        this.experiencia = experiencia;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void imprimir(ActionEvent event) {
        try {
            System.out.println(gestion.toString());
        } catch (Exception e) {
            System.out.println("gestion");
        }
        try {
            System.out.println(usuario.toString());
        } catch (Exception e) {
            System.out.println("usuario");
        }

    }

}
