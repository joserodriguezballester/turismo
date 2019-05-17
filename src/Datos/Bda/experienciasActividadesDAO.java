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
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
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

    public boolean insertarActividadExperiencia(ActividadExperiencia actExp) throws SQLException {
        boolean insertado;
        String consulta = "INSERT INTO experiencia_actividad (orden, idexperiencia, idactividad, numPlazas, fechaInicio, fechaFinal, precio) VALUES(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setInt(1, actExp.getOrden());
        ps.setInt(2, actExp.getIdExperiencia());
        ps.setInt(3, actExp.getActividad().getId());
        ps.setInt(4, actExp.getNumPlazas());
        ps.setTimestamp(5, Timestamp.valueOf(actExp.getFechaInicio()));
        ps.setTimestamp(6, Timestamp.valueOf(actExp.getFechaFinal()));
        ps.setDouble(7, actExp.getPrecio());
        ps.executeUpdate();
        insertado = true;
        return insertado;
    }

    public List<ActividadExperiencia> consultarActividadesDeExperiencia(int idExperiencia) throws SQLException {
        List<ActividadExperiencia> listaActividadesExperiencia = new ArrayList<>();
        String sql = "SELECT orden, idExperiencia, idActividad, fechaInicio, fechaFinal, precio, numPlazas FROM experiencia_actividad WHERE idExperiencia = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idExperiencia);
        ResultSet rs = ps.executeQuery();
        actividadesDAO actividadesDAO = new actividadesDAO(gestion);
        while (rs.next()) {
            listaActividadesExperiencia.add(
                    new ActividadExperiencia(rs.getInt("orden"),
                            rs.getInt("idExperiencia"),
                            actividadesDAO.consultarActividad(rs.getInt("idActividad")),
                            rs.getTimestamp("fechaInicio").toLocalDateTime(),
                            rs.getTimestamp("fechaFinal").toLocalDateTime(),                            
                            rs.getDouble("precio"),
                            rs.getInt("numPlazas")));
        }
        return listaActividadesExperiencia;
    }
    
    public boolean modificarActividadExperiencia(int orden,int idExperiencia,Actividad idActividad, LocalDateTime fechaInicio, LocalDateTime fechaFinal,Double precio,int numPlazas) throws SQLException {
        boolean modificado = false;
        
        if(conn != null){
            String consulta = "UPDATE experiencia_actividad SET orden = ?, idActividad = ?, numPlazas = ?, fechaInicio = ?, fechaFinal = ?, precio = ? WHERE idExperiencia = ?";
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setInt(1, orden);
            ps.setInt(2, idActividad.getId());
            ps.setInt(3, numPlazas);
            ps.setTimestamp(4, Timestamp.valueOf(fechaInicio));
            ps.setTimestamp(5, Timestamp.valueOf(fechaFinal));
            ps.setDouble(6, precio);
            ps.setInt(7, idExperiencia);
            ps.executeUpdate();
            modificado = true;
                                
        }
              
        return modificado;
    }
}
