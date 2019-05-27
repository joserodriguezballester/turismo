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
        if (telefono.matches("^[0-9]{2,3}-? ?[0-9]{6,7}|"
                + "([9|7|6]{1}[0-9]{1,2}){1}-?([0-9]{6,7}){1}|"
                + "([(]{1}[9|7|6]{1}[0-9]{1,2}[)]{1}){1}-?([0-9]{6,7}){1}$")) {
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
    
    public double validarNumDecimal(String cadena){
        String aux = cadena;
//        if(aux.matches("-[d]{1,7}[.]{1}[d]{1,}")){
//            Double.parseDouble(cadena = "-1"); // numero negativo
//        }
//        
//        if(!aux.matches("[0-9]{1,7}[.]{1}[0-9]{1,}")){
//            Double.parseDouble(cadena = "0.0");  // contiene letras
//            
//        }
//        
//        if(!cadena.matches("[0-9]{1,7}([.]{1})?([0-9]{1,})?") || cadena.equals("")){
//            cadena = "0.0";   // valida numero decimal o no, y cadena vacia
//        }
        
        if(!aux.matches("[0-9]{1,7}([.]{1})?([0-9]{1,})?")){

            if(!aux.contains(".")){
                aux = aux + ".00"; 
            }
            String uno = "[0-9]{1,7}";
            String tres = "[0-9]{1,}";

            String array1;
            String array3;

            String[] array;
            array = aux.split("\\.");

            array1 = array[0];
            array3 = array[1];

            array1.matches(uno);
            array1 = array1.replaceAll("[^0-9]", "");
            if(array1.equals("")){
                array1 = "0";
            }

            array1 = array1 + ".";

            array3.matches(tres);
            array3 = array3.replaceAll("[^0-9]", "");
            if(array3.equals("")){
                array3 = "0";
            }
            cadena = array1 + array3;
        }  // valida si es número decimal de 7 cifras, sino tiene punto, se lo 
        //pone, si tiene texto lo elimina, devuelve un número decimal.
        // No controla si hay dos puntos o más, falta implementar eso
        
        return Double.parseDouble(cadena);
    }
    
     public int validarNumEntero(String cadena) {
//        String string = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        cadena.matches("[0-9]*");      
           
        cadena = cadena.replaceAll("[^0-9]", "");
           
        if(cadena.equals("")){
            cadena = "0";
        }
       
        return Integer.parseInt(cadena);
    }

}
