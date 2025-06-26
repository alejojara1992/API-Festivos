package festivoscolombia.api.aplicacion.servicios;

import festivoscolombia.api.core.dominio.dtos.FestivoDTO;
import festivoscolombia.api.core.interfaces.servicios.IFestivoServicio;
import festivoscolombia.api.core.utilidades.ServicioFechas;
import festivoscolombia.api.infraestructura.repositorios.IFestivoRepositorio;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FestivoServicio implements IFestivoServicio {

    private final IFestivoRepositorio festivoRepositorio;

    public FestivoServicio(IFestivoRepositorio festivoRepositorio) {
        this.festivoRepositorio = festivoRepositorio;
    }

    @Override
    public List<FestivoDTO> obtenerFestivosPorAnio(int anio) {
        LocalDate pascua = ServicioFechas.agregarDias(
                ServicioFechas.getInicioSemanaSanta(anio), 7);

        return festivoRepositorio.findAll().stream()
                .map(festivo -> {
                    try {
                        LocalDate fecha;

                        switch (festivo.getTipo().getId()) {
                            case 1: // Fijo
                                fecha = LocalDate.of(anio, festivo.getMes(), festivo.getDia());
                                break;

                            case 2: // Ley Puente Festivo
                                fecha = ServicioFechas.siguienteLunes(
                                        LocalDate.of(anio, festivo.getMes(), festivo.getDia()));
                                break;

                            case 3: // Se calcula con la Pascua (sin mover la fecha)
                                fecha = ServicioFechas.agregarDias(pascua, festivo.getDiasPascua());
                                break;

                            case 4: // Se calcula con la Pascua (se mueve a lunes)
                                fecha = ServicioFechas.siguienteLunes(
                                        ServicioFechas.agregarDias(pascua, festivo.getDiasPascua()));
                                break;

                            default:
                                return null;
                        }

                        return new FestivoDTO(festivo.getNombre(), fecha, festivo.getTipo().getTipo());

                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }
}
