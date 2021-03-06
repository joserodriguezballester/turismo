/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Actividad;
import Modelo.Subtipo;
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
public class subtiposDAO {
    
    private GestionBD gestion;
    private Connection conn;
    
    public subtiposDAO(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();
    }
    
    public List<Subtipo> consultarSubtiposporTipo(Tipo tipo) throws SQLException {
        List<Subtipo> lista = new ArrayList<>();
        String consulta = "SELECT id, idtipo, nombre FROM subtipos WHERE idtipo = ?;";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setInt(1, tipo.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(new Subtipo(rs.getInt("id"), tipo, rs.getString("nombre")));
        }
        return lista;
    }
    
    public Subtipo consultarSubtipoActividad(Actividad actividad) throws SQLException {
        Subtipo subtipo = null;
        String consulta = "SELECT id, idTipo, nombre FROM subtipos WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setInt(1, actividad.getIdsubTipo());
        ResultSet rs = ps.executeQuery();
        tiposDAO tiposDAO = new tiposDAO(gestion);
        if (rs.next()) {
            subtipo = new Subtipo(rs.getInt("id"), tiposDAO.consultarTipoDeUnSubtipo(actividad.getIdsubTipo()), rs.getString("nombre"));
        }
        return subtipo;
    }
    
    public void insertarSubtipo(Subtipo subtipo, Tipo tipo) throws SQLException {
        String consulta = "INSERT INTO subtipos (id, idTipo, nombre) VALUES (null, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setInt(1, tipo.getId());
        ps.setString(2, subtipo.getNombre());
        ps.executeUpdate();
    }
    
    public void actualizarSubtipo(Subtipo subtipo, Tipo tipo) throws SQLException {
        String consulta = "UPDATE subtipos SET nombre = ?, idTipo = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setString(1, subtipo.getNombre());
        ps.setInt(2, tipo.getId());
        ps.setInt(3, subtipo.getId());
        ps.executeUpdate();
    }
    
    public void borrarSubtipo(Subtipo subtipo) throws SQLException {
        String consulta = "DELETE FROM subtipos WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setInt(1, subtipo.getId());
        ps.executeUpdate();
    }
}
