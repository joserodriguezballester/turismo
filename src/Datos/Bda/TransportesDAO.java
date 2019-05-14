package Datos.Bda;

import Modelo.Transporte;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
////id int(11) AI PK 
////nombre varchar(45) 
////duracion int(5) 
////precio decimal(6,2) 
////Descripcion longtext 
////foto varchar(45) 
////fotoDescripcion varchar(45) 
////Clase varchar(45)

public class TransportesDAO {

    private final GestionBD gestion;

    public TransportesDAO(GestionBD gestion) {
        this.gestion = gestion;
    }

    public List<Transporte> listarTarjetas() throws SQLException {
        List<Transporte> listaTarjetas = null;
        String consulta = "SELECT id,nombre,duracion,precio,Descripcion,foto,fotoDescripcion,Clase FROM transporte;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();
        listaTarjetas = new ArrayList<>();
        while (rs.next()) {
            Transporte transporte = new Transporte();
            
            transporte.setId(rs.getInt("id"));
            transporte.setNombre(rs.getString("nombre"));
            transporte.setDuracion(rs.getString("duracion"));
            transporte.setTipo(rs.getString("clase"));
            transporte.setPrecio(rs.getDouble("precio"));
            transporte.setDescripcion(rs.getString("descripcion"));
            transporte.setFoto(rs.getString(rs.getString("foto")));
            transporte.setFotoDescripcion(rs.getString("fotoDescripcion"));
            
            listaTarjetas.add(transporte);
        }
        return listaTarjetas;
    }

    public List<String> listaTipos() throws SQLException {
        List<String> listaTipos = null;
        String consulta = "SELECT Distinct Clase FROM transporte;";
        PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();
        listaTipos = new ArrayList<>();
        while (rs.next()) {
            listaTipos.add(rs.getString("Clase"));
        }
        return listaTipos;
    }
}
