
package Vista.Administrador.Experiencia;

import Datos.Bda.actividadesDAO;
import Datos.Bda.GestionBD;
import Datos.Bda.experienciasActividadesDAO;
import Datos.Bda.experienciasDAO;
import Modelo.Actividad;
import Modelo.ActividadExperiencia;
import Modelo.Experiencia;
import Modelo.Notificacion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;



public class ExperienciaAdminController implements Initializable {

    @FXML
    private GridPane menu2;  
    @FXML
    private TextField textExperiencia;   
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textDescripcion;
    @FXML
    private TextField textFecha;
    @FXML
    private TextField textFoto;
    @FXML
    private TextField textUsuario;
    @FXML
    private TableView<Experiencia> tableView;
    @FXML
    private TableColumn<Experiencia, Integer> tb_idExperiencia;
    @FXML
    private TableColumn<Experiencia, String> tb_nombre;
    @FXML
    private TableColumn<Experiencia, String> tb_descripcion;
    @FXML
    private TableColumn<Experiencia, LocalDate> tb_fechaTope;
    @FXML
    private TableColumn<Experiencia, String> tb_foto;
    @FXML
    private TableColumn<Experiencia, Integer> tb_idUsuario;
    @FXML
    private ImageView imageView;
    @FXML
    private TableView<ActividadExperiencia> tableListaExperiencias;
    @FXML
    private TableColumn<ActividadExperiencia, Integer> tb_orden;
    @FXML
    private TableColumn<ActividadExperiencia, Integer> tb_idExperi;
    @FXML
    private TableColumn<ActividadExperiencia, Integer> tb_idActividad;
    @FXML
    private TableColumn<ActividadExperiencia, LocalDateTime> tb_fechaInicio;
    @FXML
    private TableColumn<ActividadExperiencia, LocalDateTime> tb_fechaFinal;
    @FXML
    private TableColumn<ActividadExperiencia, Duration> tb_duracion;
    @FXML
    private TableColumn<ActividadExperiencia, Double> tb_precio;
    @FXML
    private TableColumn<ActividadExperiencia, Integer> tb_numPlazas;
    @FXML
    private TextField textOrden;
    @FXML
    private TextField textIdExperiencia;
    @FXML
    private TextField textIdActividad;
    @FXML
    private TextField textFechaInicio;
    @FXML
    private TextField textFechaFinal;
    @FXML
    private TextField textDuracion;
    @FXML
    private TextField textPrecio;
    @FXML
    private TextField textNumPlazas;
    
    
    private ObservableList<Experiencia> obExperiencias;
    private ObservableList<ActividadExperiencia> obActiviEncias;
    
    private static GestionBD gestion;
    private experienciasDAO experienDAO;
    private experienciasActividadesDAO eaDAO;
    private Experiencia experiencia;
    private Notificacion not = new Notificacion();
    private ActividadExperiencia actExperiencia;
    private actividadesDAO activiDAO;
    
      
    public void setGestion(GestionBD gestion){
        ExperienciaAdminController.gestion = gestion;
    }
    
    public void ejecutaAlPrincipio(){
        experienDAO = new experienciasDAO(gestion);
        activiDAO = new actividadesDAO(gestion);
        eaDAO = new experienciasActividadesDAO(gestion);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obExperiencias = FXCollections.observableArrayList();
        obActiviEncias = FXCollections.observableArrayList();
    }
    
// -------------------------- INSERTAR ----------------------------------
    
    private void insertar(){
        int id, idUsuario;
        String nombre,descripcion,foto;
        LocalDate fechaTope;
        List<ActividadExperiencia> lista = null;
        boolean ok = false;
        
        id = Integer.parseInt(textExperiencia.getText());
        idUsuario = Integer.parseInt(textUsuario.getText());
        nombre = textNombre.getText();
        descripcion = textDescripcion.getText();
        fechaTope = LocalDate.parse(textFecha.getText());
        foto = textFoto.getText();
        //lista = textArea.getText().toString();
        
        try {
            Experiencia nueva = new Experiencia(id, idUsuario, nombre, descripcion, fechaTope, foto,lista);
            ok = experienDAO.insertarExperiencia(nueva);
        } catch (SQLException ex) {
            not.error("ERROR SQL", "Verifica el código");
        }
        
        if(ok){
            not.info("INSERTAR REGISTRO", "Operación realizada con exito");
        }
        else{
            not.error("ERRO AL INSERTAR REGISTRO", "Operación fallida");
        }
    }

// ------------------------- MODIFICAR -------------------------------
    
    private void actualizar(){
        int id,idUsuario;
        String nombre,descripcion,foto,fechaAux;
        LocalDate fechaTope;
        boolean ok = false;
        
        experiencia = tableView.getSelectionModel().getSelectedItem();
        
        idUsuario = Integer.parseInt(textUsuario.getText());
        nombre = textNombre.getText();
        descripcion = textDescripcion.getText();
        fechaTope = LocalDate.parse(textFecha.getText());
        foto = textFoto.getText();
        id = Integer.parseInt(textExperiencia.getText());
        
        try {
            ok = experienDAO.modificarExperiencia(idUsuario,nombre,descripcion,fechaTope,foto,id);
            
        } catch(SQLException ex){
            not.error("SQL ERROR", "Verifica tu código");
        }
        
        if(ok){
            not.info("MODIFICAR EXPERIENCIA","Operación realizada con éxito");
        }
        else {
            not.error("ERROR AL MODIFICAR", "Ni se ha podido realizar la operación");
        }
        
    }
 
// ---------------------------- BORRAR --------------------------------    

    private void eliminar(){
        boolean ok = false;
        int id;
        
        experiencia = tableView.getSelectionModel().getSelectedItem();
        id = experiencia.getId();
        
        try {
            ok = experienDAO.borrarExperiencia(id);
        } catch (SQLException ex) {
            not.error("ERROR SQL", "No se puede realizar la operación");
        }
        
        if(ok){
            not.info("ELIMINAR TABLA EXPERIENCIAS", "Operación realizada con éxito");
        }
        else{
            not.alert("ELIMINACION FALLIDA", "No se ha podido eliminar el registro");
        }
    }
    
    
// ---------------------------- LISTAR -----------------------------------
    
    
    private void listar(){
        List<Experiencia> lista = new ArrayList<>();
        System.out.println("dentro de listar expeController");
        try{
            System.out.println("    DENTRO DE LISTAR");
            lista = experienDAO.consultarTodasExperiencias();
            System.out.println("   MAS ADENTRO DE LISTAR");
            obExperiencias.clear();
            obExperiencias.addAll(lista);
            tableView.setItems(obExperiencias);
            
            tb_idExperiencia.setCellValueFactory(new PropertyValueFactory<>("id"));
            tb_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tb_fechaTope.setCellValueFactory(new PropertyValueFactory<>("fechaTopeValidez"));
            tb_foto.setCellValueFactory(new PropertyValueFactory<>("foto"));
            tb_idUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
            
        } catch(SQLException es){
            not.error("ERROR SQL","" + es.getMessage() + 
                    " en listar() --- ExperienciaAdminController");
        }
    }
    
    private void listarActividadExperiencia(List<ActividadExperiencia> listaDos){
        obActiviEncias.clear();
        obActiviEncias.addAll(listaDos);
        tableListaExperiencias.setItems(obActiviEncias);

        tb_orden.setCellValueFactory(new PropertyValueFactory<>("orden"));
        tb_idExperi.setCellValueFactory(new PropertyValueFactory<>("idExperiencia"));
        tb_idActividad.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        tb_fechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        tb_fechaFinal.setCellValueFactory(new PropertyValueFactory<>("fechaFinal"));
        tb_duracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        tb_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tb_numPlazas.setCellValueFactory(new PropertyValueFactory<>("numPlazas"));
        
        
    }
    
    
// -------------------------- SELECCIONAR ITEM -----------------------------

    
    private void seleccionarItem(){
        int id, idUsuario;
        String nombre,descripcion,foto;
        LocalDate fechaTope;
        List<ActividadExperiencia> listaDos = new ArrayList<>();
        
        
        experiencia = tableView.getSelectionModel().getSelectedItem();
        
        id = experiencia.getId();
        nombre = experiencia.getNombre();
        descripcion = experiencia.getDescripcion();
        fechaTope = experiencia.getFechaTopeValidez();
        foto = experiencia.getFoto();
        idUsuario = experiencia.getIdUsuario();
        
        textExperiencia.setText(String.valueOf(id));
        textNombre.setText(nombre);
        textDescripcion.setText(descripcion);
        textFecha.setText(fechaTope.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        textFoto.setText(foto);
        textUsuario.setText(String.valueOf(idUsuario));
        
        try {
            if(foto != null){
                imageView.setVisible(true);
                imageView.setImage(new Image("Imagenes/" + foto));
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(false);
            }
            else {
                imageView.setVisible(false);                
            }
            
            listaDos = eaDAO.consultarActividadesDeExperiencia(id);
            
        } catch(Exception ex){
            not.error("ERROR EXCEPTION","" + ex.getMessage() + 
                    " en seleccionarItem() --- ExperienciaAdminController");
        } 
        System.out.println("listaDOs: " + listaDos);
        listarActividadExperiencia(listaDos);
    }
    
    private void seleccionarItemActividaExperiencia(){
        int numOrden, idExperienciaLista,numPlazas;
        double precio;
        Actividad actividad;
        LocalDateTime fechaIni, fechaFin ;
        Duration duracion;
              
        actExperiencia = tableListaExperiencias.getSelectionModel().getSelectedItem();
        
        numOrden = actExperiencia.getOrden();
        idExperienciaLista = actExperiencia.getIdExperiencia();
        actividad = actExperiencia.getActividad();
        fechaIni = actExperiencia.getFechaInicio();
        fechaFin = actExperiencia.getFechaFinal();
        duracion = actExperiencia.getDuracion();
        precio = actExperiencia.getPrecio();
        numPlazas = actExperiencia.getNumPlazas();
        
        
        actExperiencia = new ActividadExperiencia(numOrden,idExperienciaLista,actividad,fechaIni,fechaFin,precio,numPlazas);
        
        textOrden.setText(String.valueOf(numOrden));
        textIdExperiencia.setText(String.valueOf(idExperienciaLista));
        textIdActividad.setText(String.valueOf(actividad));
        textFechaInicio.setText(fechaIni.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));
        textFechaFinal.setText(fechaFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")));       
        textDuracion.setText(String.valueOf(duracion.toHours() + " Horas"));
        textPrecio.setText(String.valueOf(precio) + "€");
        textNumPlazas.setText(String.valueOf(numPlazas));
    }

    
// ------------------------- LIMPIAR ------------------------------------   
    
    @FXML
    private void limpiar(){
        textExperiencia.clear();
        textNombre.clear();
        textDescripcion.clear();
        textFecha.clear();
        textFoto.clear();
        textUsuario.clear();
        
        textOrden.clear();
        textIdExperiencia.clear();
        textIdActividad.clear();
        textFechaInicio.clear();
        textFechaFinal.clear();
        textDuracion.clear();
        textPrecio.clear();
        textNumPlazas.clear();
        
        imageView.setVisible(false);
    }
    
// ------------------------- EVENTOS -------------------------------------   

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

    @FXML
    private void ver(ActionEvent event) {
        listar();
    }

    @FXML
    private void mostrar(MouseEvent event) {
        seleccionarItem();
    }
    
    @FXML
    private void mostrarActividadExperiencia(MouseEvent event) {
        seleccionarItemActividaExperiencia();
    }
    

    private void limpiar(ActionEvent event) {
        limpiar();
    }

    @FXML
    private void añadirNueva(ActionEvent event) {
        
    }

    @FXML
    private void modificarNueva(ActionEvent event) {
        
    }

    @FXML
    private void borrarNueva(ActionEvent event) {
        
    }

    
}
