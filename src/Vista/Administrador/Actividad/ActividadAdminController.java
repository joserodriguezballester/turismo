
package Vista.Administrador.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Tipo;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private Notificacion not;
      
    public void setGestion(GestionBD gestion) {
        ActividadAdminController.gestion = gestion; 
    }
    
    public void ejecutaAlPrincipio() throws SQLException{
        activiDAO=new actividadesDAO(gestion);
        
        try {
            listaTipos = activiDAO.consultarTipoActividades();
        } catch (SQLException ex) {
             not.error("ERROR SQL","" + ex.getMessage() + 
                    " en ejecutarAlPrincipio() --- ActividadAdminController"); 
        }
        
        cargarCombo();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       not = new Notificacion();
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
            not.error("ERROR SQL","" + ex.getMessage() + 
                    " en listar() --- ActividadAdminController"); 
        } catch (Exception es){
            not.error("ERROR EXCEPTION","" + es.getMessage() + 
                    " en listar() --- ActividadAdminController");
        }
        cargarTabla(lista);
    }
// -------------------------------- CARGAR TABLA ---------------------------
    
    private void cargarTabla(List<Actividad> coleccion){

        actividades.clear();
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
// --------------------------- LISTAR POR TIPO ------------------------
    
    private void cargarTablaPorTipo(){
        List<Actividad> listaPorTipo = new ArrayList<>();
        Tipo tipo;
        int iden;      
                   
        iden = Integer.parseInt(comboListarIdTipo.getValue());
        tipo = dameTipo(iden);

        try{
           listaPorTipo = activiDAO.consultarActividadesPorTipo(tipo);
        }catch (SQLException ex){
           not.error("ERROR SQL","" + ex.getMessage() + 
                    " en cargarTablaPorTipo() --- ActividadAdminController");  
        }catch (Exception es){
           not.error("ERROR EXCEPTION","" + es.getMessage() + 
                    " en cargarTablaPorTipo() --- ActividadAdminController"); 
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
                imageView.setPreserveRatio(false);
            }
        } catch (Exception ex){
            not.error("ERROR EXCEPTION","" + ex.getMessage() + 
                    " en seleccionarItem() --- ActividadAdminController");
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
            not.error("ERROR SQL","" + ex.getMessage() + 
                    " en insertar() --- ActividadAdminController");
        }
        if(ok == true){
            not.info("INSERTAR CON EXITO EN TABLA ACTIVIDADES", 
                    " en insertar() --- ActividadAdminController");
        }
        else {
            not.info("INSERTAR SIN EXITO EN TABLA ACTIVIDADES", 
                    " en insertar() --- ActividadAdminController");
        }
    }
  
// -------------------------------- ACTUALIZAR --------------------------
    private void actualizar(){        
        boolean ok = false;
        int id, idsubTipo;
        double precio;
        String nombre, descripcion, horario, direccion, url, telefono, foto;
        
        actividad = tableview.getSelectionModel().getSelectedItem();
        
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
            not.error("ERROR SQL","" + ex.getMessage() + 
                    " en actualizar() --- ActividadAdminController");
        }if(ok){
            not.info("ACTUALIZAR CON EXITO EN TABLA ACTIVIDADES", 
                    " en actualizar() --- ActividadAdminController");
        }
        else {
            not.info("ACTUALIZAR SIN EXITO EN TABLA ACTIVIDADES", 
                    " en actualizar() --- ActividadAdminController");
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
            not.error("ERROR SQL","" + ex.getMessage() + 
                    " en actualizar() --- ActividadAdminController");
        }
        
        if(ok){
            not.info("ELIMINAR CON EXITO EN TABLA ACTIVIDADES", 
                    " en eliminar() --- ActividadAdminController");
        }
        else {
            not.info("ELIMINAR SIN EXITO EN TABLA ACTIVIDADES", 
                    " en eliminar() --- ActividadAdminController");
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
        listar();
    }

    @FXML
    private void modificar(ActionEvent event) {
        actualizar();
        listar();
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
