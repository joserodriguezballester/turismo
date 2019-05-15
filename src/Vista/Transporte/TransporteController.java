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
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class TransporteController implements Initializable {

    @FXML
    private ImageView imagTarj;
    
    
    private static GestionBD gestion;
    private Connection conn;
    private TransportesDAO transDAO;
    private Transporte transporte;
    private Notificacion not;
    
    
    private List<Transporte> listaTransporte = new ArrayList<>();
    @FXML
    private TreeView<String> informacionTV;
    

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       transDAO = new TransportesDAO(gestion);
         //CREACION NODO DAW
        TreeItem<String> mapasTreeItem = new TreeItem<>("Mapas");
        mapasTreeItem.getChildren().add(new TreeItem<>("Autobuses"));
        mapasTreeItem.getChildren().add(new TreeItem<>("Metro"));
        mapasTreeItem.getChildren().add(new TreeItem<>("Ferry"));
//        Node icono = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/icono.png")));
//        daw.setGraphic(icono);

        //CREACION NODO ASIR
        TreeItem<String> hotelTreeItem = new TreeItem<>("Hoteles");
        hotelTreeItem.getChildren().add(new TreeItem<>("hotel1"));
        hotelTreeItem.getChildren().add(new TreeItem<>("hotel2"));

        //CREACION NODO SMR
//        TreeItem<String> smr = new TreeItem<>("SMR");
//        smr.getChildren().add(new TreeItem<>("Implantacion de aplicaciones web"));
//        smr.getChildren().add(new TreeItem<>("ofimatica"));

        //CREACION DEL TREEITEM INICIAL PARA RELLENAR EL TREEVIEW ciclos
        TreeItem<String> informacionItem = new TreeItem<>("inicio arbol");
        informacionTV.setRoot(informacionItem);
//        ciclos.setShowRoot(false);  //OCULTAR EL ROOT

//ASOCIAR AL NODO INFORMATICA LOS NODOS DE CADA CICLO
        informacionItem.getChildren().addAll(mapasTreeItem, hotelTreeItem);
//OTRA FORMA DE ASOCIAR AL NODO INFORMATICA LOS NODOS DE CADA CICLO
//        informatica.getChildren().add(daw);
//        informatica.getChildren().add(asir);
//        informatica.getChildren().add(smr);

    //LO MOSTRAMOS DEPLEGADO
        informacionItem.setExpanded(true);
//           imagTarj.setImage(new Image("Imagenes/fondoActividad.jpg" ));
    }
    @FXML
    private void seleccionar(MouseEvent event) {
        TreeItem<String> nodoSeleccionado = informacionTV.getSelectionModel().getSelectedItem();
        if (nodoSeleccionado != null) {
            String modulo = nodoSeleccionado.getValue();
            if (modulo.equals("Autobuses")){
//                 Image img = new Image("Imagenes/fondoActividad.jpg");
                 imagTarj.setImage(new Image("Imagenes/fondoActividad.jpg" ));
//        ImageView imagev = new ImageView(img);
//               imagTarj.setImage(img);
            }
        }

    }
   

}
