
package Vista.Administrador.Experiencia;

import Datos.Bda.actividadesDAO;
import Datos.Bda.GestionBD;
import Datos.Bda.experienciasActividadesDAO;
import Datos.Bda.experienciasDAO;
import Modelo.Actividad;
import Modelo.ActividadExperiencia;
import Modelo.Experiencia;
import Modelo.Notificacion;
import Modelo.ValidarCampos;
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
    private TextField textHoraInicio;
    @FXML
    private TextField textHoraFin;
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
//        ValidarCampos validar = new ValidarCampos();
        int id = 0, idUsuario = 0;
        String nombre,descripcion,foto;
        LocalDate fechaTope = null;
        List<ActividadExperiencia> lista = null;
        boolean ok = false;
        
        boolean general = validarExperiencia();
        
        idUsuario = Integer.parseInt(textUsuario.getText());
        nombre = textNombre.getText();
        descripcion = textDescripcion.getText();           
        fechaTope = fechaTopeValidez.getValue();
        foto = textFoto.getText();
//        String idAux = String.valueOf(experiencia.getId());
//        id = Integer.parseInt(textExperiencia.getText());

       
            
        if(general){               
            try {
                Experiencia nueva = new Experiencia(id, idUsuario, nombre, descripcion, fechaTope, foto,lista);
                
                ok = experienDAO.insertarExperiencia(nueva);

            } catch (SQLException ex) {
                not.error("ERROR", "Error al intentar conectar con la base de datos");
                ex.getStackTrace();
            } catch (java.time.DateTimeException dt){
                not.alert("ERROR EN FECHA TOPE", "Formato de fecha invalido");
            }

            if(ok){
                not.confirm("INSERTAR REGISTRO", "Operación realizada con exito");
            }
            else{
                not.alert("ERROR AL INSERTAR REGISTRO", "Operación fallida");
            }
        }
        else{
            //not.error("ERROR AL INSERTAR LA TABLA EXPERIENCIAS", "");
        }
    }
    
    private LocalDateTime fechaDT(LocalDate fecha, LocalTime hora){
        String momentoTotal1;
        LocalDateTime fechaRetorno = null;
    
        momentoTotal1 = fecha + " " + hora;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        fechaRetorno = LocalDateTime.parse(momentoTotal1, formatter);
        
        return fechaRetorno;
    }
    
    
    private boolean insertarActividadExperiencia(){
        int numOrden, idExperiencia,numPlazas;
        double precio;        
        LocalDateTime fechaIni, fechaFinal;       
        Actividad actividad = null;
        ActividadExperiencia acEx;
        boolean ok = false;
              
        actExperiencia = tableListaExperiencias.getSelectionModel().getSelectedItem();
        try {
            numOrden = Integer.parseInt(textOrden.getText());
            idExperiencia = Integer.parseInt(textIdExperiencia.getText());
            actividad = activiDAO.consultarActividad(Integer.parseInt(textIdActividad.getText()));
            fechaIni = LocalDateTime.of(fechaInicio.getValue(), horaInicio.getValue());
            fechaFinal = LocalDateTime.of(fechaFin.getValue(), horaFin.getValue());
            precio = Double.parseDouble(textPrecio.getText());
            numPlazas = Integer.parseInt(textNumPlazas.getText());
            
            acEx = new ActividadExperiencia(numOrden,idExperiencia,actividad,fechaIni,fechaFinal,precio,numPlazas);
            ok = eaDAO.insertarActividadExperiencia(acEx);
                
        } catch (SQLException ex) {
            not.error("ERROR", "Error al insertar un registro en la tabla ActividadExperiencia");
        } catch (java.time.format.DateTimeParseException dt){           
            not.alert("ERROR EN EL FORMATO FECHA", "El formato de fecha no es correcto\n"
                    + " debe se asi --->  yyyy-MM-ddTHH:mm:ss  ");
        } catch (NumberFormatException nf) { 
            if(nf.getCause() == null && (nf.getMessage().equals("empty String") ||
                    (nf.getMessage().equals("For input string: \" \"")) ||
                    (nf.getMessage().equals("For input string: \"\"")))){
                not.alert("ERROR CAMPO DE TEXTO VACIO","Verifica los campos de texto"
                      + " \n no pueden estar vacios");
                              
            }    
            else if((nf.getMessage().equals("For input string: \""+ textOrden.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textIdExperiencia.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textIdActividad.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textPrecio.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textNumPlazas.getText() +"\""))){
                        not.alert("ERROR FORMATO NUMERICO", "No introducir texto en los siguientes "
                       + "campos\n: Orden, idExperiencia, idActividad, precio, numPlazas"); 
            }
            else{
                    not.alert("ERROR DE OTRO TIPO","Verifica los campos de texto"
                      + " \n algo raro ha ocurrido");
            }     
        }   
       
        if(ok){
            not.confirm("INSERTAR DB TURISMO","Operación realizada con éxito");
        }
        else {
            not.alert("INSERTAR DB TURISMO", "No se pudo insertar el registro");
        }
        return ok;
    }

// ------------------------- MODIFICAR -------------------------------
    
    private void actualizar(){
        ValidarCampos validar = new ValidarCampos();
        int id = 0,idUsuario = 0;
        String nombre,descripcion,foto;
        LocalDate fechaTope;
        boolean ok = false;
        
        experiencia = tableView.getSelectionModel().getSelectedItem();
        
        idUsuario = Integer.parseInt(textUsuario.getText());
        nombre = textNombre.getText();
        descripcion = textDescripcion.getText();           
        fechaTope = fechaTopeValidez.getValue();
        foto = textFoto.getText();
        String idAux = String.valueOf(experiencia.getId());

        boolean general = validarExperiencia();

        if(general){
            try {
            ok = experienDAO.modificarExperiencia(idUsuario,nombre,descripcion,fechaTope,foto,id);
            
            } catch(SQLException ex){
                not.error("ERROR", "Error al modificar en tabla Experiencias");
            } catch(DateTimeParseException dt){
                String valor = "Text '' could not be parsed at index 0";
                if(dt.equals(valor)){
                    not.alert("FORMATO FECHA INVALIDO", "Debes introducir un formato como este yyyy-MM-dd");
                }
            }        
            if(ok){
                not.confirm("MODIFICAR EXPERIENCIA","Operación realizada con éxito");
            }
            else {
                not.alert("FORMATO FECHA INVALIDO", "Debes introducir un formato de fecha \ncomo este --> yyyy-MM-dd");
            } 
        }
        else{
            not.error("ERROR ", "AL MODIFICAR LA TABLA EXPERIENCIAS");
        }       
    }
    
    
    private boolean validarExperiencia(){
        ValidarCampos validar = new ValidarCampos();
        int id = 0,idUsuario = 0;
        String nombre,descripcion,foto;
        LocalDate fechaTope;
        boolean general = true;
       
        if(textUsuario.getText().isEmpty()){
            not.error("ERROR ID USUARIO DESCONOCIDO", "El idUsuario no existe, debe ser\n"
                    + " un usuario que exista en la tabla experiencias");
            general = false;
        }
        else{
            if(validar.validarNumEntero(textUsuario.getText()) == 0){
                general = false;
                not.alert("ERROR CAMPO IDUSUARIO VACIO", "No puede estar vacio");
            }
            else{
                idUsuario = Integer.parseInt(textUsuario.getText());
            }
        }
        nombre = textNombre.getText();
        if(!nombre.isEmpty()){

        }
        else {
            general = false;
            not.alert("ERROR CAMPO NOMBRE VACIO","No puede estar vacio");
        }
        descripcion = textDescripcion.getText();           
        fechaTope = fechaTopeValidez.getValue();
        if(fechaTope != null){
            if(validar.validarFechaLD(fechaTope) == true){
                general =  false;
                not.alert("ERROR FECHA SELECCIONADA", "No puede ser una fecha anterior a la actual");
            }
            else{
                textFecha.setText(fechaTope.toString());
            } 
        }
        else{
            general = false;
            not.alert("ERROR CAMPO FECHA VACIO", "No puede estar vacio");
        }                    
        foto = textFoto.getText();
        if(foto.isEmpty()){

        }
        else{
            if(validar.validarFoto(foto) == true){

            }
            else{
                general = false;
                not.alert("ERROR DE FORMATO FOTO","La extensión no es válida");
            }
        }
//        String idAux = String.valueOf(experiencia.getId());
//        if(idAux.isEmpty()){
//            not.error("ERROR ID DESCONOCIDO", "El id no existe, debe ser\n"
//                    + " un id que exista en la tabla experiencias"); 
//            general = false;
//        }
//        else{
//            if(validar.validarNumEntero(textExperiencia.getText()) == 0){
//                general = false;
//                not.alert("ERROR CAMPO ID VACIO", "No puede estar vacio");
//            }
//            else{
//                id = Integer.parseInt(textExperiencia.getText());
//            }
//        }
        return general;
    }
    
    
    private void modificarActividadExperiencia(){
        int orden, idExperiencia,numPlazas;
        double precio;
        LocalDateTime fechaIni,fechaFinal;
        Actividad idActividad;
        boolean ok = false;
        
        actExperiencia = tableListaExperiencias.getSelectionModel().getSelectedItem();
        
        try {
            orden = Integer.parseInt(textOrden.getText());
            idExperiencia = Integer.parseInt(textIdExperiencia.getText());
            idActividad = activiDAO.consultarActividad(Integer.parseInt(textIdActividad.getText()));
            fechaIni = LocalDateTime.of(fechaInicio.getValue(), horaInicio.getValue());
            fechaFinal = LocalDateTime.of(fechaFin.getValue(), horaFin.getValue());
            precio = Double.parseDouble(textPrecio.getText());
            numPlazas = Integer.parseInt(textNumPlazas.getText());
            
            
            ok = eaDAO.modificarActividadExperiencia(orden, idExperiencia, idActividad, fechaIni, fechaFinal, precio, numPlazas);
        } catch (SQLException ex) {
            not.error("ERROR", "Error al modificar un registro en la tabla ActividadExperiencia");
        } catch (java.time.format.DateTimeParseException dt){           
            not.alert("ERROR EN EL FORMATO FECHA", "El formato de fecha no es correcto\n"
                    + " debe se asi --->  yyyy-MM-ddTHH:mm:ss  ");
        } catch (NumberFormatException nf) { 
            if(nf.getCause() == null && (nf.getMessage().equals("empty String") ||
                    (nf.getMessage().equals("For input string: \" \"")) ||
                    (nf.getMessage().equals("For input string: \"\"")))){
                not.alert("ERROR CAMPO DE TEXTO VACIO","Verifica los campos de texto"
                      + " \n no pueden estar vacios");
                              
            }    
            else if((nf.getMessage().equals("For input string: \""+ textOrden.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textIdExperiencia.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textIdActividad.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textPrecio.getText() +"\"")) ||
                    (nf.getMessage().equals("For input string: \""+ textNumPlazas.getText() +"\""))){
                        not.alert("ERROR FORMATO NUMERICO", "No introducir texto en los siguientes "
                       + "campos\n: Orden, idExperiencia, idActividad, precio, numPlazas"); 
            }
            else{
                    not.error("ERROR DE OTRO TIPO","Verifica los campos de texto"
                      + " \n algo raro ha ocurrido");
            }     
        }   
       
        if(ok){
            not.confirm("MODIFICAR DB TURISMO","Operación realizada con éxito");
        }
        else {
            not.alert("MODIFICAR DB TURISMO", "No se pudo insertar el registro");
        }      
    }
 
// ---------------------------- BORRAR --------------------------------    

    private void eliminar(){
        boolean ok = false;
        int id;
        
        experiencia = tableView.getSelectionModel().getSelectedItem();
        id = experiencia.getId();
        
        ok = not.alertWarningDelete("SE ELIMINARA EL REGISTRO " + id + " EN LA TABLA EXPERIENCIAS",
                "¿ESTAS SEGURO !!! ?");
        
        if(ok){
            try {
                experienDAO.borrarExperiencia(id);
                if(ok){
                    not.confirm("SE HA ELIMINADO EL REGISTRO " + id + " EN LA TABLA EXPERIENCIAS", 
                    " Operación realizada con éxito");
                }
            } catch (SQLException ex) {
                not.error("ERROR", "Error al eliminar el registro "+ id + " de tabla experiencias");
            }
        }

    }
  
    private void eliminarActividadExperiencia(){
        int id, orden;
        boolean ok = false;
        
        actExperiencia = tableListaExperiencias.getSelectionModel().getSelectedItem();
        id = actExperiencia.getIdExperiencia();
        orden = actExperiencia.getOrden();
        
            ok = not.alertWarningDelete("SE ELIMINARA EL REGISTRO CON NUMERO DE ORDEN " + orden + "",
                "¿ESTAS SEGURO !!! ?");
        
        if(ok){
            try {
                 delete = eaDAO.eliminarActividadExperiencia(orden, id);
                if(ok){
                    not.confirm("SE HA ELIMINADO EL REGISTRO " + orden + " EN LA TABLA EXPERIENCIA ACTIVIDAD", 
                    " Operación realizada con éxito");
                }
            } catch (SQLException ex) {
                not.error("ERROR", "Error al eliminar el registro "+ orden + " de tabla experiencia actividad");
            }
        }
        
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
            not.error("ERROR SQL", "Error al listar informacion de experiencias");
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
//        textFecha.setText(fechaTope.format(DateTimeFormatter.ofPattern("yyyy-LL-dd")));
        fechaTopeValidez.setValue(fechaTope);
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
            not.error("ERROR LA IMAGEN NO EXISTE EN EL ARCHIVO DE IMAGENES", 
                    "No encuentra la ruta de esa imagen");
        } 
        
        listarActividadExperiencia(listaDos);
    }
    
    private void seleccionarItemActividaExperiencia(){
        int numOrden, idExperienciaLista,numPlazas;
        double precio;
        Actividad actividad;
        LocalDateTime fechaInicial, fechaFinal ;
        LocalDate f_ini,f_fin;
        LocalTime h_ini, h_fin;
              
        actExperiencia = tableListaExperiencias.getSelectionModel().getSelectedItem();
        
        if(actExperiencia != null){
            numOrden = actExperiencia.getOrden();
            idExperienciaLista = actExperiencia.getIdExperiencia();
            actividad = actExperiencia.getActividad();
            fechaInicial = actExperiencia.getFechaInicio();
            f_ini = fechaInicial.toLocalDate();
            h_ini = fechaInicial.toLocalTime();
            fechaFinal = actExperiencia.getFechaFinal();
            f_fin = fechaInicial.toLocalDate();
            h_fin = fechaInicial.toLocalTime();
            precio = actExperiencia.getPrecio();
            numPlazas = actExperiencia.getNumPlazas();


            actExperiencia = new ActividadExperiencia(numOrden,idExperienciaLista,actividad,fechaInicial,fechaFinal,precio,numPlazas);

            textOrden.setText(String.valueOf(numOrden));
            textIdExperiencia.setText(String.valueOf(idExperienciaLista));
            textIdActividad.setText(String.valueOf(actividad.getId()));
            fechaInicio.setValue(f_ini);
            horaInicio.setValue(h_ini);
            fechaFin.setValue(f_fin);
            horaFin.setValue(h_fin);
            
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
        fechaTopeValidez.setValue(null);
        textFoto.clear();
        textUsuario.clear();
        
        textOrden.clear();
        textIdExperiencia.clear();
        textIdActividad.clear();
        textFechaInicio.clear();
        textFechaFinal.clear();
        fechaInicio.setValue(null);
        horaInicio.setValue(null);
        fechaFin.setValue(null);
        horaFin.setValue(null);
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
