/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author joser
 */
public class ValidarCampos {

    public ValidarCampos() {
    }
     public boolean comprobardni(String dni) {
        boolean dniCorrecto;
        if (dni.matches("^\\d?\\d{7}(-|\\s)?[A-Za-z]$") || dni.equals("")) {
            dniCorrecto = true;
        } else {
            dniCorrecto = false;
        }
        return dniCorrecto;
    }
    
       public boolean comprobarTelefono(String telefono) {
        boolean tlfCorrecto;
        if (telefono.matches("^[0-9]{2,3}-? ?[0-9]{6,7}$")) {
            tlfCorrecto = true;
        } else {
            tlfCorrecto = false;
        }
        return tlfCorrecto;

    }
    public boolean comprobarEmail(String email) {
        boolean emailCorrecto;
        if (email.matches("^[A-Za-z0-9\\-\\.]+@[A-Za-z0-9\\-\\.]+\\.[A-Za-z]{2,}$")) {
            emailCorrecto = true;
        } else {
            emailCorrecto = false;
        }
        return emailCorrecto;
    }
    
    public boolean comprobarUrl(String url){
        boolean ok = false;
        if(!url.matches("[:]?[http://www.|https://www.]{1}[a-z_0-9/.-$%&]*")){
            ok = true;
        }
        return ok;
    }

}
