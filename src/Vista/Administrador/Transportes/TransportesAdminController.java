/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador.Transportes;

import Datos.Bda.GestionBD;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
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

    /**
     * Initializes the controller class.
     */
    
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    private void cargarTV(){ 
        //CREACION NODO DAW
        TreeItem<String> daw = new TreeItem<>("DAW");
        daw.getChildren().add(new TreeItem<>("Base de datos"));
        daw.getChildren().add(new TreeItem<>("Programacion"));
        Node icono = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/icono.png")));
        daw.setGraphic(icono);

        //CREACION NODO ASIR
        TreeItem<String> asir = new TreeItem<>("ASIR");
        asir.getChildren().add(new TreeItem<>("Redes"));
        asir.getChildren().add(new TreeItem<>("Sistemas operativos"));

        //CREACION NODO SMR
        TreeItem<String> smr = new TreeItem<>("SMR");
        smr.getChildren().add(new TreeItem<>("Implantacion de aplicaciones web"));
        smr.getChildren().add(new TreeItem<>("ofimatica"));

        //CREACION DEL TREEITEM INICIAL PARA RELLENAR EL TREEVIEW ciclos
        TreeItem<String> informatica = new TreeItem<>("Ciclos de Informática");
//        ciclos.setRoot(informatica); //tree view
//        ciclos.setShowRoot(false);  //OCULTAR EL ROOT

//ASOCIAR AL NODO INFORMATICA LOS NODOS DE CADA CICLO
        informatica.getChildren().addAll(daw, asir, smr);
//OTRA FORMA DE ASOCIAR AL NODO INFORMATICA LOS NODOS DE CADA CICLO
//        informatica.getChildren().add(daw);
//        informatica.getChildren().add(asir);
//        informatica.getChildren().add(smr);

    //LO MOSTRAMOS DEPLEGADO
        informatica.setExpanded(true);
    }

    @FXML
    private void seleccionar(MouseEvent event) {
// //       TreeItem<String> nodoSeleccionado = ciclos.getSelectionModel().getSelectedItem();
//        if (nodoSeleccionado != null) {
//            String modulo = nodoSeleccionado.getValue();
//            seleccionado.setText(modulo); //muestra 
//        }
//
//    }
//
    }
    public void setGestion(GestionBD gestion) {
        this.gestion =gestion;
    }

    
    
    
    
    
}
