
package Vista.Administrador.Experiencia;

import Datos.Bda.GestionBD;
import Datos.Bda.experienciasDAO;
import Modelo.Experiencia;
import Modelo.Notificacion;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    
    
    private ObservableList<Experiencia> obExperiencias;
    
    private static GestionBD gestion;
    private experienciasDAO experienDAO;
    private Experiencia experiencia;
    private Notificacion not;
    
    
    public void setGestion(GestionBD gestion){
        ExperienciaAdminController.gestion = gestion;
    }
    
    public void ejecutaAlPrincipio(){
        experienDAO = new experienciasDAO(gestion);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obExperiencias = FXCollections.observableArrayList();
    }
    
// -------------------------- INSERTAR ----------------------------------
    
    private void insertar(){
        int id, idUsuario;
        String nombre,descripcion,foto;
        LocalDate fechaTope;
        boolean ok = false;
        
        id = Integer.parseInt(textExperiencia.getText());
        idUsuario = Integer.parseInt(textUsuario.getText());
        nombre = textNombre.getText();
        descripcion = textDescripcion.getText();
        fechaTope = LocalDate.parse(textFecha.getText());
        foto = textFoto.getText();
        
        try {
            ok = experienDAO.insertarExperiencia(id, idUsuario, nombre, descripcion, fechaTope, foto);
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
    
    
// -------------------------- SELECCIONAR ITEM -----------------------------

    
    private void seleccionarItem(){
        int id, idUsuario;
        String nombre,descripcion,foto;
        LocalDate fechaTope;
               
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
            
        } catch(Exception ex){
            not.error("ERROR EXCEPTION","" + ex.getMessage() + 
                    " en seleccionarItem() --- ExperienciaAdminController");
        }      
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

    private void limpiar(ActionEvent event) {
        limpiar();
    }
    
}
