/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador.Transportes;

import Datos.Bda.GestionBD;
import Datos.Bda.TransportesDAO;
import Modelo.Notificacion;
import Modelo.Transporte;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class TransportesAdminController implements Initializable {

    @FXML
    private TreeView<String> tarjetasTV;
    @FXML
    private ImageView imagTarj;
    @FXML
    private ImageView imagDescrip;
    @FXML
    private JFXTextArea descripTA;
    @FXML
    private JFXTextField DuracionTF;
    @FXML
    private JFXTextField precioTF;
    @FXML
    private JFXTextField seleccionado;
    private GestionBD gestion;
    private Transporte transporte;
    private Notificacion not;
    
//Amsterdam Travel Ticket
//Amsterdam Travel Ticket
//Amsterdam Travel Ticket
//Day or multi-day Ticket
//Day or multi-day Ticket
//Day or multi-day Ticket
//Day or multi-day Ticket
//Day or multi-day Ticket
//I amsterdam city card
//I amsterdam city card
//I amsterdam city card
//I amsterdam city card
//I amsterdam city card
//Amsterdam & Region Travel Ticket
//Holland Travel Ticket

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            System.out.println("entra aqui");
            transporte = new Transporte();
            TransportesDAO transporteDAO = new TransportesDAO(gestion);
            List<String> listatipos = transporteDAO.listaTipos();
            List<Transporte> listaTransportes = transporteDAO.listarTarjetas();
            for (String listatipo : listatipos) {
                TreeItem<String> padreTreeItem = new TreeItem<>(listatipo);
                for (Transporte listaTransporte : listaTransportes) {
                    if (listatipo.equals(listaTransporte.getTipo())) {
                        padreTreeItem.getChildren().add(new TreeItem<>(listaTransporte.getNombre()));
                        
                    }
                    
                }
            }
            TreeItem<String> tarjetas = new TreeItem<>("Tarjetas");
            tarjetasTV.setRoot(tarjetas);
        } catch (SQLException ex) {
            not.error("ERROR SQL", "" + ex.getMessage() + 
                    "en  initialize --- TransportesAdminController");
        }

    }

    public void setGestion(GestionBD gestion) {
        this.gestion = gestion;
    }
}