/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class usuariosDAO {

    private Connection conn;

    public usuariosDAO(Connection conn) {
        this.conn = conn;
    }

    //CREATE
    public boolean insertarUsuario(String DNI, String nombre, String apellidos, String rol, String contraseña, String direccion, String telefono, String email) {
        boolean insertado = false;
        String consulta = "INSERT INTO USUARIOS (DNI, NOMBRE, APELLIDOS, ROL, CONTRASEÑA, DIRECCION, TELEFONO, EMAIL) VALUES(?, ?, ?, ?, ?);";
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
}
