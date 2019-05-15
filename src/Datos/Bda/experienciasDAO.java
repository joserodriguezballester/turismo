/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Experiencia;
import Modelo.Notificacion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class experienciasDAO {

    private Notificacion not;
    private GestionBD gestion;
    private Connection conn;

    public experienciasDAO(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();

    }

    //CREATE
    public boolean insertarExperiencia(Experiencia experiencia) throws SQLException {
        boolean insertado = false;

        if (conn != null) {
            String consulta = "INSERT INTO EXPERIENCIAS (ID, IDUSUARIO, NOMBRE, DESCRIPCION, FECHATOPEVALIDEZ, FOTO) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setInt(1, experiencia.getId());
            ps.setInt(2, experiencia.getIdUsuario());
            ps.setString(3, experiencia.getNombre());
            ps.setString(4, experiencia.getDescripcion());
            ps.setDate(5, Date.valueOf(experiencia.getFechaTopeValidez()));
//            ps.setString(5, fechaTopeValidez.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ps.setString(6, experiencia.getFoto());
            ps.executeUpdate();
            insertado = true;

            System.out.println(" 1 Mas adentro de insertar experiencia");
        }

        return insertado;
    }

    //READ
    public List<Experiencia> consultarTodasExperiencias() throws SQLException {
        System.out.println("Dentro de consultarTodaExperiencias experienciasDAO");
        List<Experiencia> listaExperiencias = new ArrayList<>();
        if (conn != null) {
            String consulta = "SELECT id,idUsuario, nombre, descripcion, fechaTopeValidez, foto FROM experiencias;";
            PreparedStatement ps = conn.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            experienciasActividadesDAO experienciasActividadesdao = new experienciasActividadesDAO(gestion);
            while (rs.next()) {
                listaExperiencias.add(new Experiencia(
                        rs.getInt("id"),
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDate("fechaTopeValidez").toLocalDate(),
                        rs.getString("foto"),
                        experienciasActividadesdao.consultarActividadesDeExperiencia(rs.getInt("id"))));
            }
        }
        return listaExperiencias;
    }

    //UPDATE
    public boolean modificarExperiencia(int idUsuario, String nombre, String descripcion, LocalDate fechaTopeValidez, String rutaFoto, int id) throws SQLException {
        boolean insertado = false;
        if (conn != null) {
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            String fechaTope = fechaTopeValidez.format(dtf);

            String consulta = "UPDATE EXPERIENCIAS SET IDUSUARIO = ?, NOMBRE = ?, DESCRIPCION = ?, FECHATOPEVALIDEZ = ?, FOTO = ? WHERE ID = ?;";
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setInt(1, idUsuario);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            //ps.setString(4, (fechaTopeValidez.getYear() + "-" + fechaTopeValidez.getMonth() + "-" + fechaTopeValidez.getDayOfMonth()));
            ps.setString(4, fechaTopeValidez.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ps.setString(5, rutaFoto);
            ps.setInt(6, id);
            ps.executeUpdate();
            insertado = true;
        }
        return insertado;
    }

    //DELETE
    public boolean borrarExperiencia(int idExperiencia) throws SQLException {
        boolean borrado = false;

        String sql = "DELETE FROM EXPERIENCIAS WHERE id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idExperiencia);
            ps.executeUpdate();
            borrado = true;
        } catch (SQLException ex) {
            not.error("SQL ERROR", "" + ex.getMessage() + " en borrarExperiencia() --- experienciasDAO");
        }

        return borrado;
    }
}
