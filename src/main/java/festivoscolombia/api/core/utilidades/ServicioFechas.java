package festivoscolombia.api.core.utilidades;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ServicioFechas {

    public static LocalDate getInicioSemanaSanta(int año) {
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;

        int dia = 15 + dias;
        int mes = 3;
        if (dia > 31) {
            dia -= 31;
            mes = 4;
        }

        return LocalDate.of(año, mes, dia);
    }

    public static LocalDate agregarDias(LocalDate fecha, int dias) {
        return fecha.plusDays(dias);
    }

    public static LocalDate siguienteLunes(LocalDate fecha) {
        if (fecha.getDayOfWeek() != DayOfWeek.MONDAY) {
            int diasFaltantes = 8 - fecha.getDayOfWeek().getValue();
            return fecha.plusDays(diasFaltantes);
        }
        return fecha;
    }
}
