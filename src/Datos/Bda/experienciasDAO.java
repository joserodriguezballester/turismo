/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Experiencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class experienciasDAO {

    private GestionBD gestion;
    private Connection conn;

    public experienciasDAO(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();

    }

    //CREATE
    public boolean insertarExperiencia(int id, String nombre, String descripcion, LocalDate fechaTopeValidez, String rutaFoto) throws SQLException {
        boolean insertado = false;
        String consulta = "INSERT INTO EXPERIENCIAS (ID, NOMBRE, DESCRIPCION, FECHATOPEVALIDEZ, FOTO) VALUES(?, ?, ?, '?', ?);";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setInt(1, id);
        ps.setString(2, nombre);
        ps.setString(3, descripcion);
        ps.setString(4, (fechaTopeValidez.getYear() + "-" + fechaTopeValidez.getMonth() + "-" + fechaTopeValidez.getDayOfMonth()));
        ps.setString(5, rutaFoto);
        ps.executeUpdate();
        insertado = true;
        return insertado;
    }

    //READ
    public List<Experiencia> consultarTodasExperiencias() throws SQLException {
        List<Experiencia> listaExperiencias = new ArrayList<>();
        String consulta = "SELECT id, nombre, descripcion, fechaTopeValidez, foto FROM experiencias;";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();
        experienciasActividadesDAO experienciasActividadesDAO = new experienciasActividadesDAO(gestion);
        while (rs.next()) {
            listaExperiencias.add(new Experiencia(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getDate("fechaTopeValidez").toLocalDate(), rs.getString("foto"), experienciasActividadesDAO.consultarActividadesDeExperiencia(rs.getInt("id"))));
        }
        return listaExperiencias;
    }

    //UPDATE
    public boolean modificarExperiencia(String nombre, String descripcion, LocalDate fechaTopeValidez, String rutaFoto, int idUsuario, int id) throws SQLException {
        boolean insertado = false;
        String consulta = "UPDATE EXPERIENCIAS SET IDUSUARIO = ?, NOMBRE = ?, DESCRIPCION = ?, FECHATOPEVALIDEZ = ?, FOTO = ? WHERE ID = ?;";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setInt(1, idUsuario);
        ps.setString(2, nombre);
        ps.setString(3, descripcion);
        ps.setString(4, (fechaTopeValidez.getYear() + "-" + fechaTopeValidez.getMonth() + "-" + fechaTopeValidez.getDayOfMonth()));
        ps.setString(5, rutaFoto);
        ps.setInt(6, id);
        ps.executeUpdate();
        insertado = true;
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
