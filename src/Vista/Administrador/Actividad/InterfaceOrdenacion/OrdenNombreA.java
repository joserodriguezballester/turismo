
package Vista.Administrador.Actividad.InterfaceOrdenacion;

import Modelo.Actividad;
import java.util.Comparator;

public class OrdenNombreA implements Comparator<Actividad>{

    @Override
    public int compare(Actividad ac1, Actividad ac2) {
        int compara = 0;
        if(ac1.getNombre().compareTo(ac2.getNombre()) < 0){
            compara = -1;
        }
        else if(ac1.getNombre().compareTo(ac2.getNombre()) > 1){
            compara = 1;
        }        
        return compara;
    }
    
}
