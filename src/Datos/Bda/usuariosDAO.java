/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class usuariosDAO {

    private Connection conn;
    private GestionBD gestion;

    public usuariosDAO(GestionBD gestion) {
        this.gestion = gestion;
    }

    //CREATE PASANDO PARAMETROS
    public boolean insertarUsuario(String DNI, String nombre, String apellidos, String rol, String contraseña, String direccion, String telefono, String email) {

        boolean insertado = false;
        String consulta = "INSERT INTO USUARIOS (DNI, NOMBRE, APELLIDOS, ROL, CONTRASEÑA, DIRECCION, TELEFONO, EMAIL) VALUES(?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, DNI);
            ps.setString(2, nombre);
            ps.setString(3, apellidos);
            ps.setString(4, rol);
            ps.setString(5, contraseña);
            ps.setString(6, direccion);
            ps.setString(7, telefono);
            ps.setString(8, email);
            ps.executeUpdate();
            insertado = true;
        } catch (SQLException e) {
            //MENSAJE DE ERROR
        }
        return insertado;
    }

    //CREATE PASANDO USUARIO
    public boolean insertarUsuario(Usuario usuario) {
        //instruccion valida en sql
        //  insert into usuarios (nick,contraseña,fecNac,nombre,apellidos,dni,rol,telefono,direccion,email) 
//values('juanito','khjsd$kjshd','20170314','juan','Perez Garcia','12345678a',default,'963838101','Marie curie, 4-5 mislata','joserodriguezballester@gmail.com');
        boolean insertado = false;
        String consulta = "INSERT INTO USUARIOS (nick,contraseña,fecNac,nombre,apellidos,dni,telefono,direccion,email)"
                + " VALUES(?, ?, ?, ?, ?,?,?,?,?);";
        try {
//            PreparedStatement ps = conn.prepareStatement(consulta);
            PreparedStatement ps = gestion.getConn().prepareStatement(consulta);
            ps.setString(1, usuario.getNick());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getFecNac().toString());
            ps.setString(4, usuario.getNombre());
            ps.setString(5, usuario.getApellidos());
            ps.setString(6, usuario.getDNI());
            ps.setString(7, usuario.getTelefono());
            ps.setString(8, usuario.getDireccion());
            ps.setString(9, usuario.getEmail());
            ps.executeUpdate();
            insertado = true;
            System.out.println("lo he conseguido "+insertado);
        } catch (SQLException e) {
            //MENSAJE DE ERROR
        }
        return insertado;
    }

    //READ
    //UPDATE
    public boolean modificarUsuario(String DNI, String nombre, String apellidos, String rol, String contraseña, String direccion, String telefono, String email, int id) {
        boolean insertado = false;
        String consulta = "UPDATE USUARIOS SET DNI = ?, NOMBRE = ?, APELLIDOS = ?, ROL = ?, CONTRASEÑA = ?, DIRECCION = ?, TELEFONO = ?, EMAIL = ? WHERE ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, DNI);
            ps.setString(2, nombre);
            ps.setString(3, apellidos);
            ps.setString(4, rol);
            ps.setString(5, contraseña);
            ps.setString(6, direccion);
            ps.setString(7, telefono);
            ps.setString(8, email);
            ps.setInt(9, id);
            ps.executeUpdate();
            insertado = true;
        } catch (SQLException e) {
            //MENSAJE DE ERROR
        }
        return insertado;
    }

    //DELETE
    public boolean borrarUsuario(int idUsuario) {
        boolean borrado = false;

        String sql = "DELETE FROM USUARIOS WHERE id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            borrado = true;
        } catch (SQLException e) {
            //MENSAJE DE ERROR
        }

        return borrado;
    }
}
