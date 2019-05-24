
package Vista.Administrador.Experiencia.InterfaceOrdenacion;

import Modelo.Experiencia;
import java.util.Comparator;

public class OrdenFechaE implements Comparator<Experiencia> {

    @Override
    public int compare(Experiencia fe1, Experiencia fe2) {
        int compara = 0;
        if(fe1.getFechaTopeValidez().isBefore(fe2.getFechaTopeValidez())){
            compara = -1;
        }
        else if(fe1.getFechaTopeValidez().isAfter(fe2.getFechaTopeValidez())){
            compara = 1;
        }
        else if(fe1.getFechaTopeValidez().isEqual(fe2.getFechaTopeValidez())){
            compara = 0;
        }                
        return compara;
    }
    
}
