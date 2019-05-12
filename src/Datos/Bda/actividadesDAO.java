/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Actividad;
import Modelo.Tipo;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class actividadesDAO {

    private GestionBD gestion;
    private Connection conn;
    private ResultSet rs;

    public actividadesDAO(GestionBD gestion) {
        System.out.println("gestion actividades DAO"+gestion);
        System.out.println(" 1"+ gestion.isConectado());
        this.gestion = gestion;
        System.out.println(" 2"+ this.gestion.isConectado());
    }
    
    public Usuario cargarUsuario(String nick)  {
        Usuario usuario=null ;
        try {
            usuario = new Usuario();
            java.sql.Date fechabda;
            String sql = "SELECT id,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email,rol FROM USUARIOS WHERE nick=?;";
            PreparedStatement ps = gestion.getConn().prepareStatement(sql);
            ps.setString(1, nick);
            ResultSet rs = ps.executeQuery();
            usuario.setNick(nick);
            while (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setPassword(rs.getString("contraseña"));
                
                fechabda = rs.getDate("fecNac");
                usuario.setFecNac(fechabda.toLocalDate());
                
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setDNI(rs.getString("dni"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPerfil(rs.getString("rol").toUpperCase());
            }
          
        } catch (SQLException ex) {
            System.out.println("error sql");
        }
         return usuario;
    }
    //CREATE
    public boolean insertarActividad(int id,String nombre, double precio, String horario, String descripcion, String url, String direccion, String telefono, String rutaFoto, int subtipo) throws SQLException {
        boolean insertado = false;
        if(gestion.getConn() != null){
            String consulta = "INSERT INTO ACTIVIDADES (ID, NOMBRE, PRECIO, HORARIO, DESCRIPCION, URL, DIRECCION, TELEFONO, FOTO, IDSUBTIPO) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setDouble(3, precio);
            ps.setString(4, horario);
            ps.setString(5, descripcion);
            ps.setString(6, url);
            ps.setString(7, direccion);
            ps.setString(8, telefono);
            ps.setString(9, rutaFoto);
            ps.setInt(10, subtipo);
            ps.executeUpdate();
            insertado = true;
        }
        else{
            System.out.println("Conexion insertar es null");
        }
        return insertado;
    }
    
    //READ
    public List<Actividad> listarActividad() throws SQLException {
        List<Actividad> listaTodasActividades = new ArrayList<>();
        System.out.println("ESTOY FUERA DE LISTAR ACTIVIDAD");
        System.out.println("Hay conexion: " + gestion.isConectado()+"gestion "+gestion);
        if(gestion.getConn() != null){
            System.out.println("ESTOY EN LISTAR ACTIVIDAD ,gestion no nula");
            String consulta = "SELECT id, nombre, precio, horario, descripcion, url, direccion, telefono, foto, idsubtipo FROM actividades;";
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
            rs = ps.executeQuery();
            while(rs.next()){
                listaTodasActividades.add(new Actividad(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getString("horario"),
                rs.getString("descripcion"),
                rs.getString("url"),
                rs.getString("direccion"),
                rs.getString("telefono"),
                rs.getString("foto"),
                rs.getInt("idsubtipo")));
                             
            }
        }else{
            System.out.println(" ESTOY EN LISTAR ACTIVIDAD ,gestion NULA");
        }
        
        return listaTodasActividades;
    }

    //UPDATE
    public boolean modificarActividad(int id, String nombre, double precio, String horario, String descripcion, String url, String direccion, String telefono, String rutaFoto, int idSubtipo) throws SQLException {
        boolean modificado = false;
        if(gestion.getConn() != null){
            String consulta = "UPDATE ACTIVIDADES SET NOMBRE = ?, PRECIO = ?, HORARIO = ?, DESCRIPCION = ?, URL = ?, DIRECCION = ?, TELEFONO = ?, FOTO = ?, IDSUBTIPO = ? WHERE ID = ?;";
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
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
        }
        return modificado;
    }

    //DELETE
    public boolean borrarActividad(int idActividad) throws SQLException {
        boolean borrado = false;
        if(gestion.getConn() != null){
            String sql = "DELETE FROM ACTIVIDADES WHERE id = ?;";
            PreparedStatement ps = gestion.getConn().prepareStatement(sql);
            ps.setInt(1, idActividad);
            ps.executeUpdate();
            borrado = true;
        }
        return borrado;
    }

    public List<Tipo> consultarTipoActividades() throws SQLException {
        List<Tipo> listaTiposActividades = new ArrayList<>();
        if(gestion.getConn() != null){
            System.out.println("Estoy dentro de consultarTipoActividades");
            String sql = "SELECT id, nombre FROM tipos;";
            PreparedStatement ps = gestion.getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaTiposActividades.add(new Tipo(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return listaTiposActividades;
    }

    public List<Actividad> consultarActividadesPorTipo(Tipo tipo) throws SQLException {
        List<Actividad> listaActividades = new ArrayList<>();
        if(gestion.getConn() != null){
            String sql = "SELECT id, nombre, precio, horario, descripcion, url, direccion, telefono, foto, idsubtipo FROM actividades WHERE idsubtipo IN (SELECT id FROM subtipos WHERE idTipo = ?);";
            PreparedStatement ps = gestion.getConn().prepareStatement(sql);
            ps.setInt(1, tipo.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaActividades.add(new Actividad(
                rs.getInt("id"), 
                rs.getString("nombre"), 
                rs.getDouble("precio"),
                rs.getString("horario"), 
                rs.getString("descripcion"), 
                rs.getString("url"), 
                rs.getString("direccion"),
                rs.getString("telefono"), 
                rs.getString("foto"), 
                rs.getInt("idsubtipo"))); //FALTAN LOS PARAMETROS
            }
        }
        return listaActividades;
    }
    
}
