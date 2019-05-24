
package Vista.Administrador.Experiencia.InterfaceOrdenacion;

import Modelo.Experiencia;
import java.util.Comparator;

public class OrdenNombreE implements Comparator<Experiencia> {

    @Override
    public int compare(Experiencia ex1, Experiencia ex2) {
        int compara = 0;
        if(ex1.getNombre().compareTo(ex2.getNombre()) < 0){
            compara = -1;
        }
        else if(ex1.getNombre().compareTo(ex2.getNombre()) > 1){
            compara = 1;
        }       
        return compara;
    }
    
}
