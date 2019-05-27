package Vista.Administrador.Actividad;

import Datos.Bda.GestionBD;
import Datos.Bda.actividadesDAO;
import Datos.Bda.tiposDAO;
import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Tipo;
import Modelo.ValidarCampos;
import Vista.Administrador.Actividad.InterfaceOrdenacion.OrdenNombreA;
import Vista.Administrador.Actividad.InterfaceOrdenacion.OrdenPrecioA;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


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
    private ComboBox<Tipo> comboListarIdTipo = new ComboBox<Tipo>();
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ToggleGroup grupo;
    @FXML
    private RadioButton radioId;
    @FXML
    private RadioButton radioNombre;
    @FXML
    private RadioButton radioPrecio;
    @FXML
    private Button botonImportar;
    
    private ObservableList<Actividad> actividades;
    private ObservableList<String> tipos;

    private List<Tipo> listaTipos = new ArrayList<>();
    private List<Actividad> lista = new ArrayList<>();
    
    private Actividad actividad;

    private static GestionBD gestion;

    private actividadesDAO activiDAO;
    private tiposDAO tipoDAO;
    private Notificacion not;
    
    public void setGestion(GestionBD gestion) {
        ActividadAdminController.gestion = gestion;
    }

    public void ejecutaAlPrincipio() throws SQLException {
        activiDAO = new actividadesDAO(gestion);
        tipoDAO = new tiposDAO(gestion);

        try {
            listaTipos = tipoDAO.consultarTipos();
        } catch (SQLException ex) {
            not.error("ERROR SQL", "" + ex.getMessage()
                    + " Error al consultar la DB turismo");
        }

        cargarCombo();
        cargarTabla(activiDAO.listarActividad());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        not = new Notificacion();
        actividades = FXCollections.observableArrayList();
        
        anchorPane.getStyleClass().add("fondoExperienciaAdmin");
        
        radioId.setToggleGroup(grupo);
        radioNombre.setToggleGroup(grupo);
        radioPrecio.setToggleGroup(grupo);
        radioId.setSelected(true);
        
    }
    
    
// --------------------------- RADIO BUTTON -----------------------------
    
//    private void ordenarPorId(){       
//        Collections.sort(actividades);
//    }
    
    private void ordenarPorNombre(){
        Collections.sort(actividades,new OrdenNombreA());
    }
    
    private void ordenarPorPrecio(){
        Collections.sort(actividades,new OrdenPrecioA());
    }

// ---------------------------- CARGAR COMBO TIPO ------------------------
    private void cargarCombo() {

        for (Tipo valor : listaTipos) {
            comboListarIdTipo.getItems().add(valor);
        }
    }

// ----------------------------- LISTAR ------------------------------------
    @FXML
    private void listar(){    
        try {            
            lista = activiDAO.listarActividad();           
          
        } catch (SQLException ex) {
            not.error("ERROR SQL", "" + ex.getMessage()
                    + " Error al consultar la DB turismo");
        } catch (Exception es) {
            not.error("ERROR EXCEPTION", "" + es.getMessage()
                    + " Error al mostrar la información");
        }
   
        cargarTabla(lista);
    }
// -------------------------------- CARGAR TABLA ---------------------------

    private void cargarTabla(List<Actividad> coleccion) {
        
        
        actividades.clear();
        actividades.addAll(coleccion);


        if(radioNombre.isSelected()){
           this.ordenarPorNombre();

          // tb_nombre.setStyle("-fx-background-color: rgba(132, 174, 232,.5)");
        }   
        else {
            tb_nombre.setStyle("");
        }

        if(radioPrecio.isSelected()){
           this.ordenarPorPrecio();
         
           //tb_precio.setStyle("-fx-background-color: rgba(132, 174, 232,.5)");
        }   
 
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

    private void cargarTablaPorTipo() {
        List<Actividad> listaPorTipo = new ArrayList<>();
        Tipo tipo;

        tipo = comboListarIdTipo.getValue();

        try {
            listaPorTipo = activiDAO.consultarActividadesPorTipo(tipo);
        } catch (SQLException ex) {
            not.error("ERROR SQL", "" + ex.getMessage()
                    + " Error al consultar la DB turismo");
        } catch (Exception es) {
            not.error("ERROR EXCEPTION",
                    "Primero selecciona un tipo de actividad, (Combo)\n "
                    + "luego puedes presionar el boton de listarID ");
        }
        cargarTabla(listaPorTipo);
    }

// --------------------------- SELECCIONAR UN ITEM --------------------------
    private void seleccionarItem() {
        int id, idsubTipo;
        double precio;
        Actividad actividad = null;
        
        String nombre, descripcion, horario, direccion, url, telefono, foto;

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
            if (foto == null) {
                imageView.setVisible(false);
            } else {
                imageView.setVisible(true);
                imageView.setImage(new Image("Imagenes/" + foto));
                imageView.setFitHeight(200);
                imageView.setFitWidth(300);
                imageView.setPreserveRatio(false);
            }
        } catch (Exception ex) {
            not.error("NO SE ENCUENTRA ESA IMAGEN EN EL ARCHIVO DE IMAGENES", 
                    " Error al no encontrar la ruta de las imagenes");
        }
    }

/// ----------------------------- INSERTAR -------------------------------         
    private void insertar() {
        ValidarCampos validar = new ValidarCampos();
        int id = 0, idsubTipo;
        double precio;
        String nombre, descripcion, horario, url, direccion, telefono, foto;
        boolean ok = false;
        boolean general = true;
      
        nombre = textNombre.getText();
        if(!nombre.isEmpty()){

        }
        else {
            general = false;
            not.error("ERROR CAMPO NOMBRE VACIO","No puede estar vacio");
        }
        descripcion = textDescripcion.getText();       
        horario = textHorario.getText();       
        precio = validar.validarNumDecimal(textPrecio.getText());
        if(precio != 0.0){
            
        }
        else {
            general = false;
            not.error("ERROR DE FORMATO PRECIO","No puede estar vacio");
        }
        direccion = textDireccion.getText();       
        url = textUrl.getText();
        if(url.isEmpty()){
            
        }
        else{
            if(validar.isURL(url ) == true){
           
        }
            else {
                general = false;
                not.error("ERROR DE URL NO VALIDA", "URL incorrecta o no existe");
            } 
        }       
        telefono = textTelefono.getText();
        if(telefono.isEmpty()){
            
        }
        else{
            if(validar.comprobarTelefono(telefono) == true){
            
            }
            else {
                general = false;
                not.error("ERROR DE FORMATO TELEFONO","No es un telefono valido");
            }
        }        
        foto = textFoto.getText();
        if(foto.isEmpty()){
            
        }
        else{
            if(validar.validarFoto(foto) == true){
                
            }
            else{
                general = false;
                not.error("ERROR DE FORMATO FOTO","La extensión no es válida");
            }
        }
        idsubTipo = validar.validarNumEntero(textIdTipo.getText());
        if(idsubTipo != 0){
            
        }
        else {
            general = false;
            not.error("ERROR DE FORMATO NUMERO ENTERO","No puede estar vacio");
        }
               
        if(general){
            try {
                Actividad act = new Actividad(id, nombre, precio, horario,
                        descripcion, url, direccion, telefono, foto, idsubTipo);
                ok = activiDAO.insertarActividad(act);
            } catch (SQLException ex) {
                not.error("ERROR SQL", "" + ex.getMessage()
                        + " Error al insertar un registro en DB turismo");
            }
            if (ok == true) {
                not.confirm("INSERTAR CON EXITO EN LA TABLA ACTIVIDADES",
                        " La operación se ha realizado con éxito");
            } else {
                not.confirm("INSERTAR SIN EXITO EN TABLA ACTIVIDADES",
                        " No se ha insertado el registro");
            }
        }
        else {
            not.error("ERROR AL INSERTAR ACTIVIDAD","");
        }
    }

// -------------------------------- ACTUALIZAR --------------------------
    private void actualizar() {
        ValidarCampos validar = new ValidarCampos();
        boolean ok = false;
        boolean general = true;
        int id = 0, idsubTipo;
        double precio;
        String nombre, descripcion, horario, direccion, url, telefono, foto;
                
        actividad = tableview.getSelectionModel().getSelectedItem();
     
        nombre = textNombre.getText();
        if(!nombre.isEmpty()){

        }
        else {
            general = false;
            not.error("ERROR CAMPO NOMBRE VACIO","No puede estar vacio");
        }
        descripcion = textDescripcion.getText();       
        horario = textHorario.getText();       
        precio = validar.validarNumDecimal(textPrecio.getText());
        if(precio != 0.0){
            
        }
        else {
            general = false;
            not.error("ERROR DE FORMATO PRECIO","No puede estar vacio");
        }
        direccion = textDireccion.getText();       
        url = textUrl.getText();
        if(url.isEmpty()){
            
        }
        else{
            if(validar.isURL(url ) == true){
           
        }
            else {
                general = false;
                not.error("ERROR DE URL NO VALIDA", "URL incorrecta o no existe");
            } 
        }       
        telefono = textTelefono.getText();
        if(telefono.isEmpty()){
            
        }
        else{
            if(validar.comprobarTelefono(telefono) == true){
            
            }
            else {
                general = false;
                not.error("ERROR DE FORMATO TELEFONO","No es un telefono valido");
            }
        }        
        foto = textFoto.getText();
        if(foto.isEmpty()){
            
        }
        else{
            if(validar.validarFoto(foto) == true){
                
            }
            else{
                general = false;
                not.error("ERROR DE FORMATO FOTO","La extensión no es válida");
            }
        }
        idsubTipo = validar.validarNumEntero(textIdTipo.getText());
        if(idsubTipo != 0){
            
        }
        else {
            general = false;
            not.error("ERROR DE FORMATO NUMERO ENTERO","No puede estar vacio");
        }
        
        if(general){
            try {
                ok = activiDAO.modificarActividad(id, nombre, precio, horario, descripcion,
                        url, direccion, telefono, foto, idsubTipo);
            } catch (SQLException ex) {
                not.error("ERROR SQL", "" + ex.getMessage()
                        + " Error al modificar la actividad en DB turismo");
            }
            if (ok) {
                not.confirm("ACTUALIZAR CON EXITO EN LA TABLA ACTIVIDADES",
                        " Operación realizada con éxito");
            } else {
                not.confirm("ACTUALIZAR SIN EXITO EN TABLA ACTIVIDADES",
                        " No se ha modificado el registro");
            } 
        }
        else {
            not.error("ERROR AL MODIFICAR ACTIVIDAD","");
        }
    }

// -------------------------------- ELIMINAR ----------------------------
    private void eliminar() {
        boolean ok;
        int id;

        actividad = tableview.getSelectionModel().getSelectedItem();
        System.out.println("ACTIVIDAD: " + actividad);
        id = actividad.getId();
        
        ok = not.alertWarningDelete("SE ELIMINARA EL REGISTRO " + id + "",
                "¿Estas seguro !!!?\n confirma presionando Aceptar");
        
        if(ok){
            try {
                activiDAO.borrarActividad(id);
                if(ok){
                    not.confirm("SE HA ELIMINADO EL REGISTRO " + id + " EN LA TABLA ACTIVIDADES", 
                    " Operación realizada con éxito");
                }
            } catch (SQLException ex) {
                not.error("ERROR SQL", "" + ex.getMessage()
                        + "Error al eliminar el registro "+ id + " de tabla actividad");
            }
        }

//        Notifications notification = Notifications.create()
//                .title("ESTAS SEGURO DE ELIMINAR LA ACTIVIDAD " + id)
//                .text("Comfirma haciendo click sobre la ventana")
//                .graphic(null)
//                .hideAfter(Duration.seconds(25))
//                .position(Pos.TOP_LEFT)
//                .onAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent arg0) {
//
//                        try {
//                            activiDAO.borrarActividad(id);
//                        } catch (SQLException ex) {
//                            not.error("ERROR SQL", "" + ex.getMessage()
//                                    + "Error al eliminar un registro de DB turismo");
//                        }
//                    }
//                });
//
//        notification.showWarning();

//        if(ok){
//            not.info("ELIMINAR CON EXITO EN TABLA ACTIVIDADES", 
//                    " Operación realizada con éxito");
//        }
//        else {
//            not.info("ELIMINAR SIN EXITO EN TABLA ACTIVIDADES", 
//                    " No se pudo eliminar el registro");
//        }
    }

// ----------------------- LIMPIAR LOS CAMPOS DE TEXTO --------------------
    private void limpiar() {
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
        listar();
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
    
     @FXML
    private void ordenar(ActionEvent event) {
        listar();
    }

    @FXML
    private void importarActividad(ActionEvent event) {
        FileChooser fChooser = new FileChooser();
        fChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        fChooser.setTitle("Seleccione un fichero");
        File raiz = new File("");
        Path root = Paths.get(raiz.getAbsolutePath() + "\\src\\ficherosActividades");
        fChooser.setInitialDirectory(root.toFile());
        File carta = fChooser.showOpenDialog(new Stage());
        try (Stream<String> datos = Files.lines(carta.toPath(),
                StandardCharsets.ISO_8859_1)) {
            Iterator<String> it = datos.iterator();
            int numLinea = 1;
            String[] cabecera = null;
            while (it.hasNext()) {
                String linea = it.next();
                String[] trozos;
                if (numLinea == 1) {
                    cabecera = linea.split("#");
                } else {
                    trozos = linea.split("#");
                    int id = 0;
                    String nombre = "";
                    double precio = 0;
                    String horario = "";
                    String descripcion = "";
                    String url = "";
                    String direccion = "";
                    String telefono = "";
                    String foto = "";
                    int idSubtipo = 0;
                    for (int i = 0; i < cabecera.length; i++) {
                        System.out.println(cabecera[i]);

                        switch (cabecera[i]) {
                            case "id":
                                id = Integer.parseInt(trozos[i]);
                                break;
                            case "nombre":
                                nombre = trozos[i];
                                break;
                            case "precio":
                                precio = Double.parseDouble(trozos[i]);
                                break;
                            case "horario":
                                horario = trozos[i];
                                break;
                            case "descripcion":
                                descripcion = trozos[i];
                                break;
                            case "url":
                                url = trozos[i];
                                break;
                            case "direccion":
                                direccion = trozos[i];
                                break;
                            case "telefono":
                                telefono = trozos[i];
                                break;
                            case "foto":
                                foto = trozos[i];
                                break;
                            case "idsubTipo":
                                System.out.println(Integer.parseInt(trozos[i]));
                                idSubtipo = Integer.parseInt(trozos[i]);
                                break;
                        }
                    }
                    Actividad act;
                    try {
                        act = new Actividad(id, nombre, precio, horario, descripcion, url, direccion, telefono, foto, idSubtipo);
                        activiDAO.insertarActividad(act);
                    } catch (SQLException e) {
//                            ERROR SQL
                        e.printStackTrace();
                    }
                }
                numLinea++;
            }
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("ERROR " + e.getMessage());
        }
    }
}
