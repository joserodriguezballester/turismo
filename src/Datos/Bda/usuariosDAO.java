package Datos.Bda;

import Modelo.Notificacion;
import Modelo.Usuario;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * NUEVO CAMPO FOTO ver update,listar...
 *
 *
 */
public class usuariosDAO {

    private Notificacion not;
    private final GestionBD gestion;

    public usuariosDAO(GestionBD gestion) {
        this.gestion = gestion;
    }

//CREATE (pasando usuario)*********************************************************
    public boolean insertarUsuario(Usuario usuario) throws SQLException {
        boolean insertado = false;
        String consulta = "INSERT INTO USUARIOS (nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,foto)"
                + " VALUES(?, ?, ?, ?, ?,?,?,?,?,?);";

        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, usuario.getNick());
        ps.setString(2, usuario.getPassword());
        ps.setString(3, usuario.getFecNac().toString());
        ps.setString(4, usuario.getNombre());
        ps.setString(5, usuario.getApellidos());
        ps.setString(6, usuario.getDni());
        ps.setString(7, usuario.getTelefono());
        ps.setString(8, usuario.getDireccion());
        ps.setString(9, usuario.getEmail());
        ps.setString(10, usuario.getFoto());
        ps.executeUpdate();
        insertado = true;
        return insertado;
    }
// fin Create -----------------------------------
    
// DELETE (pasando idUsuario)  *********************************************
    public boolean borrarUsuario(int idUsuario) throws SQLException {
        boolean borrado = false;
        String sql = "DELETE FROM USUARIOS WHERE id = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(sql);
        ps.setInt(1, idUsuario);
        ps.executeUpdate();
        borrado = true;
        return borrado;
    }

    
//UPDATE   ***********************************************************************
    ///modificando desde administrador  // habra que revisarlo
    public boolean modificarUsuario(String DNI, String nombre, String apellidos, String rol, String nick, String direccion, String telefono, String email, int id, LocalDate fecNac) throws SQLException {
        boolean modificado = false;
//        modificarUsuariodesdeUsuario(DNI, nombre, apellidos, nick, direccion, telefono, email, id, fecNac);
        /////////////corregir//////////   

        String consulta = "UPDATE USUARIOS SET ROL = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, rol);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }

   
//// UPDATE parametro a parametro   ********************************************
    
//NICK
    public boolean modificarNick(String nick, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET nick = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, nick);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }

//NOMBRE
    public boolean modificarNombre(String nombre, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET nombre = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, nombre);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }
    
//APELLIDOS
    public boolean modificarApellidosTF(String apellidos, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET apellidos = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, apellidos);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }

//DNI
    public boolean modificarDni(String DNI, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET dni = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, DNI);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }
    
//FECHA DE NACIMIENTO
    public boolean modificarFecNac(LocalDate fecNac, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET fecNac = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, fecNac.toString());
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }

//TELEFONO
    public boolean modificarTelefono(String telefono, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET telefono = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, telefono);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }

//DIRECCION
    public boolean modificarDireccion(String direccion, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET direccion = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, direccion);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }

//EMAIL 
    public boolean modificarEmail(String email, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET email = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, email);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }

//FOTO
    public boolean modificarFoto(String foto, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET foto = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, foto);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }  
    
//CONTRASEÑA (a partir del nick)    
    public boolean introducirContra(String contra, String key) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET contraseña=? WHERE nick = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, contra);
        ps.setString(2, key);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }

//ROL  
     public boolean modificarRol(String rol, int id) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET ROL = ? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, rol);
        ps.setInt(2, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }
     
// FIN UPDATE-------------------------------------------------------------------     
     
//READ
    public List<Usuario> lista(String tabla) throws SQLException {
        List<Usuario> listaUsuarios = null;
        String consulta = "call listarUsuarios (?);";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, tabla);
        ResultSet rs = ps.executeQuery();
        listaUsuarios = darValorRs(rs);
        return listaUsuarios;
    }

    public List<Usuario> listarClientes() throws SQLException {
        String tabla = "CLIENTES";
        List<Usuario> listaUsuarios = lista(tabla);
//        if (gestion.getConn() != null) {
//            String consulta = "SELECT id,nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol FROM Clientes;";
//            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
//            ResultSet rs = ps.executeQuery();
//            listaUsuarios = darValorRs(rs);
//        }
        return listaUsuarios;
    }

    public List<Usuario> listarAdministradores() throws SQLException {
        String tabla = "ADMINISTRADOR";
        List<Usuario> listaUsuarios = lista(tabla);

//        String consulta = "SELECT id,nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol FROM administradores;";
//        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
//        ResultSet rs = ps.executeQuery();
//        listaUsuarios = darValorRs(rs);
        return listaUsuarios;
    }

    public List<Usuario> listarTodos() throws SQLException {
        String tabla = "TODOS";
        List<Usuario> listaUsuarios = lista(tabla);
////        List<Usuario> listaUsuarios = null;
//        if (gestion.getConn() != null) {
//            String consulta = "SELECT id,nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol FROM usuarios;";
//            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
//            ResultSet rs = ps.executeQuery();
//            listaUsuarios = darValorRs(rs);
//        }
        return listaUsuarios;
    }

  

// Obtener un Todos los campos de un Usuario y asignarselos
    public Usuario cargarUsuario(String nick) throws SQLException {
        Usuario usuario = new Usuario();
        java.sql.Date fechabda;
        String sql = "SELECT id,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol,foto FROM USUARIOS WHERE nick=?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(sql);
        ps.setString(1, nick);
        ResultSet rs = ps.executeQuery();
        usuario.setNick(nick);
        while (rs.next()) {
            usuario.setId(rs.getInt("id"));
            usuario.setPassword(rs.getString("contraseña"));
            fechabda = rs.getDate("fecNac");
            usuario.setFecNac(fechabda.toLocalDate());
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellidos(rs.getString("apellidos"));
            usuario.setDni(rs.getString("dni"));
            usuario.setTelefono(rs.getString("telefono"));
            usuario.setDireccion(rs.getString("direccion"));
            usuario.setEmail(rs.getString("email"));
            usuario.setPerfil(rs.getString("rol").toUpperCase());
            usuario.setFoto(rs.getString("foto"));
        }
        return usuario;
    }
    
// Obtener Contraseña
    public String obtenerContra(String nick) throws SQLException {
        String contrasena = null;
        String sql = "SELECT Contraseña FROM USUARIOS WHERE nick=?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(sql);
        ps.setString(1, nick);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            contrasena = rs.getString("Contraseña");
        }
        return contrasena;
    }

// Carga las List recogidas en el apartado READ
    private List<Usuario> darValorRs(ResultSet rs) throws SQLException {
        List<Usuario> listaUsuarios = null;
        listaUsuarios = new ArrayList<>();
        while (rs.next()) {
            Usuario usuario = new Usuario();
            listaUsuarios.add(usuario);
            usuario.setId(rs.getInt("id"));
            usuario.setPassword(rs.getString("contraseña"));
            usuario.setNick(rs.getString("nick"));
            Date fechabda = rs.getDate("fecNac");
            usuario.setFecNac(fechabda.toLocalDate());
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellidos(rs.getString("apellidos"));
            usuario.setDni(rs.getString("dni"));
            usuario.setTelefono(rs.getString("telefono"));
            usuario.setDireccion(rs.getString("direccion"));
            usuario.setEmail(rs.getString("email"));
            usuario.setPerfil(rs.getString("rol").toUpperCase());
        }
        return listaUsuarios;
    }

//obtener el correo
    public String DarCorreo(String nick) throws SQLException {
        String correo = null;
        String consulta = "select dimecorreo (?);";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, nick);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            correo = rs.getString(1);
        }
        return correo;
    }

    public boolean clienteExiste(String nick) throws SQLException {
        List<Usuario> listausuarios = new ArrayList<>();
        boolean existe = false;
        listausuarios = listarClientes();
        for (Usuario user : listausuarios) {
            if (user.getNick().equalsIgnoreCase(nick)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

  

//       public boolean modificarUsuariodesdeUsuario(String DNI, String nombre, String apellidos, String nick, String direccion, String telefono, String email, String foto, int id, LocalDate fecNac) throws SQLException {
//        boolean modificado = false; /////contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol,foto 
//        String consulta = "UPDATE USUARIOS SET DNI = ?, NOMBRE = ?, APELLIDOS = ?,  nick = ?, DIRECCION = ?, TELEFONO = ?, EMAIL = ?,fecNac =?, foto=? WHERE ID = ?;";
//        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
//        ps.setString(1, DNI);
//        ps.setString(2, nombre);
//        ps.setString(3, apellidos);
//        ps.setString(4, nick);
//        ps.setString(5, direccion);
//        ps.setString(6, telefono);
//        ps.setString(7, email);
//        ps.setString(8, fecNac.toString());
//        ps.setString(9, foto);
//        ps.setInt(10, id);
//        ps.executeUpdate();
//        modificado = true;
//        return modificado;
//    }
}
