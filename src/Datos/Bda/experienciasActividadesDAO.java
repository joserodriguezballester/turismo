/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Actividad;
import Modelo.ActividadExperiencia;
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
public class experienciasActividadesDAO {

    private GestionBD gestion;
    private Connection conn;

    public experienciasActividadesDAO(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();
    }

    public List<ActividadExperiencia> consultarActividadesDeExperiencia(int idExperiencia) throws SQLException {
        List<ActividadExperiencia> listaActividadesExperiencia = new ArrayList<>();
        String sql = "SELECT orden, idExperiencia, idActividad, fechaInicio, fechaFinal, duracion FROM experiencias_actividades WHERE idExperiencia = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idExperiencia);
        ResultSet rs = ps.executeQuery();
        actividadesDAO actividadesDAO = new actividadesDAO(gestion);
        while (rs.next()) {
            listaActividadesExperiencia.add(new ActividadExperiencia(rs.getInt("orden"), rs.getInt("idExperiencia"), actividadesDAO.consultarActividad(rs.getInt("idActividad")), rs.getTimestamp("fechaInicio").toLocalDateTime(), rs.getTimestamp("fechaFinal").toLocalDateTime()));
        }
        return listaActividadesExperiencia;
    }
}
