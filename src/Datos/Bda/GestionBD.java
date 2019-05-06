/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos.Bda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author joser
 */
public class GestionBD {

    private GestionBD bda;
    private Connection conn;
    private String informacion;  //mensajes de resultado de conexion o desconexion
    private boolean conectado;
    private PreparedStatement ps;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getInformacion() {
        return informacion;
    }

    public boolean conectar() {     //Establecer conexion con la base de datos  lanza trow
        conectado = false;
        String nombre = "turismo"; //nombre de la base de datos
        String usuario = "root";   //Usuario de la base de datos, Distinto del usuario de la aplicacion
        String pwd = "root";     //Contraseña de la base de datos, Distinto de la aplicacion
        String url = "jdbc:mysql://localhost:3306/" + nombre + "?serverTimezone=UTC";
        try {
            conn = DriverManager.getConnection(url, usuario, pwd);

            if (conn != null) {
                //CONECTADOS CON LA BDA REFERENCIADA POR URL, CON EL USUARIO LOGIN Y SU PASSWORD
                informacion = "Conexion a base de datos " + url + " ... OK";
                conectado = true;

            }
            if (conn.isClosed()) {
                informacion = "Desconexion a base de datos " + url + " ... OK";
                conectado = false;

            }

        } catch (SQLException ex) {
            informacion = "Hubo un problema al intentar conectarse con la base de datos "
                    + nombre
                    + "\n URL utilizada "
                    + url + "\n ERROR: " + ex.getMessage();

        } catch (Exception ex) {
            throw ex;
        }
        return conectado;
    }

    public boolean desconectar() throws SQLException {
        conectado = true;
        conn.close();
        if (conn.isClosed()) {
            informacion = "Desconexion a base de datos " + " ...... OK";
            conectado = false;
        }
        return conectado;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setParametro(GestionBD bda) {
        this.bda = bda;

    }

    public GestionBD getBda() {
        return bda;
    }
}
