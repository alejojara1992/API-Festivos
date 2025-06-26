package festivoscolombia.api.presentacion.controladores;

import festivoscolombia.api.core.interfaces.servicios.IFestivoServicio;
import festivoscolombia.api.core.dominio.dtos.FestivoDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
                            Map.of("Es festivo", true, "fecha", f.getFecha(), "tipo", f.getTipo(), "nombre", f.getNombre())))
                    .orElseGet(() -> ResponseEntity.ok(
                            Map.of("mensaje", "No es festivo", "Es festivo", false)));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("Error en la fecha ingresada", e.getMessage()));
        }
    }
}
