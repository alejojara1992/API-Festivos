package festivoscolombia.api.infraestructura.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import festivoscolombia.api.core.dominio.entidades.Festivo;

public interface IFestivoRepositorio extends JpaRepository<Festivo, Integer> {

    @Query("SELECT f FROM Festivo f WHERE f.nombre LIKE CONCAT('%', :dato, '%')")
    List<Festivo> buscar(String dato);

}
