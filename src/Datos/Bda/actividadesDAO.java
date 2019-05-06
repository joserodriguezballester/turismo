/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class actividadesDAO {

    private Connection conn;

    public actividadesDAO(Connection conn) {
        this.conn = conn;
    }

    //CREATE
    public boolean insertarActividad(String nombre, double precio, String horario, String descripcion, String url, String direccion, String telefono, String rutaFoto, int subtipo) throws SQLException {
        boolean insertado = false;
        String consulta = "INSERT INTO ACTIVIDADES (NOMBRE, PRECIO, HORARIO, DESCRIPCION, URL, DIRECCION, TELEFONO, FOTO, IDSUBTIPO) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setString(1, nombre);
        ps.setDouble(2, precio);
        ps.setString(3, horario);
        ps.setString(4, descripcion);
        ps.setString(5, url);
        ps.setString(6, direccion);
        ps.setString(7, telefono);
        ps.setString(8, rutaFoto);
        ps.setInt(9, subtipo);
        ps.executeUpdate();
        insertado = true;
        return insertado;
    }
    //READ

    //UPDATE
    public boolean modificarActividad(int id, String nombre, double precio, String horario, String descripcion, String url, String direccion, String telefono, String rutaFoto, int idSubtipo) throws SQLException {
        boolean modificado = false;
        String consulta = "UPDATE ACTIVIDADES SET NOMBRE = ?, PRECIO = ?, HORARIO = ?, DESCRIPCION = ?, URL = ?, DIRECCION = ?, TELEFONO = ?, FOTO = ?, IDSUBTIPO = ? WHERE ID = ?;";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setString(1, nombre);
        ps.setDouble(2, precio);
        ps.setString(3, horario);
        ps.setString(4, descripcion);
        ps.setString(5, url);
        ps.setString(6, direccion);
        ps.setString(7, telefono);
        ps.setString(8, rutaFoto);
        ps.setInt(9, idSubtipo);
        ps.setInt(10, id);
        ps.executeUpdate();
        modificado = true;
        return modificado;
    }

    //DELETE
    public boolean borrarActividad(int idActividad) throws SQLException {
        boolean borrado = false;

        String sql = "DELETE FROM ACTIVIDADES WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idActividad);
        ps.executeUpdate();
        borrado = true;

        return borrado;
    }

    public List<Tipo> consultarTipoActividades() throws SQLException {
        List<Tipo> listaActividades = new ArrayList<>();
        String sql = "SELECT id, nombre from TIPOS;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            listaActividades.add(new Tipo(rs.getInt("id"), rs.getString("nombre")));
        }

        return listaActividades;
    }
}
