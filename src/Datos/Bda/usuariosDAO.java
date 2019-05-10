/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class usuariosDAO {

    private Connection conn;
    private GestionBD gestion;

    public usuariosDAO(GestionBD gestion) {
        this.gestion = gestion;
    }

    //CREATE PASANDO USUARIO
    public boolean insertarUsuario(Usuario usuario) {

        boolean insertado = false;
        String consulta = "INSERT INTO USUARIOS (nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email)"
                + " VALUES(?, ?, ?, ?, ?,?,?,?,?);";
        try {
//            PreparedStatement ps = conn.prepareStatement(consulta);
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
            ps.setString(1, usuario.getNick());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getFecNac().toString());
            ps.setString(4, usuario.getNombre());
            ps.setString(5, usuario.getApellidos());
            ps.setString(6, usuario.getDNI());
            ps.setString(7, usuario.getTelefono());
            ps.setString(8, usuario.getDireccion());
            ps.setString(9, usuario.getEmail());
            ps.executeUpdate();
            insertado = true;

        } catch (SQLException e) {
            //MENSAJE DE ERROR
        }
        return insertado;
    }

    //READ
    //UPDATE
    public boolean modificarUsuario(String DNI, String nombre, String apellidos, String rol, String contraseña, String direccion, String telefono, String email, int id) {
        boolean insertado = false;
        String consulta = "UPDATE USUARIOS SET DNI = ?, NOMBRE = ?, APELLIDOS = ?, ROL = ?, CONTRASEÑA = ?, DIRECCION = ?, TELEFONO = ?, EMAIL = ? WHERE ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, DNI);
            ps.setString(2, nombre);
            ps.setString(3, apellidos);
            ps.setString(4, rol);
            ps.setString(5, contraseña);
            ps.setString(6, direccion);
            ps.setString(7, telefono);
            ps.setString(8, email);
            ps.setInt(9, id);
            ps.executeUpdate();
            insertado = true;
        } catch (SQLException e) {
            //MENSAJE DE ERROR
        }
        return insertado;
    }

    //DELETE
    public boolean borrarUsuario(int idUsuario) {
        boolean borrado = false;

        String sql = "DELETE FROM USUARIOS WHERE id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            borrado = true;
        } catch (SQLException e) {
            //MENSAJE DE ERROR
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
        } catch (SQLException e) {
            //MENSAJE DE ERROR
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
        while (rs.next()) {
            usuario.setId(rs.getInt("id"));
            usuario.setPassword(rs.getString("contraseña"));
//            usuario.setFecNac(rs.getLocalDate("fecNac"));
        fechabda = rs.getDate("fecNac");
       
        usuario.setFecNac(fechabda.toLocalDate());
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellidos(rs.getString("apellidos"));
            usuario.setDNI(rs.getString("dni"));
            usuario.setTelefono(rs.getString("telefono"));
            usuario.setDireccion(rs.getString("direccion"));
            usuario.setEmail(rs.getString("email"));
            usuario.setPerfil(rs.getString("rol"));
        }
        return usuario;
    }
}
