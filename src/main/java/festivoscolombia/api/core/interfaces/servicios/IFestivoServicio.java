package festivoscolombia.api.core.interfaces.servicios;

import festivoscolombia.api.core.dominio.dtos.FestivoDTO;
import java.util.List;

public interface IFestivoServicio {

    List<FestivoDTO> obtenerFestivosPorAnio(int anio);
}
