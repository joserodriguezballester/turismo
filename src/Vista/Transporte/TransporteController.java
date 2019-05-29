package Vista.Transporte;

import Datos.Bda.GestionBD;
import Datos.Bda.TransportesDAO;
import Modelo.Notificacion;
import Modelo.Transporte;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TransporteController implements Initializable {

    private static GestionBD gestion;
    private Connection conn;
    private TransportesDAO transDAO;
    private Transporte transporte;
    private Notificacion not;
    private List<Transporte> listaTransporte = new ArrayList<>();
    
    @FXML
    private TreeView<String> informacionTV;
    @FXML
    private AnchorPane fondoUsuario;
    @FXML
    private AnchorPane frenteAP;
    @FXML
    private ImageView imagTarj;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image img = new Image("Imagenes/fondoInformacion.jpg");
        ImageView imagev = new ImageView(img);
        fondoUsuario.getChildren().add(imagev);
        imagev.setFitHeight(730);
        imagev.setFitWidth(1300);
        imagev.setOpacity(0.8); 
       
        fondoUsuario.toBack();
        frenteAP.toFront();
        transDAO = new TransportesDAO(gestion);
        //CREACION NODO DAW
        TreeItem<String> mapasItem = new TreeItem<>("Mapas");
//        mapasItem.getChildren().add(new TreeItem<>("Autobuses"));
        TreeItem<String> autobuses = new TreeItem<>("Autobuses");
        autobuses.getChildren().add(new TreeItem("Turistico"));
        autobuses.getChildren().add(new TreeItem("Nocturnos"));
        mapasItem.getChildren().add(autobuses);
        mapasItem.getChildren().add(new TreeItem<>("Metro"));
        mapasItem.getChildren().add(new TreeItem<>("Ferry"));
        mapasItem.getChildren().add(new TreeItem<>("Tranvia"));
//        Node icono = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/icono.png")));
//        daw.setGraphic(icono);

        //CREACION NODO ASIR
        TreeItem<String> hotelTreeItem = new TreeItem<>("Centro");
        hotelTreeItem.getChildren().add(new TreeItem<>("Restaurantes"));
//        hotelTreeItem.getChildren().add(new TreeItem<>("hotel2"));

        //CREACION NODO SMR
//        TreeItem<String> smr = new TreeItem<>("SMR");
//        smr.getChildren().add(new TreeItem<>("Implantacion de aplicaciones web"));
//        smr.getChildren().add(new TreeItem<>("ofimatica"));
        //CREACION DEL TREEITEM INICIAL PARA RELLENAR EL TREEVIEW ciclos
        TreeItem<String> informacionItem = new TreeItem<>("inicio arbol");
        informacionTV.setRoot(informacionItem);
        informacionTV.setShowRoot(false);  //OCULTAR EL ROOT

//ASOCIAR AL NODO INFORMATICA LOS NODOS DE CADA CICLO
        informacionItem.getChildren().addAll(mapasItem, hotelTreeItem);
//OTRA FORMA DE ASOCIAR AL NODO INFORMATICA LOS NODOS DE CADA CICLO
//        informatica.getChildren().add(daw);
//        informatica.getChildren().add(asir);
//        informatica.getChildren().add(smr);

        //LO MOSTRAMOS DEPLEGADO
        informacionItem.setExpanded(true);
//           imagTarj.setImage(new Image("Imagenes/fondoActividad.jpg" ));
informacionTV.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> { 
// Body would go here
seleccionar();
});

    imagTarj.setFitWidth(927);
    imagTarj.setFitHeight(570);
    imagTarj.getStyleClass().add("imgInfor");
    imagTarj.toFront();

    
    }

    private void seleccionar() {
        TreeItem<String> nodoSeleccionado = informacionTV.getSelectionModel().getSelectedItem();
        if (nodoSeleccionado != null) {
            String modulo = nodoSeleccionado.getValue();
            transi();
            switch (modulo) {
                case "Turistico":
                    
                    imagTarj.setImage(new Image("Imagenes/informacion/autobus-turistico.jpg"));
                    break;
                case "Nocturnos":
                    imagTarj.setImage(new Image("Imagenes/informacion/autobusnocturno.gif"));
                    break;
                case "Metro":
                    imagTarj.setImage(new Image("Imagenes/informacion/metro_lineas_mapa.jpg"));
                    break;
                case "Ferry":
                    imagTarj.setImage(new Image("Imagenes/informacion/ferry.jpg"));
                    break;
                case "Tranvia":
                    imagTarj.setImage(new Image("Imagenes/informacion/plano-tranvias.png"));
                    break;
                case "Centro":
                    imagTarj.setImage(new Image("Imagenes/informacion/trans4.png"));
                    break;
                case "Restaurantes":
                    imagTarj.setImage(new Image("Imagenes/informacion/amsterdam-restaurantes-mapa.jpg"));
                    break;

            }
        }

    }
    private void transi(){
        FadeTransition ft = new FadeTransition(Duration.millis(1500), imagTarj);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();        
    }


}
