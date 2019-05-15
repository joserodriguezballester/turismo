/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Usuario;
import java.sql.Connection;
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
 *
 * @author Usuario
 */
public class usuariosDAO {
    
    private Notificacion not;
    private final GestionBD gestion;

    public usuariosDAO(GestionBD gestion) {
        this.gestion = gestion;
    }

    //CREATE PASANDO USUARIO
    public boolean insertarUsuario(Usuario usuario) {
        boolean insertado = false;
        String consulta = "INSERT INTO USUARIOS (nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email)"
                + " VALUES(?, ?, ?, ?, ?,?,?,?,?);";
        try {

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

            ps.executeUpdate();
            insertado = true;

        } catch (SQLException ex) {
            not.error("SQL ERROR","" + ex.getMessage() + " en insertarUsuario() --- usuariosDAO");
        }
        return insertado;
    }

    //READ
    
    
    
    
    //UPDATE
//    public boolean modificarUsuario(String DNI, String nombre, String apellidos, String rol, String nick, String direccion, String telefono, String email, int id) throws SQLException {
//        boolean insertado = false;
//        String consulta = "UPDATE USUARIOS SET DNI = ?, NOMBRE = ?, APELLIDOS = ?, ROL = ?, nick = ?, DIRECCION = ?, TELEFONO = ?, EMAIL = ? WHERE ID = ?;";
//
//        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
//        ps.setString(1, DNI);
//        ps.setString(2, nombre);
//        ps.setString(3, apellidos);
//        ps.setString(4, rol);
//        ps.setString(5, nick);
//        ps.setString(6, direccion);
//        ps.setString(7, telefono);
//        ps.setString(8, email);
//        ps.setInt(9, id);
//        ps.executeUpdate();
//        insertado = true;
//
//        return insertado;
//    }

    //DELETE
    public boolean borrarUsuario(int idUsuario) {
        boolean borrado = false;
        String sql = "DELETE FROM USUARIOS WHERE id = ?;";
        try {
            PreparedStatement ps = gestion.getConn().prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            borrado = true;
        } catch (SQLException ex) {
            not.error("SQL ERROR","" + ex.getMessage() + " en insertarUsuario() --- usuariosDAO");
        }
        return borrado;
    }

    public String obtenerContra(String nick) {
        String contrasena = null;
        String sql = "SELECT Contraseña FROM USUARIOS WHERE nick=?;";
        try {
            PreparedStatement ps = gestion.getConn().prepareStatement(sql);
            ps.setString(1, nick);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contrasena = rs.getString("Contraseña");
            }
        } catch (SQLException ex) {
            not.error("SQL ERROR","" + ex.getMessage() + " en insertarUsuario() --- usuariosDAO");
        }
        return contrasena;
    }

    public Usuario cargarUsuario(String nick) throws SQLException {
        Usuario usuario = new Usuario();
        java.sql.Date fechabda;
        String sql = "SELECT id,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol FROM USUARIOS WHERE nick=?;";
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
        }
        return usuario;
    }

    public List<Usuario> listarClientes() throws SQLException {    
        List<Usuario> listaUsuarios = null;
        if (gestion.getConn() != null) {       
            String consulta = "SELECT id,nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol FROM Clientes;";
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();       
            listaUsuarios = darValorRs(rs);
        }
        return listaUsuarios;
    }

    public List<Usuario> listarAdministradores() throws SQLException {
        List<Usuario> listaUsuarios;
        String consulta = "SELECT id,nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol FROM administradores;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();
        listaUsuarios = darValorRs(rs);
        return listaUsuarios;
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> listaUsuarios = null;
        if (gestion.getConn() != null) {
            String consulta = "SELECT id,nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol FROM usuarios;";
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            listaUsuarios = darValorRs(rs);
        }
        return listaUsuarios;
    }

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
      public boolean modificarUsuario(String DNI, String nombre, String apellidos, String rol, String nick, String direccion, String telefono, String email, int id, LocalDate fecNac) throws SQLException {
         
        boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET DNI = ?, NOMBRE = ?, APELLIDOS = ?, ROL = ?, nick = ?, DIRECCION = ?, TELEFONO = ?, EMAIL = ?,fecNac =? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, DNI);
        ps.setString(2, nombre);
        ps.setString(3, apellidos);
        ps.setString(4, rol);
        ps.setString(5, nick);
        ps.setString(6, direccion);
        ps.setString(7, telefono);
        ps.setString(8, email);
//        ps.setDate(9, fecNac);
//        java.sql.Date fecNacaInsertar=Date.valueOf(fecNac);
        ps.setString(9, fecNac.toString());
        ps.setInt(10, id);
//        System.out.println("instruccion"+ps);
        ps.executeUpdate();
        modificado = true;

        return modificado;
    }

    public void modificarUsuario(String DNI, String nombre, String apellidos, String nick, String direccion, String telefono, String email, int id, LocalDate fecNac) throws SQLException {
      boolean modificado = false;
        String consulta = "UPDATE USUARIOS SET DNI = ?, NOMBRE = ?, APELLIDOS = ?, ROL = ?, nick = ?, DIRECCION = ?, TELEFONO = ?, EMAIL = ?,fecNac =? WHERE ID = ?;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ps.setString(1, DNI);
        ps.setString(2, nombre);
        ps.setString(3, apellidos);
//        ps.setString(4, rol);
        ps.setString(4, nick);
        ps.setString(5, direccion);
        ps.setString(6, telefono);
        ps.setString(7, email);
//        ps.setDate(9, fecNac);
//        java.sql.Date fecNacaInsertar=Date.valueOf(fecNac);
        ps.setString(8, fecNac.toString());
        ps.setInt(10, id);
//        System.out.println("instruccion"+ps);
        ps.executeUpdate();
        modificado = true;
    
    
    
    
    
    }
}
