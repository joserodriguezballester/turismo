/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Usuario;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joser
 */
public class TransportesDAO {
    
     private final GestionBD gestion;

    public TransportesDAO(GestionBD gestion) {
        this.gestion = gestion;
    }

       public List<Usuario> listarTarjetas() throws SQLException {    
        List<Usuario> listaUsuarios = null;
        if (gestion.getConn() != null) {       
            String consulta = "SELECT id,nombre,duracion,precio,Descripcion,foto,fotoDescripcion,Clase FROM transporte;";
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();       
                   
            listaUsuarios = new ArrayList<>();
            while (rs.next()) {
                TransportesAdmin transportesAdmin = new TransportesAdmin();
                listaUsuarios.add(usuario);
                usuario.setId(rs.getInt("id"));
                usuario.setPassword(rs.getString("contraseña"));
                usuario.setNick(rs.getString("nick"));
                Date fechabda = rs.getDate("fecNac");
                usuario.setFecNac(fechabda.toLocalDate());
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setDni(rs.getString("dni"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPerfil(rs.getString("rol").toUpperCase());
            }  
         return listaUsuarios;
            
        }
////id int(11) AI PK 
////nombre varchar(45) 
////duracion int(5) 
////precio decimal(6,2) 
////Descripcion longtext 
////foto varchar(45) 
////fotoDescripcion varchar(45) 
////Clase varchar(45)
////        return listaUsuarios;
//    }
//
//   
//
//    private List<Usuario> darValorRs(ResultSet rs) throws SQLException {
//        List<Usuario> listaUsuarios = null;   
//            listaUsuarios = new ArrayList<>();
//            while (rs.next()) {
//                Usuario usuario = new Usuario();
//                listaUsuarios.add(usuario);
//                usuario.setId(rs.getInt("id"));
//                usuario.setPassword(rs.getString("contraseña"));
//                usuario.setNick(rs.getString("nick"));
//                Date fechabda = rs.getDate("fecNac");
//                usuario.setFecNac(fechabda.toLocalDate());
//                usuario.setNombre(rs.getString("nombre"));
//                usuario.setApellidos(rs.getString("apellidos"));
//                usuario.setDni(rs.getString("dni"));
//                usuario.setTelefono(rs.getString("telefono"));
//                usuario.setDireccion(rs.getString("direccion"));
//                usuario.setEmail(rs.getString("email"));
//                usuario.setPerfil(rs.getString("rol").toUpperCase());
//            }  
//         return listaUsuarios;
//    }
//    

