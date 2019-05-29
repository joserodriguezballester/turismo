/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Actividad;
import Modelo.Notificacion;
import Modelo.Subtipo;
import Modelo.Tipo;
import Modelo.Usuario;
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
public class actividadesDAO {

    private Notificacion not;
    private GestionBD gestion;
    private Connection conn;

    public actividadesDAO(GestionBD gestion) {
        this.gestion = gestion;
        this.conn = gestion.getConn();
    }

    public Usuario cargarUsuario(String nick) {
        Usuario usuario = null;
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
                usuario.setDni(rs.getString("dni"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPerfil(rs.getString("rol").toUpperCase());
            }

        } catch (SQLException ex) {
            System.out.println("error sql");
            not.error("SQL ERROR", "" + ex.getMessage() + " Hay un problema al cargar el Usuario");
        }
        return usuario;
    }

    //CREATE
    public boolean insertarActividad(Actividad act) throws SQLException {
        boolean insertado = false;

        if (gestion.getConn() != null) {
            String consulta = "INSERT INTO ACTIVIDADES (ID, NOMBRE, PRECIO, HORARIO, DESCRIPCION, URL, DIRECCION, TELEFONO, FOTO, IDSUBTIPO) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
            ps.setObject(1, null);
            ps.setString(2, act.getNombre());
            ps.setDouble(3, act.getPrecio());
            ps.setString(4, act.getHorario());
            ps.setString(5, act.getDescripcion());
            ps.setString(6, act.getUrl());
            ps.setString(7, act.getDireccion());
            ps.setString(8, act.getTelefono());
            ps.setString(9, act.getFoto());
            ps.setInt(10, act.getIdsubTipo());
            ps.executeUpdate();
            insertado = true;
        } else {
            System.out.println("Conexion insertar es null");

        }

        return insertado;
    }

    //READ
    public List<Actividad> listarActividad() throws SQLException {
        List<Actividad> listaTodasActividades = new ArrayList<>();
        if (gestion.getConn() != null) {
            String consulta = "SELECT id, nombre, precio, horario, descripcion, url, direccion, telefono, foto, idsubtipo FROM actividades ORDER BY nombre;";
            PreparedStatement ps = conn.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        }
        return listaTodasActividades;
    }

    public Actividad consultarActividad(int idActividad) throws SQLException {
        Actividad actividad = null;
        String consulta = "SELECT id, nombre, precio, horario, descripcion, url, direccion, telefono, foto, idSubtipo FROM actividades WHERE id = ? ORDER BY nombre;";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setInt(1, idActividad);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            actividad = new Actividad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getString("horario"),
                    rs.getString("descripcion"),
                    rs.getString("url"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("foto"),
                    rs.getInt("idSubtipo"));
        }
        return actividad;
    }

    //UPDATE
    public boolean modificarActividad(int id, String nombre, double precio, String horario, String descripcion, String url, String direccion, String telefono, String rutaFoto, int idSubtipo) throws SQLException {
        boolean modificado = false;
       
        if (gestion.getConn() != null) {

            String consulta = "UPDATE ACTIVIDADES SET NOMBRE = ?, PRECIO = ?, HORARIO = ?, DESCRIPCION = ?, URL = ?, DIRECCION = ?, TELEFONO = ?, FOTO = ?, IDSUBTIPO = ? WHERE ID = ? ORDER BY nombre;";
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, horario);
            ps.setString(4, descripcion);
            ps.setString(5, url);
            ps.setString(6, direccion);
//            System.out.println("ps"+ps);
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

        if (gestion.getConn() != null) {

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

        if (gestion.getConn() != null) {

            String sql = "SELECT id, nombre FROM tipos ORDER BY nombre;";
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

        if (gestion.getConn() != null) {

            String sql = "SELECT id, nombre, precio, horario, descripcion, url, direccion, telefono, foto, idsubtipo FROM actividades WHERE idsubtipo IN (SELECT id FROM subtipos WHERE idTipo = ?) ORDER BY nombre;";
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
                        rs.getInt("idsubtipo")));
            }
        }
        return listaActividades;
    }

    public List<Actividad> consultarActividadesPorTipoYSubTipo(Subtipo subtipo) throws SQLException {
        List<Actividad> listaActividades = new ArrayList<>();

        if (gestion.getConn() != null) {
            String sql = "SELECT * FROM actividades WHERE idsubtipo IN (SELECT id FROM subtipos WHERE idTipo = ? AND idsubtipo = ?);"; //falta la consulta
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, subtipo.getTipo().getId());
            ps.setInt(2, subtipo.getId());
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
                        rs.getInt("idsubtipo")));
            }
        }
        return listaActividades;
    }

    public List<Actividad> filtrarActividades(String busqueda, double precioMin, double precioMax, Tipo tipo, Subtipo subtipo) throws SQLException {
        String textoTipo, textoSubtipo;
        if (tipo == null) {
            textoTipo = null;
        } else {
            textoTipo = tipo.getNombre();
        }
        if (subtipo == null) {
            textoSubtipo = null;
        } else {
            textoSubtipo = subtipo.getNombre();
        }

        List<Actividad> listaActividades = new ArrayList<>();
        String sql = "CALL filtrarActividades(?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, busqueda);
        ps.setDouble(2, precioMin);
        ps.setDouble(3, precioMax);
        ps.setString(4, textoTipo);
        ps.setString(5, textoSubtipo);
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
                    rs.getInt("idsubtipo")));
        }
        return listaActividades;
    }

}
