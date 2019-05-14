/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Transporte;

import Datos.Bda.GestionBD;
import Datos.Bda.TransportesDAO;
import Modelo.Notificacion;
import Modelo.Transporte;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;


public class TransporteController implements Initializable {

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
    
    @FXML
    private TreeView<?> tarjetasTV;
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
    
    
    private static GestionBD gestion;
    private Connection conn;
    private TransportesDAO transDAO;
    private Transporte transporte;
    
    
    private List<Transporte> listaTransporte = new ArrayList<>();
    

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       transDAO = new TransportesDAO(gestion);
    }
    
    private void listar(){      
        try {
            listaTransporte = transDAO.listarTarjetas();
        } catch (SQLException ex) {
        
        }
        System.out.println("Lista Transporte: " + listaTransporte);
    }

}
