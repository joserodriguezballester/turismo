
package Vista.Administrador.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Tipo;
import Modelo.Usuario;
import Vista.Actividad.ActividadController;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author joser
 */
public class ActividadAdminController implements Initializable {

    @FXML
    private GridPane menu2;
    @FXML
    private Button botonAñadir;
    @FXML
    private Button botonListar;
    @FXML
    private Button botonBorrar;
    @FXML   
    private Button botonGuardar;
    @FXML
    private Button botonModificar;
    @FXML
    private Button botonListarIdTipo;
    @FXML
    private TableView<Actividad> tableview;
    @FXML
    private TableColumn<Actividad, Integer> tb_id;
    @FXML
    private TableColumn<Actividad, String> tb_nombre;
    @FXML
    private TableColumn<Actividad, String> tb_descripcion;
    @FXML
    private TableColumn<Actividad, String> tb_horario;
    @FXML
    private TableColumn<Actividad, Double> tb_precio;
    @FXML
    private TableColumn<Actividad, String> tb_direccion;
    @FXML
    private TableColumn<Actividad, String> tb_url;
    @FXML
    private TableColumn<Actividad, String> tb_telefono;
    @FXML
    private TableColumn<Actividad, String> tb_foto;
    @FXML
    private TableColumn<Actividad, Integer> tb_idTipo;
    @FXML
    private Pane paneFoto;
    @FXML
    private TextField textId;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textHorario;
    @FXML
    private TextArea textDescripcion;
    @FXML
    private TextField textPrecio;
    @FXML
    private TextField textDireccion;
    @FXML
    private TextField textUrl;
    @FXML
    private TextField textTelefono;
    @FXML
    private TextField textFoto;
    @FXML
    private TextField textIdTipo;
    @FXML
    private ComboBox<String> comboListarIdTipo = new ComboBox<String>();
    @FXML
    private ImageView imageView;
    
    
    private ObservableList<Actividad> actividades;
    private ObservableList<String> tipos;
    
    private List<Tipo> listaTipos = new ArrayList<>();
    
    private Actividad actividad;
    
    private static GestionBD gestion;

    private actividadesDAO activiDAO;
    
      
    public void setGestion(GestionBD gestion) {
        ActividadAdminController.gestion = gestion; 
    }
    
    public void ejecutaAlPrincipio() throws SQLException{
        activiDAO=new actividadesDAO(gestion);
        try {
            listaTipos = activiDAO.consultarTipoActividades();
        } catch (SQLException ex) {
            Notificacion.error("ERROR SQL EXCEPTION", "Ha habido un problema al "
                                                     + "\nconsultar a la DB"); 
        }
        cargarCombo();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actividades = FXCollections.observableArrayList();
    }
    
// ---------------------------- CARGAR COMBO TIPO ------------------------
    
    private void cargarCombo(){      
        tipos = FXCollections.observableArrayList();
        String id;                    
        for(Tipo valor: listaTipos){
            id = String.valueOf(valor.getId());
            tipos.add(id);
        }
        comboListarIdTipo.setItems(tipos);
    }
    
// ----------------------------- LISTAR ------------------------------------
    
    @FXML
    private void listar(){
        List<Actividad> lista = new ArrayList<>();       
        try {
            
            lista = activiDAO.listarActividad();
        } catch (SQLException ex) {
            Notificacion.error("ERROR SQL EXCEPTION", "Ha habido un problema al "
                                                     + "\nconsultar a la DB"); 
        } catch (Exception es){
            Notificacion.error("ERROR EXCEPTION ", "Ha ocurrido un grave problema");
        }
        cargarTabla(lista);
    }
// -------------------------------- CARGAR TABLA ---------------------------
    
    private void cargarTabla(List<Actividad> coleccion){
        actividades.remove(0, actividades.size());
        actividades.addAll(coleccion);
        tableview.setItems(actividades);

        tb_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tb_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tb_horario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        tb_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tb_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tb_url.setCellValueFactory(new PropertyValueFactory<>("url"));
        tb_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tb_foto.setCellValueFactory(new PropertyValueFactory<>("foto"));
        tb_idTipo.setCellValueFactory(new PropertyValueFactory<>("idsubTipo"));
            
    }
// --------------------------- TABLE VIEW POR TIPO ------------------------
    
    private void cargarTablaPorTipo(){
        List<Actividad> listaPorTipo = new ArrayList<>();
        Tipo tipo;
        int iden;
        
        iden = Integer.parseInt(comboListarIdTipo.getValue());
        tipo = dameTipo(iden);
        
        try{
           listaPorTipo = activiDAO.consultarActividadesPorTipo(tipo);
        }catch (SQLException ex){
           Notificacion.error("ERROR SQL EXCEPTION", "Ha habido un problema al "
                                                     + "\nconsultar a la DB"); 
        }catch (Exception es){
           Notificacion.error("ERROR EXCEPTION","verifica tu código no es correcto"); 
        }
        cargarTabla(listaPorTipo);
    }
    
    private Tipo dameTipo(int id){
       Tipo t = listaTipos.get(id -1);
       return t;
    }
    
// --------------------------- SELECCIONAR UN ITEM --------------------------
    
    private void seleccionarItem(){
        int id, idsubTipo;
        double precio;
        String nombre,descripcion,horario,direccion,url,telefono,foto;
               
        actividad = tableview.getSelectionModel().getSelectedItem();
                       
        id = actividad.getId();
        nombre = actividad.getNombre();
        descripcion = actividad.getDescripcion();
        horario = actividad.getHorario();
        precio = actividad.getPrecio();
        direccion = actividad.getDireccion();
        url = actividad.getUrl();
        telefono = actividad.getTelefono();
        foto = actividad.getFoto();
        idsubTipo = actividad.getIdsubTipo();

        textId.setText(String.valueOf(id));
        textNombre.setText(nombre);
        textDescripcion.setText(descripcion);
        textHorario.setText(horario);
        textPrecio.setText(String.valueOf(precio));
        textDireccion.setText(direccion);
        textUrl.setText(url);
        textTelefono.setText(telefono);
        textFoto.setText(foto);
        textIdTipo.setText(String.valueOf(idsubTipo));
        
        try {
            if(foto == null){
                imageView.setVisible(false);
            }
            else{
                imageView.setVisible(true);
                imageView.setImage(new Image("Imagenes/" + foto));
                imageView.setFitHeight(250);
                imageView.setFitWidth(250);
//                imageView.setPreserveRatio(false);
            }
        } catch (Exception ex){
            Notificacion.error("ERROR EXCEPTION","la foto no ha podido cargarse");
        }          
    }
    
/// ----------------------------- INSERTAR -------------------------------         
    private void insertar(){
       int id, idsubTipo;
       double precio;
       String nombre,descripcion, horario, url, direccion, telefono, foto;
       boolean ok = false;
       
       id = Integer.parseInt(textId.getText());
       nombre = textNombre.getText();
       descripcion = textDescripcion.getText();
       horario = textHorario.getText();
       precio = Double.parseDouble(textPrecio.getText());
       direccion = textDireccion.getText();
       url = textUrl.getText();
       telefono = textTelefono.getText();
       foto = textFoto.getText();
       idsubTipo = Integer.parseInt(textIdTipo.getText());
            
        try {
            ok = activiDAO.insertarActividad(id, nombre, precio, horario,
                         descripcion, url, direccion, telefono, foto, idsubTipo);
        } catch (SQLException ex) {
            Notificacion.error("ERROR SQL EXCEPTION", "Ha habido un problema al "
                                                     + "\ninsertar un registro");
        }
        if(ok == true){
            Notificacion.info("INSERTAR EN TABLA ACTIVIDADES", "La operación "
                    + "\nse ha realizado con éxito");
        }
        else {
            Notificacion.info("INSERTAR EN TABLA ACTIVIDADES","No se ha podido "
                     + "\ninsertar el registro verifica el problema");
        }
    }
  
// -------------------------------- ACTUALIZAR --------------------------
    private void actualizar(){        
        boolean ok = false;
        int id, idsubTipo;
        double precio;
        String nombre, descripcion, horario, direccion, url, telefono, foto;
        
        actividad = tableview.getSelectionModel().getSelectedItem();
        
//        id = actividad.getId();
//        nombre = actividad.getNombre();
//        descripcion = actividad.getDescripcion();
//        horario = actividad.getHorario();
//        precio = actividad.getPrecio();
//        direccion = actividad.getDireccion();
//        url = actividad.getUrl();
//        telefono = actividad.getTelefono();
//        foto = actividad.getFoto();
//        idsubTipo = actividad.getIdsubTipo();
        
        id = Integer.parseInt(textId.getText());
        nombre = textNombre.getText();
        descripcion = textDescripcion.getText();
        horario = textHorario.getText();
        precio = Double.parseDouble(textPrecio.getText());
        direccion = textDireccion.getText();
        url = textUrl.getText();
        telefono = textTelefono.getText();
        foto = textFoto.getText();
        idsubTipo = Integer.parseInt(textIdTipo.getText());
        
        try {
            ok = activiDAO.modificarActividad(id,nombre,precio,horario,descripcion,
                                      url,direccion,telefono,foto,idsubTipo);
        } catch (SQLException ex) {
            Notificacion.error("ERROR SQL EXCEPTION", "Ha habido un problema al "
                                                     + "\nactualizar un registro");
        }if(ok){
            Notificacion.info("ACTUALIZAR EN TABLA ACTIVIDADES", "La operación "
                    + "\nse ha realizado con éxito");
        }
        else {
            Notificacion.info("ACTUALIZAR EN TABLA ACTIVIDADES","No se ha podido "
                     + "\nactualizar el registro verifica el problema");
        }
    }
 
// -------------------------------- ELIMINAR ----------------------------
    
    private void eliminar(){
        boolean ok = false;
        int id;
              
        actividad = tableview.getSelectionModel().getSelectedItem();
        System.out.println("ACTIVIDAD: " + actividad);        
        id = actividad.getId();
               
        try {
            ok = activiDAO.borrarActividad(id);
        } catch (SQLException ex) {
//            Logger.getLogger(ActividadAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(ok){
            Notificacion.info("ELIMINAR EN TABLA ACTIVIDADES", "La operación "
                    + "\nse ha realizado con éxito");
        }
        else {
            Notificacion.info("ELIMINAR EN TABLA ACTIVIDADES","No se ha podido "
                     + "\neliminar el registro verifica el problema");
        }
    }
    
    
// ----------------------- LIMPIAR LOS CAMPOS DE TEXTO --------------------
    
    private void limpiar(){
        textId.clear();
        textNombre.clear();
        textHorario.clear();
        textDescripcion.clear();
        textPrecio.clear();
        textDireccion.clear();
        textUrl.clear();
        textTelefono.clear();
        textFoto.clear();
        textIdTipo.clear();
        
        imageView.setVisible(false);
        
    }
  
// ------------------------------ EVENTOS ---------------------------------
    
    @FXML
    private void anadir(ActionEvent event) {
        insertar();
    }

    @FXML
    private void modificar(ActionEvent event) {
        actualizar();
    }

    @FXML
    private void borrar(ActionEvent event) {
        eliminar();
    }

    private void listar(ActionEvent event) {      
        listar();
    }
    
    @FXML
    private void guardar(ActionEvent event) {
        limpiar();
    }

    @FXML
    private void mostrar(MouseEvent event) {
        seleccionarItem();
    }

    @FXML
    private void listarId(ActionEvent event) {
         cargarTablaPorTipo();
    }
}
