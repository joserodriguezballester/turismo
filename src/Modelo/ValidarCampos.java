
package Modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
        public boolean isURL(String url) {
            boolean ok = false;
            try {
               (new java.net.URL(url)).openStream().close();
                ok = true;
            } catch (Exception ex) { }
            return ok;
        }
    
    public double validarNumDecimal(String cadena){
        String aux = cadena;
        
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

        cadena.matches("[0-9]*");      
           
        cadena = cadena.replaceAll("[^0-9]", "");
           
        if(cadena.equals("")){
            cadena = "0";
        }
       
        return Integer.parseInt(cadena);
    }
     
    public boolean validarFoto(String cadena) {
        boolean ok = false;
        
        if(cadena.matches("([a-zA-Z0-9\\s_\\\\.\\-:])+(.png|.jpg|.gif|.jpeg|.PNG|.JPG|.GIF|.JPEG)$")){
            ok = true;
        }
        
        return ok;
    }
     
     
    public boolean validarFechaLD(LocalDate date){
        boolean ok = false;
  
        if(date.isBefore(LocalDate.now())){
            ok = true;
        }
        return ok;
    }
    
    public boolean validarFechaLDT(LocalDateTime ldt){
        boolean ok = false;
        
        if(ldt.isBefore(LocalDateTime.now())){
            ok = true;
        }
        return ok;
    }
}
