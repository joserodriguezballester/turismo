
package Vista.Administrador.Actividad.InterfaceOrdenacion;

import Modelo.Actividad;
import java.util.Comparator;

public class OrdenPrecioA implements Comparator<Actividad> {

    @Override
    public int compare(Actividad ac1, Actividad ac2) {
        int compara = 0;
        if(ac1.getPrecio() < ac2.getPrecio()){
            compara = -1;
        }
        else if(ac1.getPrecio() > ac2.getPrecio()){
            compara = 1;
        }       
        return compara;
    }
    
}
