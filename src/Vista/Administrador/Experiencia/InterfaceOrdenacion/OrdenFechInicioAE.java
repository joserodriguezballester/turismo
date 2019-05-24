
package Vista.Administrador.Experiencia.InterfaceOrdenacion;

import Modelo.ActividadExperiencia;
import java.util.Comparator;

public class OrdenFechInicioAE implements Comparator<ActividadExperiencia>{

    @Override
    public int compare(ActividadExperiencia ae1, ActividadExperiencia ae2) {
        int compara = 0;
        if(ae1.getFechaInicio().isBefore(ae2.getFechaInicio())){
            compara = -1;
        }
        else if(ae1.getFechaInicio().isAfter(ae2.getFechaInicio())){
            compara = 1;
        }
        else if(ae1.getFechaInicio().isEqual(ae2.getFechaInicio())){
            compara = 0;
        }
        return compara;
    }
    
}
