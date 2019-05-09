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
public class experienciasDAO {

    private Connection conn;

    public experienciasDAO(Connection conn) {
        this.conn = conn;
    }

    //CREATE
    public boolean insertarExperiencia(int idUsuario, String nombre, String descripcion, LocalDate fechaTopeValidez, String rutaFoto) {
        boolean insertado = false;
        String consulta = "INSERT INTO EXPERIENCIAS (IDUSUARIO, NOMBRE, DESCRIPCION, FECHATOPEVALIDEZ, FOTO) VALUES(?, ?, ?, '?', ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setInt(1, idUsuario);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setString(4, (fechaTopeValidez.getYear() + "-" + fechaTopeValidez.getMonth() + "-" + fechaTopeValidez.getDayOfMonth()));
            ps.setString(5, rutaFoto);
            ps.executeUpdate();
            insertado = true;
        } catch (SQLException e) {
            //MENSAJE DE ERROR
        }
        return insertado;
    }
    
    //READ

    //UPDATE
    public boolean modificarExperiencia(String nombre, String descripcion, LocalDate fechaTopeValidez, String rutaFoto, int idUsuario, int id) {
        boolean insertado = false;
        String consulta = "UPDATE EXPERIENCIAS SET IDUSUARIO = ?, NOMBRE = ?, DESCRIPCION = ?, FECHATOPEVALIDEZ = ?, FOTO = ? WHERE ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setInt(1, idUsuario);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setString(4, (fechaTopeValidez.getYear() + "-" + fechaTopeValidez.getMonth() + "-" + fechaTopeValidez.getDayOfMonth()));
            ps.setString(5, rutaFoto);
            ps.setInt(6, id);
            ps.executeUpdate();
            insertado = true;
        } catch (SQLException e) {
            //MENSAJE DE ERROR
        }
        return insertado;
    }
    //DELETE
    public boolean borrarExperiencia(int idExperiencia) {
        boolean borrado = false;

        String sql = "DELETE FROM EXPERIENCIAS WHERE id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idExperiencia);
            ps.executeUpdate();
            borrado = true;
        } catch (SQLException e) {
            //MENSAJE DE ERROR
        }

        return borrado;
    }
}
