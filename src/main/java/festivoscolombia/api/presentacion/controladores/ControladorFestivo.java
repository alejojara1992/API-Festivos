package festivoscolombia.api.presentacion.controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import festivoscolombia.api.core.dominio.dtos.FestivoDTO;
import festivoscolombia.api.core.interfaces.servicios.IFestivoServicio;

@RestController
@RequestMapping("/festivos")
public class ControladorFestivo {

    private final IFestivoServicio festivoServicio;

    public ControladorFestivo(IFestivoServicio festivoServicio) {
        this.festivoServicio = festivoServicio;
    }

    @GetMapping("/{anio}")
    public List<FestivoDTO> obtenerPorAnio(@PathVariable int anio) {
        return festivoServicio.obtenerFestivosPorAnio(anio);
    }

    @GetMapping("/{anio}/{mes}/{dia}")
    public ResponseEntity<?> verificarFestivo(
            @PathVariable int anio,
            @PathVariable int mes,
            @PathVariable int dia) {

        try {
            LocalDate fecha = LocalDate.of(anio, mes, dia);

            return festivoServicio.obtenerFestivosPorAnio(anio).stream()
                    .filter(f -> f.getFecha().equals(fecha))
                    .findFirst()
                    .<ResponseEntity<?>>map(f -> ResponseEntity.ok(
                    Map.of("Fecha", f.getFecha(), "Tipo de festivo: ", f.getTipo(), "Festivo: ", f.getNombre(), "Festivo: ", true)))
                    .orElseGet(() -> ResponseEntity.ok(
                    Map.of("La fecha ", fecha + " no es festivo", "Festivo: ", false)));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("Error en la fecha ingresada", e.getMessage()));
        }
    }
}
