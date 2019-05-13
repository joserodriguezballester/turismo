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
public class tiposDAO {

    private GestionBD gestion;
    private Connection conn;

    public tiposDAO(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();
    }

    public List<Tipo> consultarTipos() throws SQLException {
        List<Tipo> lista = new ArrayList<>();
        String consulta = "SELECT id, nombre FROM tipos;";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(new Tipo(rs.getInt("id"), rs.getString("nombre")));
        }
        return lista;
    }
}
