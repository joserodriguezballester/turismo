
package Vista.Administrador.Experiencia.InterfaceOrdenacion;

import Modelo.ActividadExperiencia;
import java.util.Comparator;

public class OrdenPrecioAE implements Comparator<ActividadExperiencia>{

    @Override
    public int compare(ActividadExperiencia ae1, ActividadExperiencia ae2) {
        int compara = 0;
        if(ae1.getPrecio() < ae2.getPrecio()){
            compara = -1;
        }
        else if(ae1.getPrecio() > ae2.getPrecio()){
            compara = 1;
        }       
        return compara;
    }
    
}
