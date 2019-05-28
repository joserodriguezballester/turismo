/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador.TiposSubtipos;

import Datos.Bda.GestionBD;
import Datos.Bda.experienciasDAO;
import Datos.Bda.subtiposDAO;
import Datos.Bda.tiposDAO;
import Modelo.Notificacion;
import Modelo.Tipo;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;

/**
 * FXML Controller class
 *
 * @author 20857464y
 */
public class TiposSubtiposController implements Initializable {

    private GestionBD gestion;
    private tiposDAO tipDAO;
    private subtiposDAO subDAO;
    private Notificacion not;

    @FXML
    JFXTreeTableView tablaTipos;
    @FXML
    JFXTreeTableView tablaSubtipos;

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
        tipDAO = new tiposDAO(gestion);
        subDAO = new subtiposDAO(gestion);

        JFXTreeTableColumn<Tipo, String> columnaIdTipos = new JFXTreeTableColumn<>("Id");
        columnaIdTipos.setPrefWidth(125);
        JFXTreeTableColumn<Tipo, String> columnaNombreTipos = new JFXTreeTableColumn<>("Tipo");
        columnaNombreTipos.setPrefWidth(125);

//        final TreeItem<Tipo> root = new RecursiveTreeItem<Tipo>(tipDAO.consultarTipos(), RecursiveTreeObject::getChildren);

//        tablaTipos = new JFXTreeTableView(root);
        tablaTipos.setEditable(true);

        tablaTipos.getColumns().setAll(columnaIdTipos, columnaNombreTipos);

        try {
            for (Tipo tipo : tipDAO.consultarTipos()) {

            }
        } catch (Exception e) {
            not.error("Error", "Error cargando los tipos");
        }

    }
}
