/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

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
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(new Subtipo(rs.getInt("id"), tipo, rs.getString("nombre")));
        }
        return lista;
    }
}
