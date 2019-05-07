/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Actividad;
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

    private GestionBD gestion;
    private Connection conn;

    public actividadesDAO(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();
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
        List<Tipo> listaTiposActividades = new ArrayList<>();
        String sql = "SELECT id, nombre FROM tipos;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            listaTiposActividades.add(new Tipo(rs.getInt("id"), rs.getString("nombre")));
        }

        return listaTiposActividades;
    }

    public List<Actividad> consultarActividadesPorTipo(Tipo tipo) throws SQLException {
        List<Actividad> listaActividades = new ArrayList<>();
        String sql = "SELECT id, nombre, precio, horario, descripcion, url, direccion, telefono, foto, idsubtipo FROM actividades WHERE idsubtipo IN (SELECT id FROM subtipos WHERE idTipo = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, tipo.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            listaActividades.add(new Actividad(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getString("horario"), rs.getString("descripcion"), rs.getString("url"), rs.getString("direccion"), rs.getString("telefono"), rs.getString("foto"), rs.getInt("idsubtipo"))); //FALTAN LOS PARAMETROS
        }

        return listaActividades;
    }
}
