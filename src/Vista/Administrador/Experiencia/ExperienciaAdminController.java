
package Vista.Administrador.Experiencia;

import Datos.Bda.actividadesDAO;
import Datos.Bda.GestionBD;
import Datos.Bda.experienciasActividadesDAO;
import Datos.Bda.experienciasDAO;
import Modelo.Actividad;
import Modelo.ActividadExperiencia;
import Modelo.Experiencia;
import Modelo.Notificacion;
import Vista.Administrador.Experiencia.InterfaceOrdenacion.OrdenFechInicioAE;
import Vista.Administrador.Experiencia.InterfaceOrdenacion.OrdenFechaE;
import Vista.Administrador.Experiencia.InterfaceOrdenacion.OrdenNombreE;
import Vista.Administrador.Experiencia.InterfaceOrdenacion.OrdenPrecioAE;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.Notifications;



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
    private TextField textPrecio;
    @FXML
    private TextField textNumPlazas;
    @FXML
    private JFXTimePicker horaInicio;
    @FXML
    private JFXTimePicker horaFin;
    @FXML
    private JFXDatePicker fechaInicio;
    @FXML
    private JFXDatePicker fechaFin;
    @FXML
    private JFXDatePicker fechaTopeValidez;
    @FXML
    private ToggleGroup grupoEx;    
    @FXML
    private RadioButton radioId;
    @FXML
    private RadioButton radioNombre;
    @FXML
    private RadioButton radioFechaTope;
    @FXML
    private ToggleGroup grupoAcEx;
    @FXML
    private RadioButton radioPrecio;
    @FXML
    private RadioButton radioFechaInicio;
    @FXML
    private RadioButton radioOrden;
    @FXML
    private AnchorPane anchorPane;
    
    
    private ObservableList<Experiencia> obExperiencias;
    private ObservableList<ActividadExperiencia> obActiviEncias;
    
    private static GestionBD gestion;
    private experienciasDAO experienDAO;
    private experienciasActividadesDAO eaDAO;
    private Experiencia experiencia;
    private Notificacion not;
    private ActividadExperiencia actExperiencia;
    private actividadesDAO activiDAO;
    
    boolean delete;
    
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obExperiencias = FXCollections.observableArrayList();
        obActiviEncias = FXCollections.observableArrayList();
        
        not = new Notificacion();
        anchorPane.getStyleClass().add("fondoExperienciaAdmin");
    }
    
       
    public void setGestion(GestionBD gestion){
        ExperienciaAdminController.gestion = gestion;
    }
    
    public void ejecutaAlPrincipio(){
        experienDAO = new experienciasDAO(gestion);
        activiDAO = new actividadesDAO(gestion);
        eaDAO = new experienciasActividadesDAO(gestion);
        
        listar();
       
    }
    
// ----------------------- RADIO BUTTON EXPERIENCIAS ----------------------
    
    private void ordenarId(){
        Collections.sort(obExperiencias);
    }
    
    private void ordenarNombre(){
        Collections.sort(obExperiencias,new OrdenNombreE());
    }
    
    private void ordenarFechaTope(){
        Collections.sort(obExperiencias,new OrdenFechaE());
    }
    
     
    
// -------------------- RADIO BUTTON ACTIVIDAD EXPERIENCIAS ---------------
    
    private void ordenarOrden(){
        Collections.sort(obActiviEncias);
    }
    
    private void ordenarFechaInicio(){
        Collections.sort(obActiviEncias,new OrdenFechInicioAE());
    }
    
    private void ordenarPrecio(){
        Collections.sort(obActiviEncias, new OrdenPrecioAE());
    }
    
    
    
// -------------------------- INSERTAR ----------------------------------
    
    private void insertar(){
        int id, idUsuario;
        String nombre,descripcion,foto;
        LocalDate fechaTope = null;
        List<ActividadExperiencia> lista = null;
        boolean ok = false;
        

        
        try {
            id = Integer.parseInt(textExperiencia.getText());
            idUsuario = Integer.parseInt(textUsuario.getText());
            nombre = textNombre.getText();
            descripcion = textDescripcion.getText();
            fechaTope = fechaTopeValidez.getValue();
            textFecha.setText(fechaTope.toString());
            foto = textFoto.getText();

            Experiencia nueva = new Experiencia(id, idUsuario, nombre, descripcion, fechaTope, foto,lista);
          
            ok = experienDAO.insertarExperiencia(nueva);
            
        } catch (SQLException ex) {
            not.error("ERROR SQL", "Verifica el código");
            ex.getStackTrace();
        } catch (java.time.DateTimeException dt){
            not.error("ERROR EN FECHA TOPE", "Formato de fecha invalido");
        }
        
        if(ok){
            not.info("INSERTAR REGISTRO", "Operación realizada con exito");
        }
        else{
            not.error("ERRO AL INSERTAR REGISTRO", "Operación fallida");
        }
    }
    
    private LocalDateTime fechaDT(LocalDate fecha, LocalTime hora){
        String momentoTotal1;
        LocalDateTime fechaRetorno = null;
    
        momentoTotal1 = fecha + " " + hora;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        fechaRetorno = LocalDateTime.parse(momentoTotal1, formatter);
        
        return fechaRetorno;
    }
    
    
    private List<ActividadExperiencia> insertarActividadExperiencia(){
        int numOrden, idExperiencia,numPlazas;
        
        Actividad actividad = null;
        LocalDateTime fechaIni, fechaFinal;
        double precio;
        List<ActividadExperiencia> listaActividadExperiencia = new ArrayList<>();
        ActividadExperiencia acEx;
        boolean ok = false;
        
               
        actExperiencia = tableListaExperiencias.getSelectionModel().getSelectedItem();
        try {
            numOrden = Integer.parseInt(textOrden.getText());
            idExperiencia = Integer.parseInt(textIdExperiencia.getText());
            actividad = activiDAO.consultarActividad(Integer.parseInt(textIdActividad.getText()));
//            fechaIni = LocalDateTime.parse(textFechaInicio.getText());
            fechaIni = fechaDT(fechaInicio.getValue(),horaInicio.getValue());
            textFechaInicio.setText(fechaIni.toString().replace('T', ' '));
            fechaFinal = fechaDT(fechaFin.getValue(),horaFin.getValue());
            textFechaFinal.setText(fechaFinal.toString().replace('T',' '));
            precio = Double.parseDouble(textPrecio.getText());
            numPlazas = Integer.parseInt(textNumPlazas.getText());
            
            acEx = new ActividadExperiencia(numOrden,idExperiencia,actividad,fechaIni,fechaFinal,precio,numPlazas);
            listaActividadExperiencia.add(acEx);
            ok = eaDAO.insertarActividadExperiencia(acEx);
                
        } catch (SQLException ex) {
            not.error("ERROR SQL", "Error al insertar un registro en la tabla ActividadExperiencia");
        } catch (java.time.format.DateTimeParseException dt){           
            not.error("ERROR EN EL FORMATO FECHA", "El formato de fecha no es correcto\n"
                    + " debe se asi --->  yyyy-MM-ddTHH:mm:ss  ");
        } catch (NumberFormatException nf) { 
            if(nf.getCause() == null && (nf.getMessage().equals("empty String") ||
                    (nf.getMessage().equals("For input string: \" \"")) ||
                    (nf.getMessage().equals("For input string: \"\"")))){
                not.error("ERROR CAMPO DE TEXTO VACIO","Verifica los campos de texto"
                      + " \n no pueden estar vacios");
                              
            }    
            else if((nf.getMessage().equals("For input string: \""+ textOrden.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textIdExperiencia.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textIdActividad.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textPrecio.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textNumPlazas.getText() +"\""))){
                        not.error("ERROR FORMATO NUMERICO", "No introducir texto en los siguientes "
                       + "campos\n: Orden, idExperiencia, idActividad, precio, numPlazas"); 
            }
            else{
                
                    not.error("ERROR DE OTRO TIPO","Verifica los campos de texto"
                      + " \n algo raro ha ocurrido");
            }     
        }   
       
        if(ok){
            not.info("INSERTAR DB TURISMO","Operación realizada con éxito");
        }
        else {
            not.info("INSERTAR DB TURISMO", "No se pudo insertar el registro");
        }
        return listaActividadExperiencia;
    }

// ------------------------- MODIFICAR -------------------------------
    
    private void actualizar(){
        int id,idUsuario;
        String nombre,descripcion,foto;
        LocalDate fechaTope;
        boolean ok = false;
        
        experiencia = tableView.getSelectionModel().getSelectedItem();
        try {
            idUsuario = Integer.parseInt(textUsuario.getText());
            nombre = textNombre.getText();
            descripcion = textDescripcion.getText();
            fechaTope = LocalDate.parse(textFecha.getText());
            foto = textFoto.getText();
            id = Integer.parseInt(textExperiencia.getText());
        
            ok = experienDAO.modificarExperiencia(idUsuario,nombre,descripcion,fechaTope,foto,id);
            
        } catch(SQLException ex){
            not.error("SQL ERROR", "Error al modificar en tabla Experiencias, Verifica tu código");
        } catch(DateTimeParseException dt){
            String valor = "Text '' could not be parsed at index 0";
            if(dt.equals(valor)){
                not.info("FORMATO FECHA INVALIDO", "Debes introducir un formato como este yyyy-MM-dd");
            }
        }        
        if(ok){
            not.info("MODIFICAR EXPERIENCIA","Operación realizada con éxito");
        }
        else {
            not.error("FORMATO FECHA INVALIDO", "Debes introducir un formato de fecha \ncomo este --> yyyy-MM-dd");
        }
        
    }
    
    private void modificarActividadExperiencia(){
        int orden, idExperiencia,numPlazas;
        double precio;
        LocalDateTime fechaInicio,fechaFinal;
        Actividad idActividad;
        boolean ok = false;
        
        actExperiencia = tableListaExperiencias.getSelectionModel().getSelectedItem();
        
        try {
            orden = Integer.parseInt(textOrden.getText());
            idExperiencia = Integer.parseInt(textIdExperiencia.getText());
            idActividad = activiDAO.consultarActividad(Integer.parseInt(textIdActividad.getText()));
            fechaInicio = LocalDateTime.parse(textFechaInicio.getText().replace(' ', 'T'));
            fechaFinal = LocalDateTime.parse(textFechaFinal.getText().replace(' ', 'T'));
            precio = Double.parseDouble(textPrecio.getText());
            numPlazas = Integer.parseInt(textNumPlazas.getText());
            
            
            ok = eaDAO.modificarActividadExperiencia(orden, idExperiencia, idActividad, fechaInicio, fechaFinal, precio, numPlazas);
        } catch (SQLException ex) {
            not.error("ERROR SQL", "Error al modificar un registro en la tabla ActividadExperiencia");
        } catch (java.time.format.DateTimeParseException dt){           
            not.error("ERROR EN EL FORMATO FECHA", "El formato de fecha no es correcto\n"
                    + " debe se asi --->  yyyy-MM-ddTHH:mm:ss  ");
        } catch (NumberFormatException nf) { 
            if(nf.getCause() == null && (nf.getMessage().equals("empty String") ||
                    (nf.getMessage().equals("For input string: \" \"")) ||
                    (nf.getMessage().equals("For input string: \"\"")))){
                not.error("ERROR CAMPO DE TEXTO VACIO","Verifica los campos de texto"
                      + " \n no pueden estar vacios");
                              
            }    
            else if((nf.getMessage().equals("For input string: \""+ textOrden.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textIdExperiencia.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textIdActividad.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textPrecio.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textNumPlazas.getText() +"\""))){
                        not.error("ERROR FORMATO NUMERICO", "No introducir texto en los siguientes "
                       + "campos\n: Orden, idExperiencia, idActividad, precio, numPlazas"); 
            }
            else{
                
                    not.error("ERROR DE OTRO TIPO","Verifica los campos de texto"
                      + " \n algo raro ha ocurrido");
            }     
        }   
       
        if(ok){
            not.info("MODIFICAR DB TURISMO","Operación realizada con éxito");
        }
        else {
            not.info("MODIFICAR DB TURISMO", "No se pudo insertar el registro");
        }      
    }
 
// ---------------------------- BORRAR --------------------------------    

    private void eliminar(){
        boolean ok = false;
        int id;
        
        experiencia = tableView.getSelectionModel().getSelectedItem();
        id = experiencia.getId();
        
        Notifications notification = Notifications.create()
        .title("ESTAS SEGURO DE ELIMINAR LA EXPERIENCIA  " + id)
        .text("El proceso no tiene vuelta atras")
        .graphic(null)
        .hideAfter(javafx.util.Duration.seconds(40))
        .position(Pos.TOP_LEFT)
        .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {

                Notifications notification = Notifications.create()
                .title("ELIMINAR REGISTRO EXPERIENCIA  " + id + " \nEN TABLA EXPERIENCIA ACTIVIDAD")
                .text("Comfirma haciendo click sobre la ventana")
                .graphic(null)
                .hideAfter(javafx.util.Duration.seconds(40))
                .position(Pos.TOP_LEFT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                            experienDAO.borrarExperiencia(id);
                        } catch(SQLException ex){
                            not.error("ERROR AL ELIMINAR EL REGISTRO \nEN TABLA EXPERIENCIAS", "No es posible realizar la operación");
                        }
                        
                        if(delete){
                            not.info("REGISTRO ELIMINADO EN TABLA EXPERIENCIAS", "Operación realizada con éxito");
                        }
                        else{
                            not.info("ELIMINACION FALLIDA EN TABLA EXPERIENCIAS", "No se ha podido eliminar el registro");
                        }
                    }
                });
        
                notification.showConfirm();
                                    
            }
        });
        
        notification.showWarning();

    }
    
    private void eliminarActividadExperiencia(){
        int id, orden;
        
        actExperiencia = tableListaExperiencias.getSelectionModel().getSelectedItem();
        id = actExperiencia.getIdExperiencia();
        orden = actExperiencia.getOrden();

        Notifications notification = Notifications.create()
        .title("ESTAS SEGURO DE ELIMINAR \nLA EXPERIENCIA CON NUMERO DE ORDEN " + orden)
        .text("El proceso no tiene vuelta atras")
        .graphic(null)
        .hideAfter(javafx.util.Duration.seconds(40))
        .position(Pos.TOP_LEFT)
        .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {

                Notifications notification = Notifications.create()
                .title("ELIMINAR REGISTRO EXPERIENCIA CON NUMERO DE ORDEN " + orden + " \nEN TABLA EXPERIENCIA ACTIVIDAD")
                .text("Comfirma haciendo click sobre la ventana")
                .graphic(null)
                .hideAfter(javafx.util.Duration.seconds(40))
                .position(Pos.TOP_LEFT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                            delete = eaDAO.eliminarActividadExperiencia(orden, id);
                        } catch(SQLException ex){
                            not.error("ERROR AL ELIMINAR EL REGISTRO \nEN TABLA EXPERIENCIA ACTIVIDAD", "No es posible realizar la operación");
                        }
                        
                        if(delete){
                            not.info("REGISTRO ELIMINADO EN TABLA EXPERIENCIA ACTIVIDAD", "Operación realizada con éxito");
                        }
                        else{
                            not.info("ELIMINACION FALLIDA EN TABLA EXPERIENCIA ACTIVIDAD", "No se ha podido eliminar el registro");
                        }
                    }
                });
        
                notification.showConfirm();
                                    
            }
        });
        
        notification.showWarning();
        
    }
    
    
// ---------------------------- LISTAR -----------------------------------
    
    
    private void listar(){
        List<Experiencia> lista = new ArrayList<>();
        
        try{
            
            lista = experienDAO.consultarTodasExperiencias();
            
            obExperiencias.clear();
            obExperiencias.addAll(lista);
            
            if(radioId.isSelected()){
               this.ordenarId();
            }
            if(radioNombre.isSelected()){
                this.ordenarNombre();
            }
            if(radioFechaTope.isSelected()){
                this.ordenarFechaTope();
            }
            
            
            tableView.setItems(obExperiencias);
            
            tb_idExperiencia.setCellValueFactory(new PropertyValueFactory<>("id"));
            tb_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tb_fechaTope.setCellValueFactory(new PropertyValueFactory<>("fechaTopeValidez"));
            tb_foto.setCellValueFactory(new PropertyValueFactory<>("foto"));
            tb_idUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
            
        } catch(SQLException es){
            not.error("ERROR SQL","" + es.getMessage() + 
                    "Error al listar informacion de experiencias");
        }
    }
    
    
    
    private void listarActividadExperiencia(List<ActividadExperiencia> listaDos){
        obActiviEncias.clear();
        obActiviEncias.addAll(listaDos);
        
        if(radioOrden.isSelected()){
            this.ordenarOrden();
        }
        if(radioFechaInicio.isSelected()){
            this.ordenarFechaInicio();
        }
        if(radioPrecio.isSelected()){
            this.ordenarPrecio();
        }
        
        
        tableListaExperiencias.setItems(obActiviEncias);

        tb_orden.setCellValueFactory(new PropertyValueFactory<>("orden"));
        tb_idExperi.setCellValueFactory(new PropertyValueFactory<>("idExperiencia"));
        tb_idActividad.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        tb_fechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        tb_fechaFinal.setCellValueFactory(new PropertyValueFactory<>("fechaFinal"));
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
        textFecha.setText(fechaTope.format(DateTimeFormatter.ofPattern("yyyy-LL-dd")));
        textFoto.setText(foto);
        textUsuario.setText(String.valueOf(idUsuario));
        
        try {
            if(foto != null){
                imageView.setVisible(true);
                imageView.setImage(new Image("Imagenes/" + foto));
                imageView.setFitWidth(275);
                imageView.setFitHeight(250);
                imageView.setPreserveRatio(false);
            }
            else {
                imageView.setVisible(true);                
            }
            
            listaDos = eaDAO.consultarActividadesDeExperiencia(id);
            
        } catch(Exception ex){
            not.error("ERROR EXCEPTION","" + ex.getMessage() + 
                    "Error no encuentra la ruta de las imagenes");
        } 
        
        listarActividadExperiencia(listaDos);
    }
    
    private void seleccionarItemActividaExperiencia(){
        int numOrden, idExperienciaLista,numPlazas;
        double precio;
        Actividad actividad;
        LocalDateTime fechaIni, fechaFin ;
              
        actExperiencia = tableListaExperiencias.getSelectionModel().getSelectedItem();
        
        if(actExperiencia != null){
            numOrden = actExperiencia.getOrden();
            idExperienciaLista = actExperiencia.getIdExperiencia();
            actividad = actExperiencia.getActividad();
            fechaIni = actExperiencia.getFechaInicio();
            fechaFin = actExperiencia.getFechaFinal();
            precio = actExperiencia.getPrecio();
            numPlazas = actExperiencia.getNumPlazas();


            actExperiencia = new ActividadExperiencia(numOrden,idExperienciaLista,actividad,fechaIni,fechaFin,precio,numPlazas);

            textOrden.setText(String.valueOf(numOrden));
            textIdExperiencia.setText(String.valueOf(idExperienciaLista));
            textIdActividad.setText(String.valueOf(actividad.getId()));
            textFechaInicio.setText(fechaIni.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            textFechaFinal.setText(fechaFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));       
            textPrecio.setText(String.valueOf(precio));
            textNumPlazas.setText(String.valueOf(numPlazas)); 
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
        
        textOrden.clear();
        textIdExperiencia.clear();
        textIdActividad.clear();
        textFechaInicio.clear();
        textFechaFinal.clear();
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
        insertarActividadExperiencia();
        seleccionarItem();

    }

    @FXML
    private void modificarNueva(ActionEvent event) {
        modificarActividadExperiencia();
        seleccionarItem();
    }

    @FXML
    private void borrarNueva(ActionEvent event) {
        eliminarActividadExperiencia();
        seleccionarItem();
    }

    @FXML
    private void ordenarEx(ActionEvent event) {
        listar();
    }

    @FXML
    private void ordenarAcEx(ActionEvent event) {
        seleccionarItem();
    }

    
}
